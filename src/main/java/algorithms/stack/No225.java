package algorithms.stack;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 225. 用队列实现栈
 * @author Sean Yu
 */
public class No225 {
    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.top(); // 返回 2
        myStack.pop(); // 返回 2
        myStack.empty(); // 返回 False
    }
}

/**
 *  思路：一个队列纯当备用
 *  https://mp.weixin.qq.com/s/yzn6ktUlL-vRG3-m5a8_Yw
 */
class MyStack {
    Queue<Integer> curr;
    Queue<Integer> backup;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {
        curr = new ArrayDeque<>();
        backup = new ArrayDeque<>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        curr.offer(x);
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        while (curr.size() > 1) {
            backup.offer(curr.poll());
        }
        int res = curr.poll();
        Queue temp = curr;
        curr = backup;
        backup = temp;
        return res;
    }

    /**
     * Get the top element.
     */
    public int top() {
        while (curr.size() > 1) {
            backup.offer(curr.poll());
        }
        int res = curr.poll();
        backup.offer(res);
        Queue temp = curr;
        curr = backup;
        backup = temp;
        return res;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return curr.isEmpty() && backup.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
