from typing import Optional

from algorithms.linkedlist.list_node import ListNode


class Solution_206:
    # Definition for singly-linked list.
    # class ListNode:
    #     def __init__(self, val=0, next=None):
    #         self.val = val
    #         self.next = next
    def reverseList(self, head: Optional[ListNode]) -> Optional[ListNode]:
        if head is None or head.next is None:
            return head
        h = self.reverseList(head.next)
        head.next.next = head
        head.next = None
        return h
