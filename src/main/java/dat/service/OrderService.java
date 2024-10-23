package dat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dat.daos.impl.OrderDAO;
import dat.entities.Order;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrderService {

    private final ObjectMapper objectMapper;
    private final OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.orderDAO = orderDAO;
    }

    // Save a single order to JSON file
    public void saveOrderToFile(Order order, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), order);
    }

    // Save a list of orders to JSON file
    public void saveOrderListToFile(List<Order> orders, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), orders);
    }

    // Save an order to the database (using OrderDAO)
    public Order saveOrderToDatabase(Order order) {
        return orderDAO.create(order);
    }

    // Get all orders from the database
    public List<Order> getAllOrdersFromDatabase() {
        return orderDAO.readAll();
    }
}
