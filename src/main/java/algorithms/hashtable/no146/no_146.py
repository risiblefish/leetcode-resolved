class LRUCache:
    class Node:
        def __init__(self, key: int = 0, val: int = 0) -> None:
            self.key = key
            self.val = val
            self.prev = None
            self.next = None

    def __init__(self, capacity: int) -> None:
        self.cap = capacity
        self.head = self.Node()
        self.tail = self.Node()
        self.head.next = self.tail
        self.tail.prev = self.head
        self.map = dict()
        self.size = 0

    def __isolate_node(self, cur: 'LRUCache.Node') -> None:
        cur.prev.next = cur.next
        cur.next.prev = cur.prev

    def __move_to_head(self, cur: 'LRUCache.Node') -> None:
        old_first = self.head.next
        self.head.next = cur
        cur.prev = self.head
        old_first.prev = cur
        cur.next = old_first

    def get(self, key: int) -> int:
        if key not in self.map:
            return -1

        cur = self.map[key]
        self.__isolate_node(cur)
        self.__move_to_head(cur)
        return cur.val

    def put(self, key: int, value: int) -> None:
        if self.get(key) != -1:
            self.map[key].val = value
        else:
            if self.size == self.cap:
                # delete the longest unused node
                to_del = self.tail.prev
                self.__isolate_node(to_del)
                self.map.pop(to_del.key)
                self.size -= 1

            ins = self.Node(key, value)
            self.__move_to_head(ins)
            self.map[key] = ins
            self.size += 1
