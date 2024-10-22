package dat.config;

import dat.entities.Order;
import dat.entities.OrderList;
import dat.entities.Pizza;
import dat.security.entities.User; // Opdateret import
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;

public class Populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Opret brugere
            User user1 = new User("john.doe", "password123"); // Brugernavn og password
            user1.setAge(30); // Hvis du har en metode til at sætte alder
            user1.setGender("Male"); // Hvis du har en metode til at sætte køn
            user1.setEmail("john.doe@example.com"); // Hvis du har en metode til at sætte email

            User user2 = new User("jane.smith", "password456"); // Brugernavn og password
            user2.setAge(28);
            user2.setGender("Female");
            user2.setEmail("jane.smith@example.com");

            // Opret pizzaer
            Pizza pizza1 = new Pizza();
            pizza1.setName("Margherita");
            pizza1.setDescription("Classic pizza with tomato and mozzarella.");
            pizza1.setToppings("Tomato, Mozzarella, Basil");
            pizza1.setPrice(75.0);

            Pizza pizza2 = new Pizza();
            pizza2.setName("Pepperoni");
            pizza2.setDescription("Spicy pepperoni with cheese.");
            pizza2.setToppings("Tomato, Mozzarella, Pepperoni");
            pizza2.setPrice(85.0);

            // Opret ordrer
            Order order1 = new Order();
            order1.setOrderDate("2023-10-01");
            order1.setOrderPrice(150.0);
            order1.setUser(user1);

            Order order2 = new Order();
            order2.setOrderDate("2023-10-02");
            order2.setOrderPrice(160.0);
            order2.setUser(user2);

            // Opret orderlister
            OrderList orderList1 = new OrderList();
            orderList1.setOrder(order1);
            orderList1.setPizza(pizza1);
            orderList1.setQuantity(2);
            orderList1.setPrice(150.0);

            OrderList orderList2 = new OrderList();
            orderList2.setOrder(order2);
            orderList2.setPizza(pizza2);
            orderList2.setQuantity(2);
            orderList2.setPrice(160.0);

            // Gem entiteterne
            em.persist(user1);
            em.persist(user2);
            em.persist(pizza1);
            em.persist(pizza2);
            em.persist(order1);
            em.persist(order2);
            em.persist(orderList1);
            em.persist(orderList2);

            em.getTransaction().commit();
        }
    }
}