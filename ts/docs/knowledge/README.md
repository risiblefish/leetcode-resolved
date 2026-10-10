# ts/docs/knowledge — 知识库（两块，互相独立）

> **为什么分两块**：算法水位和 TS 水位是**两条独立的轴**（见 `AGENTS.md` §1.1）。
> 换算法类型 → 切算法水位；但 TS 的概念不会因此清零。
> 所以知识也要分库存，否则「找一张 DP 卡」和「找一张类型收窄的卡」会混在一起。

## 两个子库

| 子库 | 装什么 | 命名 | 索引 |
|---|---|---|---|
| [`algorithm/`](algorithm/) | **算法侧**：套路卡（pattern primer × 13）+ 手写基本功 drill（× 6） | `pattern-<id>.md` · `drill-<NN>-<名字>.md` | [algorithm/README.md](algorithm/README.md) |
| [`language/`](language/) | **TS 语言侧**：反直觉坑点卡（pitfall） | `PF-<4位编号>-<slug>.md` | [language/README.md](language/README.md) |

> 每份文档的第 3 行（或 H1 之后）都有 `**知识块**：算法 · …` / `**知识块**：TS 语言 · …` 的标识，
> 单看文件也能知道它属于哪一块。

## 怎么判断一张新卡放哪

| 你想记的东西 | 放哪 | 例子 |
|---|---|---|
| 一类题的识别信号 / 套路 / 模板 | `algorithm/pattern-<id>.md` | 「看到『原地修改 + 值域 1..n』→ 原地标记」 |
| 要闭卷默写的基本功（模板肌肉记忆） | `algorithm/drill-<NN>-*.md` | 链表反转、手写堆、LRU |
| TS 的语法/类型系统反直觉点 | `language/PF-xxxx-*.md` | 「`Map.has` 判空后 `.get()` 还是 `V \| undefined`」 |
| 一道具体题的记录 | ❌ 不放这 | `problems/<编号>-<slug>/notes.md` |
| 一次会话的过程与反馈 | ❌ 不放这 | `ts/docs/daily/<日期>.md` |

**一句话**：**跨题可复用的知识**才进 knowledge；**绑在一道题/一次会话上**的留在原地。

## 维护规则

- **落卡时机**：会话**收尾时**统一整理，中途只在 daily 反馈区记一句「疑问 + 我以为 + 实际 + TODO: PF」
- **什么值得立卡**：反直觉 **+** 下次还会撞。一问就通的不立
- **双向引用**：新卡 → 在原题 `notes.md` 写 `踩坑：PF-xxxx` / `见 pattern-*.md 番外 X`；`notes.md` → 卡里写 `source`
- **算法卡在长**：番外（XOR / Boyer-Moore / 原地标记…）就长在 `pattern-*.md` 底部，不另开文件；长到「独立成类」时才拆
- **不要建平行体系**：这里是唯一的算法/语言知识入口，别在别处再开一个 `notes/`、`tips/` 之类

## 历史沿革

| 日期 | 事件 |
|---|---|
| 2026-10-08 | 原始布局：`ts/docs/tasks/`（算法套路 + drill）+ `ts/docs/pitfalls/`（TS 坑点） |
| 2026-10-10 | **合并为 `ts/docs/knowledge/{algorithm,language}/`**，两份 README 变子库索引，每份文档加「知识块」标识（原 `tasks/` + `pitfalls/` 两个目录已移除，git 历史保留） |