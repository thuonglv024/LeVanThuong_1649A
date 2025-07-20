package algorithm;

import adt.LinkedListADT;
import model.Order;

public class Search {
    public static Order findOrderByCustomerName(LinkedListADT<Order> orders, String name) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getCustomer().getName().equalsIgnoreCase(name)) {
                return orders.get(i);
            }
        }
        return null;
    }
}
