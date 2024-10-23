package dat.dtos;

import dat.entities.Order;
import dk.bugelhartmann.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Setter
public class OrderDTO {
    private Integer id;
    private String orderDate;
    private Double orderPrice;
    private Set<OrderLineDTO> orderLines = new HashSet<>();
    private UserDTO user;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderPrice = order.getOrderPrice();
        this.user = new UserDTO(order.getUser().getUsername(), order.getUser().getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()));

        if (order.getOrderLines() != null)
        {
            order.getOrderLines().forEach(orderLine -> orderLines.add(new OrderLineDTO(orderLine)));
        }
    }

    public OrderDTO(Integer id, String orderDate, Double orderPrice, UserDTO user)
    {
        this.id = id;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.user = user;

    }
}