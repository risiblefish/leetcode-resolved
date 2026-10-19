---
id: PF-0007
title: 类型位置的 `[]` 是空元组（长度严格为 0），不是空数组
tags: [tuple, type-vs-value, array-lib, never, java-vs-ts]
source: 2026-10-13 · #49 group-anagrams（r2）
created: 2026-10-13
---

# PF-0007 · `Map<string, []>` 里的 `[]` 不是"空数组"，是空元组

> **知识块**：TS 语言 · 坑点卡（pitfall）　|　**ID**：PF-0007　|　反向引用：做题文档里写「踩坑：PF-0007」

## 现象

想要一个「key → 字符串数组」的 Map，顺手写：

```ts
const curMap = new Map<string, []>();        // ← 本意：value 是 string[]
curMap.set("key", ["x"]);                     // ❌ Type 'string[]' is not assignable to type '[]'.

// 或者：
const arr = curMap.get("key") ?? [];
arr.push("word");                             // ❌ Argument of type 'string' is not assignable to parameter of type 'never'.
```

两条报错指向同一件事：**那个 `[]` 不是我以为的"空数组"。**

## 一句话根因

TS 里有两套系统：**类型位**（type position）和**值位**（value position）。
同一个符号 `[]`，在两边含义**完全不同**：

| 位置 | 代码片段 | `[]` 的含义 |
|---|---|---|
| **类型位** | `Map<string, []>` / `type T = []` / `const a: [] = ...` | **空元组类型**（Tuple of length 0）—— 一个长度严格为 0、元素类型是 `never` 的 tuple |
| **值位** | `const a = []` / `return []` / `map.set(k, [])` | **空数组字面值**（Empty array literal）—— 运行时的空数组，类型待推断（`any[]` / `never[]`） |

于是 `Map<string, []>` 的 value 类型 = "空元组"：

- 把 `string[]` 塞进去 → 类型不兼容（可变长 ≠ 固定长 0）
- 从里面取出来 push → `[].push()` 的参数被推成 `never`（元素类型是 `never`），`string` 不是 `never` → 报错

这不是 bug，是 TS 为了支持 tuple（有固定元素类型/长度的数组）而付出的符号代价。

## 修法（≥2 条）

**正解 = 类型位用 `T[]` 或 `Array<T>`：**

```ts
const curMap = new Map<string, string[]>();            // 推荐
const curMap2 = new Map<string, Array<string>>();      // 等价，更啰嗦
```

**真要 tuple**（固定长度 / 异构类型）才写出来：

```ts
// 一个点坐标：定长 2，必须都是 number
type Point = [number, number];
const pts = new Map<string, Point>();

// 一个"至少 1 个元素"的 tuple：
type NonEmpty<T> = [T, ...T[]];
```

**判断自己该用哪个**（一句话）：

- "装 n 个同类的东西，n 任意" → `T[]`
- "固定几个格子，每个格子类型可以不同" → tuple `[A, B, C]`
- "装 n 个 + 第一个要特殊" → `[A, ...A[]]`

## 自测反问

1. `type A = []` 和 `const a = []` 的区别在哪？分别推成什么？
   —— 前者 = 空元组类型（长度 0）；后者 = 空数组值，类型取决于上下文（`any[]` / `never[]`）。
2. `const a: [] = []; a.push(1)` 为什么报 `never`？—— tuple 的元素类型是 `never`（长度 0 ⇒ 没有元素，没有类型可容纳）。
3. 这些类型分别装什么？
   - `number[]` → 任意长度的 `number` 数组
   - `[number]` → 恰好 1 个 `number` 的元组
   - `[number, number]` → 恰好 2 个 `number` 的元组
   - `[number, ...number[]]` → 至少 1 个 `number`
   - `readonly number[]` → 不可变 `number` 数组
4. 从这题的报错 `... is not assignable to parameter of type 'never'`，为什么应该一秒想到"类型变成了元组/`never[]`"？
   —— `never` 是"没有合法值"的类型；一旦看到 push/set 的参数被推成 `never`，说明目标容器被 TS 认为**不能装任何东西** → 多半是空元组或 `never[]`。
5. 还有哪些"类型位 vs 值位"的符号陷阱？
   —— `null` / `undefined`（既是类型也是值）；`void`（类型位是"没有返回值"，运算符位是 `void 0` = undefined）；`typeof x`（类型位取类型，表达式位取 `"object"` 这类字符串）；`as const`。

## 延伸

- 反向引用：`ts/problems/0049-group-anagrams/notes.md`（r2 复习时的疑问）
- 同族（都源于"类型 vs 值"两套系统）：
  - [PF-0005](./PF-0005-new-array-is-any.md)：`new Array(n)` 返回类型是 `any[]`（库签名明写，不是你没标）—— 另一种"看起来对，其实类型位行为出乎意料"
  - [PF-0001](./PF-0001-map-has-get-narrowing.md)：`Map.has` 的类型谓词不收窄 `.get()` —— 类型系统 vs 运行时逻辑的另一道裂缝
- Java 对照：
  - Java 的泛型里只会写 `Map<String, List<String>>` —— **没有** tuple 概念，所以不会撞这个坑
  - Java 的 `String[]` 是正宗数组；Java 从没有"类型位的 `[]`" 这种句法
  - 这个坑属于 TS 独有，Java 老手一定会踩
- 一句话纪律：**类型位要装"可变长同类集合"时，永远写 `T[]`，别写 `[]`。** `[]` 留给"我真的要空元组"的场合（很少）。
- 相关 TS 概念：Tuple types / `never` / variance of readonly tuples / const assertions（`[1, 2] as const` 得到 `readonly [1, 2]`）
