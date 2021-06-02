package algorithms.linkedlist;

/**
 * 707. 设计链表
 */
public class No707 {
    public static void main(String[] args) {
        /**
         * ["MyLinkedList","addAtHead","addAtTail","addAtIndex","get","deleteAtIndex","get"]
         * [[],[1],[3],[1,2],[1],[1],[1]]
         */
        MyLinkedList list = new MyLinkedList();
        list.addAtHead(1);
        list.addAtTail(3);
        list.addAtIndex(1,2);
        list.get(1);
        list.deleteAtIndex(1);
        list.get(1);
        System.out.println();
    }
}

/**
 * 解法1 ： 双链表
 * 思路： 增加虚拟头尾，从而避免空指针
 */
class MyLinkedList {
    class Node{
        int val;
        Node prev;
        Node next;
        public Node(int val){
            this.val = val;
        }
    }

    private int len;
    private Node vh;
    private Node vt;

    /** Initialize your data structure here. */
    public MyLinkedList() {
        len = 0;
        vh = new Node(0);
        vt = new Node(0);
        vh.next = vt;
        vt.prev = vh;
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if(index < 0 || index >= len){
            return -1;
        }
        Node curr = vh;
        for(int i = -1 ; i < index; i++){
            curr = curr.next;
        }
        return curr.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node insert = new Node(val);
        if(len == 0){
            vh.next = insert;
            insert.prev = vh;
            insert.next = vt;
            vt.prev = insert;
        }
        else{
            Node oldHead = vh.next;
            vh.next = insert;
            insert.prev = vh;
            insert.next = oldHead;
            oldHead.prev = insert;
        }
        len++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        if(len == 0){
            addAtHead(val);
        }
        else{
            Node insert = new Node(val);
            Node oldTail = vt.prev;
            oldTail.next = insert;
            insert.prev = oldTail;
            insert.next = vt;
            vt.prev = insert;
            len++;
        }
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if(index < 0 || index > len){
            return;
        }
        else if(index == 0){
            addAtHead(val);
        }
        else if(index == len){
            addAtTail(val);
        }
        else {
            Node curr = vh;
            Node insert = new Node(val);
            for(int i = -1; i < index ; i++){
                curr = curr.next;
            }
            Node prev = curr.prev;
            prev.next = insert;
            insert.prev = prev;
            insert.next = curr;
            curr.prev = insert;
            len++;
        }
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(index < 0 || index >= len){
            return;
        }
        else{
            Node curr = vh;
            for(int i = -1 ; i < index ; i++){
                curr = curr.next;
            }
            Node prev = curr.prev;
            Node next = curr.next;
            prev.next = next;
            next.prev = prev;
            len--;
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */