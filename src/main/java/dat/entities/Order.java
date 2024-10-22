package dat.entities;

import dat.dtos.OrderDTO;
import dat.security.entities.User;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dat.security.entities.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
@Setter
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, unique = true)
    private Integer id;

    @Setter
    @Column(name = "order_date", nullable = false)
    private String orderDate;

    @Setter
    @Column(name = "order_price", nullable = false)
    private Double orderPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderList> orderLists = new HashSet<>();

    // Equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate);
    }

    public Order (OrderDTO orderDTO) {
        this.id = orderDTO.getId();
        this.orderDate = orderDTO.getOrderDate();
        this.orderPrice = orderDTO.getOrderPrice();
        UserDTO userDTO = orderDTO.getUser();
        this.user = new User(userDTO.getUsername(), userDTO.getRoles().stream().map(r -> new dat.security.entities.Role(r)).collect(Collectors.toSet()));
        if (orderDTO.getPizzas() != null) {
//            TODO: JEG HAR HARDCODED QUANTITY TIL 1, DA DER IKKE ER EN MULIGHED FOR AT INDSÆTTE QUANTITY I DTO ELLER NÅR VI OPRETTER EN PIZZA, DA DET ER UNDERORDNET AT MAN BESTILLER EN PIZZA AF GANGEN - KAN DOG GIVE PROBLEMER VED FX 4 ÉNS PIZZAER
            orderDTO.getPizzas().forEach(pizza -> orderLists.add(new OrderList(this, new Pizza(pizza), 1, pizza.getPrice())));
        }
    }


    public enum PizzaType {
        CHILDSIZE, CALZONE, FAMILY, PARTY, REGULAR, SICILIAN, CHEESESTUFFED_CRUST;
    }
}