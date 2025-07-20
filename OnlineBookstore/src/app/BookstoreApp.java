
package app;

import model.*;
import adt.*;
import algorithm.*;
import java.util.Scanner;

public class BookstoreApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookList inventory = new BookList();
        LinkedListADT<Order> orders = new LinkedListADT<>();
        LinkedStackADT<Book> importHistory = new LinkedStackADT<>();

        while (true) {
            System.out.println("\n===== BOOKSTORE MENU =====");
            System.out.println("1. View book stock");
            System.out.println("2. Place new order");
            System.out.println("3. Find order by customer name");
            System.out.println("4. Show all orders");
            System.out.println("5. Import new book");
            System.out.println("6. Undo last import");
            System.out.println("7. Sort books by title");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    inventory.displayStock();
                    break;
                case 2:
                    System.out.print("Customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Address: ");
                    String address = sc.nextLine();
                    Customer customer = new Customer(name, address);
                    LinkedListADT<Book> orderedBooks = new LinkedListADT<>();

                    while (true) {
                        System.out.print("Book title to buy: ");
                        String bookName = sc.nextLine();
                        int qty;
                        while (true) {
                            System.out.print("Quantity: ");
                            String input = sc.nextLine();
                            if (input.matches("\\d+")) {
                                qty = Integer.parseInt(input);
                                break;
                            } else {
                                System.out.println("[!] Please enter a number.");
                            }
                        }

                        if (inventory.reduceStock(bookName, qty)) {
                            Book found = inventory.findBook(bookName);
                            orderedBooks.addLast(new Book(found.getTitle(), found.getAuthor(), qty));
                        } else {
                            System.out.println("Not enough stock or book not found.");
                        }
                        System.out.print("Add more books? (y/n): ");
                        if (!sc.nextLine().equalsIgnoreCase("y")) break;
                    }
                    orders.addLast(new Order(customer, orderedBooks));
                    System.out.println("Order created for " + name);
                    break;
                case 3:
                    System.out.print("Customer name: ");
                    String searchName = sc.nextLine();
                    Order foundOrder = null;
                    for (int i = 0; i < orders.size(); i++) {
                        if (orders.get(i).getCustomer().getName().equalsIgnoreCase(searchName)) {
                            foundOrder = orders.get(i);
                            break;
                        }
                    }
                    if (foundOrder != null) {
                        System.out.println(foundOrder);
                        for (int i = 0; i < foundOrder.getBooks().size(); i++) {
                            System.out.println("- " + foundOrder.getBooks().get(i));
                        }
                    } else {
                        System.out.println("Order not found.");
                    }
                    break;
                case 4:
                    if (orders.size() == 0) {
                        System.out.println("No orders found.");
                    } else {
                        System.out.println("\n All Orders:");
                        for (int i = 0; i < orders.size(); i++) {
                            Order o = orders.get(i);
                            System.out.println(o);
                            for (int j = 0; j < o.getBooks().size(); j++) {
                                System.out.println("   - " + o.getBooks().get(j));
                            }
                        }
                    }
                    break;

                case 5:
                    Book newBook = inventory.addBookViaInput();
                    if (newBook != null) {
                        importHistory.push(newBook);
                        System.out.println("Book imported and saved to history.");
                    }
                    break;
                case 6:
                    if (!importHistory.isEmpty()) {
                        Book undoBook = importHistory.pop();
                        if (inventory.undoImport(undoBook)) {
                            System.out.println("Last import undone.");
                        }
                    } else {
                        System.out.println("No import history.");
                    }
                    break;
                case 7:
                    Sort.bubbleSortByTitle(inventory.getStock());
                    System.out.println("Books sorted by title.");
                    break;
                case 8:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
