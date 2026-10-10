#!/usr/bin/env node
/**
 * 生成 task 文档（手写 drill / 类型套路卡）。
 * 做题份的文档由 new.mjs 生成到 problems/<编号>-<slug>/notes.md。
 * 输出目录：docs/knowledge/algorithm/（知识库·算法侧）
 *
 * 用法：
 *   npm run task                 # 生成全部 drill + 预习卡文档
 *   npm run task -- drill-heap   # 只生成某个手写 drill
 *   npm run task -- dp           # 只生成某个类型的预习卡
 */
import { readFileSync, writeFileSync, existsSync, mkdirSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const __dirname = dirname(fileURLToPath(import.meta.url));
const tsRoot = join(__dirname, "..");
const docsDir = join(tsRoot, "docs", "knowledge", "algorithm");
const dataDir = join(tsRoot, "data");

const syllabus = JSON.parse(readFileSync(join(dataDir, "syllabus.json"), "utf8"));
const plan = JSON.parse(readFileSync(join(dataDir, "plan.json"), "utf8"));
const patternById = new Map(syllabus.patterns.map((p) => [p.id, p]));
const drillById = new Map((syllabus.drills ?? []).map((d) => [d.id, d]));
const drillIndex = new Map(plan.drills.map((id, i) => [id, i]));

mkdirSync(docsDir, { recursive: true });

const arg = process.argv.slice(2).find((a) => !a.startsWith("--"));

if (!arg) {
  for (const id of plan.drills) writeDrill(id);
  for (const pat of plan.patterns) writePattern(pat.id);
  process.exit(0);
}

if (arg.startsWith("drill:") || drillById.has(arg)) {
  writeDrill(arg.replace(/^drill:/, ""));
} else if (patternById.has(arg)) {
  writePattern(arg);
} else {
  console.error(`找不到: ${arg}`);
  console.error("可选：drill 的 id（npm run task 查看）或类型 id（npm run learn 查看）");
  process.exit(1);
}

function writeDrill(id) {
  const d = drillById.get(id);
  if (!d) return;
  const path = join(docsDir, `drill-${String((drillIndex.get(id) ?? 0) + 1).padStart(2, "0")}-${id.replace(/^drill-/, "")}.md`);
  if (existsSync(path)) {
    console.log(`· 已存在：${rel(path)}`);
    return;
  }
  writeFileSync(path, drillDoc(d));
  console.log(`✅ 已生成：${rel(path)}`);
}

function writePattern(id) {
  const pat = patternById.get(id);
  if (!pat) return;
  const path = join(docsDir, `pattern-${id}.md`);
  if (existsSync(path)) {
    console.log(`· 已存在：${rel(path)}`);
    return;
  }
  writeFileSync(path, patternDoc(pat));
  console.log(`✅ 已生成：${rel(path)}`);
}

function rel(p) {
  return p.slice(tsRoot.length + 1).replace(/\\/g, "/");
}

function drillDoc(d) {
  return `# 手写 drill · ${d.title}

> **知识块**：算法 · 手写基本功（drill，闭卷）　|　**归属 pattern**：${d.pattern}

## 要点

${(d.learn ?? []).map((x) => `- ${x}`).join("\n")}

## 自查（做完打勾）

${(d.checklist ?? []).map((x) => `- [ ] ${x}`).join("\n")}

## 我的实现 / 卡点（留白）

<!-- 写不出时先记下卡在哪：是思路、边界，还是 TS 语法？ -->

## AI 复盘（留白）

<!-- 写完后可贴给 AI 看：还有没有更自然的写法 / 边界遗漏 -->

## 复习记录

| 日期 | 第几次 | 是否闭卷写出 | 备注 |
|------|--------|-------------|------|
|      | 1      |             |      |
`;
}

function patternDoc(pat) {
  const pr = pat.primer ?? {};
  return `# 预习卡 · ${pat.name}

> **知识块**：算法 · 套路卡（pattern primer）　|　**pattern**：\`${pat.id}\`　|　回看：\`npm run learn -- ${pat.id}\`

## 它是什么

${pr.what ?? ""}

## 核心思想

${pr.core ?? ""}

## 考什么 / 学什么

${pr.test ?? ""}

## TS 模板骨架（半成品）

\`\`\`ts
${pr.template ?? "// TODO"}
\`\`\`

## 常见变体

${(pr.variants ?? []).map((x) => `- ${x}`).join("\n")}

## 易错 / 边界

${(pr.pitfalls ?? []).map((x) => `- ${x}`).join("\n")}

## 识别信号

${(pat.signals ?? []).map((x) => `- ${x}`).join("\n")}

## 必做题（经典）

${(pat.classics ?? []).map((n) => `#${n}`).join(" ")}

## 我的默写（合上资料后凭记忆写模板）

\`\`\`ts
// 在这里默写，不要看上面
\`\`\`

## 卡点 / 想问的问题（留白）

## AI 复盘 / 补充（留白）

## 复习记录

| 日期 | 第几次 | 能默写模板 | 备注 |
|------|--------|-----------|------|
|      | 1      |           |      |
`;
}