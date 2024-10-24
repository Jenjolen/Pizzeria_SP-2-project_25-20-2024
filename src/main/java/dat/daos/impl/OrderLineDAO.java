package dat.daos.impl;

import dat.dtos.OrderLineDTO;
import dat.entities.Order;
import dat.entities.OrderLine;
import dat.entities.Pizza;
import dat.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public class OrderLineDAO {

    private static OrderLineDAO instance;
    private final EntityManagerFactory emf;

    private OrderLineDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static OrderLineDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new OrderLineDAO(emf);
        }
        return instance;
    }

    public OrderLineDTO create (OrderLineDTO orderLineDTO) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        OrderLine orderLine = new OrderLine(orderLineDTO);
        Order order = em.find(Order.class, orderLineDTO.getOrder().getId());
        Pizza pizza = em.find(Pizza.class, orderLineDTO.getPizza().getId());
        orderLine.setOrder(order);
        orderLine.setPizza(pizza);
        order.getOrderLines().add(orderLine);
        em.persist(orderLine);
        em.merge(order);
        em.getTransaction().commit();
        em.close();
        return new OrderLineDTO(orderLine);
    }

    public OrderLineDTO read(int id) {
        EntityManager em = emf.createEntityManager();
        OrderLine orderLine = em.find(OrderLine.class, id);
        em.close();
        return orderLine != null ? new OrderLineDTO(orderLine) : null; // Returner OrderLineDTO
    }

    public List <OrderLineDTO> readAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<OrderLine> query = em.createQuery("SELECT o FROM OrderLine o", OrderLine.class);
        List<OrderLine> orders = query.getResultList();
        em.close();
        return orders.stream().map(OrderLineDTO::new).collect(Collectors.toList()); // Konverter til OrderLineDTO
    }

    public List<OrderLineDTO> readAllOrderLinesByOrder(Integer orderId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<OrderLine> query = em.createQuery("SELECT o FROM OrderLine o WHERE o.order.id = :orderId", OrderLine.class);
        List<OrderLine> orderLines = query.getResultList();
        em.close();
        return orderLines.stream().map(OrderLineDTO::new).collect(Collectors.toList());
    }

    public OrderLineDTO update(Integer id, OrderLineDTO orderLineDTO) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        OrderLine orderLine = em.find(OrderLine.class, id);
        if (orderLine != null) {
            orderLine.setOrder(orderLineDTO.getOrder());
            orderLine.setPizza(orderLineDTO.getPizza());
            orderLine.setQuantity(orderLineDTO.getQuantity());
            orderLine.setPrice(orderLineDTO.getPrice());
            em.merge(orderLine);
        }
        em.getTransaction().commit();
        em.close();
        return new OrderLineDTO(orderLine);}

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        OrderLine orderLine = em.find(OrderLine.class, id);
        if (orderLine != null) {
            em.remove(orderLine); // Slet orderLine
            em.remove(orderLine.getOrder().getOrderLines().remove(orderLine)); // Sletter orderLine fra order
        }
        em.getTransaction().commit();
        em.close();
    }

    public boolean validatePrimaryKey(Integer id) {
        EntityManager em = emf.createEntityManager();
        OrderLine orderLine = em.find(OrderLine.class, id);
        em.close();
        return orderLine != null; // Returner true, hvis orderLine findes
    }





}
