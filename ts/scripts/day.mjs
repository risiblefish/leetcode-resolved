#!/usr/bin/env node
/**
 * 生成「今日任务文档」`ts/docs/daily/<YYYY-MM-DD>.md` —— 每次新会话的起点。
 *
 * 文档包含：今日主题 / 今日讲解 / 要做的题 / 练习反馈(留白) / 今日复习 / 复盘与计划调整。
 * 脚本只生成**骨架 + 数据**；「今日主题」「讲解」「识别信号」由 AI 读预习卡后补全。
 * 练完把反馈回填留白，AI 再据今日情况更新 ROADMAP / STATE。
 *
 * 用法：
 *   npm run day                    # 默认 plan.dailyMin 道新题 + 到期复习
 *   npm run day -- --units 3       # 今天想多做
 *   npm run day -- --pattern dp    # 今天专攻某类型
 *   npm run day -- --force         # 覆盖已存在的今日文档
 */
import { readFileSync, writeFileSync, existsSync, mkdirSync } from "node:fs";
import { join } from "node:path";
import { localDate } from "./lib.mjs";
import {
  loadState,
  buildQueues,
  unsolvedOf,
  pickCurrentPattern,
  isDrillDone,
  dataDir,
  tsRoot,
} from "./state.mjs";

const argv = process.argv.slice(2);
const unitsFlag = numberArg("--units");
const patternArg = stringArg("--pattern");
const force = argv.includes("--force");
const today = localDate();

const syllabus = JSON.parse(readFileSync(join(dataDir, "syllabus.json"), "utf8"));
const plan = JSON.parse(readFileSync(join(dataDir, "plan.json"), "utf8"));
const bank = new Map(JSON.parse(readFileSync(join(dataDir, "problems.json"), "utf8")).map((p) => [p.id, p]));
const drillById = new Map((syllabus.drills ?? []).map((d) => [d.id, d]));

const { solvedIds, manualDone, due, masteredCount } = loadState();
const queues = buildQueues(plan);
const currentPattern = pickCurrentPattern(plan, queues, solvedIds, patternArg);
const units = Math.max(1, unitsFlag ?? plan.dailyMin);
const pool = unsolvedOf(queues, currentPattern.id, solvedIds);
const selected = pool.slice(0, units);
const pendingDrills = plan.drills.filter((id) => !isDrillDone(manualDone, id));

const dir = join(tsRoot, "docs", "daily");
mkdirSync(dir, { recursive: true });
const path = join(dir, `${today}.md`);

if (existsSync(path) && !force) {
  console.log(`· 今日文档已存在：${rel(path)}`);
  console.log("  AI 直接在上面补全 / 回填即可；要重建加 --force（会覆盖已写内容）。");
} else {
  writeFileSync(path, render());
  console.log(`✅ 已生成：${rel(path)}`);
}

console.log("");
console.log(`  今日类型：${currentPattern.name}（${currentPattern.id}）`);
console.log(`  新题 ${selected.length} 道 · 到期复习 ${due.length} 道 · 未完成手写热身 ${pendingDrills.length} 个`);
console.log("  下一步：AI 读 ROADMAP / STATE / 最近 daily，补全「今日主题」「讲解」「识别信号」。");

function render() {
  const L = [];
  const weekday = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"][new Date(`${today}T00:00:00`).getDay()];
  L.push(`# 今日任务 · ${today} ${weekday}`);
  L.push("");
  L.push(`> 会话起点：\`npm run day\` 生成骨架；AI 补全「今日主题 / 讲解」，你练完回填反馈，AI 据今日情况更新 ROADMAP。`);
  L.push(`> 现状：累计 **${solvedIds.size} 道** · 已内化 ${masteredCount} · 当前类型 **${currentPattern.name}**`);
  L.push("");

  L.push("## 今日主题");
  L.push("");
  L.push("<!-- AI：一句话——今天练什么、为什么是它 -->");
  L.push("");

  L.push("## 今日学习内容（讲解）");
  L.push("");
  L.push(`<!-- AI：用「${currentPattern.id}」预习卡 + 题目背景，写 5–10 分钟能读完的讲解。`);
  L.push("     给：是什么 / 核心思想 / TS 模板骨架 / 易错边界。");
  L.push("     红线：读完立刻动代码；超过 20 分钟没写代码就跳过。 -->");
  L.push("");

  if (pendingDrills.length > 0) {
    L.push("## 可选热身（手写 drill，穿插做）");
    L.push("");
    for (const id of pendingDrills) {
      const d = drillById.get(id);
      L.push(`- [ ] ${d?.title ?? id} → \`npm run task -- ${id}\`，做完 \`npm run done -- ${id}\``);
    }
    L.push("");
  }

  L.push(`## 今日要做的题（新题 ${selected.length} 道）`);
  L.push("");
  if (selected.length === 0) {
    L.push("本类型已刷完，`npm run next -- --pattern <id>` 换个类型。");
    L.push("");
  }
  selected.forEach((x, i) => {
    const p = bank.get(x.id);
    const en = p.title !== p.titleCn ? ` / ${p.title}` : "";
    const tag = x.classic ? "必做" : "备选";
    L.push(`### ${i + 1}. #${p.id} ${p.titleCn}${en}（${p.difficulty} · ${tag} · ${currentPattern.name}）`);
    L.push("");
    L.push(`- 链接：https://leetcode.cn/problems/${p.slug}/`);
    L.push(`- 建目录：\`npm run new -- ${p.id} ${p.slug} ${p.difficulty} ${currentPattern.id}\``);
    L.push(`- 识别信号：<!-- AI 可补：看到什么关键词想到这个套路 -->`);
    L.push(`- 我的思路（先写再做）：`);
    L.push(`- 结果：☐ 卡住　☐ AC　☐ 看了题解`);
    L.push("");
  });

  L.push("## 练习过程 · 反馈与讨论（留白）");
  L.push("");
  L.push("<!-- 卡在哪 / 怎么讨论的 / 怎么解决；顺利就不必硬写。AI 在这里补全。 -->");
  L.push("");

  L.push("## 今日要复习的题");
  L.push("");
  if (due.length === 0) {
    L.push("今天没有到期复习。");
  } else {
    for (const d of due) {
      const en = "";
      L.push(`### #${d.id} ${d.title}${en}（第 ${d.stage} 次复习，到期 ${d.next}${d.overdue > 0 ? `，逾期 ${d.overdue} 天` : ""}）`);
      L.push("");
      L.push(`- 回刷后记录：\`npm run solve -- ${d.id} --min <耗时>\``);
      L.push(`- 闭卷重写结果（写不出再看旧代码）：`);
      L.push("");
    }
    L.push("> 复习前先看对应 `problems/<编号>-<slug>/notes.md` 的「可改进点 / 标准要点」。");
  }
  L.push("");

  L.push("## 复盘 & 对计划的调整");
  L.push("");
  L.push("<!-- 太轻松→加量/进阶；吃力→AI 主动问是否放慢加深（例：原计划 3 次过，现吃力→延长到 5 次、减新题、加复习）。");
  L.push("     确认后记在这里，并更新 STATE.md / ROADMAP.md 与该类型的推进节奏。 -->");
  L.push("");
  return L.join("\n");
}

function rel(p) {
  return p.slice(tsRoot.length + 1).replace(/\\/g, "/");
}
function stringArg(flag) {
  const i = argv.indexOf(flag);
  return i >= 0 ? argv[i + 1] : undefined;
}
function numberArg(flag) {
  const v = stringArg(flag);
  return v === undefined ? undefined : Number(v);
}