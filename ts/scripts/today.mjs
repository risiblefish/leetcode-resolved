#!/usr/bin/env node
/**
 * 给出「下一批题」——按类型分库、由易到难，不设上限。
 *
 * 同时是**欠账视图**（`AGENTS.md` §1.6）：必做进度 X/Y + 还欠的必做 + 到期复习 + 周日清账提醒。
 *
 * 用法：
 *   npm run next                       # 当前类型的下一批（默认 plan.dailyMin 道）
 *   npm run next -- --units 5          # 多来几道
 *   npm run next -- --quick|--normal|--sprint   # 1 / 2 / 3 道
 *   npm run next -- --pattern dp       # 指定类型
 *   npm run next -- --scaffold         # 为选中的题自动建目录/文档
 */
import { readFileSync, writeFileSync } from "node:fs";
import { join } from "node:path";
import { spawnSync } from "node:child_process";
import { localDate } from "./lib.mjs";
import {
  loadState,
  buildQueues,
  unsolvedOf,
  pickCurrentPattern,
  isDrillDone,
  isPrimerDone,
  dataDir,
  tsRoot,
} from "./state.mjs";

const argv = process.argv.slice(2);
const unitsFlag = numberArg("--units");
const patternArg = stringArg("--pattern");
const doScaffold = argv.includes("--scaffold");
const today = localDate();

const syllabus = JSON.parse(readFileSync(join(dataDir, "syllabus.json"), "utf8"));
const plan = JSON.parse(readFileSync(join(dataDir, "plan.json"), "utf8"));
const bank = new Map(JSON.parse(readFileSync(join(dataDir, "problems.json"), "utf8")).map((p) => [p.id, p]));
const drillById = new Map((syllabus.drills ?? []).map((d) => [d.id, d]));

const { solvedIds, manualDone, startedPatterns, metas, masteredCount, due } = loadState();
const queues = buildQueues(plan);
const currentPattern = pickCurrentPattern(plan, queues, solvedIds, patternArg);

const gearDefault = argv.includes("--sprint") ? 3 : argv.includes("--normal") ? 2 : argv.includes("--quick") ? 1 : plan.dailyMin;
const unitsToShow = Math.max(1, unitsFlag ?? gearDefault);

const pendingDrills = plan.drills.filter((id) => !isDrillDone(manualDone, id));
const pool = unsolvedOf(queues, currentPattern.id, solvedIds);
const selected = pool.slice(0, unitsToShow);
const preview = pool.slice(selected.length, selected.length + 4);
const classics = queues.get(currentPattern.id).filter((x) => x.classic);
const classicsSolved = classics.filter((x) => solvedIds.has(x.id)).length;
const classicsLeft = classics.filter((x) => !solvedIds.has(x.id));
const isSunday = new Date(`${today}T00:00:00`).getDay() === 0;

const md = `# 下一批题  ${today}\n\n${render()}`;
writeFileSync(join(tsRoot, "NEXT.md"), md);
process.stdout.write(md);

if (doScaffold) {
  console.log("\n── 自动生成文档 / 目录 ──────────────");
  for (const x of selected) {
    const p = bank.get(x.id);
    const r = spawnSync(
      process.execPath,
      [join(dataDir, "..", "scripts", "new.mjs"), String(p.id), p.slug, p.difficulty, currentPattern.id],
      { encoding: "utf8" },
    );
    process.stdout.write(r.stdout + r.stderr);
  }
}
console.log("\n已写入: ts/NEXT.md");

function render() {
  const L = [];
  L.push(`**累计题解 ${solvedIds.size} 道** · 已内化 ${masteredCount} · 当前类型 **${currentPattern.name}**`);
  L.push("");
  L.push(`> 按类型分库、由易到难，**题目数量没有上限**：保底 ${plan.dailyMin} 道，有精力照预览随意多做。`);
  L.push(`> 换类型：\`npm run next -- --pattern <id>\`；看全部类型：\`npm run dash\`。`);
  L.push("");

  // 欠账视图（AGENTS.md §1.6）：必做会欠账，拓展不会；周日必须清零。
  if (due.length > 0) {
    L.push(`## ⏰ 到期复习（${due.length} 道）`);
    for (const d of due) {
      const od = d.overdue > 0 ? ` · 逾期 ${d.overdue} 天` : "";
      L.push(
        `- [ ] **#${d.id} ${d.title}**（第 ${d.stage} 次，到期 ${d.next}${od}）→ \`npm run solve -- ${d.id} --min <耗时>\``,
      );
    }
    L.push("");
  }
  if (isSunday) {
    L.push(`> 🔔 **今天是周日 · 清账日**：先把「复习队列 + 必做剩余」清零；拓展题不参与清账。`);
    L.push("");
  }

  if (pendingDrills.length > 0) {
    L.push(`## 🔧 手写热身未完成（${pendingDrills.length} 个，建议穿插做）`);
    for (const id of pendingDrills) {
      const d = drillById.get(id);
      L.push(`- [ ] ${d?.title ?? id}  →  \`npm run task -- ${id}\` 建文档，做完 \`npm run done -- ${id}\``);
    }
    L.push("");
  }

  L.push(`## 📚 当前类型：${currentPattern.name}（\`${currentPattern.id}\`）`);
  L.push(`必做 **${classicsSolved}/${classics.length}** · 本类型已刷 **${queues.get(currentPattern.id).length - pool.length}** · 备选池 ${queues.get(currentPattern.id).length - classics.length} 道`);
  if (classicsLeft.length > 0) {
    L.push(
      `还欠的必做：${classicsLeft
        .map((x) => `#${x.id} ${bank.get(x.id)?.titleCn ?? ""}`)
        .join(" · ")}`,
    );
  } else {
    L.push("还欠的必做：无 ✅（下面是备选加练，多多益善）");
  }
  L.push(`预习卡：${isPrimerDone(manualDone, startedPatterns, currentPattern.id) ? "✅ 已看" : `⬜ 未看 → \`npm run learn -- ${currentPattern.id}\``}`);
  L.push("");

  if (pool.length === 0) {
    L.push("🎉 本类型已全部刷完！换个类型：`npm run next -- --pattern <id>`。");
  } else {
    if (pool.filter((x) => x.classic).length === 0) {
      L.push(`> ✅ 本类型必做已清，下面都是**备选加练**（由易到难）。`);
      L.push("");
    }
    L.push(`### 接下来 ${selected.length} 道（本类型 · 由易到难）`);
    for (const x of selected) L.push(problemLine(x, currentPattern.id));
    L.push("");
    if (preview.length > 0) {
      L.push("### 再往后（想做就继续）");
      for (const x of preview) L.push(previewLine(x, currentPattern.id));
      L.push("");
    }
  }

  L.push("## 完成一道后");
  L.push("");
  L.push("- 建目录 / 文档：`npm run next -- --scaffold` 或逐个 `npm run new -- <题号> <slug> <难度> <类型>`");
  L.push("- 写实现 → `npm test` → LeetCode Accepted");
  L.push("- 记录：`npm run solve -- <题号> [--first] --min <耗时>`（自动 commit，计入复习）");
  L.push("- 想多做：`npm run next -- --units 5`；换类型：`--pattern <id>`");
  return L.join("\n");
}

function problemLine(x, patId) {
  const p = bank.get(x.id);
  const en = p.title !== p.titleCn ? ` / ${p.title}` : "";
  const tag = x.classic ? "必做" : "备选";
  const L = [`- [ ] **#${p.id} ${p.titleCn}${en}**（${p.difficulty}）· ${tag}`];
  L.push(`      https://leetcode.cn/problems/${p.slug}/`);
  L.push(`      \`npm run new -- ${p.id} ${p.slug} ${p.difficulty} ${patId}\``);
  return L.join("\n");
}

function previewLine(x, patId) {
  const p = bank.get(x.id);
  const en = p.title !== p.titleCn ? ` / ${p.title}` : "";
  return `- \`#${p.id}\` ${p.titleCn}${en}（${p.difficulty}${x.classic ? " · 必做" : ""}）`;
}

function stringArg(flag) {
  const i = argv.indexOf(flag);
  return i >= 0 ? argv[i + 1] : undefined;
}
function numberArg(flag) {
  const v = stringArg(flag);
  return v === undefined ? undefined : Number(v);
}