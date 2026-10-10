# AGENTS.md — leetcode-resolved 会话记忆录

> 这是本仓库的「记忆录」，每次会话自动加载。**新会话读这个 → 读 `OVERVIEW.md` → 开工。**
> 目标读者：AI 助手。人类请看 `ROADMAP.md`（愿景）和 `OVERVIEW.md`（当前位置）。

---

## 0. 项目定位

`memo-palace` 的 **TS 手写能力恢复训练场**。用户有 264 道 Java LeetCode 旧解，TS 新手、久未手写。

**核心定位**：**基础功训练场**，不是面试冲刺。

**衡量尺度**：**学了要有用**。
- 带回新算法套路 → 有用
- 带回新 TS 概念 / 坑点 → 有用
- 做对了有多巴胺 → 有用
- 过程愉快 → 有用
- **唯一浪费 = 痛苦 + 空手**

**第一优先级**：兴趣与连续性，不是题量，不是 Rating。

---

## 1. 核心模型（Agent 决策逻辑）

### 1.1 二维独立水位

**算法侧**（13 类，每类独立）：

- **L0** 未动
- **L1** 认识套路（过预习卡 + 5 道必做 AC）
- **L2** 能默写（闭卷重写 2 道 + 讲得清核心）
- **L3** 能变通（必做之外 3 道变种一次 AC）

**TS 侧**（横切所有类）：核心概念覆盖度（泛型 / 工具类型 / 类型谓词 / 异步...）

**两维独立**：换算法类型 → 切算法水位，**不切 TS 水位**。array-hash L3 + dp L0 完全合理。

### 1.2 心流调节（agent 自动做，透明）

> **挑战难度 ≈ 当前水位 + 10-20%**
> 心流三条件：挑战-技能匹配、清晰目标、即时反馈。

| 信号 | 判断 | 动作 |
|---|---|---|
| 连续 3 道同类 AC + 讨论清晰 | 水位稳固 | 升一档 |
| 单道 AC 但代码冗余 / 讨论卡本质 | 水位虚高 | 保持，加同难度 |
| 单道卡 + 卡算法本质 | 水位虚标 | 降一档 |
| 单道卡 + 卡 TS 语法 | 算法 OK，TS 盲点 | **不动算法水位**，立 TS 卡 |
| 换类型首道卡 | L0 正常 | 保持 Easy 继续探底 |

### 1.3 判断信号来源（从粗到细）

1. **AC/WA/TLE** — git log 的 solve/review 提交
2. **代码质量** — 读 `ts/problems/<id>/solution.ts`：一遍过？冗余？边界？复杂度？
3. **讨论质量** — 读 `ts/docs/daily/*.md` 反馈区 + `ts/docs/knowledge/`（算法卡 + TS 卡）：讲得清本质？卡表层还是深层？
4. **跨期一致性** — git log 的 review 时间戳 + 复习结果
5. **用户主观收尾** — 一字反馈：**爽 / 一般 / 卡**

### 1.4 透明且可反驳（铁律）

- 每次调水位 / 换难度 / 换类型，**必须说清依据**（在 OVERVIEW 和 Session 开场）
- 用户可以反驳 → agent 收回判断
- **不是独裁，是辅助判断**

### 1.5 份（unit）协议

- **份 = 一次坐下来练的一批题**
- 大小由 `--units N` 控制，默认 `plan.dailyMin`（= 2）
- 一天可以多份、也可以不开；跨天就新文档
- **唯一绑日历的是复习**（遗忘曲线要真实间隔）
- JSON key `dailyMin` 是历史留存，实际含义是「一份的默认题数」

**文档用语铁律（曾经翻过车）**：

- daily / OVERVIEW 里**只写「第 N 份」**，**不写 `Session 1/2/3`**（用户不知道哪份是哪份）
- **不写 commit 哈希**（`89647aa` 这种）、不写需要背景才能懂的编号与黑话 —— 看不懂的信息 = 噪音
- 给指令就直说：**做哪几道题 + 敲什么命令 + 点哪个文件看**；不要先讲一段背景故事
- 题目**永远带中英文名 + 链接**，不许只给题号

---

## 2. 会话协议

**首选加载 skill**：`.agents/skills/daily-study/SKILL.md`。

### 开场（新会话 / 用户问"今天做什么"）

1. **摸底（只读）**：读 `OVERVIEW.md`（主线）→ 最近 2–3 份 `ts/docs/daily/*.md` → `git log --oneline -20`
2. **讲选题依据**：当前水位 / 近期信号 / 为什么是这类型-这档难度-这几道
3. **讲本份目标**：AC 几道 + 期望带回什么（新套路 / 新 TS 维度 / 肌肉记忆）
4. **先问，别写**："开本份吗？" 得到同意再 `npm run day`
5. 补全文档的 AI 部分（主题 / 讲解 / 识别信号）；红线：>20min 没动代码就跳过预习

### 中场（卡住 / AC）

- **卡住**：只讲"为什么 / 概念 / TS 语法"，**绝不给完整实现**。先让用户硬扛 25–30 分钟
- **AC**：读代码复盘（表达、边界、复杂度、更自然的写法）；保持讨论语气，先肯定再指出可改进项
- 讨论中碰到反直觉 TS 问题 → **收尾时**落 pitfall 卡（见 §4）

### 收尾

- **一字问**：本份**爽 / 一般 / 卡**？
- 刷新 OVERVIEW：水位 / 近期信号 / 下一份建议
- 愿意多说就多记，不逼复盘
- 需要调节奏（太轻松 / 太吃力）→ 主动提，等 confirm

---

## 3. 写题纪律（最重要，别违反）

- **用户先自己写**，独立硬扛 25–30 分钟再看提示
- AI **只回答**「为什么 / 概念 / TS 语法 / 复杂度」，**绝不给完整实现**
- 预习卡**薄的 10–15 分钟**：合上默写模板，立刻做题；**>20 分钟没动代码 → 停下直接做题**
- 旧 Java 解翻译过来？**翻译不算完成**，必须关掉旧答案重写
- DP #47 历史阴影真实存在，**DP 必须拆分推进、绝不连续硬啃**
- 家庭时间不可侵犯（早遛狗+健身、晚陪娃）

### 3.1 代码住哪：两区契约（**唯一的存放规则，别再推**）

| 区 | 路径 | 角色 | 谁写 |
|---|---|---|---|
| **草稿区** | `ts/src/<id>.ts` | **收件箱**：随手写、AC 了但还没走流程的稿子 | 用户 |
| **正式区** | `ts/problems/<4位题号>-<slug>/` | **题目的家**：`solution.ts` + `solution.test.ts` + `notes.md` + `meta.json` | 用户写解法，AI 补文档 |

- **铁律：一道题只住一处。** 转正 = **移动**（搬完就删 `ts/src/<id>.ts`），**绝不复制、绝不两边维护**
- **只有正式区进复习曲线**：`npm run solve` / `review` 只认 `ts/problems/`。草稿区的题不产生 `solve(ts)` commit → 等于没做（Session 2 的 6 道就是这么“白做”的）
- **用户的习惯是先写在 `ts/src/`，写完喊 AI 读**。AI 的活：读稿 → 评质量 → `npm run new` 建正式目录 → 搬进 `solution.ts` → 填 `notes.md` → 删 src 副本
- **搬运 ≠ 完成**：稿子若是从旧答案 / 题解搬来的 → 必须在正式区**闭卷重写一遍**才算过；若是**当场闭卷写的** → 转正就是纯移动（不用再写一遍，别折腾用户）
- `ts/src/types.ts` 是基础设施（`ListNode` / `TreeNode` + 转换辅助），**不属于草稿区，任何情况都不删**；链表/树的题从它 import
- **AI 每份开场必做**：`ls ts/src/` → 有未转正的稿就列出来并提议转正（没写进本文件的记忆 = 不存在）

---

## 4. Pitfall 卡机制（反直觉 TS 问题）

讨论中碰到「我以为 X，TS 说 Y」的反直觉问题（典型例：`Map.has` 判空后 `.get()` 仍是 `V | undefined`），**不要只口头讲清楚**，落成 pitfall 卡：

- **位置**：`ts/docs/knowledge/language/PF-<4位编号>-<slug>.md`，索引 `ts/docs/knowledge/language/README.md`（知识库总索引：`ts/docs/knowledge/README.md`）
- **时机**：**Session 收尾时统一整理**，不中途打断；现场只在 daily 反馈区写一句「疑问 + 我以为 + 实际 + TODO: PF」
- **什么值得立卡**：反直觉 + 下次还会撞；一问就通的不立
- **固定结构**：现象 → 一句话根因 → 修法（≥2 条）→ 自测反问 → 延伸
- **关联**：`problems/<id>/notes.md` 对应行写 `踩坑：PF-xxxx` 做反向引用

---

## 5. 验收标准（Definition of Done）

单题完成，缺一不可：

1. `npm test` 全绿（示例 + ≥1 边界 + ≥1 反例）
2. `npm run typecheck` 无错（strict 下不留 `any`）
3. LeetCode 官网提交 **Accepted**
4. `problems/<id>/notes.md` 填清：思路 / 卡点 / 结果 / AI 复盘 / 可改进点
5. `npm run solve -- <题号> [--first] --min <耗时>`（自动 git commit，复习时间线以此为准）

**长期验收（1 年）**：
- 最近 5 次周赛各 ≥ 2 题（**仅作参考**，用户不在意 Rating）
- 20 道高频题能闭卷白板重写并通过 tsc + 测试
- 累计 ≥ 200 道 TS 解且都有可复习文档

---

## 6. 复习机制（Ebbinghaus · git 是唯一时间线）

- `solve.mjs` 每次自动 commit：`solve(ts): 0001 two-sum Easy` 或 `review(ts): 0001 two-sum (r1)`
- 复习计划从 git log 时间戳反推，**不需要手动维护任何复习字段**
- 间隔：完成 → `+1 → +3 → +7 → +15 → +30 → +60` 天 → 内化（第 7 次活动即 mastered）
- 逾期不惩罚，从实际回刷日重新计时
- 复习要**闭卷重写**；复习前先看 `problems/<id>/notes.md` 的「标准要点」

---

## 7. 文件地图

| 路径 | 作用 |
|---|---|
| `AGENTS.md` | 本文件（记忆录，自动加载） |
| `OVERVIEW.md` | **全局主线**：决策模型 / 水位图 / 当前位置 / 下一份建议 |
| `ROADMAP.md` | 项目愿景与长期验收标准（给人类看） |
| `docs/guides/` | 操作指南：起 task（`start-task.md`）/ 封版 commit（`commit-guide.md`） |
| `.agents/skills/daily-study/SKILL.md` | 会话 SOP（开场/中场/收尾） |
| `ts/docs/daily/<日期>.md` | **Session 文档**（`npm run day` 生成）：主题 / 讲解 / 题目 / 反馈 / 收尾 |
| `ts/docs/knowledge/` | **知识库（两块独立）**：算法侧 + TS 语言侧。怎么分、怎么维护见 `knowledge/README.md` |
| `ts/docs/knowledge/algorithm/` | **算法侧**：套路卡 `pattern-*.md` + 手写 drill `drill-*.md`（`npm run task` 生成） |
| `ts/docs/knowledge/language/` | **TS 语言侧**：坑点卡 `PF-<4位编号>-<slug>.md`（一坑一卡）+ 索引 README |
| `ts/data/syllabus.json` | 课程大纲：13 类型预习卡 + 6 手写 drill + 必做题（可策展修改） |
| `ts/data/plan.json` | 分库计划（`npm run plan` 生成，勿手改） |
| `ts/data/problems.json` | 真实题库 3288 道（用 `npm run sync` 重建） |
| `ts/data/progress.json` | 手动勾掉的 drill / 预习卡 |
| `ts/src/<id>.ts` | **草稿区（收件箱）**：用户随手写的稿；转正后**移动**到 `ts/problems/` 并删除本处副本（进不了复习曲线的东西不算完成） |
| `ts/src/types.ts` | 基础设施：`ListNode` / `TreeNode` / 转换辅助（非草稿，不删） |
| `ts/problems/<4位题号>-<slug>/` | 每道题：`solution.ts` / `solution.test.ts` / `notes.md` / `meta.json` |
| `ts/scripts/` | `day` / `new` / `solve` / `learn` / `task` / `done` / `plan` / `sync` / `next` 等 |
| `src/main/java/...` | 264 道历史 Java 解（仅供选题参考，禁止翻译） |

**已废弃**（git 历史保留）：
- `STATE.md` → 功能并入 `OVERVIEW.md`
- `ts/PROGRESS.md` → 功能并入 `OVERVIEW.md`（`npm run dash` 命令待重构）
- `ts/docs/tasks/` + `ts/docs/pitfalls/` → 已迁入 `ts/docs/knowledge/{algorithm,language}/`（2026-10-10，引用已全部更新）

---

## 8. 命令速查

```bash
cd ts
npm run day [-- --units N|--pattern <id>|--force]   # 起本份 Session 文档（会话起点）
npm run learn -- <pattern>                           # 回看某类预习卡
npm run new -- <id> <slug> <Easy|Medium|Hard> [pattern]   # 建题目目录
npm test                                             # 跑测试
npm run typecheck                                    # tsc 严格检查
npm run solve -- <id> [--first] [--min N]            # 记录完成/回刷并自动 commit
npm run task [-- <drill-id|pattern-id>]              # 生成 task 文档
npm run done -- <drill-id|pattern-id>                # 勾掉 drill / 预习卡
npm run plan                                         # 重新分库（改过 syllabus 后）
npm run sync                                         # 重新同步题库
```

---

## 9. 不要做的事

- ❌ 把 264 道 Java 旧解翻译成 TS 充数
- ❌ 写题时给完整实现（用户明确要求关掉 AI 手写）
- ❌ 用日期给用户施压（按类型分库 + 份制，只有复习带日期）
- ❌ 只给题号——永远带中英文名 + 链接
- ❌ 为了赶进度牺牲连续性或家庭时间
- ❌ 不说依据就调水位 / 换难度（必须透明）
- ❌ 单道题 AC 就升水位（累积信号才动）
- ❌ 手动改 `ts/data/plan.json` / `ts/data/problems.json`（由脚本生成）
