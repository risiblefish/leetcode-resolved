#!/usr/bin/env node
/**
 * 建题脚手架。
 *
 * 用法：
 *   npm run new -- <题号> <slug> <难度> [patterns]
 * 例：
 *   npm run new -- 1 two-sum Easy hashmap,array
 *   npm run new -- 42 trapping-rain-water Hard two-pointers,stack
 */
import { mkdirSync, writeFileSync, existsSync, readFileSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const root = join(__dirname, "..");

const [idArg, slugArg, difficultyArg, patternsArg] = process.argv.slice(2);

if (!idArg || !slugArg) {
  console.error("用法: npm run new -- <题号> <slug> <Easy|Medium|Hard> [patterns]");
  console.error("例:   npm run new -- 1 two-sum Easy hashmap,array");
  process.exit(1);
}

const id = Number.parseInt(idArg, 10);
if (Number.isNaN(id)) {
  console.error(`题号必须是数字，收到: ${idArg}`);
  process.exit(1);
}

const slug = slugArg
  .toLowerCase()
  .trim()
  .replace(/[^a-z0-9]+/g, "-")
  .replace(/^-+|-+$/g, "");

const difficulty = normalizeDifficulty(difficultyArg ?? "Easy");
const patterns = (patternsArg ?? "")
  .split(",")
  .map((p) => p.trim())
  .filter(Boolean);

const dirName = `${String(id).padStart(4, "0")}-${slug}`;
const dir = join(root, "problems", dirName);

if (existsSync(dir)) {
  console.error(`已存在: problems/${dirName}`);
  process.exit(1);
}
mkdirSync(dir, { recursive: true });

// 标题以真实题库为准（中文优先），搜不到再退回 slug
const info = lookupProblem(id);
const title = info?.titleCn || info?.title || slugToTitle(slug);
const titleEn = info?.title || slugToTitle(slug);
const url = `https://leetcode.cn/problems/${slug}/`;

writeFileSync(join(dir, "solution.ts"), solutionTemplate(title, titleEn, url));
writeFileSync(join(dir, "solution.test.ts"), testTemplate(title));
writeFileSync(join(dir, "notes.md"), notesTemplate(title, titleEn, url, id, difficulty, patterns));
writeFileSync(
  join(dir, "meta.json"),
  `${JSON.stringify(
    {
      id,
      slug,
      title,
      titleEn,
      url,
      difficulty,
      patterns,
      source: "new",
      status: "todo",
      solvedOn: null,
      firstTry: false,
      timeSpentMin: 0,
      complexity: "",
    },
    null,
    2,
  )}\n`,
);

console.log(`✅ 已创建 problems/${dirName}`);
console.log("下一步:");
console.log("  1. 先在 notes.md 用自己的话写思路（先写再做）");
console.log("  2. 写 solution.ts，然后 npm test");
console.log(
  "  3. 全绿后 npm run solve -- " + id + " （会自动 git commit，复习时间线据此计算）",
);

function normalizeDifficulty(value) {
  const v = value.trim().toLowerCase();
  if (v === "easy") return "Easy";
  if (v === "medium" || v === "mid") return "Medium";
  if (v === "hard") return "Hard";
  console.warn(`未知难度 "${value}"，按 Easy 处理`);
  return "Easy";
}

function lookupProblem(id) {
  const bankPath = join(root, "data", "problems.json");
  if (!existsSync(bankPath)) return null;
  try {
    return JSON.parse(readFileSync(bankPath, "utf8")).find((p) => p.id === id) ?? null;
  } catch {
    return null;
  }
}

function lookupPattern(id) {
  const p = join(root, "data", "syllabus.json");
  if (!existsSync(p)) return null;
  try {
    return JSON.parse(readFileSync(p, "utf8")).patterns.find((x) => x.id === id) ?? null;
  } catch {
    return null;
  }
}

function slugToTitle(s) {
  return s
    .split("-")
    .map((w) => (w ? w[0].toUpperCase() + w.slice(1) : w))
    .join(" ");
}

function solutionTemplate(title, titleEn, url) {
  return `/**
 * LeetCode ${String(id).padStart(4, "0")}. ${title}${titleEn && titleEn !== title ? ` / ${titleEn}` : ""}
 * ${url}
 *
 * 思路（先写再做）：
 *   1. 
 *
 * 复杂度：时间 O(?) / 空间 O(?)
 */

function solve(): unknown {
  throw new Error("TODO: 先写思路，再写实现");
}

export default solve;
`;
}

function testTemplate(title) {
  return `import { describe, it } from "vitest";
import solve from "./solution";

describe("${title}", () => {
  // 写完实现后，把 todo 换成真正的断言
  // 例: expect(solve([2, 7, 11, 15], 9)).toEqual([0, 1]);
  it.todo("示例 1");
  it.todo("示例 2");
});
`;
}

function notesTemplate(title, titleEn, url, id, difficulty, patterns) {
  const pat = patterns.map(lookupPattern).find(Boolean);
  const patId = pat?.id ?? patterns[0] ?? "";
  const pr = pat?.primer ?? {};
  const bullets = (arr) => (arr?.length ? arr.map((x) => `  - ${x}`).join("\n") : "  - ");
  return `# ${String(id).padStart(4, "0")} ${title}${titleEn && titleEn !== title ? ` / ${titleEn}` : ""}

${url}  ·  难度：${difficulty}  ·  pattern：${patId}

> 本文档是该份的**学习记录与复习参考标准**。做前写「思路」，卡住写「卡点」，做完让 AI 复盘并回填。

## 这份要学 / 复习什么（薄预习）

> 来自预习卡：\`npm run learn -- ${patId}\`

- **核心思想**：${pr.core ?? ""}
- **识别信号**（看到这些条件就该想到该套路）：
${bullets(pat?.signals)}
- **易错 / 边界**：
${bullets(pr.pitfalls)}

## 目标（Definition of Done）

- [ ] \`npm test\` 全绿：示例 + ≥1 边界 + ≥1 反例
- [ ] \`npm run typecheck\` 无错
- [ ] LeetCode 提交 Accepted
- [ ] 本文档「思路 / 结果 / 复盘」填完
- [ ] \`npm run solve -- ${id} [--first] --min <耗时>\`（自动 commit，计入进度与复习）

## 我的思路（先写再做，哪怕写错）



## 卡点（卡在哪 / 看了什么提示）



## 提交结果

- 是否一次过：
- 耗时（分钟）：
- 复杂度：时间 O(?) / 空间 O(?)

## AI 复盘（AC 之后也值得看）

<!-- 做完把解法交给 AI，让它指出：表达是否清晰、边界是否遗漏、复杂度能否更优、有没有更自然的写法 -->



## 可改进点 / 标准要点

<!-- 下次复习对照这里：这题真正要练的是什么、正确做法长什么样、我当时差点在哪翻车 -->



## 复习记录

| 日期 | 第几次 | 是否闭卷写出 | 备注 |
|------|--------|-------------|------|
|      | 1      |             |      |
`;
}