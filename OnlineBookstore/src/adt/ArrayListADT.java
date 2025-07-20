package adt;

import java.util.Arrays;


public class ArrayListADT<T> {
    private T[] data;
    private int size;

    public ArrayListADT() {
        data = (T[]) new Object[10];
        size = 0;
    }
    private void resize() {
        int newLength = data.length * 2;
        data = Arrays.copyOf(data, newLength);
    }

    public void add(T value) {
        if (size == data.length) {
            resize();
        }
        data[size] = value;
        size++;
    }

    public T get(int index) {
        checkIndex(index);
        return data[index];
    }

    public void set(int index, T value) {
        checkIndex(index);
        data[index] = value;
    }

    public int size() {
        return size;
    }


    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
    }
}
