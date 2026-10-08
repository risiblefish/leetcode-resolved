#!/usr/bin/env node
/**
 * 从 leetcode.cn GraphQL 同步完整题库与官方标签到 ts/data/。
 *
 * 真实数据，不依赖模型记忆；题目分类/难度/中文标题以本文件同步结果为准。
 * 用法：npm run sync
 */
import { mkdirSync, writeFileSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const dataDir = join(__dirname, "..", "data");

const ENDPOINT = "https://leetcode.cn/graphql/";
const LIMIT = 100;
const QUERY = `
query problemsetQuestionList($categorySlug: String, $limit: Int, $skip: Int, $filters: QuestionListFilterInput) {
  problemsetQuestionList(categorySlug: $categorySlug, limit: $limit, skip: $skip, filters: $filters) {
    total
    questions {
      frontendQuestionId
      titleSlug
      title
      titleCn
      difficulty
      paidOnly
      acRate
      solutionNum
      topicTags { slug name nameTranslated }
    }
  }
}`;

const DIFFICULTY = { EASY: "Easy", MEDIUM: "Medium", HARD: "Hard" };

async function fetchPage(skip) {
  const res = await fetch(ENDPOINT, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "User-Agent": "Mozilla/5.0",
    },
    body: JSON.stringify({
      query: QUERY,
      variables: { categorySlug: "", skip, limit: LIMIT, filters: {} },
    }),
  });
  if (!res.ok) throw new Error(`GraphQL HTTP ${res.status}`);
  const json = await res.json();
  if (json.errors) throw new Error(JSON.stringify(json.errors));
  return json.data.problemsetQuestionList;
}

async function main() {
  console.log("同步题库中…");
  const problems = [];
  const tags = new Map();
  let total = Infinity;
  let skip = 0;

  while (skip < total) {
    const page = await fetchPage(skip);
    total = page.total;
    for (const q of page.questions) {
      const id = q.frontendQuestionId;
      if (!/^\d+$/.test(id)) continue; // 跳过 LCP / 面试题 等非数字编号
      if (q.paidOnly) continue; // 只保留免费题
      const difficulty = DIFFICULTY[q.difficulty] ?? q.difficulty;
      problems.push({
        id: Number(id),
        slug: q.titleSlug,
        title: q.title,
        titleCn: q.titleCn,
        difficulty,
        acRate: Math.round((q.acRate ?? 0) * 1000) / 1000,
        solutions: q.solutionNum ?? 0,
        tags: (q.topicTags ?? []).map((t) => t.slug),
      });
      for (const t of q.topicTags ?? []) {
        if (!tags.has(t.slug)) {
          tags.set(t.slug, { slug: t.slug, name: t.name, nameCn: t.nameTranslated, problems: [] });
        }
        tags.get(t.slug).problems.push(Number(id));
      }
    }
    skip += LIMIT;
    process.stdout.write(`\r  已抓取 ${problems.length} 题 (skip=${skip}/${total})   `);
    await sleep(150);
  }

  problems.sort((a, b) => a.id - b.id);
  const tagList = [...tags.values()].sort((a, b) => b.problems.length - a.problems.length);
  for (const t of tagList) t.problems.sort((a, b) => a - b);

  mkdirSync(dataDir, { recursive: true });
  writeFileSync(join(dataDir, "problems.json"), `${JSON.stringify(problems, null, 0)}\n`);
  writeFileSync(join(dataDir, "tags.json"), `${JSON.stringify(tagList, null, 0)}\n`);
  writeFileSync(
    join(dataDir, "meta.json"),
    `${JSON.stringify(
      {
        source: ENDPOINT,
        syncedAt: new Date().toISOString(),
        freeProblems: problems.length,
        tags: tagList.length,
      },
      null,
      2,
    )}\n`,
  );

  console.log(`\n✅ 同步完成：${problems.length} 道免费题，${tagList.length} 个标签`);
  console.log("   ts/data/problems.json / tags.json / meta.json");
}

function sleep(ms) {
  return new Promise((r) => setTimeout(r, ms));
}

main().catch((err) => {
  console.error("\n❌ 同步失败:", err.message);
  process.exit(1);
});