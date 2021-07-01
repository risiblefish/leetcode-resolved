package algorithms.stackandqueue;

import java.util.Stack;

/**
 * 232. 用栈实现队列
 */
public class No232 {
    public static void main(String[] args) {
        MyQueue q = new MyQueue();
        q.push(1);
        q.push(2);
        System.out.println(q.peek());
        System.out.println(q.pop());
    }
}

class MyQueue {
    Stack<Integer> curr,backup;

    /** Initialize your data structure here. */
    public MyQueue() {
        curr = new Stack();
        backup = new Stack();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        curr.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if(!backup.isEmpty()){
            return backup.pop();
        }
        while(!curr.isEmpty()){
            backup.push(curr.pop());
        }
        return backup.pop();
    }

    /** Get the front element. */
    public int peek() {
        if(!backup.isEmpty()){
            return backup.peek();
        }
        while(!curr.isEmpty()){
            backup.push(curr.pop());
        }
        return  backup.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return curr.isEmpty() && backup.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */