package dat.daos.impl;

import dat.dtos.OrderDTO;
import dat.dtos.OrderLineDTO;
import dat.entities.Orders;
import dat.entities.OrderLine;
import dat.security.entities.User;
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
        Orders orders = new Orders(orderDTO);
        User user = em.find(User.class, orderDTO.getUser().getUsername());
        orders.setUser(user);
        em.persist(orders);
        em.getTransaction().commit();
        em.close();
        return new OrderDTO(orders);
    }

    public OrderDTO read(int id) {
        EntityManager em = emf.createEntityManager();
        Orders orders = em.find(Orders.class, id);
        em.close();
        return orders != null ? new OrderDTO(orders) : null; // Returner OrderDTO
    }

    public List<OrderDTO> readAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Orders> query = em.createQuery("SELECT o FROM Orders o", Orders.class);
        List<Orders> orders = query.getResultList();
        em.close();
        return orders.stream().map(OrderDTO::new).collect(Collectors.toList()); // Konverter til OrderDTO
    }

    public OrderDTO update(Integer id, OrderDTO orderDTO) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Orders orders = em.find(Orders.class, id);
        if (orders != null) {
            orders.setOrderDate(orderDTO.getOrderDate());
            orders.setOrderPrice(orderDTO.getOrderPrice());
            orders.getOrderLines().clear();
            for (OrderLineDTO orderLineDTO : orderDTO.getOrderLines()) {
                orders.getOrderLines().add(new OrderLine(orderLineDTO));
            }
            User user = em.find(User.class, orderDTO.getUser().getUsername());
            orders.setUser(user);
            em.merge(orders);
        }
        em.getTransaction().commit();
        em.close();
        return new OrderDTO(orders);}

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Orders orders = em.find(Orders.class, id);
        if (orders != null) {
            em.remove(orders); // Slet order
        }
        em.getTransaction().commit();
        em.close();
    }

    public boolean validatePrimaryKey(Integer id) {
        EntityManager em = emf.createEntityManager();
        Orders orders = em.find(Orders.class, id);
        em.close();
        return orders != null; // Returner true, hvis order findes
    }

}