# 预习卡 · 图进阶与并查集

> 类型：`graph-unionfind`  ·  随时回看：`npm run learn -- graph-unionfind`

## 它是什么

并查集（Union-Find）维护一组元素的连通分组，支持『查根』和『合并』。最短路有 Dijkstra（单源非负权）、Floyd（多源）。

## 核心思想

并查集 = parent 数组 + find（路径压缩）+ union（按秩/大小合并），近 O(1)。Dijkstra = 小顶堆 + 贪心松弛。

## 考什么 / 学什么

考『动态连通性』建模（并查集）和带权最短路（Dijkstra/Kruskal）。

## TS 模板骨架（半成品）

```ts
const parent = Array.from({ length: n }, (_, i) => i);
function find(x: number): number {
  return parent[x] === x ? x : (parent[x] = find(parent[x]));
}
function union(a: number, b: number) {
  const ra = find(a), rb = find(b);
  if (ra !== rb) parent[ra] = rb;
}
```

## 常见变体

- 连通分量 / 省份数量
- 冗余连接（环检测）
- 账户合并
- 最小生成树（Kruskal = 排序边 + 并查集）
- Dijkstra / Floyd
- 拓扑排序（有向图判环）

## 易错 / 边界

- 初始化 parent[i] = i 别忘了
- 路径压缩与按秩合并
- 有向图判环要用拓扑排序，不能直接套并查集
- Dijkstra 不能处理负权边

## 识别信号

- 『是否连通 / 合并集合 / 连通块个数』→ 并查集
- 带权最短路径 → Dijkstra
- 多源最短/传递闭包 → Floyd
- 依赖顺序 + 无环判定 → 拓扑排序

## 必做题（经典）

#547 #684 #721 #399 #743 #787 #1584 #1202 #1971 #802 #1334

## 我的默写（合上资料后凭记忆写模板）

```ts
// 在这里默写，不要看上面
```

## 卡点 / 想问的问题（留白）

## AI 复盘 / 补充（留白）

## 复习记录

| 日期 | 第几次 | 能默写模板 | 备注 |
|------|--------|-----------|------|
|      | 1      |           |      |
