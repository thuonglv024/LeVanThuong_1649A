package model;

import adt.LinkedListADT;

public class Order {
    private static int nextId = 1000;
    private int orderId;
    private Customer customer;
    private LinkedListADT<Book> books;

    public Order(Customer customer, LinkedListADT<Book> books) {
        this.orderId = nextId++;
        this.customer = customer;
        this.books = books;
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public LinkedListADT<Book> getBooks() { return books; }

    @Override
    public String toString() {
        return "🧾 Order ID: " + orderId + ", Customer: " + customer.getName() + ", Address: " + customer.getAddress();
    }
}
