#!/usr/bin/env node
/**
 * 生成 / 追加「本份学习文档」`ts/docs/daily/<YYYY-MM-DD>.md`。
 *
 * - 当日文件不存在 → 创建完整文档（含第 1 份 + 讲解区 + 后续必做 + 收尾）
 * - 当日文件已存在 → 追加「第 N 份」到文末（轻量：不重复讲解；切 pattern 由 AI 补讲解）
 *
 * 文档用语：只用「第 N 份」——不写 Session、不写 commit 哈希、不写需要背景才能懂的编号。
 * - --force → 覆盖整个文件重建
 *
 * 用法：
 *   npm run day                    # 新建或追加 Session
 *   npm run day -- --units 3       # 本份想多做
 *   npm run day -- --preview 10    # 多展示本类型后续必做
 *   npm run day -- --pattern dp    # 本份专攻某类型
 *   npm run day -- --force         # 覆盖重建整个文件
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
const previewCount = numberArg("--preview") ?? 6;
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
const preview = pool.slice(units, units + previewCount);
const pendingDrills = (plan.drills ?? [])
  .filter((id) => !isDrillDone(manualDone, id))
  .filter((id) => drillById.get(id)?.pattern === currentPattern.id);

const dir = join(tsRoot, "docs", "daily");
mkdirSync(dir, { recursive: true });
const path = join(dir, `${today}.md`);

let mode;
if (existsSync(path) && !force) {
  // 已有文件 → 追加「第 N 份」
  const existing = readFileSync(path, "utf8");
  // 兼容旧结构（旧文档写的是 "## Session N"）
  const sessionMarks = existing.match(/^## (?:Session \d+|第 \d+ 份)/gm) || [];
  const nextN = sessionMarks.length === 0 ? 2 : sessionMarks.length + 1;
  writeFileSync(path, existing.trimEnd() + "\n\n" + renderSession(nextN));
  mode = `追加第 ${nextN} 份`;
} else {
  writeFileSync(path, renderInitial());
  mode = force && existsSync(path) ? "覆盖重建" : "新建";
}

console.log(`✅ ${mode}：${rel(path)}`);
console.log("");
console.log(`  本份类型：${currentPattern.name}（${currentPattern.id}）`);
console.log(`  本份新题 ${selected.length} 道 · 本类型后续预览 ${preview.length} 道 · 到期复习 ${due.length} 道 · 本类型 drill ${pendingDrills.length} 个`);
console.log("  下一步：AI 读 OVERVIEW / 最近 daily / git log，补全「选题依据」「本份目标」「主题/讲解」「识别信号」。");

/**
 * 完整文档（第一份，含文件头 + 讲解区）。
 */
function renderInitial() {
  const L = [];
  const weekday = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"][new Date(`${today}T00:00:00`).getDay()];
  L.push(`# ${today}（${weekday}）`);
  L.push("");
  L.push("> 一天可以写多份：再跑一次 `npm run day` 会在文末追加「第 2 份」。要覆盖整个文件加 `--force`。");
  L.push(`> 每份就是「一次坐下来练的一批题」：AI 补全「选题依据 / 本份目标 / 主题 / 讲解」，你练完回填反馈。`);
  L.push(`> 现状：累计 **${solvedIds.size} 道** · 已内化 ${masteredCount} · 本份类型 **${currentPattern.name}**`);
  L.push("");

  L.push("## 第 1 份");
  L.push("");

  pushSessionBody(L, { headingLevel: 3, includeTeaching: true });
  return L.join("\n") + "\n";
}

/**
 * 追加的一份（轻量，不重复讲解）。
 */
function renderSession(n) {
  const L = [];
  L.push("---");
  L.push("");
  L.push(`## 第 ${n} 份`);
  L.push("");
  L.push(`> 本份类型 **${currentPattern.name}**（${currentPattern.id}）。切类型的话让 AI 在「本份学习内容」小节补讲解。`);
  L.push("");

  pushSessionBody(L, { headingLevel: 3, includeTeaching: false });
  return L.join("\n") + "\n";
}

/**
 * 共用的份主体。
 */
function pushSessionBody(L, { headingLevel, includeTeaching }) {
  const h = "#".repeat(headingLevel);
  const subh = "#".repeat(headingLevel + 1);

  L.push(`${h} 选题依据`);
  L.push("");
  L.push(`<!-- AI 必填（透明可反驳）：`);
  L.push(`     - 本份类型：${currentPattern.id}（当前水位 L? · 必做进度 X/Y）`);
  L.push(`     - 近期信号：最近 2-3 道的 AC/质量/讨论卡点、TS 侧覆盖哪些`);
  L.push(`     - 结论：本份做什么，为什么是这个难度档，为什么是这几道 -->`);
  L.push("");

  L.push(`${h} 本份目标`);
  L.push("");
  L.push("<!-- AI 必填：具体可验证的成果。例：");
  L.push("     - AC 两道");
  L.push("     - 带回 ≥1 条新 X（新算法套路 / 新 TS 维度 / 新模板肉感） -->");
  L.push("");

  if (includeTeaching) {
    L.push(`${h} 本份主题`);
    L.push("");
    L.push("<!-- AI：一句话——本份练什么、为什么是它 -->");
    L.push("");

    L.push(`${h} 本份学习内容（讲解）`);
    L.push("");
    L.push(`<!-- AI：用「${currentPattern.id}」预习卡 + 题目背景，写 5–10 分钟能读完的讲解。`);
    L.push("     给：是什么 / 核心思想 / TS 模板骨架 / 易错边界。");
    L.push("     红线：读完立刻动代码；超过 20 分钟没写代码就跳过。 -->");
    L.push("");
  }

  if (pendingDrills.length > 0) {
    L.push(`${h} 可选热身（本类型手写 drill）`);
    L.push("");
    for (const id of pendingDrills) {
      const d = drillById.get(id);
      L.push(`- [ ] ${d?.title ?? id} → \`npm run task -- ${id}\`，做完 \`npm run done -- ${id}\``);
    }
    L.push("");
  }

  L.push(`${h} 本份要做的题（${selected.length} 道）`);
  L.push("");
  if (selected.length === 0) {
    L.push("本类型已刷完，`npm run day -- --pattern <id>` 换个类型。");
    L.push("");
  }
  selected.forEach((x, i) => {
    const p = bank.get(x.id);
    const en = p.title !== p.titleCn ? ` / ${p.title}` : "";
    const tag = x.classic ? "必做" : "备选";
    L.push(`${subh} ${i + 1}. #${p.id} ${p.titleCn}${en}（${p.difficulty} · ${tag}）`);
    L.push("");
    L.push(`- 链接：https://leetcode.cn/problems/${p.slug}/`);
    L.push(`- 建目录：\`npm run new -- ${p.id} ${p.slug} ${p.difficulty} ${currentPattern.id}\``);
    L.push(`- 识别信号：<!-- AI 可补 -->`);
    L.push(`- 我的思路（先写再做）：`);
    L.push(`- 结果：☐ 卡住　☐ AC　☐ 看了题解`);
    L.push("");
  });

  L.push(`${h} 反馈与讨论（留白）`);
  L.push("");
  L.push("<!-- 卡在哪 / 怎么讨论 / 怎么解决；顺利就不必硬写。");
  L.push("     碰到反直觉 TS 问题 → 这里记「我以为 X + 实际 Y + TODO: PF」，收尾时统一落 pitfall 卡。 -->");
  L.push("");

  if (preview.length > 0) {
    L.push(`${h} 📚 本类型后续必做（按顺序的下一批）`);
    L.push("");
    L.push(`> **不是「可选加码」，是你最终都要做的**，只是本份没排进「要做的题」。精力好直接往下做；下一份 \`npm run day -- --units N\` 批量排。`);
    L.push("");
    for (const x of preview) {
      const p = bank.get(x.id);
      const en = p.title !== p.titleCn ? ` / ${p.title}` : "";
      const tag = x.classic ? "必做" : "备选";
      L.push(`- [ ] **#${p.id} ${p.titleCn}**${en}（${p.difficulty} · ${tag}）`);
      L.push(`  - https://leetcode.cn/problems/${p.slug}/`);
      L.push(`  - \`npm run new -- ${p.id} ${p.slug} ${p.difficulty} ${currentPattern.id}\``);
    }
    L.push("");
  }

  L.push(`${h} 本份要复习的题`);
  L.push("");
  if (due.length === 0) {
    L.push("本份没有到期复习。");
  } else {
    for (const d of due) {
      L.push(`${subh} #${d.id} ${d.title}（第 ${d.stage} 次复习，到期 ${d.next}${d.overdue > 0 ? `，逾期 ${d.overdue} 天` : ""}）`);
      L.push("");
      L.push(`- 回刷后记录：\`npm run solve -- ${d.id} --min <耗时>\``);
      L.push(`- 闭卷重写结果（写不出再看旧代码）：`);
      L.push("");
    }
  }
  L.push("");

  L.push(`${h} 本份收尾（agent 做完时填）`);
  L.push("");
  L.push("- **感觉**：☐ 爽　☐ 一般　☐ 卡");
  L.push("- **带回**：<!-- 本份带走了什么。例：PF-xxxx · 新套路 X · 模板肉感 -->");
  L.push("- **水位判断**：<!-- 本类型水位 升/保/降，依据是什么。用户可反驳。 -->");
  L.push("- **下一份建议**：<!-- 并在 OVERVIEW 刷新。 -->");
  L.push("");
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
