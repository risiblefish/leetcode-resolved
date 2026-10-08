#!/usr/bin/env node
/**
 * 回看某个 pattern 的预习卡。
 * 用法：
 *   npm run learn                # 列出所有 pattern
 *   npm run learn -- array-hash  # 打印该 pattern 的预习卡
 */
import { readFileSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const dataDir = join(__dirname, "..", "data");
const syllabus = JSON.parse(readFileSync(join(dataDir, "syllabus.json"), "utf8"));

const arg = process.argv.slice(2).find((a) => !a.startsWith("--"));

if (!arg) {
  console.log("可用 pattern（npm run learn -- <id>）：\n");
  for (const p of syllabus.patterns) console.log(`  ${p.id.padEnd(16)} ${p.name}`);
  console.log("\n手写 drills（npm run next 查看）：");
  for (const d of syllabus.drills ?? []) console.log(`  ${d.id.padEnd(20)} ${d.title}`);
  process.exit(0);
}

const pat = syllabus.patterns.find((p) => p.id === arg || p.name === arg);
if (!pat) {
  console.error(`找不到 pattern: ${arg}\n先跑 \`npm run learn\` 看列表。`);
  process.exit(1);
}

const pr = pat.primer ?? {};
const L = [];
L.push(`# 预习卡 · ${pat.name}  (\`${pat.id}\`)`);
L.push("");
L.push(`**它是什么**：${pr.what ?? ""}`);
L.push("");
L.push(`**核心思想**：${pr.core ?? ""}`);
L.push("");
L.push(`**考什么 / 学什么**：${pr.test ?? ""}`);
L.push("");
L.push("**TS 模板骨架**（半成品，不是答案）");
L.push("```ts");
L.push(pr.template ?? "// TODO");
L.push("```");
L.push("");
L.push("**常见变体**");
for (const x of pr.variants ?? []) L.push(`- ${x}`);
L.push("");
L.push("**易错 / 边界**");
for (const x of pr.pitfalls ?? []) L.push(`- ${x}`);
L.push("");
L.push("**识别信号**");
for (const x of pat.signals ?? []) L.push(`- ${x}`);
L.push("");
L.push("**必做题（经典）**：" + (pat.classics ?? []).map((n) => `#${n}`).join(" "));
L.push("");
L.push("> 看完**合上，凭记忆默写一遍模板**，再去做题。");
console.log(L.join("\n"));