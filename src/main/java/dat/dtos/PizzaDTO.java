package dat.dtos;

import dat.entities.Pizza;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Setter

public class PizzaDTO{
    private Integer id;
    private String name;
    private String description;
    private String topping;
    private Double price;
    private Pizza.PizzaType pizzaType;


    public PizzaDTO(Pizza pizza){
        this.id = pizza.getId();
        this.name = pizza.getName();
        this.description = pizza.getDescription();
        this.topping = pizza.getToppings();
        this.price = pizza.getPrice();
        this.pizzaType = pizza.getPizzaType();


    }

    public PizzaDTO(String name, String description, String topping, Double price, Pizza.PizzaType pizzaType)
    {
        this.name = name;
        this.description = description;
        this.topping = topping;
        this.price = price;
        this.pizzaType = pizzaType;
    }

    public static List<PizzaDTO> toPizzaDTOList(List<Pizza> pizzas) {
        return pizzas.stream().map(PizzaDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof PizzaDTO pizzaDto)) return false;

        return getId().equals(pizzaDto.getId());
    }

    @Override
    public int hashCode()
    {
        return getId().hashCode();
    }



}
