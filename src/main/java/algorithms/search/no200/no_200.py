from typing import List


class Solution:
    def numIslands(self, grid: List[List[str]]) -> int:
        row = len(grid)
        col = len(grid[0])
        visited = [[False] * col for _ in range(row)]
        ans = 0
        for i in range(row):
            for j in range(col):
                if grid[i][j] == '1' and visited[i][j] == False:
                    ans += 1
                    self.search(grid, i, j, visited)
        return ans

    def search(self, grid: List[List[str]], i: int, j: int, visited: List[List[bool]]) -> None:
        if i < 0 or i == len(grid) or j < 0 or j == len(grid[0]): return
        if grid[i][j] == '0': return
        if visited[i][j]: return
        visited[i][j] = True
        self.search(grid, i + 1, j, visited)
        self.search(grid, i - 1, j, visited)
        self.search(grid, i, j + 1, visited)
        self.search(grid, i, j - 1, visited)
