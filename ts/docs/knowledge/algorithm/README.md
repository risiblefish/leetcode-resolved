# 算法侧 · 套路卡 + 手写 drill

> **知识块**：算法　|　上层索引：[`../README.md`](../README.md)　|　姊妹库：[`../language/`](../language/)（TS 语言）
>
> **解决的问题**：不是「做了多少题」，而是「**做到什么程度才算过、我卡在哪、下次怎么复习**」。
> 每个手写 drill、每个类型的套路卡都有一份文档：做的时候留白，做完回填，形成可对照的**参考标准**。

## 文档在哪

| 类型 | 文档位置 | 生成方式 |
|------|----------|----------|
| 手写 drill | `docs/knowledge/algorithm/drill-<NN>-<名字>.md` | `npm run task` / `npm run task -- <drill-id>` |
| 类型套路卡 | `docs/knowledge/algorithm/pattern-<id>.md` | 同上 |
| 做题 | `problems/<编号>-<slug>/notes.md` | `npm run new -- ...` 或 `npm run next -- --scaffold` |
| TS 语言坑点 | `docs/knowledge/language/PF-<4位编号>-<slug>.md` | 收尾时手写（见 [../language/README.md](../language/README.md)） |

> 常用：**用户同意后**用 `npm run day` 起今日任务文档，再 `npm run day -- --units N` 调量；只想看题目用 `npm run next`。
> 建做题目录：`npm run next -- --scaffold` 或逐个 `npm run new -- ...`。
> 全部 drill + 预习卡文档用 `npm run task` 一次性生成（已存在的不覆盖）。

## 一道题的完整流程

```
看 npm run day             # 起今日文档：主题 / 讲解 / 题目 / 复习
  ↓ （AI 补全讲解）
在「我的思路」用自己的话写一遍        ← 先写再做，哪怕错
  ↓
硬扛 25–30 分钟
  ├─ 卡住 → 把卡点写进「卡点」，找 AI 讨论（只问为什么/概念/TS 语法）
  ↓
tests 全绿 + LeetCode Accepted
  ↓
填「提交结果」；把解法交给 AI 复盘 → 写进「AI 复盘」「可改进点」
  ↓
npm run solve -- <题号> [--first] --min N     # 计进度 + 排下次复习
```

## 文档结构（做题）

```md
# <题号> <中文名> / <英文名>
<URL> · 难度 · 类型

## 这份要学 / 复习什么（薄预习）   ← 预习卡自动带出：核心思想 / 识别信号 / 易错边界
## 目标（DoD）                     ← 5 条验收，缺一不算完成
## 我的思路（先写再做 · 留白）
## 卡点（留白）
## 提交结果
## AI 复盘（AC 之后也值得看 · 留白）
## 可改进点 / 标准要点（留白）      ← 下次复习的对照标准
## 复习记录                        ← 每次练习留一行
```

## 复习时怎么用（关键）

复习**不是重看代码**，而是回到这份文档：

1. 只看「这份要学 / 复习什么」和「可改进点 / 标准要点」；
2. **闭卷重写**解法（写不出再看旧代码）；
3. 对照标准要点，检查自己这次是否漏了边界、是否更快；
4. 在「复习记录」写一行：日期 / 第几次 / 是否闭卷写出 / 备注。

这样「做到什么程度」就有了锚：**能闭卷写出 + 边界不漏 + 说得出识别信号**，才算真正掌握（累计 7 次活动 → `mastered`）。

## 当前已有

**手写 drill（6）**

| 文档 | 状态 |
|------|------|
| [drill-01-reverse-list](drill-01-reverse-list.md) | 待做 |
| [drill-02-binary-search](drill-02-binary-search.md) | 待做 |
| [drill-03-heap](drill-03-heap.md) | 待做 |
| [drill-04-quicksort](drill-04-quicksort.md) | 待做 |
| [drill-05-mergesort](drill-05-mergesort.md) | 待做 |
| [drill-06-lru](drill-06-lru.md) | 待做 |

**类型套路卡（13）**

| 文档 | 文档 | 文档 |
|------|------|------|
| [array-hash](pattern-array-hash.md) | [two-pointers](pattern-two-pointers.md) | [sliding-window](pattern-sliding-window.md) |
| [stack-queue](pattern-stack-queue.md) | [binary-search](pattern-binary-search.md) | [linked-list](pattern-linked-list.md) |
| [tree-recursion](pattern-tree-recursion.md) | [bfs-graph](pattern-bfs-graph.md) | [backtracking](pattern-backtracking.md) |
| [heap-greedy](pattern-heap-greedy.md) | [dp](pattern-dp.md) | [graph-unionfind](pattern-graph-unionfind.md) |
| [advanced-mixed](pattern-advanced-mixed.md) | | |

> 做题文档随进度用 `npm run next -- --scaffold` 生成，不预先铺满。