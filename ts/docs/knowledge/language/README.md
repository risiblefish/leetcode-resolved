# TS 语言侧 · 踩坑卡（Pitfalls）

> **知识块**：TS 语言　|　上层索引：[`../README.md`](../README.md)　|　姊妹库：[`../algorithm/`](../algorithm/)（算法套路）
>
> 练习中遇到的「反直觉 / 容易再踩」的 TS 问题，一坑一卡。
> 卡片是**知识点维度**，不是题目维度；一张卡可能被多道题触发。
>
> **落卡时机**：daily 收尾时统一整理，不在练习中途打断节奏。
> **什么值得立卡**：反直觉、下次做题还会撞见的；一问就通的小问题不必立。

## 结构

- 位置：`docs/knowledge/language/`
- 文件名：`PF-<4位编号>-<slug>.md`（编号全局递增，**不重用、不重排**，旧编号是稳定 ID）
- 必填 front matter：`id` / `title` / `tags` / `source` / `created`
- 固定小节：**现象 → 一句话根因 → 修法（≥2 条）→ 自测反问 → 延伸**

## 关联

- `problems/<id>/notes.md`：踩坑行写 `踩坑：PF-xxxx`，反向引用
- `docs/daily/<日期>.md` 反馈区：现场只记一句「疑问 + 我以为 + 实际 + TODO: PF」，收尾时提炼成卡

## 索引

| ID | 标题 | 标签 | 来源 |
|---|---|---|---|
| [PF-0001](./PF-0001-map-has-get-narrowing.md) | Map.has 判空无法让 .get() 的 undefined 消失 | type-narrowing, strict-mode, map-set | 2026-10-08 · #1 two-sum |
| [PF-0002](./PF-0002-const-let-vs-java-final.md) | TS 的 const/let 和 Java 的 final 怎么对应 | variable-binding, immutability, java-vs-ts | 2026-10-08 · #1 two-sum review |
| [PF-0003](./PF-0003-no-late-assignment-in-strict-ts.md) | TS 没有「先声明后赋值」的免费午餐（strict 下 `let x;` 就是 undefined） | strict-mode, type-inference, java-vs-ts, definite-assignment | 2026-10-10 · #169 majority-element |

## 标签表（出现一次就登记一次，便于以后按标签复习）

- `type-narrowing`：类型收窄 / 控制流分析
- `strict-mode`：严格模式特有
- `map-set`：Map/Set/WeakMap 相关
- `variable-binding`：变量绑定（const/let/var）
- `immutability`：不可变性（readonly / as const / Object.freeze）
- `java-vs-ts`：Java 经验容易误导的对比点
- `type-inference`：类型推断（演化型 any / 字面量收窄 / typeof）
- `definite-assignment`：明确赋值断言 `let x!: T`、`strictPropertyInitialization`
