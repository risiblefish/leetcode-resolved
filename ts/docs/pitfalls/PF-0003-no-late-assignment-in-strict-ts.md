---
id: PF-0003
title: TS 没有「先声明后赋值」的免费午餐（strict 下 let x; 就是 undefined）
tags: [strict-mode, type-inference, java-vs-ts, definite-assignment]
source: 2026-10-10 · #169 majority-element
created: 2026-10-10
---

# PF-0003 · `let x;` 不是 Java 的 `int x;`

## 现象

从 Java 过来，写循环里的「滚动变量」时下意识想先声明、用到时再赋值：

```ts
let candidate;          // 心里想的是 Java 的 int candidate;
let cnt = 0;
for (const num of nums) {
  if (cnt === 0) { candidate = num; cnt++; }   // 首次赋值在这里
  else { cnt += num === candidate ? 1 : -1; }
}
return candidate;
```

`strict` 下直接报错，而且报错位置很迷惑（在**比较**那一行）：

```
error TS18048: 'candidate' is possibly 'undefined'.
error TS2322: Type 'number | undefined' is not assignable to type 'number'.
```

换成看起来「更明确」的写法，**照样报**：

```ts
let candidate = undefined;   // Type 'number | undefined' is not assignable to type 'number'
```

## 一句话根因

**TS 的类型在声明那一刻就定了，没有 Java 的「字段默认值」概念。**

Java 的 `int candidate;` 有一个真实存在的默认值 `0`（`boolean` 是 `false`，对象是 `null`）；
TS/JS 里未赋值的变量**真的就是 `undefined`** —— 它不是「暂时还没值」的占位，它本身就是一个值。

各写法的实际类型（`--strict` 实测）：

| 写法 | 控制流类型 | 能不能 `return` 它 |
|---|---|---|
| `let a;` | 未赋值时 `undefined`（赋值后演化成 `number`） | ❌ |
| `let b = undefined;` | `undefined` | ❌ |
| `let c: number \| undefined;` | `number \| undefined` | ❌（要 `!` 或判空） |
| `let d!: number;` | `number`（**明确赋值断言**） | ✅ |
| `let e = nums[0];` | `number` | ✅ |

注意 `let a;` 的「演化型 any」：TS 会盯着后面的赋值把类型**演化**成 `number`，但**在赋值之前的代码位置**它仍是 `undefined` —— 所以 `i < candidate` 那一行才报 `possibly 'undefined'`。**报错行不是出错的那一行，声明那行才是。**

## 修法（都实测通过 `tsc --strict`）

1. **给初值**（最省事，推荐）——把「Java 的默认值」显式写出来：
   ```ts
   let candidate = nums[0];   // 用第一个元素当种子
   ```
   > 本仓库同款：`#0136` 的 `let ans = nums[0]`、`#0121` 的 `let lowest = prices[0]`。
   > 注意这只解决**类型**，语义上仍要保证循环第一次迭代会把 `candidate` 覆盖成正确值（BM 里 `cnt === 0` 那一支就保证了这点）。

2. **显式声明联合类型 + 使用点收窄**：
   ```ts
   let candidate: number | undefined;
   // ...
   return candidate!;                    // 非空断言（＝我保证已赋值）
   // 或 return candidate ?? nums[0];    // 兜底
   // 或 if (candidate === undefined) throw new Error("unreachable");
   ```
   和 `PF-0001` 是同一套思路：**类型系统要你证明「这里不可能是 undefined」**。

3. **明确赋值断言 `!`**（TS 独有语法，Java 没有）：
   ```ts
   let candidate!: number;   // 声明处 `!`：我保证首次使用前一定赋值
   ```
   把「保证」的责任从编译器转到你身上，**写错不会报错，只会运行时炸** —— 只在确实要遍历才赋值、且逻辑上必然赋值时用。

### 附带教训：报错会把你带偏

当时为了绕开这条报错，把循环条件改成了 `for (let i = 0; i < candidate; i++)` —— 报错确实消失了，但**循环上界变成了元素的值**，逻辑全错（`candidate` 是「内容」，`i` 是「进度」，不是一个量纲）。
`[1,2,2,2,2]` 这种输入会返回 `1`，而 LeetCode 的官方示例恰好全都能骗过它。

> **看到类型报错：先问「我的类型假设错在哪」，别先改逻辑去迎合报错。**

## 自测反问

1. Java 的 `int candidate; if (x) candidate = 1; return candidate;` 翻译成 TS 会报错吗？为什么？
2. `let a;` 和 `let a = undefined;` 的推断类型分别是？为什么前者会在**使用**时报错而不是**声明**时报错？
3. 下面哪个能过 `--strict`？说出每个的类型：
   ```ts
   let a;  let b = undefined;  let c: number | undefined;  let d!: number;
   ```
4. `let d!: number` 的 `!` 和 `seen.get(k)!` 的 `!` 是同一个东西吗？
5. 为什么 `#0136` 的 `let ans = nums[0]` 从来没报过这个错？

## 延伸 / 什么时候还会撞见

- **任何滚动变量**：前缀和 `let best`、最大子数组 `let cur`、Kadane、双指针 `let fast`、树遍历的 `let res` —— 全部会遇到
- **类字段**：`class A { x: number; }` 在 `strictPropertyInitialization` 下必须在构造函数里赋值，否则同样报错（同类问题的字段版本）
- **可选链 / `??` 的场合**：TS 逼你区分「没有这个值」和「值是 undefined」，这其实是它的优点 —— Java 的 `Optional` 是同动机的另一种解法
- **`noUncheckedIndexedAccess`**：如果以后打开这个开关（本仓库没开），连 `nums[0]` 都会变成 `number | undefined`，那时 `!` 会用得更多（对照 `pattern-array-hash.md` 番外 C 的 TS 小坑）

## 一句话记忆口诀

> **Java 的 `int x;` 有默认值，TS 的 `let x;` 就是 `undefined`。要「先声明后赋值」，就得给初值、或显式带上 `| undefined` 再收窄。**