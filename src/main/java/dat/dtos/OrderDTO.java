package dat.dtos;

import dat.entities.Order;
import dat.security.entities.User;
import dk.bugelhartmann.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private String orderDate;
    private Double orderPrice;
    private Order.PizzaType pizzaType;
    private Set<PizzaDTO> pizzas = new HashSet<>();
    private UserDTO user;

    public OrderDTO(Order order){
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderPrice = order.getOrderPrice();
        this.user = new UserDTO(order.getUser().getUsername(), order.getUser().getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()));
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
