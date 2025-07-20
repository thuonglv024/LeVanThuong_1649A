package adt;

public class LinkedListADT<T> {
    private class Node {
        T data;
        Node next;
        Node(T data) {
            this.data = data;
        }
    }

    private Node head;
    private int size = 0;

    public void addLast(T data) {
        Node node = new Node(data);
        if (head == null) {
            head = node;
        } else {
            //cur = current
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    public void set(int index, T data) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.data = data;
    }

    public void swap(int i, int j) {
        T temp = get(i);
        set(i, get(j));
        set(j, temp);
    }

    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node toDelete;

        if (index == 0) {
            toDelete = head;
            head = head.next;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            toDelete = prev.next;
            prev.next = toDelete.next;
        }

        toDelete.next = null;
        toDelete = null;

        size--;
    }


    public int size() {
        return size;
    }
}
