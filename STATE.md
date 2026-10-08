# STATE — 当前指针

> **作用**：新会话读 `AGENTS.md` 接手协议后立刻读这里，就知道"现在在哪、下一步干嘛"。
> **规则**：每次会话结束前更新本文件。**只写事实，不写计划愿景**（愿景在 ROADMAP）。

---

## 当前

| 字段 | 值 |
|---|---|
| 更新日期 | 2026-10-05（周一） |
| 阶段 | **点火期 · L0 手写热身**（按类型分库，无日期） |
| 当前类型 | 数组与哈希 `array-hash`（必做 0/15；`npm run next` 实时确认） |
| 总进度 | **累计 0 道题**（无上限；必做 170 道为主线，手写 drill 6 个可穿插） |
| 当前 level | **L0 冷启动** |
| 累计 TS 题解 | 0 道 |
| 周赛记录 | 未开始 |
| 待复习 | 无（还没有已解出的题） |

## 下一步（按类型分库，按顺序做，不按日期）

- [ ] 新会话：AI 读 `ROADMAP`/`STATE`/最近 `daily`/git → `cd ts && npm run day` 起今日文档 → AI 补全讲解
- [ ] 按今日文档做题：`npm run new` → 写 → `npm test` → LeetCode Accepted → `npm run solve`
- [ ] 手写 drill 穿插（从 `drill-reverse-list` 起，闭卷，`npm run done -- drill-reverse-list`）
- [ ] 今日复习题：闭卷重写后 `npm run solve -- <id>`
- [ ] 收尾：AI 回填「复盘 & 对计划的调整」，更新 `STATE.md` / `ROADMAP.md`
- [ ] 想多做：`npm run day -- --units 4`；换类型：`--pattern <id>`

## 工具链现状（已就绪 ✅）

- `npm run plan`：从 `syllabus.json` 按类型分库（必做 + 备选，无上限）→ `data/plan.json`
- `npm run day`：生成今日任务文档 `docs/daily/<日期>.md`（会话起点）；`npm run next` 只看下一批
- `.agents/skills/daily-study/SKILL.md`：每日会话 SOP（开场 / 中场 / 收尾 + 难度校准）
- `npm run learn -- <pattern>`：回看预习卡；`npm run task`：生成全部 drill / 预习卡文档
- `npm run done -- <drill-id|pattern-id>`：手动勾掉预习卡 / 手写 drill（做题由 `solve` 自动计）
- `npm run solve`：记录完成/回刷并**自动 git commit**，复习时间线以 git 为准
- `ts/data/`：真实题库（3288 题）+ 课程大纲（13 类型预习卡 + 6 drill）
- `ts/docs/tasks/`：每类 / 每题的记录 + 复习参考标准
- 记忆录：仓库根 `AGENTS.md`（新会话自动加载）

## 已确认的方向（来自用户）

1. **不假设「Java 时期全会」**：数据结构也会退化（例：堆排序已完全想不起），要当新内容恢复
2. **按类型分库，不设题量上限**：每类由易到难，保底几道、有精力多做；不按日期

## 待用户确认的假设（按推荐默认写的）

1. 每日一题在 **LeetCode 官网**做；pattern 专项在 **本地 `ts/`** 做
2. 周赛 **第 4 周起**、先虚拟后真实
3. 最低配日 **15–30min**
4. 同日仓库放 `ts/` 子目录（不另开仓库）

## 阻塞 / 备注

- 今天是国庆假期（弹性日），时间宽裕
- 无阻塞

---

## 更新日志（新往上加）

| 日期 | 阶段 | 进度 | 备注 |
|---|---|---|---|
| 2026-10-05 | L0 点火 | 0 | 新增**今日任务文档**（`npm run day` → `docs/daily/`）+ `daily-study` skill（会话开场/中场/收尾 + 难度校准），把「每天新会话怎么开始」固化成流程 |
| 2026-10-05 | L0 点火 | 0 | 改为**按类型分库**（必做 + 备选、由易到难、无上限）、加**预习卡 + 手写 drill**、写 `ROADMAP.md` 与 task 文档体系 |
| 2026-10-05 | W1 点火 | 0 | 计划建立，骨架搭建 |
| 2026-10-05 | W1 点火 | 0 | 接入真实题库+课程大纲，新增 `npm run today` 今日清单与 `AGENTS.md` 记忆录 |