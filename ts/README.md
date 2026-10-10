# TS 刷题工作区

一年养成计划见仓库根目录 [`../ROADMAP.md`](../ROADMAP.md)；当前进度（水位 / 位置 / 下一份）见 [`../OVERVIEW.md`](../OVERVIEW.md)；**AI 接手协议见 [`../AGENTS.md`](../AGENTS.md)**。

> **按类型分库，不按日期、不设题量上限**：题按类型（数组 / 链表 / DP …）分库并**由易到难**排列（`data/plan.json`）。每类先「必做」策展题，再「备选」无限加练；你按当天精力决定做几道，保底 `dailyMin` 道。

## 最快上手

```bash
cd ts
npm install
npm run day         # 起今日任务文档：docs/daily/<日期>.md（AI 先征求同意；每次新会话的起点）
npm run next        # 只想看下一批题（不写文档时）
```

## 一道题的完整流程

```bash
# 1. 起今日任务文档（AI 补全主题/讲解）
npm run day -- --units 2

# 2. 是手写 drill / 预习卡 → 文档已生成；做完勾掉
#    （不需要先勾掉 drill 才能刷题，热身可以穿插做）
npm run done -- drill-heap

# 3. 做题 → 建题，先写思路再写实现
npm run new -- 1 two-sum Easy array-hash
npm test

# 4. 全绿 + LeetCode Accepted 后记录（自动 git commit，据此排 Ebbinghaus 复习）
npm run solve -- 1 --first --min 15

# 5. 刷新看板
npm run dash
```

## 目录

```
ts/
  src/<id>.ts             草稿区（收件箱）：随手写的稿；转正后移动到 problems/ 并删掉本处
  src/types.ts            ListNode / TreeNode / 转换辅助（基础设施，非草稿，不删）
  data/problems.json      真实题库（3288 道免费题，leetcode.cn 同步）
  data/tags.json          官方标签 → 题目映射
  data/syllabus.json      课程大纲：13 类型预习卡 + 6 手写 drill + 必做题
  data/plan.json          类型分库计划（npm run plan 生成，无题量上限）
  data/progress.json      手动勾掉的清单（预习卡 / 手写 drill）
  docs/daily/             今日任务文档（每次会话一份）
  docs/knowledge/         知识库（两块独立）：algorithm/ 算法 · language/ TS 语言
  docs/knowledge/algorithm/  算法侧：pattern-*.md 套路卡 + drill-*.md 手写基本功
  docs/knowledge/language/   TS 语言侧：PF-*.md 坑点卡
  docs/README.md          文档地图
  problems/<id>-<slug>/   【正式区】solution.ts / solution.test.ts / notes.md / meta.json
  scripts/                next / plan / learn / done / task / new / solve / dash / sync-data / lib
  contest.json            周赛记录（周赛开始后手工追加）
  NEXT.md                 下一批题（自动生成）
  PROGRESS.md             看板（自动生成）
```

## 命令

| 命令 | 作用 |
|---|---|
| `npm run day [-- --units N\|--pattern <id>\|--force]` | 起今日任务文档（会话起点） |
| `npm run next [-- --quick\|--normal\|--sprint\|--units N\|--pattern <id>\|--scaffold]` | 只看下一批题（1 / 2 / 3 / N 道，可换类型，可自动建文档） |
| `npm run learn -- <pattern>` | 回看某个类型的预习卡 |
| `npm run task [-- <drill-id\|pattern-id>]` | 生成 task 文档（不带参数=全部） |
| `npm run done -- <drill-id\|pattern-id>` | 手动勾掉预习卡 / 手写 drill |
| `npm run new -- <id> <slug> <难度> [pattern]` | 建题 |
| `npm test` | 跑测试（完成判据） |
| `npm run typecheck` | tsc 类型检查 |
| `npm run check` | typecheck + test |
| `npm run solve -- <id> [--first] [--min N]` | 记录完成 / 回刷（自动 commit） |
| `npm run dash` | 刷新 `PROGRESS.md` |
| `npm run plan` | 重新分库（改过 syllabus 后） |
| `npm run sync` | 重新同步题库（数据旧了才需要） |

## 复习机制

git 是唯一时间线：每次 `npm run solve` 自动提交，看板读提交时间戳排复习。
间隔 **+1 / +3 / +7 / +15 / +30 / +60 天**，第 7 次活动即内化。回刷要**闭卷重写**；
复习前先看 task 文档里的「可改进点 / 标准要点」。

## 心法（摘自 ROADMAP）

1. **不假设「Java 时期全会」**：底层数据结构也会退化，当新内容恢复（如手写堆 / 堆排序）
2. 先自己想 25–30 分钟，卡住只看提示；AI 只答「为什么 / 概念 / TS 语法」，不给实现
3. 预习卡是薄的 10–15 分钟，看完默写模板就去做题（超过 20 分钟没写代码就停下）
4. 完成判据 = 测试全绿 + LeetCode Accepted + task 文档留痕
5. 翻译旧 Java 答案不算完成