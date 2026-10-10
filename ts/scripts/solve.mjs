#!/usr/bin/env node
/**
 * 记录完成 / 复习，然后自动 git commit。
 *
 * 用法：
 *   npm run solve -- <题号>                # 首次完成或回刷
 *   npm run solve -- <题号> --first        # 首次完成，且是一次过
 *   npm run solve -- <题号> --min 25       # 记录本次耗时（分钟）
 *   npm run solve -- <题号> --no-commit    # 只更新 meta，不提交
 *
 * 复习节奏（Ebbinghaus，以本次 commit 时间为锚点）：
 *   完成 → +1 → +3 → +7 → +15 → +30 → +60 天 → 内化
 * 具体哪天复习由 `npm run dash` 根据 git 历史算出。
 */
import { readdirSync, readFileSync, writeFileSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";
import { execFileSync } from "node:child_process";
import {
  getActivityDates,
  activityDatesFor,
  nextReviewDate,
  MASTERED_ACTIVITY_COUNT,
  localDate,
} from "./lib.mjs";

const __dirname = dirname(fileURLToPath(import.meta.url));
const tsRoot = join(__dirname, "..");
const repoRoot = join(tsRoot, "..");
const problemsDir = join(tsRoot, "problems");

const args = process.argv.slice(2);
const idArg = args.find((a) => /^\d+$/.test(a));
if (!idArg) {
  console.error("用法: npm run solve -- <题号> [--first] [--min N] [--no-commit]");
  process.exit(1);
}
const id = Number.parseInt(idArg, 10);
const firstTry = args.includes("--first");
const noCommit = args.includes("--no-commit");
const minIdx = args.indexOf("--min");
const minutes = minIdx >= 0 ? Number.parseInt(args[minIdx + 1] ?? "0", 10) : 0;

const dirName = readdirSync(problemsDir).find((d) =>
  d.startsWith(`${String(id).padStart(4, "0")}-`),
);
if (!dirName) {
  console.error(`找不到题号 ${id}，先跑 npm run new -- ${id} <slug> <难度>`);
  process.exit(1);
}
const problemRel = `ts/problems/${dirName}`;

const metaPath = join(problemsDir, dirName, "meta.json");
const meta = JSON.parse(readFileSync(metaPath, "utf8"));
const today = localDate();

const priorDates = activityDatesFor(meta, getActivityDates(repoRoot));
const isFirst = priorDates.length === 0;

if (isFirst) {
  meta.status = "solved";
  meta.solvedOn = today;
  meta.firstTry = Boolean(firstTry);
} else {
  meta.reviews = [...(meta.reviews ?? []), today];
}

if (minutes > 0) meta.timeSpentMin = (meta.timeSpentMin ?? 0) + minutes;

// 口径与 state.mjs 一致：按**不同日期**计数（同一天做完首版又复习 = 一个活动日，不算一次间隔重复）。
// 例：10-10 当天 solve --first + r1 → 活动日只有 10-10 → 下次 +1 天，而不是 +3。
const activityCount = priorDates.includes(today) ? priorDates.length : priorDates.length + 1;
if (activityCount >= MASTERED_ACTIVITY_COUNT) meta.status = "mastered";

writeFileSync(metaPath, `${JSON.stringify(meta, null, 2)}\n`);

const message = isFirst
  ? `solve(ts): ${String(id).padStart(4, "0")} ${meta.slug} ${meta.difficulty}`
  : `review(ts): ${String(id).padStart(4, "0")} ${meta.slug} (r${activityCount - 1})`;

let committed = false;
if (!noCommit) committed = tryCommit();

const next = nextReviewDate(today, activityCount);
console.log("");
if (isFirst) console.log(`✅ ${dirName} 首次完成`);
else console.log(`🔁 ${dirName} 第 ${activityCount - 1} 次复习`);
if (meta.status === "mastered") {
  console.log("🏅 达到内化（mastered），不再排复习");
} else if (next) {
  console.log(`📅 下次复习：${next}（+${diffDays(today, next)} 天）`);
}
console.log(
  committed
    ? `📦 已提交：${message}`
    : noCommit
      ? "（--no-commit，跳过提交）"
      : `⚠️ 自动提交失败，请手动执行：git add ${problemRel} && git commit -m "${message}"`,
);

function tryCommit() {
  try {
    execFileSync("git", ["-C", repoRoot, "add", "--", problemRel]);
    execFileSync("git", ["-C", repoRoot, "commit", "-m", message, "--", problemRel], {
      stdio: "pipe",
    });
    return true;
  } catch {
    return false;
  }
}

function diffDays(a, b) {
  return Math.round(
    (new Date(`${b}T00:00:00`) - new Date(`${a}T00:00:00`)) / 86400000,
  );
}