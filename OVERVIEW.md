# 主线 Overview · TypeScript 刷题训练场

> 这是**全局唯一主线**。看这一个就知道：学什么、在哪、下一步、为什么。
> `STATE.md` / `ts/PROGRESS.md` 已废弃并入本文件；项目愿景见 `ROADMAP.md`；Agent 契约见 `AGENTS.md`。

---

## 一、定位（一段话）

- 恢复 TypeScript 手写能力，反哺 memo-palace
- **第一优先级：兴趣与连续性**（不是题量，不是 Rating）
- **衡量尺度：学了要有用**
  - 带回新算法套路 → 有用
  - 带回新 TS 概念 / 坑点 → 有用
  - 做对了有多巴胺 → 有用
  - 过程愉快 → 有用
  - **唯一浪费 = 痛苦 + 空手**

---

## 二、决策模型

### 2.1 二维独立水位

**算法侧**（13 类，每类独立水位）：

| 档位 | 达标信号（可观察，不绑时间） |
|---|---|
| **L0 未动** | 没做过 |
| **L1 认识套路** | 过完预习卡 + 5 道必做 AC |
| **L2 能默写** | 闭卷重写 2 道（r1 复习通过）+ 讲得清核心思想 |
| **L3 能变通** | 必做之外 3 道变种一次 AC，识别信号成肌肉记忆 |

**TS 侧**（横切所有类）：核心概念覆盖度（见 §4）。

**两维独立**：换算法类型 → 切算法水位，不切 TS 水位。张三 array-hash L3 + dp L0 完全合理。

### 2.2 心流调节（agent 自动做，透明）

> **挑战难度 ≈ 当前水位 + 10-20%**
> 三条件：挑战-技能匹配、清晰目标、即时反馈

Agent 读你的信号，调整当前类型的难度梯度：

| 信号 | 判断 | 动作 |
|---|---|---|
| 连续 3 道同类 AC + 讨论清晰 | 水位稳固 | 升一档 |
| 单道 AC 但代码冗余 / 讨论卡本质 | 水位虚高 | 保持，加同难度 |
| 单道卡 + 卡算法本质 | 水位虚标 | 降一档 |
| 单道卡 + 卡 TS 语法 | 算法 OK，TS 盲点 | **不动算法水位**，立 TS 卡 |
| 换类型首道卡 | L0 正常现象 | 保持 Easy 继续探底 |

### 2.3 Agent 的判断信号来源

从粗到细：

1. **AC/WA/TLE** — `git log` 的 solve/review 提交
2. **代码质量** — agent 读 `ts/problems/<id>/solution.ts`：一遍过？冗余？边界？复杂度？
3. **讨论质量** — agent 读 `ts/docs/daily/*.md` 反馈区 + `ts/docs/pitfalls/`：讲得清本质？卡表层还是深层？
4. **跨期一致性** — git log 的 review 时间戳 + 复习结果（闭卷能重写？）
5. **你的主观收尾** — 一字反馈：**爽 / 一般 / 卡**

### 2.4 透明且可反驳

Agent 每次调水位 / 换难度，必须在 OVERVIEW 和 Session 开场**说清依据**。你可以反驳 → agent 收回。
**不是独裁，是辅助判断。**

### 2.5 Session（份）协议

- "**份** (unit)" = 一次坐下来练的一批题
- 大小由 `--units N` 控制，默认 `plan.dailyMin`（= 2）
- 一天可以多份、也可以不开；跨天就新文档
- **唯一绑日历的是复习**（遗忘曲线需要真实间隔）

**开场**（agent 必做）：
1. 读 OVERVIEW + 最近 daily + git log
2. 讲**选题依据**：为什么是这类型、这档难度、这几道
3. 讲**本份目标**：AC 几道？带回什么新东西？
4. 得到你同意再 `npm run day`

**收尾**（agent 必做）：
- 一字反馈：**爽 / 一般 / 卡**
- 刷新 OVERVIEW 的水位 + 近期信号
- 不逼复盘，愿意多说再记

---

## 三、算法侧水位图

> Agent 维护，不手改。切类型顺序只是建议，你随时可以说"下次换 X"。

| 类别 | 水位 | 必做进度 | 近期信号 |
|---|---|---|---|
| 数组与哈希 `array-hash` | **L1** | 2/15 正式 + 6 裸写 | #1 / #121 已 solve commit；**Session 2 延伸裸写 6 道**（#169 #136 #242 #217 #350 #448 全 AC，代码在 `ts/src/<id>.ts`，未走 scaffold / solve，**未入复习循环**）；**本份核心目标 2/2 未达**（#169 用哈希不是 Boyer-Moore、#136 用排序不是 XOR） |
| 双指针 `two-pointers` | L0 | 0/? | 未开始 |
| 滑动窗口 `sliding-window` | L0 | 0/? | 未开始 |
| 二分 `binary-search` | L0 | 0/? | 未开始 |
| 栈与队列 `stack-queue` | L0 | 0/? | 未开始 |
| 链表 `linked-list` | L0 | 0/? | 未开始 |
| 树与递归 `tree-recursion` | L0 | 0/? | 未开始 |
| BFS/DFS/图 `bfs-graph` | L0 | 0/? | 未开始 |
| 并查集 `graph-unionfind` | L0 | 0/? | 未开始 |
| 回溯 `backtracking` | L0 | 0/? | 未开始 |
| 动态规划 `dp` | L0 | 0/? | **⚠ Java 时代 #47 卡过，切入时降档起步** |
| 堆与贪心 `heap-greedy` | L0 | 0/? | 未开始 |
| 综合 `advanced-mixed` | L0 | 0/? | 未开始 |

---

## 四、TS 侧覆盖度

**已覆盖**（有卡片沉淀）：

- 类型收窄 / 控制流分析 → [PF-0001](ts/docs/pitfalls/PF-0001-map-has-get-narrowing.md)
- 变量绑定 const/let/readonly（含 Java `final` 对照）→ [PF-0002](ts/docs/pitfalls/PF-0002-const-let-vs-java-final.md)

**未覆盖**（可以刻意在后续题目里暴露）：

- 泛型（函数泛型 / 约束 `extends` / 推断）
- 工具类型（`Pick` / `Omit` / `Partial` / `Record` / `ReturnType` / `Awaited`）
- 字面量类型 + `as const`
- 类型谓词 `is` / 断言 `as` / 非空断言 `!`
- 索引签名 / 映射类型 / 条件类型
- 可选属性 / 可缺失 / `noUncheckedIndexedAccess`
- 异步 / `Promise<T>` 类型
- 枚举 `enum` vs 字面量联合（为什么联合更好）
- `unknown` vs `any` vs `never`

---

## 五、当前位置 & 下一份建议

**当前主练**：数组与哈希（`array-hash`）
**水位**：L1（2/15 正式 + 6 裸写待补流程）
**累计 TS 卡**：2 张
**待复习**：2 道（2026-10-09 r1：#1 + #121）
**本天已开 Session**：2 份（Session 2 有裸写延伸，见下）

### 当前阻塞点（换电脑重开会话必读）

**Session 2 之后用户延伸裸写了 6 道**，现在是「半完成」状态：

| 题 | AC | 走法 | 问题 | 文件 |
|---|---|---|---|---|
| #169 | ✓ | 哈希计数 | **本份目标是 Boyer-Moore，偷懒写哈希了** | `ts/src/169.ts` |
| #136 | ✓ | 排序+找孤岛 | **本份目标是 XOR，完全没碰** | `ts/src/136.ts` |
| #242 | ✓ | Map 加减抵消 | 解法 OK，没走 scaffold | `ts/src/242.ts` |
| #217 | ✓ | 排序找相邻 | 可改 Set O(n)；没走 scaffold | `ts/src/217.ts` |
| #350 | ✓ | 排序双指针 | 解法 OK；没走 scaffold | `ts/src/350.ts` |
| #448 | ✓ | Set + 遍历 | **有经典「原地标记」O(1) 空间解未带回** | `ts/src/448.ts` |

**已处理**：

- ✅ XOR + Boyer-Moore 原理落档到 `ts/docs/tasks/pattern-array-hash.md` 底部「番外 A / B」
- ✅ 「原地标记法」补齐学习卡（同文件「番外 C」，含三前提 / #442 完整例 / TS 越界坑 / 自测反问）
- ✅ Session 2 文档收尾已更新（反馈与讨论 · 水位判断正在其中）

**未处理（下一份要做）**：

1. 读完预习卡「番外 A · XOR」→ **闭卷**重写 #136 → `npm run new -- 136 single-number Easy array-hash` → `npm run solve -- 136 --first --min <耗时>`
2. 读完预习卡「番外 B · Boyer-Moore」→ **闭卷**重写 #169 → scaffold + solve
3. 读预习卡「番外 C · 原地标记」→ **闭卷**重写 #448（重点：两个循环的分工，别在第一个循环里找答案）→ scaffold + solve
4. ☆可选：#217 / #350 走正式 scaffold（是否重写看心情；解法没大问题，没入复习循环是唯一损失）
5. ☆可选：#242 小优化（先判 `s.length !== t.length`、`s[i]` 替 `s.charAt(i)`）+ scaffold

**不建议**：直接把 `ts/src/*.ts` 裸写解法照搬到 scaffold 里跑 solve——这样 r1 复习时你会重复一次次优解，强化错误肌肉记忆。

## 六、路径规划（粗粒度，不绑日期）

初步顺序（agent 根据水位信号主动提议切换，你 confirm）：

1. **array-hash** → `two-pointers`（和数组衔接自然）→ `sliding-window`
2. `binary-search` → `stack-queue` → `linked-list`
3. `tree-recursion` → `bfs-graph`
4. `backtracking` → **`dp`（历史痛点，拆细推进）**
5. `heap-greedy` → `graph-unionfind` → `advanced-mixed`

**不是日程表**，是建议顺序。中间随时可以切。

---

## 七、维护说明

- 本文件在 Session 收尾时由 agent 刷新
- 不手改（想调整路径规划或水位分档可以手改）
- 想看会话历史：`ts/docs/daily/`
- 想看 TS 坑点卡：`ts/docs/pitfalls/`
- 项目愿景与长期验收：`ROADMAP.md`
- Agent 契约（新会话读）：`AGENTS.md`

---

## 更新日志

| 日期 | 事件 |
|---|---|
| 2026-10-08 | 建立本文件；废弃 STATE.md / ts/PROGRESS.md；心流 + 二维水位模型落地；**Session 1** (#1 + #121) solve 完成；**Session 2** 追加 (#169 + #136 带新套路)；`day.mjs` 支持同日 Session 追加 |
| 2026-10-08 | **Session 2 裸写延伸 6 道**（#169 #136 #242 #217 #350 #448）全 AC 但未走流程；XOR + Boyer-Moore 原理落档到 pattern-array-hash.md 番外；Session 2 收尾更新至真实状态；拉临时分支 `wip/handoff-20261008` 用于换电脑交接 |
