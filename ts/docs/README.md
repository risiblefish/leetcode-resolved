# ts/docs — 文档地图

刷题系统全在 `ts/` 下。文档分三层：

## 1. 路线与状态（给「人」和「新会话」看）

| 文件 | 作用 |
|------|------|
| [`../../ROADMAP.md`](../../ROADMAP.md) | 一年的总路线：定位/硬约束、横切关注点、L0–L4 领域清单、分库计划与进度口径 |
| [`../../STATE.md`](../../STATE.md) | 当前指针：阶段、当前类型、卡点、待确认假设 |
| [`../../AGENTS.md`](../../AGENTS.md) | 新会话接手契约：读到它就知道该跑什么命令、给什么清单 |
| `../NEXT.md` | 由 `npm run next` 生成：下一批题（含预习卡 / 题目 / 验收） |
| `../PROGRESS.md` | 由 `npm run dash` 生成：streak / XP / 各 pattern 进度 / 复习到期 |

## 2. 计划数据（给「脚本」读）

| 文件 | 作用 |
|------|------|
| `../data/syllabus.json` | 课程大纲：13 个类型的预习卡 + 6 个手写 drill + 必做题 |
| `../data/plan.json` | 由 `npm run plan` 分好的类型题库（必做 + 备选，无上限） |
| `../data/problems.json` | 真实题库（3288 题，leetcode.cn 同步） |
| `../data/tags.json` | 标签 → 题号 |
| `../data/progress.json` | 手动勾掉的（预习卡 / 手写 drill） |

## 3. 学习记录（给「复习」看）

| 位置 | 作用 |
|------|------|
| [`daily/`](daily/) | **今日任务文档**（会话起点）：今日主题 / 讲解 / 题目 / 练习反馈 / 今日复习 / 复盘与计划调整 |
| [`tasks/`](tasks/) | 各类型 / drill 一份 task 文档：要学什么、关联题目、留白、AI 复盘、复习标准 |
| `../problems/<编号>-<slug>/notes.md` | 做题的记录（结构与 task 文档一致） |

> 每天的学习流程见 `.agents/skills/daily-study/SKILL.md`；task 文档详见 [`tasks/README.md`](tasks/README.md)，会话文档详见 [`daily/README.md`](daily/README.md)。
> 起 task / 封版 commit 清单见 [`../../docs/guides/`](../../docs/guides/)。