package dat.daos.impl;

import dat.dtos.OrderDTO;
import dat.entities.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDAO {
    private static OrderDAO instance;
    private final EntityManagerFactory emf;

    private OrderDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static OrderDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new OrderDAO(emf);
        }
        return instance;
    }

    public OrderDTO create(OrderDTO orderDTO) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Order order = new Order(); // Opret en ny Order
        // Konverter OrderDTO til Order
        order.setOrderDate(orderDTO.getOrderDate());
        order.setOrderPrice(orderDTO.getOrderPrice());
        // ... sæt andre felter ...
        em.persist(order); // Gem order i databasen
        em.getTransaction().commit();
        em.close();
        return new OrderDTO(order); // Returner den nye OrderDTO
    }

    public OrderDTO read(int id) {
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class, id);
        em.close();
        return order != null ? new OrderDTO(order) : null; // Returner OrderDTO
    }

    public List<OrderDTO> readAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o", Order.class);
        List<Order> orders = query.getResultList();
        em.close();
        return orders.stream().map(OrderDTO::new).collect(Collectors.toList()); // Konverter til OrderDTO
    }

    public OrderDTO update(Integer id, OrderDTO orderDTO) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Order order = em.find(Order.class, id);
        if (order != null) {
            // Opdater felter
            order.setOrderDate(orderDTO.getOrderDate());
            order.setOrderPrice(orderDTO.getOrderPrice());
            // ... opdater andre felter ...
            em.merge(order); // Gem ændringerne
        }
        em.getTransaction().commit();
        em.close();
        return new OrderDTO(order); // Returner den opdaterede OrderDTO
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Order order = em.find(Order.class, id);
        if (order != null) {
            em.remove(order); // Slet order
        }
        em.getTransaction().commit();
        em.close();
    }

    public boolean validatePrimaryKey(Integer id) {
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class, id);
        em.close();
        return order != null; // Returner true, hvis order findes
    }
}