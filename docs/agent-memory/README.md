# Agent Memory · 记忆与设置（可搬运）

> ⚠️ **本目录是冻结快照，已落后于仓库现况（勿当资料读了不改）**：
> - 快照时间：`93adb8c`（2026-10-08 落地 TS 训练场骨架时导出）
> - 其后 live 版本已大改：`STATE.md` **已废弃**（功能并入 `OVERVIEW.md`）；`ts/docs/tasks/` + `ts/docs/pitfalls/` 已迁为 `ts/docs/knowledge/{algorithm,language}/`；会话 SOP 改为「二维水位 + 份制」
> - **要接手会话请读仓库根 `AGENTS.md` / `OVERVIEW.md`**，不要读本目录的副本
> - 本目录只在「搬到没有 git 历史的新环境」时才用；除非真要做搬运，**不必维护、不必同步**

> 本目录是「AI 会话记忆 + 工作设置」的**可搬运快照**，用于在另一台机器 / 另一个环境里恢复同一套工作方式。
> **只读副本**：源头仍以仓库根 `AGENTS.md` / `OVERVIEW.md` / `ROADMAP.md`、`.agents/skills/`、`docs/guides/` 为准。

## 一、文件对照（搬到新环境时放哪）

| 本目录 | 新环境放置位置 | 作用 |
|---|---|---|
| `AGENTS.md` | 仓库根 `AGENTS.md` | 会话记忆录，AI 每次会话自动加载 |
| `STATE.md` | ~~仓库根 `STATE.md`~~（**已废弃**，改为 `OVERVIEW.md`） | 历史快照，仅参考 |
| `ROADMAP.md` | 仓库根 `ROADMAP.md` | 一年路线（给人类看） |
| `skills/daily-study/SKILL.md` | `.agents/skills/daily-study/SKILL.md` | 每日学习会话 SOP（开场 / 中场 / 收尾） |
| `guides/README.md` | `docs/guides/README.md` | 操作指南索引 |
| `guides/start-task.md` | `docs/guides/start-task.md` | 起 task 清单 |
| `guides/commit-guide.md` | `docs/guides/commit-guide.md` | 封版 commit 清单 |

## 二、在新环境加载

1. 按上表把文件拷进新仓库，**保持「放置位置」的相对路径**（`.agents/skills/...`、`docs/guides/...`）。
2. 新会话：先读 `AGENTS.md` → 再读 `STATE.md`，即可接手（见 `AGENTS.md` §1 会话接手协议）。
3. 工作区在 `ts/`，所有命令都在 `ts/` 下执行（命令速查见 `AGENTS.md` §8）。
4. 本快照只含「记忆 + 设置」；要恢复完整功能，还需一并搬运工作区：`ts/data/`（题库 / 大纲 / 计划）、`ts/scripts/`（工具链）、`ts/package.json`、`ts/tsconfig.json` 等。

## 三、维护方式

- 本目录是**手动快照**（非脚本生成），**且已冻结不再同步**（见顶部警告）。
- 想恢复「可搬运」状态：在确认 live 文件稳定后，用 `cp` 覆盖这里的副本（别忘了把 `STATE.md` 换成 `OVERVIEW.md`）。