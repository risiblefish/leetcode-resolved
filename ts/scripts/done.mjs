#!/usr/bin/env node
/**
 * 手动勾掉「手写热身」或「类型预习卡」（这两类不产生题解，无法自动判断）。
 * 做题份由 `npm run solve` 自动计入，无需手动勾。
 *
 * 用法：
 *   npm run done -- drill-heap     # 勾掉手写堆
 *   npm run done -- array-hash     # 勾掉「数组与哈希」预习卡
 *   npm run learn                  # 查看类型 id
 */
import { readFileSync, writeFileSync, existsSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const dataDir = join(__dirname, "..", "data");
const syllabus = JSON.parse(readFileSync(join(dataDir, "syllabus.json"), "utf8"));
const progressPath = join(dataDir, "progress.json");

const arg = process.argv.slice(2).find((a) => !a.startsWith("--"));
if (!arg) {
  console.error("用法: npm run done -- <drill-id | pattern-id>");
  process.exit(1);
}

const clean = arg.replace(/^drill:/, "").replace(/^primer:/, "");
const isDrill = (syllabus.drills ?? []).some((d) => d.id === clean);
const isPattern = syllabus.patterns.some((p) => p.id === clean);

if (!isDrill && !isPattern) {
  console.error(`找不到: ${arg}`);
  console.error("手写 drill 用 drill-<名字>，类型用 npm run learn 里的 id");
  process.exit(1);
}

const key = isDrill ? `drill:${clean}` : `primer:${clean}`;
const progress = existsSync(progressPath) ? JSON.parse(readFileSync(progressPath, "utf8")) : { completed: [] };
const completed = new Set(progress.completed ?? []);
completed.add(key);
writeFileSync(progressPath, `${JSON.stringify({ completed: [...completed].sort() }, null, 2)}\n`);
console.log(`✅ 已勾掉 ${key}`);
console.log("   下一批：npm run next");