from typing import Optional

from algorithms.linkedlist.list_node import ListNode


class Solution25:
    def reverseKGroup(self, head: Optional[ListNode], k: int) -> Optional[ListNode]:
        if head is None or head.next is None:
            return head
        curHead = head
        cur = head
        for i in range(0, k - 1):
            cur = cur.next
            if cur is None:
                return head
        curTail = cur
        nextHead = curTail.next
        curTail.next = None
        self.reverse(curHead)
        curHead.next = self.reverseKGroup(nextHead, k)
        return curTail

    def reverse(self, head: Optional[ListNode]) -> None:
        if head is None or head.next is None:
            return
        self.reverse(head.next)
        head.next.next = head
        head.next = None
