# 0121 买卖股票的最佳时机 / Best Time to Buy and Sell Stock

https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/  ·  难度：Easy  ·  pattern：array-hash

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

- [x] `npm test` 全绿：7 个用例（2 示例 + 2 边界 + 3 反例）
- [x] `npm run typecheck` 无错
- [x] LeetCode 提交 Accepted
- [x] 本文档「思路 / 结果 / 复盘」填完
- [x] `npm run solve -- 121 --first`（已 commit：`solve(ts): 0121 best-time-to-buy-and-sell-stock Easy`）

> ⚠️ 补记（2026-10-10）：上面第 1 条**当时并未真正满足** —— `solution.test.ts` 一直是 `it.todo` 空壳，
> 而 `vitest run --passWithNoTests` 不会因此失败（假绿）。断言于 2026-10-10 补齐。

## 我的思路（先写再做，哪怕写错）

一次扫描 + 维护历史最小值：每到一个位置 `i`，先问「用今天这个价卖能赚多少（当前 - minSoFar）」，再更新 minSoFar。**顺序关键**：得先用历史最小算收益，再包含今天更新最小，否则等于偷看未来。

## 卡点（卡在哪 / 看了什么提示）

算法没卡。带回来的模板肉感：**扩展到多次交易 / 带手续费 / 冷冻期 就得换 DP 套路**，本模板就是「只能交易一次」的最简壳子。

## 提交结果

- 是否一次过：是
- 耗时（分钟）：~8
- 复杂度：时间 O(n) / 空间 O(1)

## AI 复盘（AC 之后也值得看）

**好的**：
- 「先用历史最小算收益再更新 lowest」顺序正确——这是这道题最多人踩的坑
- 命名 `lowest` / `curMaxProfit` 比教科书的 `minSoFar` / `best` 更贴题
- 从 `i = 1` 开始、`curMaxProfit = 0`、`lowest = prices[0]` 初始化合理
- 末尾 `return curMaxProfit` 所有路径都走到

**可改进**：
- 末尾 `};` 多余（和 #1 一样，function 声明不带分号）
- 更鲁棒的初始化：`let lowest = Infinity` 不依赖 `prices[0]`（现在 tsconfig 没开 `noUncheckedIndexedAccess`，所以没问题；开了 `prices[0]` 就是 `number | undefined`）

**没应改**：
- 这是 O(n) 最优解，别看「分治 O(n log n)」那种学术秀——把简单问题复杂化

## 可改进点 / 标准要点（复习对照）

1. **模板肉感**：`let lowest = prices[0]; let best = 0; for i from 1 ... best = max(best, prices[i] - lowest); lowest = min(lowest, prices[i])`
2. **顺序铁律**：先用历史最小算收益，再更新历史最小。试图倒过来 = 偷看未来 = 答案小 1 天
3. **识别信号**：「只能从左到右走一遍」+ 「当前答案依赖历史某极值」 → 一次扫描 + 维护极值
4. **边界**：数组长度 1 时返回 0（不能亏本）
5. **别套错模板**：多次交易 / 带手续费要换 DP，别碍找本模板套



## 复习记录

| 日期 | 第几次 | 是否闭卷写出 | 备注 |
|------|--------|-------------|------|
| 2026-10-10 | 1 | ✅ 是（2 分 32 秒） | 一次写对，顺序铁律（先算收益、再更新最低）没倒；变量名 `day` / `curMaxProfit` 比 `i` / `best` 更可读 |
| 2026-10-19 | 2 | ✅ 是（闭卷，未计时） | r2 闭卷一次 AC，**和正式 solution 几乎逐字一致**（命名、顺序、逻辑全同）——滚动变量模式肌肉记忆完全回来了；和当天做的 #238 O(1) 版是同心法（扣一个滚动变量累积历史极值 / 累积积） |
