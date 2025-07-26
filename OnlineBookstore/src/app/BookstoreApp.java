
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

        // LOGIN SYSTEM
        String role = "";
        while (true) {
            System.out.print("Login as (admin/user): ");
            role = sc.nextLine().trim().toLowerCase();
            if (role.equals("admin") || role.equals("user")) {
                break;
            } else {
                System.out.println("[!] Invalid role. Please enter 'admin' or 'user'.");
            }
        }

        while (true) {
            System.out.println("\n===== BOOKSTORE MENU =====");
            System.out.println("1. View book stock");
            if (role.equals("user") || role.equals("admin")) {
                System.out.println("2. Place new order");
            }
            if (role.equals("admin")) {
                System.out.println("3. Find order by customer name");
                System.out.println("4. Show all orders");
            }
            if (role.equals("admin")) {
                System.out.println("5. Import new book");
                System.out.println("6. Undo last import");
            }
            System.out.println("7. Sort books by title");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    inventory.displayStock();
                    break;

                case 2:
                    if (role.equals("user") || role.equals("admin")) {
                        System.out.println("Place a New Order");

                        String name;
                        do {
                            System.out.print("Customer name: ");
                            name = sc.nextLine().trim();
                            if (name.isEmpty()) {
                                System.out.println("[!] Name cannot be empty. Please enter again.");
                            }
                        } while (name.isEmpty());

                        String address;
                        do {
                            System.out.print("Address: ");
                            address = sc.nextLine().trim();
                            if (address.isEmpty()) {
                                System.out.println("[!] Address cannot be empty. Please enter again.");
                            }
                        } while (address.isEmpty());

                        Customer customer = new Customer(name, address);
                        LinkedListADT<Book> orderedBooks = new LinkedListADT<>();

                        while (true) {
                            String bookName;
                            do {
                                System.out.print("Book title to buy: ");
                                bookName = sc.nextLine().trim();
                                if (bookName.isEmpty()) {
                                    System.out.println("[!] Book title cannot be empty.");
                                }
                            } while (bookName.isEmpty());

                            int qty = -1;
                            while (qty <= 0) {
                                System.out.print("Quantity (positive number): ");
                                String input = sc.nextLine().trim();
                                if (input.matches("\\d+")) {
                                    qty = Integer.parseInt(input);
                                    if (qty <= 0) {
                                        System.out.println("[!] Quantity must be greater than 0.");
                                    }
                                } else {
                                    System.out.println("[!] Please enter a valid number.");
                                }
                            }

                            if (inventory.reduceStock(bookName, qty)) {
                                Book found = inventory.findBook(bookName);
                                orderedBooks.addLast(new Book(found.getTitle(), found.getAuthor(), qty));
                                System.out.println("Book added to order.");
                            } else {
                                System.out.println("Not enough stock or book not found.");
                            }

                            System.out.print("Add more books? (y/n): ");
                            if (!sc.nextLine().equalsIgnoreCase("y")) break;
                        }

                        orders.addLast(new Order(customer, orderedBooks));
                        System.out.println("Order created successfully for " + name);
                    } else {
                        System.out.println("[!] Access denied. Only users or admins can place orders.");
                    }
                    break;

                case 3:
                    if (!role.equals("admin")) {
                        System.out.println("[!] Access denied. Admin only function.");
                        break;
                    }
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
                    if (!role.equals("admin")) {
                        System.out.println("[!] Access denied. Admin only function.");
                        break;
                    }
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
                    if (!role.equals("admin")) {
                        System.out.println("[!] Access denied. Admin only function.");
                        break;
                    }
                    String title = "";
                    String author = "";
                    int quantity = 0;

                    while (true) {
                        System.out.print("Book title: ");
                        title = sc.nextLine().trim();
                        if (title.isEmpty()) {
                            System.out.println("[!] Title cannot be empty. Please re-enter.");
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        System.out.print("Author: ");
                        author = sc.nextLine().trim();
                        if (author.isEmpty()) {
                            System.out.println("[!] Author cannot be empty. Please re-enter.");
                        } else {
                            break;
                        }
                    }

                    while (true) {
                        System.out.print("Quantity: ");
                        String input = sc.nextLine().trim();
                        if (input.isEmpty()) {
                            System.out.println("[!] Quantity cannot be empty. Please re-enter.");
                            continue;
                        }
                        if (!input.matches("\\d+")) {
                            System.out.println("[!] Quantity must be a positive integer.");
                            continue;
                        }
                        quantity = Integer.parseInt(input);
                        if (quantity <= 0) {
                            System.out.println("[!] Quantity must be greater than 0.");
                        } else {
                            break;
                        }
                    }

                    Book existing = inventory.findBook(title);
                    Book newBook = new Book(title, author, quantity);

                    if (existing != null) {
                        existing.setQuantity(existing.getQuantity() + quantity);
                    } else {
                        inventory.getStock().addLast(newBook);
                    }

                    importHistory.push(newBook);
                    System.out.println("Book imported and saved to history.");
                    break;

                case 6:
                    if (!role.equals("admin")) {
                        System.out.println("[!] Access denied. Admin only function.");
                        break;
                    }
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
