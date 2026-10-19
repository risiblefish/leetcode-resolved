---
id: PF-0006
title: JS 字符串是 primitive 且不可变，没有 .sort() —— 必须拆成数组
tags: [string, immutability, array-lib, java-vs-ts]
source: 2026-10-13 · #242 valid-anagram（r2）
created: 2026-10-13
---

# PF-0006 · `s.sort()` 不存在 —— 字符串不可变，没有数组方法

> **知识块**：TS 语言 · 坑点卡（pitfall）　|　**ID**：PF-0006　|　反向引用：做题文档里写「踩坑：PF-0006」

## 现象

写字母异位词判断，最顺手就想按字符排完直接比：

```ts
function isAnagram(s: string, t: string): boolean {
  if (s.length !== t.length) return false;
  return s.sort().toString() === t.sort().toString();   // ❌
}
```

TS 直接报：

```
Property 'sort' does not exist on type 'string'.
```

"排序" 这个想法没错，错的是 **以为 `string` 像数组一样有 `.sort()`**。

## 一句话根因

**JS 字符串是 primitive（原始类型）且 immutable（不可变）**。
`.sort()` 挂在 `Array.prototype` 上、语义是**原地排序**——
一个不可变、不是数组的东西，当然既不该有 `.sort()`，也不可能原地改。

所有看起来"修改字符串"的内建方法（`toUpperCase` / `replace` / `trim` / `slice`...）
都是**返回新串**。唯一能"排序"的办法，就是先把它拆成一个可变的数组容器，排完再拼回去。

对比 Java（心法完全相同，只是 Java 多了一个 `toCharArray()` 糖）：

```java
char[] arr = s.toCharArray();   // 拆成可变容器
Arrays.sort(arr);               // 原地排序
String sorted = new String(arr);// 拼回去
```

TS 的等价：

```ts
const sorted = [...s].sort().join("");    // 拆 → 排 → 拼
```

## 修法（≥2 条）

1. **拆数组再排序**（最直接）：

   ```ts
   const key = [...s].sort().join("");         // 推荐：spread 拆单字符
   // 或：const key = s.split("").sort().join("");
   ```

   ⚠️ 注意：`[...s]` 按 Unicode **码点**拆（emoji、代理对会被拆对），`s.split("")` 按 **UTF-16 code unit** 拆（代理对会被拆散）。
   纯 ASCII / 小写字母场景两者等价；要兼容 emoji / 非 BMP 字符用 spread。

2. **根本不要排序**（本题最优路线 —— 走计数签名）：

   ```ts
   // 两串字符频次相同 ⟺ 异位词；O(n) vs 排序版 O(n log n)
   const cnt = new Array<number>(26).fill(0);
   for (const c of s) cnt[c.charCodeAt(0) - 97]++;
   for (const c of t) if (--cnt[c.charCodeAt(0) - 97] < 0) return false;
   return true;
   ```

3. **想要稳定区域敏感排序**（本题用不到，但做自然语言字母表排序时有用）：

   ```ts
   const key = [...s].sort(new Intl.Collator("en").compare).join("");
   ```

> ❌ 别指望 `s.split("").sort()` 的返回值还能当字符串比较 —— `.sort()` 返回的是 `string[]`。
> 比较时要么 `.join("")` 回字符串，要么用数组深比较（`JSON.stringify` 之类）。
> 原稿里的 `.toString()` 恰好等于 `.join(",")`，在单字符元素下与 `.join("")` **结果等价**（见 #49 notes 讨论），
> 但依赖这一点是脆的 —— 场景一换就撞键。**显式写 `.join("")`**，不要赌。

## 自测反问

1. `s.toUpperCase()` 能链式调用，为什么 `s.sort()` 不行？
   —— 前者返回**新串**（非原地）；后者不存在（`sort` 在数组上才有，且是原地）。
2. 为什么内置 `String.prototype` **没有** `.sort()` / `.reverse()` / `.push()`？
   —— 这些都是"原地修改"语义，和"不可变"根本冲突；语言设计上不给才自洽。
3. `const s = "a"; s[0] = "b";` 严格模式下会怎样？
   —— TypeError。非严格模式静默失败，`s` 还是 `"a"`。
4. `[...s]` 和 `s.split("")` 在处理 `"🙂"` 时结果不同吗？
   —— 不同。`[...s]` → `["🙂"]`（1 个码点）；`s.split("")` → 两个代理半（长度 2）。
5. 如果你强行要"原地排序一个字符串"，整个思路错在哪里？
   —— 字符串就不是"位置"，是"值"。"原地改一个值"的概念不成立。
   唯一合法的操作是：拿到字符的**可变副本**（数组）→ 改副本 → **产生新串**。

## 延伸

- 反向引用：`ts/problems/0242-valid-anagram/notes.md`（r2 复习时的疑问）
- 同族："看起来能原地改但其实必须拆" —— `str.replace` 返回新串；`str.replaceAll` 同；`str.trim` 同。
  **字符串方法里凡是动词都返回新串**，没有例外。
- Java 老手要换的直觉：Java `String` 也不可变（`s.toUpperCase()` 返回新串），
  但 Java 的 `char[]` 是正宗数组、`StringBuilder` 可变 —— TS 没有 `StringBuilder`，就 "拆成数组 → 操作 → `join`" 一条路。
- 相关 TS 概念：**primitive vs object**（`string` / `number` / `boolean` / `symbol` / `bigint` / `null` / `undefined` 是 primitive，不可变、按值传）
- 顺手记：想不起 `string.prototype` 有啥方法时直接看 MDN —— TS 的类型定义就是从那里映过来的
