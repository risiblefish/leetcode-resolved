---
id: PF-0008
title: IEEE 754 的 `-0` —— `=== 0` 为真，`Object.is` 为假，vitest.toEqual 还会炸
tags: [number, ieee754, negative-zero, vitest, java-vs-ts]
source: 2026-10-19 · #238 product-of-array-except-self
created: 2026-10-19
---

# PF-0008 · `-0 !== 0` 吗？在 JS/TS 的三套比较里**答案不一致**

> **知识块**：TS 语言 · 坑点卡（pitfall）　|　**ID**：PF-0008　|　反向引用：做题文档里写「踩坑：PF-0008」

## 现象

`#238 除自身以外数组的乘积` 本地测试炸了：

```
expected [ -0, +0, 9, -0, +0 ] to deeply equal [ +0, +0, 9, +0, +0 ]
```

代码没错，LeetCode AC 了。可本地 `vitest.toEqual` 不认账：

```ts
expect(solve([-1, 1, 0, -3, 3])).toEqual([0, 0, 9, 0, 0]);   // ❌ 挂
```

实际返回的是 `[-0, 0, 9, -0, 0]` —— **里面悄悄混了 `-0`**。

## 一句话根因

**IEEE 754 规定 `-0` 和 `+0` 是两个不同的位模式**（符号位不同），但 JS/TS 为了"大多数时候符合直觉"，故意让 `===` 把它们当相等。
于是**同一个值在三套比较里行为分裂**：

| 比较方式 | `-0` vs `+0` | 用在哪 |
|---|---|---|
| `===` / `==` | **相等** | 日常判断 |
| `Object.is(-0, +0)` | **不等** | 严格相等 |
| `vitest` 的 `.toEqual` / `.toBe` | **不等**（内部用 Object.is 系） | 单元测试 |
| `JSON.stringify(-0)` | 返回 `"0"`（**符号丢失**） | 序列化 → 这就是 LeetCode AC 的原因 |

**`-0` 怎么产生的**（本题轨迹）：

```
nums = [-1, 1, 0, -3, 3]
右扫 rPre = 1 → 3 → -9 → ?
                       ↑ i=2 时 rPre *= 0
                         IEEE 754：负数 × +0 = -0
                         rPre 变 -0，之后一路带着符号
```

- 负数 × 0 → `-0`
- `-0` 继续 × 任何数 → 符号由对方决定（但 × 0 永远是 `-0`）
- 一旦 `-0` 产生，它会**污染后续运算结果**，直到被一个"异号"的运算改掉

## 修法（≥2 条）

**修法 A：规范化测试断言（推荐）**

在测试文件顶部放一个 helper，把 `-0` 归一化成 `+0`，不污染 solution：

```ts
// -0 + 0 === +0（IEEE 754：-0 + +0 规则下结果是 +0）
const normalize = (arr: number[]) => arr.map(x => x + 0);

it("含 0 用例", () => {
  expect(normalize(solve([-1, 1, 0, -3, 3]))).toEqual([0, 0, 9, 0, 0]);
});
```

**修法 B：在 solution 里规范化**（侵入代码，一般不推荐）

```ts
ans[i] *= rPre;
if (ans[i] === 0) ans[i] = 0;   // -0 → +0
```

缺点：污染算法主干；每道可能产生 -0 的题都要加一行。

**修法 C：显式断言 `-0`**（最诚实但最啰嗦）

```ts
expect(solve([-1, 1, 0, -3, 3])).toEqual([-0, 0, 9, -0, 0]);
```

缺点：暴露实现细节；每换个输入都要重算哪里会出 `-0`。

**修法 D：换 matcher**

- `.toStrictEqual` → 同样区分 `-0`（基于 `Object.is`）
- 自定义 matcher `.toEqualIgnoringNegativeZero`（过度工程）

**结论**：**用修法 A**，一行 helper 一劳永逸。只在"涉及负数 × 0"的题上用。

## 自测反问

1. `-0 === 0` 和 `Object.is(-0, 0)` 的结果为什么不同？
   —— `===` 为了符合直觉特判了零；`Object.is` 的目标就是"位模式等价"，不能特判。
2. 什么运算会产生 `-0`？
   —— 负数 × +0 / 负数 × -0 / `-1 * 0` / `Math.round(-0.1)` / `Math.ceil(-0.5)` / `-0 - 0` / 负数的小于机器 epsilon 的下溢
3. 什么运算会把 `-0` 洗回 `+0`？
   —— `x + 0`（当 x 是 -0 时结果是 +0）、`+0 - -0`、`Math.abs(x)`、`x | 0`（按位或归 +0，但会丢失小数位）
4. 为什么 LeetCode 判题 AC？
   —— 它用 `JSON.stringify` 等序列化比较，而 `JSON.stringify(-0) === "0"`，符号信息直接丢了。
5. `-0 < 0` 吗？`1 / -0` 是什么？
   —— `-0 < 0` 是 `false`（跟 `===` 一样特判）；`1 / -0` 是 `-Infinity`（**这里符号又回来了**，可以用它检测 -0：`Object.is(x, -0)` 或 `1/x === -Infinity`）。

## 延伸

- 反向引用：`ts/problems/0238-product-of-array-except-self/notes.md`（本次踩坑现场）
- 同族（都是"JS 数值边角 case 让类型/相等不自洽"）：
  - [PF-0005](./PF-0005-new-array-is-any.md)：`new Array(n)` 是 `any[]`，strict 不报
  - `JSON.stringify(NaN) === "null"`（这就是 #238 第一次踩的 null bug 的来源）
- Java 对照：
  - Java 的 `Double.compare(-0.0, +0.0)` 返回 `-1`（**区分**，和 `Object.is` 一致）
  - 但 Java 的 `-0.0 == +0.0` 是 `true`（**不区分**，和 JS `===` 一致）
  - **Java 和 JS 在这个边角 case 上的分裂路线完全一样**，只是方法名不同；换语言不会救你
- 日常纪律：
  - **写涉及"累积乘积 / 累积除法 / 滚动极值"的题时**，如果输入可能含负数 × 0，预料会出 `-0` → 测试里直接用 `normalize` helper
  - **debug "测试过不去但 LeetCode AC"** 时，先 `.map(x => Object.is(x, -0))` 看一眼有没有混 `-0`
- 相关 TS 概念：数值类型 `number` 是 **IEEE 754 double**，所有 double 的坑都继承（`NaN`、`-0`、精度溢出、`Infinity`）。TS 的类型系统**管不到这些**（都是 `number`）—— 只能靠测试和警觉。
