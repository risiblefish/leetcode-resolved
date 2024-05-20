# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
from collections import deque
from idlelib.tree import TreeNode
from typing import List, Optional


class Solution:
    def zigzagLevelOrder(self, root: Optional[TreeNode]) -> List[List[int]]:
        ans = []
        if root is None: return ans
        q = deque()
        q.append(root)
        needReverse = False
        while len(q) > 0:
            cnt = len(q)
            cur_level = []
            while cnt > 0:
                cur_node = q.pop()
                cur_level.append(cur_node.val)
                if cur_node.left is not None: q.appendleft(cur_node.left)
                if cur_node.right is not None: q.appendleft(cur_node.right)
                cnt -= 1
            if (needReverse):
                cur_level.reverse()
            ans.append(cur_level)
            needReverse = not needReverse
        return ans
