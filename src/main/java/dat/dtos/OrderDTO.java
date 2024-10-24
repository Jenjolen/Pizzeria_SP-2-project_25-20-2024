package dat.dtos;

import dat.entities.Orders;
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

    public OrderDTO(Orders orders) {
        this.id = orders.getId();
        this.orderDate = orders.getOrderDate();
        this.orderPrice = orders.getOrderPrice();
        this.user = new UserDTO(orders.getUser().getUsername(), orders.getUser().getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()));

        if (orders.getOrderLines() != null)
        {
            orders.getOrderLines().forEach(orderLine -> this.orderLines.add(new OrderLineDTO(orderLine)));
        }
    }

    public OrderDTO(String orderDate, Double orderPrice, UserDTO user)
    {
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.user = user;

    }

    public OrderDTO(String orderDate, Double orderPrice, UserDTO user, Set<OrderLineDTO> orderLines) {
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.user = user;
        this.orderLines = orderLines;
    }

}