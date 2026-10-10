# 0001 两数之和 / Two Sum

https://leetcode.cn/problems/two-sum/  ·  难度：Easy  ·  pattern：array-hash

> 本文档是该份的**学习记录与复习参考标准**。做前写「思路」，卡住写「卡点」，做完让 AI 复盘并回填。

## 这份要学 / 复习什么（薄预习）

> 来自预习卡：`npm run learn -- array-hash`

- **核心思想**：用 O(n) 的额外空间（Map/Set）把『查找/计数』从 O(n) 降到 O(1)。遍历时边读边把已经见过的信息存起来。
- **识别信号**（看到这些条件就该想到该套路）：
  - 题目要你判断『是否出现过』『两数之和』『分组』→ 哈希
  - 要求 O(n) 但暴力是 O(n²) → 用哈希/前缀和降复杂度
  - 需要统计次数/去重/求交集 → Map / Set
- **易错 / 边界**：
  - 空数组 / 单元素
  - 有重复元素时返回下标还是值
  - TS 里字符串不可变，改字符要先拆成数组
  - Map 的键是值语义（对象当键要注意引用）

## 目标（Definition of Done）

- [x] `npm test` 全绿：6 个用例（2 示例 + 3 边界 + 1 反例）
- [x] `npm run typecheck` 无错
- [x] LeetCode 提交 Accepted
- [x] 本文档「思路 / 结果 / 复盘」填完
- [x] `npm run solve -- 1 --first --min 8`（已 commit：`solve(ts): 0001 two-sum Easy`）

> ⚠️ 补记（2026-10-10）：上面第 1 条**当时并未真正满足** —— `solution.test.ts` 一直是 `it.todo` 空壳，
> 而 `vitest run --passWithNoTests` 不会因此失败（假绿）。断言于 2026-10-10 补齐。

## 我的思路（先写再做，哪怕写错）

哈希一遍过：用 `Map<值, 下标>` 记录见过的数，遍历时问「我要找的搭档 `target - nums[i]` 之前出现过吗」。先查再存，避免自己配自己。

## 卡点（卡在哪 / 看了什么提示）

算法本身没卡（Java 时代做过几十次）。卡在 **TS 类型**：`seen.get(another)` 的返回类型是 `number | undefined`，即使先调了 `has` 判空，类型仍不收窄 → 将 `number | undefined` 赋给 `number[]` 报错。

**根因**：TS 的类型收窄只认「对变量本身的判断」，不认「对同一对象的另一个方法调用」。
**已落卡**：[PF-0001](../../docs/knowledge/language/PF-0001-map-has-get-narrowing.md)

## 提交结果

- 是否一次过：是
- 耗时（分钟）：~8
- 复杂度：时间 O(n) / 空间 O(n)

## AI 复盘（AC 之后也值得看）

**好的**：
- 哈希一遍过的标准模板，没多余步骤
- **「先查再存」顺序正确**——这是最多人踩的坑，你一次过
- 用了非空断言 `!`（路线 A），是 TS 惯用写法
- `return []` 兑现 strict 的「所有路径必须 return」要求

**可改进**：
- `let another = target - nums[i]` 应为 `const`——没 reassign；`prefer-const` 规则会抓这条
- 末尾 `};` 多余——函数声明不是表达式语句，不应带分号。不影响运行，观感问题
- 兑现路线 B（`const j = seen.get(k); if (j !== undefined) ...`）也是正确写法，知道有这条路就行，不必切换

**没应改**：
- 复杂度已是 O(n)，没更优
- 返回 `[]` vs `throw new Error('unreachable')` 是风格选择，两边都行

## 可改进点 / 标准要点（复习对照）

1. **哈希一遍过模板**：`Map<number, number>` 显式泛型 + 先查再存 + `!` 断言
2. **「排闪自己配自己」是关键**——[3,3] target=6
3. **TS 坑点**：`has` 不收窄 `get` 的类型（PF-0001）
4. **风格**：默认 `const`，只有 reassign 才用 `let`（PF-0002）

复习时闭卷重写要达到：从空白到 AC、不看任何提示、包含 `has` + `!` 正确写法。



## 复习记录

| 日期 | 第几次 | 是否闭卷写出 | 备注 |
|------|--------|-------------|------|
|      | 1      |             |      |
