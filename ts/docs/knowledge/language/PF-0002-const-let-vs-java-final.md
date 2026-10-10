---
id: PF-0002
title: TS 的 const/let 和 Java 的 final 怎么对应
tags: [variable-binding, immutability, java-vs-ts]
source: 2026-10-08 · #1 two-sum review
created: 2026-10-08
---

# PF-0002 · `const` / `let` / Java `final` 的对应关系

> **知识块**：TS 语言 · 坑点卡（pitfall）　|　**ID**：PF-0002　|　反向引用：做题文档里写「踩坑：PF-0002」

## 现象

从 Java 过来会下意识想：
- "我这个变量不会再改了，是不是该标 `final`？" → **TS 没有 `final` 关键字**
- "`const` 是不是就等于 `final`？" → **大致等于，但有一个关键坑**

## 一句话根因

**TS 的 `const` 和 Java 的 `final` 语义等价：都保护「绑定」不保护「值内部」。**

而「final 类」「final 方法」这种 Java 的继承控制功能，**TS 里没有直接对应的关键字**。

## 完整对照表

### 1. 局部变量 / 字段上的 `final` ↔ `const` / `readonly`

| Java | TS | 语义 |
|---|---|---|
| `final int x = 10;` | `const x = 10;` | 局部变量：绑定不可变 |
| `int x = 10;` | `let x = 10;` | 局部变量：绑定可变 |
| `class A { final int x; }` | `class A { readonly x: number; }` | **类字段**：绑定不可变 |
| `class A { int x; }` | `class A { x: number; }` | 类字段：可变 |

**关键**：**局部变量的"不可变"用 `const`，类字段的"不可变"用 `readonly`**。两个关键字，不是一个。

### 2. `final` 类 / 方法 ↔ **TS 没有**

| Java | TS |
|---|---|
| `final class Foo { }`（禁止继承） | ❌ **没有** |
| `class Foo { final void bar() { } }`（禁止 override） | ❌ **没有** |

想禁止被继承/覆盖，只能靠约定（注释、ESLint 规则）或类型系统 trick。日常业务代码**基本不管这个**，知道有缺就行。

### 3. 没有 Java 对应的 TS 概念

- `var`：旧语法，**作用域是函数不是块**，`var i` 在 for 外还能访问。**不要用**。
- `as const`：字面量断言，让类型变成最窄的字面量。例：
  ```ts
  const dir = { x: 1 } as const;  // 类型是 { readonly x: 1 }，不是 { x: number }
  ```
  这是 TS 独有的类型层技巧，Java 没有。

## 关键坑：`const` 和 `final` 都**保护不了对象内部**

这是从 Java 过来的人最容易误解的点。但其实**Java 的 `final` 也是这样**，只是你可能没踩到过：

```ts
// TS
const arr: number[] = [];
arr.push(1);        // ✅ 完全合法！const 保护的是 arr 这个绑定
arr = [1, 2];       // ❌ 这才会被 const 拦住
```

```java
// Java 等价行为
final List<Integer> arr = new ArrayList<>();
arr.add(1);         // ✅ 合法
arr = new ArrayList<>();  // ❌ final 拦住
```

**两边语义完全一致**：`final` / `const` 保护的是「变量指向谁」，不是「指向的那个对象能不能被改」。

### 真正想"值也不可变"的 TS 写法

1. **浅层不可变**：`readonly` 修饰符

   ```ts
   const arr: readonly number[] = [1, 2, 3];
   arr.push(4);  // ❌ TS 报错：Property 'push' does not exist on type 'readonly number[]'
   ```

2. **字面量级不可变**：`as const`

   ```ts
   const DAYS = ['Mon', 'Tue', 'Wed'] as const;
   // 类型是 readonly ['Mon', 'Tue', 'Wed']
   DAYS[0] = 'X';  // ❌ 报错
   ```

3. **运行时冻结**：`Object.freeze()`（**慎用**，只冻结一层）

## 刷题时的实操规则（信心高）

1. **默认全写 `const`**。几乎所有刷题变量都是"算一次用一次"，没必要 `let`。
2. **只有真正要 reassign 的用 `let`**：循环计数器 `i`、累加器 `sum`、滚动指针 `prev/curr` 等。
3. **类/接口字段**：想标记"构造后不能改"用 `readonly`。LeetCode 刷题几乎用不到。
4. **参数**：TS 的函数参数**不能加 `const`**（语法上就不允许，和 Java `final int x` 不一样）。参数默认可以在函数体内被重新赋值，但正常都不会这么做。
5. **忘了 `const` 导致所有变量都 `let`** 不是 bug，但代码 review 时会被指出来——养成默认 `const` 的习惯。

### 回顾你今天 #1 的代码

```ts
let another = target - nums[i];  // ← 这里应该是 const，没有 reassign
```

**可改进**：`const another = target - nums[i];` 更规范。

而 #121 里的：

```ts
let lowest = prices[0];
let curMaxProfit = 0;
```

**这两个必须是 `let`**：循环里会 reassign，用 `const` 会报错。写对了。

## 自测反问（将来转成题的素材）

1. Java 的 `final int x = 10` 翻译成 TS，关键字应该用哪个？函数参数位置呢？类字段位置呢？
2. 下面代码能跑过吗？解释原因：
   ```ts
   const user = { name: 'A' };
   user.name = 'B';
   ```
3. 把下面代码加上合适的修饰符，让第二行报错：
   ```ts
   const arr: number[] = [1, 2, 3];
   arr.push(4);  // 让这一行报错
   ```
4. `as const` 和 `readonly` 的区别是什么？什么情况下用哪个？
5. 为什么说 TS 没有 Java 的 `final class` 对应？想禁止继承有什么迂回办法？（提示：不常考，知道没有就行）

## 延伸 / 什么时候还会撞见

- **代码风格**：ESLint 的 `prefer-const` 规则会强制把没有 reassign 的 `let` 改成 `const`。你 #1 的 `let another` 就会被这条规则抓
- **React / Vue**：组件里的 state、props、ref 常常是 `readonly` 类型，想"赋值"要走 setter（`setState` / `.value = `）
- **函数式风格**：如果你以后写不可变数据流（map/filter/reduce 链），`readonly` 和 `as const` 会用得非常多
- **泛型约束**：`<T extends readonly unknown[]>` 这种是 TS 独有的类型层操作，Java 泛型做不到

## 一句话记忆口诀

> **变量用 `const`（≈ Java `final 变量`），字段用 `readonly`（≈ Java `final 字段`），类和方法的 `final`——TS 没有。**
