package dat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dat.dtos.OrderDTO;
import dat.dtos.OrderLineDTO;
import dat.dtos.PizzaDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriterUtil { // Kommentar så jeg kan commit :)

    private static final String ORDERS_FILE = "orders.json";
    private static final String ORDER_LINES_FILE = "order_lines.json";
    private static final String PIZZAS_FILE = "pizzas.json";

    private final ObjectMapper objectMapper;

    public FileWriterUtil() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing
    }

    public void saveOrders(List<OrderDTO> orders) throws IOException {
        objectMapper.writeValue(new File(ORDERS_FILE), orders);
    }

    public void saveOrderLines(List<OrderLineDTO> orderLines) throws IOException {
        objectMapper.writeValue(new File(ORDER_LINES_FILE), orderLines);
    }

    public void savePizzas(List<PizzaDTO> pizzas) throws IOException {
        objectMapper.writeValue(new File(PIZZAS_FILE), pizzas);
    }
}