package dat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dat.daos.impl.OrderDAO;
import dat.dtos.OrderDTO;
import dat.entities.Orders;

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
    public void saveOrderToFile(Orders orders, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), orders);
    }

    // Save a list of orders to JSON file
    public void saveOrderListToFile(List<Orders> orders, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), orders);
    }

    // Save an order to the database (using OrderDAO)
    public Orders saveOrderToDatabase(OrderDTO orderDTO) {

        orderDAO.create(orderDTO);
        Orders orders = new Orders(orderDTO);
        return orders;

    }

    // Get all orders from the database
    public List<OrderDTO> getAllOrdersFromDatabase() {
        return (orderDAO.readAll());
    }
}
