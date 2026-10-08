#!/usr/bin/env node
/**
 * 扫描 problems 下每个题目的 meta.json + git 历史，生成看板 ts/PROGRESS.md。
 *
 * 复习时间线来自 git（solve(ts) / review(ts) 提交），Ebbinghaus 间隔 1/3/7/15/30/60 天。
 * 用法：npm run dash
 */
import { readdirSync, readFileSync, writeFileSync, existsSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";
import {
  getActivityDates,
  activityDatesFor,
  nextReviewDate,
  MASTERED_ACTIVITY_COUNT,
  REVIEW_INTERVALS,
  localDate,
  daysBetween,
} from "./lib.mjs";

const __dirname = dirname(fileURLToPath(import.meta.url));
const tsRoot = join(__dirname, "..");
const repoRoot = join(tsRoot, "..");
const problemsDir = join(tsRoot, "problems");
const contestPath = join(tsRoot, "contest.json");

const today = localDate();
const gitMap = getActivityDates(repoRoot);

const metas = [];
if (existsSync(problemsDir)) {
  for (const dir of readdirSync(problemsDir)) {
    const p = join(problemsDir, dir, "meta.json");
    if (!existsSync(p)) continue;
    try {
      metas.push({ dir, ...JSON.parse(readFileSync(p, "utf8")) });
    } catch {
      console.warn(`跳过无法解析的 ${dir}/meta.json`);
    }
  }
}

const items = metas.map((m) => {
  const dates = activityDatesFor(m, gitMap);
  const last = dates.length > 0 ? dates[dates.length - 1] : null;
  const next = nextReviewDate(last, dates.length);
  const mastered = dates.length >= MASTERED_ACTIVITY_COUNT;
  return {
    ...m,
    dates,
    count: dates.length,
    last,
    next: mastered ? null : next,
    mastered,
    due: !mastered && next !== null && next <= today,
  };
});

const solved = items.filter((m) => m.count > 0);
const mastered = items.filter((m) => m.mastered);
const due = items.filter((m) => m.due).sort((a, b) => String(a.next).localeCompare(String(b.next)));

const byDifficulty = { Easy: 0, Medium: 0, Hard: 0 };
for (const m of solved) byDifficulty[m.difficulty] = (byDifficulty[m.difficulty] ?? 0) + 1;

const xp = solved.reduce((sum, m) => {
  const base = { Easy: 10, Medium: 20, Hard: 40 }[m.difficulty] ?? 10;
  return sum + base + (m.firstTry ? 5 : 0) + (m.mastered ? 10 : 0);
}, 0);

const allDates = [...new Set(items.flatMap((m) => m.dates))].sort();
const { current, longest } = computeStreaks(allDates, today);
const todayActive = allDates.includes(today);

const patternCount = new Map();
for (const m of solved) {
  for (const p of m.patterns ?? []) patternCount.set(p, (patternCount.get(p) ?? 0) + 1);
}
const patterns = [...patternCount.entries()].sort((a, b) => b[1] - a[1] || a[0].localeCompare(b[0]));
const maxPattern = patterns.length > 0 ? patterns[0][1] : 1;

const contests = existsSync(contestPath) ? JSON.parse(readFileSync(contestPath, "utf8")) : [];
const recentContests = contests.slice(-5);

writeFileSync(join(tsRoot, "PROGRESS.md"), renderMarkdown());

console.log("── 刷题看板 ──────────────────");
console.log(`连续打卡: ${current} 天${todayActive ? "  (今日已打卡)" : "  (今日待打卡)"}`);
console.log(`XP: ${xp}  (Lv.${Math.floor(xp / 100) + 1})`);
console.log(
  `已完成: ${solved.length}  (E${byDifficulty.Easy}/M${byDifficulty.Medium}/H${byDifficulty.Hard})  已内化: ${mastered.length}`,
);
console.log(`最长记录: ${longest} 天`);
if (due.length > 0) {
  console.log(`\n📅 今日复习 (${due.length}):`);
  for (const m of due) {
    const overdue = daysBetween(m.next, today);
    console.log(
      `  #${m.id} ${m.title}  R${m.count}  到期 ${m.next}${overdue > 0 ? ` (逾期${overdue}天)` : " ⏰"}`,
    );
  }
} else {
  console.log("\n📅 今日无到期复习");
}
console.log(`\n看板已写入: ts/PROGRESS.md`);

function renderMarkdown() {
  const L = [];
  L.push("# PROGRESS — 刷题看板");
  L.push("");
  L.push("> 自动生成，请勿手改。数据来源：`ts/problems/*/meta.json` + git 历史（`solve(ts)` / `review(ts)` 提交）。");
  L.push(`> 生成时间：${today}`);
  L.push("");
  L.push("## 今日");
  L.push("");
  L.push(`- ${todayActive ? "✅ 今日已打卡" : "⬜ 今日待打卡（先做每日一题）"}`);
  if (due.length === 0) {
    L.push("- 📅 今日无到期复习");
  } else {
    L.push(`- 📅 **今日复习 ${due.length} 题**（Ebbinghaus 到期）`);
    for (const m of due) {
      const overdue = daysBetween(m.next, today);
      L.push(
        `  - \`#${m.id}\` ${m.title} — 第 ${m.count} 次活动，到期 ${m.next}${overdue > 0 ? `（逾期 ${overdue} 天）` : ""}`,
      );
    }
  }
  L.push("");
  L.push("## 总览");
  L.push("");
  L.push("| 指标 | 值 |");
  L.push("|---|---|");
  L.push(`| 连续打卡 | ${current} 天${todayActive ? " 🔥" : ""} |`);
  L.push(`| 最长记录 | ${longest} 天 |`);
  L.push(`| 已完成 | ${solved.length} |`);
  L.push(`| 已内化 | ${mastered.length} |`);
  L.push(`| 难度分布 | Easy ${byDifficulty.Easy} / Medium ${byDifficulty.Medium} / Hard ${byDifficulty.Hard} |`);
  L.push(`| XP | ${xp} (Lv.${Math.floor(xp / 100) + 1}) |`);
  L.push("");
  L.push("## 复习节奏");
  L.push("");
  L.push(`Ebbinghaus：完成 → ${REVIEW_INTERVALS.map((d) => `+${d}`).join(" → ")} 天 → 内化（共 ${MASTERED_ACTIVITY_COUNT} 次活动）。`);
  L.push("时间锚点 = git commit 时间；回刷后重新计时。");
  L.push("");
  L.push("## Pattern 进度");
  L.push("");
  if (patterns.length === 0) {
    L.push("_还没有数据，先完成第一题。_");
  } else {
    for (const [name, count] of patterns) L.push(`- \`${progressBar(count, maxPattern)}\` ${name}  ${count}`);
  }
  L.push("");
  L.push("## 周赛记录");
  L.push("");
  if (contests.length === 0) {
    L.push("_未开始（W4 起）。_");
  } else {
    L.push("| 周 | 做出题数 | 备注 |");
    L.push("|---|---|---|");
    for (const c of recentContests) L.push(`| ${c.week} | ${c.solved} | ${c.note ?? ""} |`);
  }
  L.push("");
  L.push("## 全部题目");
  L.push("");
  if (items.length === 0) {
    L.push("_空。_");
  } else {
    L.push("| # | 题 | 难度 | 活动 | 状态 | 上次活动 | 下次复习 |");
    L.push("|---|---|---|---|---|---|---|");
    for (const m of items.sort((a, b) => a.id - b.id)) {
      L.push(
        `| ${m.id} | ${m.title} | ${m.difficulty} | ${m.count} | ${statusLabel(m)} | ${m.last ?? "-"} | ${m.next ?? "-"} |`,
      );
    }
  }
  L.push("");
  return `${L.join("\n")}\n`;
}

function statusLabel(m) {
  if (m.count === 0) return "⬜ 待做";
  if (m.mastered) return "🏅 内化";
  if (m.due) return "📅 待复习";
  return `✅ R${m.count - 1}`;
}

function progressBar(value, total, width = 10) {
  const filled = Math.max(0, Math.min(width, Math.round((value / total) * width)));
  return "█".repeat(filled) + "░".repeat(width - filled);
}

function computeStreaks(dates, today) {
  const sorted = [...new Set(dates)].sort();
  if (sorted.length === 0) return { current: 0, longest: 0 };

  let longest = 1;
  let run = 1;
  for (let i = 1; i < sorted.length; i++) {
    if (daysBetween(sorted[i - 1], sorted[i]) === 1) {
      run++;
      longest = Math.max(longest, run);
    } else {
      run = 1;
    }
  }

  const last = sorted[sorted.length - 1];
  const gap = daysBetween(last, today);
  let current = 0;
  if (gap === 0 || gap === 1) {
    current = 1;
    for (let i = sorted.length - 1; i > 0; i--) {
      if (daysBetween(sorted[i - 1], sorted[i]) === 1) current++;
      else break;
    }
  }
  return { current, longest };
}