---
id: PF-0001
title: Map.has 判空无法让 .get() 的 undefined 消失
tags: [type-narrowing, strict-mode, map-set]
source: 2026-10-08 · #1 two-sum
created: 2026-10-08
---

# PF-0001 · Map.has 判空无法让 `.get()` 的 undefined 消失

## 现象

写了：

```ts
if (seen.has(another)) {
  const ans: number[] = [seen.get(another), i];  // ← TS 报错
}
```

TS strict 下报：

> Type 'number | undefined' is not assignable to type 'number'.

直觉：「我都 `has` 判过了，`get` 怎么还可能是 `undefined`？」

## 一句话根因

**TS 的类型收窄只认「对变量本身的判断」，不认「对同一对象的另一个方法调用」。**

`Map.get` 的签名是 `get(key: K): V | undefined`，雷打不动。前一句 `has` 返回 `true` 这件事，没有办法传染给下一句 `get` 的返回类型——TS 不执行你的代码，不知道这两次调用的 key 相同、Map 没被改过。

## 修法（二选一，都正确）

### A. 非空断言（惯用）

```ts
if (seen.has(another)) {
  return [seen.get(another)!, i];
}
```

`!` = 「我以人格担保它不是 undefined」。代价：担保错了运行时就是 `undefined`，TS 不管。

### B. 先取再判（更顺 TS 的脾气）

```ts
const j = seen.get(another);
if (j !== undefined) {
  return [j, i];
}
```

TS 对**变量** `j` 做了 `!== undefined` 判断，这是它认的收窄模式，`j` 在 if 块里被收窄为 `number`。只查一次 Map，无需 `!`。

## 为什么是「对变量本身的判断」才行

TS 的 narrowing 规则看的是「这个**符号**（identifier）在此处的类型能否被某个判定收窄」。
- `seen.has(k)` → 返回一个新的 boolean 值，不绑定到 `seen.get(k)` 这个表达式的类型
- `j !== undefined` → `j` 是一个变量，if 块里 TS 直接给它换类型

**凡是「调用返回新值」就断了收窄链路。**

## 自测反问（将来转成题的素材）

1. 为什么 `seen.has(k)` 之后 `seen.get(k)` 的类型还是 `V | undefined`？用一句话答。
2. 把下面代码改成 strict 下能过，写**两种**不同的改法：
   ```ts
   function firstEven(nums: number[]): number {
     const v = nums.find(x => x % 2 === 0);
     return v;  // 报错
   }
   ```
3. 下列哪些场景 TS **能**自动收窄？能/不能分别说明：
   - `const v = arr.find(...); if (v !== undefined) return v;`
   - `if (map.has(k)) return map.get(k);`
   - `if (obj.hasOwnProperty('x')) obj.x;`
   - `const a = obj.x; if (a) return a.toUpperCase();`（`obj.x: string | undefined`）

## 延伸 / 什么时候还会撞见

- `arr.find(pred)` → `T | undefined`：先取出来判 `undefined`
- `document.querySelector(...)` → `Element | null`：先取再判 `null`
- `arr[i]`（在 `noUncheckedIndexedAccess` 打开时）→ `T | undefined`
- 普通对象的可选属性 `obj.x?: string` 读完也是 `string | undefined`

**万变不离其宗**：报 `undefined / null not assignable` 时，先问自己——我判的是**变量**还是**又调了一个方法**？
