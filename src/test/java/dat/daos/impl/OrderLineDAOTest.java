package dat.daos.impl;

import dat.dtos.OrderLineDTO;
import dat.entities.Order;
import dat.entities.OrderLine;
import dat.entities.Pizza;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderLineDAOTest {

    private OrderLineDAO orderLineDAO;
    private EntityManagerFactory emf;
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        emf = HibernateConfig.getEntityManagerFactory(); // Hent EntityManagerFactory
        em = emf.createEntityManager(); // Opret EntityManager
        orderLineDAO = OrderLineDAO.getInstance(emf); // Hent instans af OrderLineDAO
    }

    @Test
    public void testCreate() {
        OrderLineDTO orderLineDTO = new OrderLineDTO();
        // Sæt de nødvendige værdier for orderLineDTO
        orderLineDTO.setQuantity(2);
        orderLineDTO.setPrice(19.99);
        orderLineDTO.setOrder(new Order()); // Antag at du har en Order med ID
        orderLineDTO.setPizza(new Pizza()); // Antag at du har en Pizza med ID

        em.getTransaction().begin(); // Start transaktionen
        OrderLineDTO createdOrderLine = orderLineDAO.create(orderLineDTO);
        em.getTransaction().commit(); // Commit transaktionen

        assertNotNull(createdOrderLine);
        assertEquals(orderLineDTO.getQuantity(), createdOrderLine.getQuantity());
    }

    @Test
    public void testRead() {
        int id = 1; // Antag at der findes en OrderLine med ID 1
        em.getTransaction().begin();
        OrderLineDTO orderLineDTO = orderLineDAO.read(id);
        em.getTransaction().commit();

        assertNotNull(orderLineDTO);
        assertEquals(id, orderLineDTO.getId());
    }

    @Test
    public void testReadAll() {
        em.getTransaction().begin();
        List<OrderLineDTO> orderLines = orderLineDAO.readAll();
        em.getTransaction().commit();

        assertNotNull(orderLines);
        assertFalse(orderLines.isEmpty()); // Antag at der er OrderLines i databasen
    }

    @Test
    public void testUpdate() {
        int id = 1; // Antag at der findes en OrderLine med ID 1
        OrderLineDTO orderLineDTO = new OrderLineDTO();
        orderLineDTO.setQuantity(3);
        orderLineDTO.setPrice(25.00);
        orderLineDTO.setOrder(new Order()); // Sæt en eksisterende Order
        orderLineDTO.setPizza(new Pizza()); // Sæt en eksisterende Pizza

        em.getTransaction().begin();
        OrderLineDTO updatedOrderLine = orderLineDAO.update(id, orderLineDTO);
        em.getTransaction().commit();

        assertNotNull(updatedOrderLine);
        assertEquals(orderLineDTO.getQuantity(), updatedOrderLine.getQuantity());
    }

    @Test
    public void testDelete() {
        int id = 1; // Antag at der findes en OrderLine med ID 1

        em.getTransaction().begin();
        orderLineDAO.delete(id);
        em.getTransaction().commit();

        assertFalse(orderLineDAO.validatePrimaryKey(id)); // Bekræft at den er slettet
    }

    @Test
    public void testValidatePrimaryKey() {
        int id = 1; // Antag at der findes en OrderLine med ID 1
        em.getTransaction().begin();
        boolean exists = orderLineDAO.validatePrimaryKey(id);
        em.getTransaction().commit();

        assertTrue(exists);
    }

    @AfterEach
    public void tearDown() {
        if (em != null) {
            em.close(); // Luk EntityManager
        }
    }

    @AfterAll
    public static void tearDownClass() {
        // Luk EntityManagerFactory hvis nødvendigt
        // HibernateConfig.getEntityManagerFactory().close(); // Hvis du har en statisk reference til emf
    }
}