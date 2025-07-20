package model;

import adt.LinkedListADT;
import java.util.Scanner;

public class BookList {
    private LinkedListADT<Book> stock;

    public BookList() {
        stock = new LinkedListADT<>();
        initializeStock();
    }

    private void initializeStock() {
        stock.addLast(new Book("Java Programming Basics", "John A. Nguyen", 10));
        stock.addLast(new Book("Data Structures and Algorithms", "Alice Tran", 8));
        stock.addLast(new Book("Advanced Algorithms", "Charles Pham", 12));
        stock.addLast(new Book("System Analysis and Design", "Diana Le", 7));
        stock.addLast(new Book("Computer Networks", "Edward Ngo", 5));
        stock.addLast(new Book("Operating Systems", "Fiona Dang", 9));
        stock.addLast(new Book("Database Systems", "George Truong", 11));
        stock.addLast(new Book("Object-Oriented Programming", "Hannah Vo", 6));
        stock.addLast(new Book("Information Security", "Isaac Nguyen", 13));
        stock.addLast(new Book("Artificial Intelligence", "Jennifer Doan", 10));
    }

    public LinkedListADT<Book> getStock() {
        return stock;
    }

    public void displayStock() {
        System.out.println("\n=== 📚 Book Stock ===");
        for (int i = 0; i < stock.size(); i++) {
            System.out.println((i + 1) + ". " + stock.get(i));
        }
    }

    public Book findBook(String title) {
        for (int i = 0; i < stock.size(); i++) {
            if (stock.get(i).getTitle().equalsIgnoreCase(title)) {
                return stock.get(i);
            }
        }
        return null;
    }

    public boolean reduceStock(String title, int quantity) {
        Book b = findBook(title);
        if (b != null && b.getQuantity() >= quantity) {
            b.setQuantity(b.getQuantity() - quantity);
            return true;
        }
        return false;
    }

    public Book addBookViaInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Book title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("Quantity: ");
        int quantity = sc.nextInt();

        Book existing = findBook(title);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return new Book(title, author, quantity);  // Log for undo
        } else {
            Book newBook = new Book(title, author, quantity);
            stock.addLast(newBook);
            return newBook;
        }
    }

    public boolean undoImport(Book b) {
        Book existing = findBook(b.getTitle());
        if (existing != null) {
            if (existing.getQuantity() > b.getQuantity()) {
                existing.setQuantity(existing.getQuantity() - b.getQuantity());
            } else {
                for (int i = 0; i < stock.size(); i++) {
                    if (stock.get(i).getTitle().equalsIgnoreCase(b.getTitle())) {
                        stock.removeAt(i);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
