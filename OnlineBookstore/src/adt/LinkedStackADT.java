package adt;

public class LinkedStackADT<T> {
    private class Node {
        T data;
        Node next;
        Node(T data) { this.data = data; }
    }

    private Node top;
    private int size = 0;

    public void push(T item) {
        Node node = new Node(item);
        node.next = top;
        top = node;
        size++;
    }

    public T pop() {
        if (top == null) return null;
        T item = top.data;
        Node oldTop = top;
        top = top.next;
        oldTop.next = null;

        size--;
        return item;
    }


    public T peek() {
        return (top != null) ? top.data : null;
    }

    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }
}
