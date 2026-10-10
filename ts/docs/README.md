# ts/docs — 文档地图

刷题系统全在 `ts/` 下。文档分三层：

## 1. 路线与状态（给「人」和「新会话」看）

| 文件 | 作用 |
|------|------|
| [`../../ROADMAP.md`](../../ROADMAP.md) | 一年的总路线：定位/硬约束、横切关注点、L0–L4 领域清单、分库计划与进度口径 |
| [`../../OVERVIEW.md`](../../OVERVIEW.md) | **当前指针（主线）**：决策模型 / 水位图 / 当前位置 / 下一份建议（收尾时刷新） |
| [`../../AGENTS.md`](../../AGENTS.md) | 新会话接手契约：读到它就知道该跑什么命令、给什么清单 |
| `../NEXT.md` | 由 `npm run next` 生成：下一批题（含套路卡 / 题目 / 验收） |
| `../PROGRESS.md` | 由 `npm run dash` 生成：streak / XP / 复习到期（**已废弃，勿手改**；进度看 `OVERVIEW.md`） |

## 2. 计划数据（给「脚本」读）

| 文件 | 作用 |
|------|------|
| `../data/syllabus.json` | 课程大纲：13 个类型的套路卡 + 6 个手写 drill + 必做题 |
| `../data/plan.json` | 由 `npm run plan` 分好的类型题库（必做 + 备选，无上限） |
| `../data/problems.json` | 真实题库（3288 题，leetcode.cn 同步） |
| `../data/tags.json` | 标签 → 题号 |
| `../data/progress.json` | 手动勾掉的（套路卡 / 手写 drill） |

## 3. 学习记录（给「复习」看）

| 位置 | 作用 |
|------|------|
| [`daily/`](daily/) | **今日任务文档**（会话起点）：今日主题 / 讲解 / 题目 / 练习反馈 / 今日复习 / 复盘与计划调整 |
| [`knowledge/`](knowledge/) | **知识库（两块独立）**：[`algorithm/`](knowledge/algorithm/) 算法套路卡 + 手写 drill · [`language/`](knowledge/language/) TS 坑点卡 |
| `../problems/<编号>-<slug>/notes.md` | 做题的记录（结构与套路卡一致） |

> 每天的学习流程见 `.agents/skills/daily-study/SKILL.md`；知识库怎么分、怎么维护见 [`knowledge/README.md`](knowledge/README.md)，会话文档详见 [`daily/README.md`](daily/README.md)。
> 起 task / 封版 commit 清单见 [`../../docs/guides/`](../../docs/guides/)。
> **代码住哪（唯一规则）**：`ts/src/<id>.ts` = 草稿区（收件箱，不进复习曲线）；`ts/problems/<id>-<slug>/` = 正式区（进复习曲线）。转正 = 移动 + 删副本，见 `AGENTS.md` §3.1。