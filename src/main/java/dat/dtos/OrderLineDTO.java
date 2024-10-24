package dat.dtos;

import dat.entities.Orders;
import dat.entities.OrderLine;
import dat.entities.Pizza;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class OrderLineDTO {

    private Integer id;
    private Orders orders;
    private Pizza pizza;
    private Integer quantity;
    private Double price;

    public OrderLineDTO(Orders orders, Pizza pizza, Integer quantity, Double price) {
        this.orders = orders;
        this.pizza = pizza;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLineDTO(OrderLine orderLine) {
        this.id = orderLine.getId();
        this.orders = orderLine.getOrders();
        this.pizza = orderLine.getPizza();
        this.quantity = orderLine.getQuantity();
        this.price = orderLine.getPrice();
    }


}
