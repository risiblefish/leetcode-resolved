#!/usr/bin/env node
/**
 * 生成 data/plan.json —— **按类型分库，不设题目总数上限**。
 *
 * 结构：
 *   - drills:     L0 手写热身（有序，有限）
 *   - patterns[]: 每个类型一个「题库」
 *       · classics  策展必做题（由易到难）
 *       · extended  该类型的其余题（由易到难，数量不限）
 *
 * 没有硬性题量上限：你有精力就多刷，`npm run next` 只负责给出
 * 当前类型里「下一批按难度排好的未做题」。
 *
 * 用法：npm run plan
 */
import { readFileSync, writeFileSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const dataDir = join(__dirname, "..", "data");

const syllabus = JSON.parse(readFileSync(join(dataDir, "syllabus.json"), "utf8"));
const bank = JSON.parse(readFileSync(join(dataDir, "problems.json"), "utf8"));
const bankById = new Map(bank.map((p) => [p.id, p]));

const difficulties = { Easy: 0, Medium: 1, Hard: 2 };
const byDifficulty = (a, b) =>
  (difficulties[a.difficulty] ?? 1) - (difficulties[b.difficulty] ?? 1) ||
  b.solutions - a.solutions ||
  b.acRate - a.acRate;

const drills = [...(syllabus.drills ?? [])].sort((a, b) => (a.order ?? 99) - (b.order ?? 99)).map((d) => d.id);

const patterns = [];
let missing = 0;
for (const pat of syllabus.patterns) {
  const klassics = [];
  for (const id of pat.classics ?? []) {
    if (!bankById.has(id)) {
      missing++;
      continue;
    }
    klassics.push(id);
  }
  klassics.sort((a, b) => byDifficulty(bankById.get(a), bankById.get(b)));

  const tagSet = new Set(pat.poolTags ?? pat.tags ?? []);
  const klassicsSet = new Set(klassics);
  const extended = bank
    .filter((p) => (p.tags ?? []).some((t) => tagSet.has(t)))
    .filter((p) => !klassicsSet.has(p.id))
    .sort(byDifficulty)
    .map((p) => p.id);

  patterns.push({ id: pat.id, name: pat.name, classics: klassics, extended });
}

const plan = {
  version: 3,
  dailyMin: 2,
  note: "按类型分库，无题目上限。classics 必做（由易到难），extended 想刷多少刷多少。dailyMin 是保底题数（也是 npm run day 的默认新题数）。",
  drills,
  patterns,
};
writeFileSync(join(dataDir, "plan.json"), `${JSON.stringify(plan)}\n`);

console.log("✅ 已生成 data/plan.json（v3 · 按类型分库）");
console.log(`   手写热身 ${drills.length} 个`);
for (const p of patterns) {
  console.log(`   ${p.id.padEnd(16)} 必做 ${String(p.classics.length).padStart(2)} · 备选 ${p.extended.length}`);
}
if (missing > 0) console.log(`   ⚠️ 跳过 ${missing} 道不在题库的必做题`);