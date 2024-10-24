package dat.service;

import dat.entities.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrderListService {

    private List<Orders> orders;

    public OrderListService() {
        this.orders = new ArrayList<>();
    }

    // Add an order to the list
    public void addOrder(Orders orders) {
        this.orders.add(orders);
    }

    // Get all orders
    public List<Orders> getOrders() {
        return this.orders;
    }

    // Clear the order list (if needed)
    public void clearOrders() {
        this.orders.clear();
    }
}
