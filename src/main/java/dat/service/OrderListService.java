package dat.service;

import dat.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListService {

    private List<Order> orders;

    public OrderListService() {
        this.orders = new ArrayList<>();
    }

    // Add an order to the list
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    // Get all orders
    public List<Order> getOrders() {
        return this.orders;
    }

    // Clear the order list (if needed)
    public void clearOrders() {
        this.orders.clear();
    }
}
