---
id: PF-0005
title: new Array(n) 是 any[]，strict 一个错都不报（.fill() 也救不了）
tags: [array-lib, any, type-inference, strict-mode]
source: 2026-10-11 · #53 maximum-subarray
created: 2026-10-11
---

# PF-0005 · `new Array(n)` 是 `any[]`（strict 静默放行）

> **知识块**：TS 语言 · 坑点卡（pitfall）　|　**ID**：PF-0005　|　反向引用：做题文档里写「踩坑：PF-0005」

## 现象

开 DP 数组时最顺手的写法：

```ts
const sum = new Array(nums.length);   // 我以为：number[]（长度 n 的空数组）
sum[0] = nums[0];
let curMax = sum[0];                  // 我以为：number
for (let i = 1; i < n; i++) {
  sum[i] = Math.max(sum[i - 1] + nums[i], nums[i]);   // 全是 any，一行都不报
}
return curMax;                         // 返回 any，签名说要 number，也不报
```

`tsc --noEmit`（`strict: true`）**零错误**，`npm test` 也全绿 —— 但这一整段其实一路 `any`。

探测确认（`strict: true` 下逐行试）：

| 写法 | 推断出的类型 | 给 `const s: string = arr[0]` 会报错吗 |
|---|---|---|
| `new Array(3)` | `any[]` | ❌ 不报 |
| `new Array(3).fill(0)` | **`any[]`** | ❌ **不报**（`.fill(0)` 没用！） |
| `[...new Array(3)]` | `any[]` | ❌ 不报 |
| `new Array<number>(3)` | `number[]` | ✅ 报错（正常保护） |
| `new Array<number>(3).fill(0)` | `number[]` | ✅ 报错 |
| `Array.from({ length: 3 }, () => 0)` | `number[]` | ✅ 报错 |

## 一句话根因

TS 的 `ArrayConstructor` 有两条重载：

```ts
new (arrayLength?: number): any[];          // ← 走的是这条
new <T>(...items: T[]): T[];
```

`new Array(3)` 匹配到的是 **`any[]`**（长度在运行时才知道、元素类型未知，TS 干脆给了 `any`）。
而 `strict` / `noImplicitAny` 只管「**没写类型**」的地方，`new Array` 是**标准库里明确写着 `any`** 的，所以不算「隐式」→ 不报。

于是 `any` 会顺着索引读传染出去：`sum[0]` → `any` → `curMax` → 返回值，**全程无感**。

## 修法（≥2 条）

1. **写泛型**（最小改动）：

   ```ts
   const sum = new Array<number>(n);   // number[]
   ```

2. **别建数组**（DP 场景通常更好 —— 顺带把空间从 O(n) 压到 O(1)）：

   ```ts
   let cur = nums[0], best = nums[0];
   for (let i = 1; i < n; i++) { cur = Math.max(cur + nums[i], nums[i]); best = Math.max(best, cur); }
   ```

3. **用 `Array.from`**（要初值就带上）：

   ```ts
   const dp = Array.from({ length: n }, () => 0);   // number[]
   ```

4. **只想表意、不在意保护**时也可以显式标注，但要知道它**不提供保护**：

   ```ts
   const dp: number[] = new Array(n);   // 合法（any[] → number[] 允许），但 dp[0] 依旧在使用处被收成 number 假象
   ```

   —— 类型标注只挡「写错」，挡不住「运行时是 undefined」。要真空数组用 `new Array<number>(n)`。

> ❌ 常见误区：`new Array(n).fill(0)` 看起来给了 0、类型也该定下来了，实测**仍是 `any[]`**（`fill` 是在 `any[]` 上调用的，返回自己）。

## 自测反问

1. `new Array(3)` 和 `[1, 2, 3]` 的推断类型差在哪？为什么？
2. 为什么 `strict: true` 没拦住？它到底管什么？（提示：`noImplicitAny` 管「没写」，不管「库里就是 any」）
3. 我该用什么**动作**发现这种静默 `any`？—— 给一个故意写错的赋值（`const s: string = arr[0]`）看 tsc 报不报；或用编辑器的 hover 看推断类型
4. `.fill(0)` 为什么救不了类型？那 `new Array<number>(n).fill(0)` 呢？
5. 同类「静默 any」还有哪些？（`JSON.parse` 返回 `any`、`document.getElementById`、`import` 缺类型、`catch (e)` 以前是 `any`…）

## 延伸

- 反向引用：`ts/problems/0053-maximum-subarray/notes.md`（「卡点」一节）
- 同族：[PF-0003](./PF-0003-no-late-assignment-in-strict-ts.md)（`let x;` 是 `undefined`，也是「类型没保护住运行时」）
- 一句话纪律：**`new Array(n)` 后面必须跟 `<T>`，否则就换 `Array.from` / 干脆别建数组**
- 相关 TS 概念：`any` 的传染性（`any` 参与运算结果还是 `any`）／`unknown` 才是「安全的未知」，需要显式收窄