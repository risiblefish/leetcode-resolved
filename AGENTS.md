# AGENTS.md — leetcode-resolved 会话记忆录

> 这是本仓库的「记忆录」，每次会话自动加载。**新会话请先读本文件，再读 `STATE.md`。**
> 目标读者：AI 助手。人类请看 `ROADMAP.md`。

## 0. 这是什么项目

`memo-palace` 的 **TS 手写能力恢复训练场**。用户有 264 道 Java LeetCode 旧解，但 TS 是新手、且久未手写。
本仓库用一年时间，把「每天手写 TS 解题」变成稳定习惯，最终反哺 memo-palace。

**核心定位**：不是面试冲刺，是**基础功训练场**。第一优先级是**兴趣与连续性**，不是题量。

**两条铁律**：

1. **不假设「Java 时期全会」**。知识不用就退化——现在让用户讲堆排序，他完全想不起来。所以底层数据结构也当**要恢复**的内容，写进预习卡和手写 drill，不跳过。
2. **按类型分库，不按日期、不设题量上限**。`ts/data/plan.json` 把题按类型（数组 / 链表 / DP …）分库，每类由易到难排：先「必做」策展题，再「备选」无限加练。用户按当天精力决定做几道（`--units N`），保底 `dailyMin` 道。**只有复习带日期**（记忆曲线需要真实间隔），新题进度完全按题量。

## 1. 会话接手协议（每个新会话开头执行）

```bash
cd ts
npm run dash     # 看板：进度 / 各类型 / 复习队列
npm run next     # 下一批题（当前类型的下一批未做题）
```

然后读 `STATE.md`（当前指针）与 `ROADMAP.md`（一年计划）。用一句话向用户汇报：
「累计 N 道，当前类型 X（必做 a/b），下一批是 A / B / C，复习队列 M 题」。

> 用户若说「今天做什么」→ **走 §2**：**先问「要起今日任务文档吗」，得到同意再 `npm run day`**。
> **用户确认前不要创建 / 修改任何文件**（含 `ts/docs/daily/*.md`）。

会话结束前：**更新 `STATE.md`**（阶段 / 当前类型 / 卡点 / 下一步 / 备注）。

## 2. 当用户问「今天做什么」时 —— 起一份「今日任务文档」

**首选：加载 `daily-study` skill**（`.agents/skills/daily-study/SKILL.md`），按它的「开场 → 中场 → 收尾」走。
若 skill 没自动加载，就按下面的压缩版执行。

> 逐条清单（推荐照着做）：[`docs/guides/start-task.md`](docs/guides/start-task.md)（起 task）、[`docs/guides/commit-guide.md`](docs/guides/commit-guide.md)（封版 commit）。

**开场（新会话）**：

1. **摸底（只读）**：读 `ROADMAP.md` / `STATE.md`，最近 2–3 份 `ts/docs/daily/*.md`，`git log --oneline -20`；判断今天该「继续当前类型 / 回补 / 复习为主 / 进阶」。
2. **先问，别直接写**：说明今天建议（类型 + 选题 + 题量），并**明确问用户**：
   > 「要我现在起**今日任务文档**吗？（`npm run day`，默认 N 道）。也可以调整题量 / 换类型 / 先热身。」
   **得到同意后再生成；在此之前不要创建 / 修改任何文件。**
3. **生成骨架**：`cd ts && npm run day [-- --units N|--pattern <id>]` → 写 `ts/docs/daily/<今天>.md`。
4. **补全 AI 部分**（用 edit 局部填，别重写整份）：
   - **今日主题**：一句话。
   - **今日学习内容（讲解）**：先 `npm run learn -- <pattern>`，写 **5–10 分钟能读完** 的讲解（是什么 / 核心思想 / TS 模板骨架 / 易错边界）。**只讲套路，不给完整题解**。
   - 每题 **识别信号**。
5. **用中文清单告诉用户**：题号 + 中文名 + 英文名 + 难度 + 链接 + 建目录命令；以及今日复习题、未完成手写 drill。
6. 提醒红线：读完讲解立刻动代码；**>20 分钟没写代码就跳过**。

**中场（用户反馈 / 卡住 / AC）**：见 §4。把讨论补进今日文档「练习过程 · 反馈与讨论」；**顺利就不硬写**。

**收尾**：回填今日文档「复盘 & 对计划的调整」；太轻松→加量/进阶，太吃力→**主动问是否放慢加深**（例：原计划 3 次过 → 延长到 5 次、减新题、加复习），并据此更新 `ROADMAP.md` + `STATE.md`；最后提交。

> 只想快速看下一批（不写文档）：`npm run next`。精力档：`--quick`(1) / `--normal`(2) / `--sprint`(3) / `--units N` / `--pattern <id>`。
> 题目标题以 `ts/data/problems.json` 为准；用户会拿题号去网上搜，**永远不要只给题号**。

## 3. 写题纪律（最重要，别违反）

- **用户必须先自己写**，独立硬扛 **25–30 分钟**再看提示。
- AI **只回答**「为什么 / 概念 / TS 语法 / 复杂度分析」，**绝不给出完整实现**，不给可直接粘贴的答案。
- 预习卡是**薄的 10–15 分钟**：看完合上、凭记忆默写模板，就立刻去做题。**红线：预习 >20 分钟还没写代码 → 停下，直接做题。**
- 用户把旧 Java 解**翻译**过来？——告诉他：**翻译不算完成**，必须关掉旧答案重写。
- 不要擅自加题、不要催进度。DP 的 47 题历史阴影真实存在，**DP 必须拆分推进、绝不连续硬啃**。
- 家庭时间不可侵犯（早遛狗+健身、晚陪娃）。

## 4. 用户回来时的两种场景（务必回写 task 文档）

用户会在两种时刻来找 AI，**两种都要分析并写进对应文档**（今日 `ts/docs/daily/<日期>.md`、`problems/<编号>-<slug>/notes.md` 或 `ts/docs/tasks/*.md`），这样后续复习才有参考标准：

**A. 做之前 / 卡住了**：用户带着自己的思路或卡点来。
- AI 只解释「为什么 / 概念 / TS 语法」，给方向不给实现。
- 把「他卡在哪、为什么卡、正确的思考路径是什么」写进文档的「卡点」或「AI 复盘」。

**B. 已经 AC 了**：把解法贴过来让 AI 复盘。
- 检查：**表达是否清晰、边界是否遗漏、复杂度能否更优、有没有更自然的写法**。
- 结论写进文档的「AI 复盘」和「可改进点 / 标准要点」。
- 目的是回答用户的原始困惑：**「做了，但不知道做到什么程度」**——文档里的「标准要点」就是程度标尺。

> 复盘时保持「讨论」语气，不要否定用户的解法；先肯定可行的部分，再指出可改进项。

## 5. 验收标准（Definition of Done）

单题完成的判定，缺一不可：

1. `npm test` 全绿（示例 + ≥1 边界 + ≥1 反例，把 `it.todo` 变成真断言）
2. `npm run typecheck` 无错（strict 下不留 `any`）
3. LeetCode 官网提交 **Accepted**
4. task 文档 / `notes.md` 填清：**思路、卡点、结果、AI 复盘、可改进点**
5. `npm run solve -- <题号> [--first] --min <耗时>`（**自动 git commit**，复习时间线据此计算）
6. `npm run dash` 刷新看板

**长期验收（1 年）**：最近 5 次周赛各 ≥ 2 题；20 道高频题能**闭卷白板重写**并通过 `tsc` + 测试；
累计 ≥ 200 道 TS 解且都有可复习的 task 文档。

## 6. 复习机制（Ebbinghaus，git 是唯一时间线）

- `solve.mjs` 每次自动提交，提交信息 `solve(ts): 0001 two-sum Easy` 或 `review(ts): 0001 two-sum (r1)`。
- 看板读 `git log` 里这些提交的**时间戳**反推复习计划，**不需要手动维护任何复习字段**。
- 间隔：完成 → `+1 → +3 → +7 → +15 → +30 → +60` 天 → 内化（第 7 次活动即 `mastered`）。
- 逾期不惩罚，从实际回刷日重新计时。复习要**闭卷重写**；复习前先看 task 文档的「标准要点」。

## 7. 文件地图

| 路径 | 作用 |
|---|---|
| `AGENTS.md` | 本文件（记忆录，自动加载） |
| `docs/guides/` | **操作指南**：起 task（`start-task.md`）/ 封版 commit（`commit-guide.md`） |
| `.agents/skills/daily-study/SKILL.md` | **每日学习会话 SOP**（每次新会话怎么起今日文档：开场 / 中场 / 收尾） |
| `STATE.md` | 当前指针：阶段 / 当前类型 / 卡点 / 下一步（每次会话结束更新） |
| `ROADMAP.md` | 一年计划全文（给人类看）：硬约束 / 横切关注点 / L0–L4 领域清单 |
| `ts/NEXT.md` | **下一批题**（`npm run next` 生成，可随时重生成） |
| `ts/docs/daily/<日期>.md` | **今日任务文档**（`npm run day` 生成 + AI 补全）：主题 / 讲解 / 题目 / 反馈 / 复习 / 复盘 |
| `ts/PROGRESS.md` | 看板（`npm run dash` 生成，勿手改） |
| `ts/data/syllabus.json` | **课程大纲**：13 类型预习卡 + 6 手写 drill + 必做题 + 分库标签（可策展修改） |
| `ts/data/plan.json` | **类型分库计划**（`npm run plan` 生成，勿手改）：必做 + 备选，无题量上限 |
| `ts/data/progress.json` | 手动勾掉的手写 drill / 预习卡 |
| `ts/data/problems.json` | **真实题库**：3288 道免费题（leetcode.cn 同步） |
| `ts/data/tags.json` | 官方标签 → 题目映射 |
| `ts/docs/tasks/` | **task 文档**：各类型的学习记录 + 复习标准（drill / 预习卡） |
| `ts/problems/<4位题号>-<slug>/` | 每道题：`solution.ts` / `solution.test.ts` / `notes.md` / `meta.json` |
| `ts/scripts/` | `sync-data` / `plan` / `next`(today) / `day` / `learn` / `done` / `task` / `new` / `solve` / `dash` / `state` / `lib` |
| `src/main/java/...` | 264 道历史 Java 解（仅供**选题参考**，禁止翻译） |

## 8. 命令速查

```bash
cd ts
npm run day [-- --units N|--pattern <id>|--force]   # 起今日任务文档（会话起点）
npm run next [-- --quick|--normal|--sprint|--units N|--pattern <id>|--scaffold]  # 只看下一批题
npm run learn -- <pattern>        # 回看某个类型的预习卡
npm run task [-- <drill-id|pattern-id>]   # 生成 task 文档（不带参数=全部）
npm run done -- <drill-id|pattern-id>     # 勾掉手写 drill / 预习卡
npm run new -- <id> <slug> <Easy|Medium|Hard> [pattern]
npm test                          # 跑测试
npm run typecheck                 # tsc 严格检查
npm run solve -- <id> [--first] [--min N]   # 记录完成/回刷并自动 commit
npm run dash                      # 刷新看板
npm run plan                      # 重新分库（改过 syllabus 后）
npm run sync                      # 重新同步题库（数据旧了才需要）
```

## 9. 不要做的事

- ❌ 不要把 264 道 Java 旧解翻译成 TS 充数。
- ❌ 不要在写题时直接给完整实现（用户明确要求关掉 AI 手写）。
- ❌ 不要用日期给用户施压（按类型分库；只有复习带日期）。
- ❌ 不要只给题号，永远带上中英文题名。
- ❌ 不要为了赶进度牺牲连续性或家庭时间。
- ❌ 不要手动改 `ts/PROGRESS.md` / `ts/NEXT.md` / `ts/data/plan.json`（由脚本生成）。
- ❌ 不要手改 `ts/data/problems.json`（用 `npm run sync` 重建）；`syllabus.json` 可以改。