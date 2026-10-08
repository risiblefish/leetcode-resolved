/**
 * 共享工具：日期计算、Ebbinghaus 复习间隔、从 git 历史提取每题的活动时间线。
 *
 * 复习时间线以 git commit 时间为准（不可篡改、不需要手动维护）。
 * 只有提交信息以 solve(ts) / review(ts) 开头的 commit 才被计入。
 */
import { execFileSync } from "node:child_process";

/** Ebbinghaus 间隔（天）：首次完成后 +1，之后依次 +3/+7/+15/+30/+60，共 6 次复习后内化。 */
export const REVIEW_INTERVALS = [1, 3, 7, 15, 30, 60];

/** 完成 + 全部复习的次数：达到即视为内化。 */
export const MASTERED_ACTIVITY_COUNT = REVIEW_INTERVALS.length + 1;

/** 给定最后一次活动日期与累计活动次数，返回下次复习日；已内化返回 null。 */
export function nextReviewDate(lastDate, activityCount) {
  const interval = REVIEW_INTERVALS[activityCount - 1];
  if (!interval || !lastDate) return null;
  return addDays(lastDate, interval);
}

/** 当前处于第几次复习（1 = 刚完成，还没复习过）。 */
export function reviewStage(activityCount) {
  return Math.max(1, activityCount);
}

/**
 * 扫描 git 历史，返回 Map<题号, 日期数组>。
 * 只统计 solve(ts) / review(ts) 提交，且以提交信息里的题号为准。
 */
export function getActivityDates(repoRoot) {
  const map = new Map();
  let out;
  try {
    out = execFileSync(
      "git",
      ["-C", repoRoot, "log", "--format=%x00%cI%x01%s", "--name-only", "--", "ts/problems"],
      { encoding: "utf8" },
    );
  } catch {
    return map;
  }

  for (const chunk of out.split("\x00")) {
    if (!chunk.trim()) continue;
    const [firstLine, ...rest] = chunk.split("\n");
    const [iso = "", subject = ""] = firstLine.split("\x01");
    const m = subject.match(/^(?:solve|review)\(ts\):\s*(\d{4})/);
    if (!m) continue;
    const date = iso.slice(0, 10);
    if (!/^\d{4}-\d{2}-\d{2}$/.test(date)) continue;
    const id = Number(m[1]);
    if (!map.has(id)) map.set(id, []);
    if (!map.get(id).includes(date)) map.get(id).push(date);
  }
  for (const dates of map.values()) dates.sort();
  return map;
}

/** 规范化后的每题时间线：优先 git，缺失时回退到 meta.solvedOn。 */
export function activityDatesFor(meta, gitMap) {
  const fromGit = gitMap.get(meta.id);
  if (fromGit && fromGit.length > 0) return fromGit;
  const fallback = [];
  if (meta.solvedOn) fallback.push(meta.solvedOn);
  for (const r of meta.reviews ?? []) fallback.push(r);
  return [...new Set(fallback)].sort();
}

export function localDate(d = new Date()) {
  const y = d.getFullYear();
  const m = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  return `${y}-${m}-${day}`;
}

export function addDays(dateStr, days) {
  const d = new Date(`${dateStr}T00:00:00`);
  d.setDate(d.getDate() + days);
  return localDate(d);
}

export function daysBetween(a, b) {
  const da = new Date(`${a}T00:00:00`);
  const db = new Date(`${b}T00:00:00`);
  return Math.round((db - da) / 86400000);
}