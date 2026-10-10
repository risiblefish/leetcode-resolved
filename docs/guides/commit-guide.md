# Commit 指南（给一次 task / 一天封版）

commit = 把这次 task 封版：**代码 + 文档一起成为事实**。
**一次询问 + 两个硬门槛，都满足才能 commit。**

## 门槛 0：先问用户

commit 前先问「现在提交吗？」，得到明确同意才提交。**不要自主 commit。**

> 例外：`npm run solve` 的**自动提交**（`solve(ts): ...`）是复习时间线的一部分，不询问。

## 门槛 1：文档已同步

逐项确认该改的都改了（清单见 [start-task.md](start-task.md) 的「文档同步」）：

- 今日 `ts/docs/daily/<日期>.md`：「复盘 & 对计划的调整」已回填。
- 每题 `notes.md`：思路 / 卡点 / 结果 / AI 复盘 / 可改进点。
- `OVERVIEW.md`：水位 / 近期信号 / 当前位置 / 下一份建议已刷新。
- `ROADMAP.md`：**仅在路线真的变了**时改；没变就不动。
- **文档没同步 → 不许 commit。**

## 门槛 2：验收通过

- `npm test` 全绿（示例 + 边界 + 反例）。
- `npm run typecheck` 无错（strict，不留 `any`）。
- LeetCode 官网 **Accepted**。

## 步骤

1. 问用户「现在提交吗」，得到同意（门槛 0）。
2. 确认文档已同步（门槛 1）+ 验收通过（门槛 2）。
3. `git status` + `git diff` 通读本次改动。
4. **文档 + 代码一个 commit**（daily 文档 / notes / `ROADMAP.md` / `OVERVIEW.md` 与代码一起提）。
5. message 格式：`study(ts): <日期> <今日主题>`
   - 例：`study(ts): 2026-10-05 数组与哈希 · 两数之和`
   - 单题由 `npm run solve` 自动生成：`solve(ts): 0001 two-sum Easy` / `review(ts): 0001 two-sum (r1)`。
6. 不进版本库：`ts/NEXT.md`、`ts/PROGRESS.md`（`.gitignore` 已排）。

## 收尾即封版清单（勾完再提交）

- [ ] 今日 daily 复盘已填
- [ ] notes.md 留白已填
- [ ] `OVERVIEW.md` 已刷新
- [ ] `ROADMAP.md`（如路线有变）
- [ ] `npm test` + `npm run typecheck` 通过
- [ ] 已问用户并获同意