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
- [ ] 已问用户并获同意- [ ] **push 前：**合并本地 ahead 的所有 commit 成 1 个，message 用 `study(ts): ...` 格式（见下文「流程模型」节）

---

## 流程模型：**本地多 commit，push 前合并**

> 本地 commit 随意，是复习时间线 + 记录了怎么做的自由申请系统；
> **push 前必须合并**，交上带到 origin 的是「本份工作」的最终资产版本。
> **push 本身只能用户来**，agent 不 push。

### 本地 commit（多 commit 欢迎）

- `npm run solve` 自动生成 `solve(ts): ...` 和 `review(ts): ...` —— 复习时间线的锚点，切勿关掉
- 自己手动小 commit（`docs:` / `fix:` / 调试现场 snapshot）也欢迎
- 在本地 ahead 的状态里，agent 可以连续跑 solve、补 PF、改 notes——都会成为独立的本地 commit

### Push 前（用户说「要 push 了」或 agent 判断「本份已封版」）

1. agent 确认门槛 1 + 门槛 2 都过（见上文）
2. agent 把本地 ahead 的多个 commit **合并成 1 个**：
   ```bash
   git reset --soft @{u}         # 退回到远程 HEAD，所有改动保留在 index
   git commit -m "<单条 message>"  # 封版成 1 个 commit
   ```
3. agent **停在这里**，不执行 `git push`
4. 用户自己 `git push origin ts`

### 合并后 commit 的 message 格式

```
study(ts): <日期或日期范围> <本份主题>

<正文：2-5 条最小单位的输出，每条 1 行；
详情留在 daily / notes / OVERVIEW 里，别把商业小说塞到 commit message 里。>
```

示例：

```
study(ts): 2026-10-13~10-19 追账第 1 份

- 10-13: 4 道 r1 复习 AC + PF-0006/0007 立卡 + #53 升 O(1)
- 10-19: #238 前后缀乘积 AC（朴素 → O(1)，踩 null + PF-0005 复发）
- 10-19: #1 / #121 r2 复习一次 AC
- PF-0008 新立：IEEE 754 的 `-0` 坐 vitest.toEqual 和 LeetCode
```

### 关键约定：**合并 commit 的 subject 不允许是 `solve(ts): ...` 或 `review(ts): ...`**

**为什么**：`scripts/lib.mjs:getActivityDates` 用正则 `^(?:solve|review)\(ts\):\s*(\d{4})` 匹配 git log 获取每道题的复习活动日期。
**如果合并 commit 匹配上**：该 commit 的单一日期会被归到「批量所有题」的复习历史，覆盖掉原本本地的 `solve/review(ts): 0217 ...` 单次 commit 的真实日期 → **复习间隔计算全锅。**
**保开「合并 subject 用 `study(ts)`」这条约定**：
- `study(ts): ...` 不匹配正则 → `getActivityDates` 忽略它
- `activityDatesFor` 回退到 `meta.json` 的 `solvedOn + reviews` 数组 → **历史正确保留**

**验证已做（2026-10-19）**：7 道题的 meta.json `reviews` 数组全部完整，合并安全。

### 支持命令速查

```bash
git log --oneline @{u}..HEAD        # 看本地 ahead 哪些 commit
git log --oneline @{u}..HEAD | wc -l # 数量
git status                           # 当前改动
git diff @{u} --stat                 # 本地改过哪些文件
git reset --soft @{u}                # 合并前调回远程 HEAD
```
