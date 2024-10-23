package dat.entities;

import dat.dtos.OrderLineDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Setter
@Table(name = "order_list")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false, unique = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    @Setter
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Setter
    @Column(name = "price", nullable = false)
    private Double price;

    public OrderLine(Order order, Pizza pizza, Integer quantity, Double price) {
        this.order = order;
        this.pizza = pizza;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderLine(OrderLineDTO orderLineDTO) {
        this.id = orderLineDTO.getId();
        this.order = orderLineDTO.getOrder();
        this.pizza = orderLineDTO.getPizza();
        this.quantity = orderLineDTO.getQuantity();
        this.price = orderLineDTO.getPrice();
    }

    // Equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return Objects.equals(order, orderLine.order) && Objects.equals(pizza, orderLine.pizza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, pizza);
    }
}