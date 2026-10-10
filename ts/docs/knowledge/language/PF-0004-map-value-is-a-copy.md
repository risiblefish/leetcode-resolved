---
id: PF-0004
title: Map.get 拿到的是值本身，不是通往表内的钥匙（改它不会改表）
tags: [map-set, value-semantics, java-vs-ts]
source: 2026-10-10 · #242 valid-anagram（r1 复习时踩）
created: 2026-10-10
---

# PF-0004 · `map.get(k)` 拿到的是副本，改它不会改表

> **知识块**：TS 语言 · 坑点卡（pitfall）　|　**ID**：PF-0004　|　反向引用：做题文档里写「踩坑：PF-0004」

## 现象

用 `Map` 当计数器，消数的时候「读出来 → 减 → 存回去」听起来是三步，但很容易只做前两步：

```ts
for (const ch of t) {
  if (cntMap.has(ch)) {
    let cnt = cntMap.get(ch)!;
    cnt--;                       // 我以为这一行把表里的值也减了
    if (cnt === 0) cntMap.delete(ch);
    // ❌ 减到非 0 的时候，什么都没写回表
  } else return false;
}
```

结果：**计数永远减不下去**，只有「一次就减到 0」的字符能正确消失。

最坑的是它**经常碰巧全过**：

- `("aab","abb")` → 第一个 `a` 减到 0 直接 `delete` 了 → 返回 `false`，**竟然是对的**
- `("aaab","baaa")` → 第一个 `a` 减到 2，没写回 → 表里 `a` 还是 3 → 结束时 `size !== 0` → 返回 `false`，**答案是 `true`** ❌

## 一句话根因

`Map` 存的是**值**，不是**引用**（当值类型是 `number` / `string` / `boolean` 这类原始值时）。

`cntMap.get(ch)` 返回的是那个数字的**副本**。`cnt--` 改的是局部变量 `cnt`，
跟表里那个数字**没有任何关系** —— 想让表变，唯一的办法是 `set` 回去。

> 同一句话在「值是数组 / 对象」时**反过来**：`map.get(k)!.push(x)` 是**生效**的。
> 因为这时表里存的是引用，`push` 改的是引用指向的那个对象。
> **这个不一致就是坑的来源** —— 你不能靠「上次这样写生效了」来推断这次也行。

## 修法（≥2 条）

1. **老老实实写回**（最直白的修法）：

   ```ts
   let cnt = cntMap.get(ch)!;
   cnt--;
   if (cnt === 0) cntMap.delete(ch);
   else cntMap.set(ch, cnt);        // ← 少这一行就是这次踩的坑
   ```

2. **读-改-写合并成一步**（推荐：结构上就不可能忘）：

   ```ts
   const next = (cntMap.get(ch) ?? 0) - 1;
   if (next === 0) cntMap.delete(ch);
   else cntMap.set(ch, next);
   ```

   建表那半边同理：`cntMap.set(ch, (cntMap.get(ch) ?? 0) + 1);`

3. **需要频繁改内部状态时，就让值是对象**（这时引用语义帮你干活）：

   ```ts
   const cntMap = new Map<string, { n: number }>();
   // ...
   cntMap.get(ch)!.n--;             // 这样才真的改到表里的对象
   ```

   代价是写法更啰嗦、还得处理「先建桶再改」；计数器场景不划算，了解即可。

4. **换个数据结构**：字符集已知就把定长数组当计数器（`arr[i]--` 天然写回，不存在这个问题）：

   ```ts
   const cnt = new Array(26).fill(0);
   cnt[ch.charCodeAt(0) - 97]--;
   ```

## 自测反问

1. `map.get(k)` 返回的是表里那个值的引用还是副本？—— 取决于**值是什么类型**，所以要分开答
2. 值是 `number` 时怎么改表？值是 `number[]` 时怎么改？
3. 什么样的测试用例会抓出「忘写回」？—— **同一个键要被减 ≥2 次**的用例（`("aaab","baaa")`），只减一次就会被 `delete` 掩盖
4. 为什么示例用例全过、直觉上也没觉得错？—— bug 只在「减到非 0」这条分支上
5. 为什么 `let cnt = map.get(k)!` 用 `!` 没问题，但后面还是要 `set`？—— `!` 只解决「可能是 `undefined`」的类型问题，不解决「拿到的是副本」的语义问题

## 延伸

- 反向引用：`ts/problems/0242-valid-anagram/notes.md`（「卡点」一节，r1 复习时踩的）
- 姊妹坑：[PF-0001](./PF-0001-map-has-get-narrowing.md) —— `has` 无法让 `get` 的 `undefined` 消失（类型层）；这张卡是**值语义层**
- 同族惯性：`Map<number, number>` 当计数器时，「读-改-写」建议一律用一行 `?? 0` 的写法
- 相关 TS 概念：JS 里「对象按引用传递、原始值按值传递」；`Object.freeze`、`readonly`、`as const` 都是同一族话题