package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizza")
@Getter
@Setter
@NoArgsConstructor
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pizza_id")
    private Integer pizzaId;

    private String name;
    private String description;
    private String toppings;
    private double price;

    @OneToMany(mappedBy = "pizza")
    private List<OrderItem> orderItems = new ArrayList<>();
}