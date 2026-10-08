// 共享状态：读题目 meta、git 活动、手动勾选、到期复习。
// 被 today.mjs / day.mjs / dashboard.mjs 复用，保证口径一致。
import { readdirSync, readFileSync, existsSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";
import {
  getActivityDates,
  activityDatesFor,
  nextReviewDate,
  MASTERED_ACTIVITY_COUNT,
  localDate,
  daysBetween,
} from "./lib.mjs";

const __dirname = dirname(fileURLToPath(import.meta.url));
export const tsRoot = join(__dirname, "..");
export const repoRoot = join(tsRoot, "..");
export const problemsDir = join(tsRoot, "problems");
export const dataDir = join(tsRoot, "data");
const progressPath = join(dataDir, "progress.json");

export function loadState() {
  const progress = existsSync(progressPath) ? JSON.parse(readFileSync(progressPath, "utf8")) : { completed: [] };
  const manualDone = new Set(progress.completed ?? []);

  const gitMap = getActivityDates(repoRoot);
  const metas = [];
  if (existsSync(problemsDir)) {
    for (const dir of readdirSync(problemsDir)) {
      const p = join(problemsDir, dir, "meta.json");
      if (!existsSync(p)) continue;
      try {
        metas.push(JSON.parse(readFileSync(p, "utf8")));
      } catch {
        /* 跳过坏 meta */
      }
    }
  }

  const solvedIds = new Set();
  for (const m of metas) {
    const dates = activityDatesFor(m, gitMap);
    if (m.status === "solved" || m.status === "mastered" || dates.length > 0) solvedIds.add(m.id);
  }
  const startedPatterns = new Set(metas.flatMap((m) => m.patterns ?? []));

  const today = localDate();
  const due = metas
    .map((m) => {
      const dates = activityDatesFor(m, gitMap);
      if (dates.length === 0 || dates.length >= MASTERED_ACTIVITY_COUNT) return null;
      const last = dates[dates.length - 1];
      const next = nextReviewDate(last, dates.length);
      if (!next || next > today) return null;
      return { id: m.id, title: m.title, slug: m.slug, difficulty: m.difficulty, stage: dates.length, next, overdue: daysBetween(next, today) };
    })
    .filter(Boolean)
    .sort((a, b) => a.next.localeCompare(b.next));

  const masteredCount = metas.filter((m) => activityDatesFor(m, gitMap).length >= MASTERED_ACTIVITY_COUNT).length;

  return { metas, solvedIds, manualDone, startedPatterns, due, masteredCount, today, gitMap };
}

/** 每个类型的题库：必做在前（由易到难），备选在后（由易到难）。 */
export function buildQueues(plan) {
  const map = new Map();
  for (const pat of plan.patterns) {
    map.set(pat.id, [
      ...pat.classics.map((id) => ({ id, classic: true })),
      ...pat.extended.map((id) => ({ id, classic: false })),
    ]);
  }
  return map;
}

export function unsolvedOf(queues, patId, solvedIds) {
  return queues.get(patId).filter((x) => !solvedIds.has(x.id));
}

/** 当前类型：先看还有未完成必做题的，否则看还有备选的，否则第一个。 */
export function pickCurrentPattern(plan, queues, solvedIds, patternArg) {
  const left = (id) => unsolvedOf(queues, id, solvedIds);
  return (
    (patternArg && plan.patterns.find((p) => p.id === patternArg)) ||
    plan.patterns.find((p) => left(p.id).some((x) => x.classic)) ||
    plan.patterns.find((p) => left(p.id).length > 0) ||
    plan.patterns[0]
  );
}

export const isDrillDone = (manualDone, id) => manualDone.has(`drill:${id}`);
export const isPrimerDone = (manualDone, startedPatterns, patId) =>
  manualDone.has(`primer:${patId}`) || startedPatterns.has(patId);