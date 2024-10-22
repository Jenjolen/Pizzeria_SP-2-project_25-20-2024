package dat.dtos;

import dat.entities.Order;
import dk.bugelhartmann.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class OrderDTO {
    private Integer id;
    private String orderDate;
    private Double orderPrice;
    //private Order.PizzaType pizzaType;
    private Set<PizzaDTO> pizzas = new HashSet<>();

    public OrderDTO(Order order){
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderPrice = order.getOrderPrice();
        if (order.getOrderLists() != null)
        {
            order.getOrderLists().forEach( pizza -> pizzas.add(new PizzaDTO(pizza.getPizza())));
        }

    }
    public OrderDTO(Integer id, String orderDate, Double orderPrice)
    {
        this.id = id;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
    }
}
