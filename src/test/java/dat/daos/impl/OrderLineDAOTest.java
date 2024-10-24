package dat.daos.impl;

import dat.dtos.OrderLineDTO;
import dat.entities.Orders;
import dat.entities.Pizza;
import dat.security.entities.User;
import dat.security.entities.Role;// Importer User
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

        // Opret testdata for User, Orders og Pizza
        em.getTransaction().begin();

        // Opret bruger
        User user = new User();
        user.setUsername("testUser");
        user.setAge(25);
        user.setEmail("test@example.com");
        // Tilføj eventuelle roller til brugeren her, hvis nødvendigt
        em.persist(user); // Gem bruger i databasen

        // Opret ordre
        Orders order = new Orders();
        order.setOrderDate("2024-10-25");
        order.setOrderPrice(0.0);
        order.setUser(user); // Sæt den oprettede bruger
        em.persist(order); // Gem ordre i databasen

        // Opret pizza
        Pizza pizza = new Pizza();
        pizza.setName("Test Pizza");
        pizza.setPrice(19.99);
        pizza.setDescription("Delicious test pizza");
        pizza.setPizzaType(Pizza.PizzaType.REGULAR); // Sæt pizzaType til en enum værdi
        em.persist(pizza); // Gem pizza i databasen

        em.getTransaction().commit();
    }

    @Test
    public void testCreate() {
        OrderLineDTO orderLineDTO = new OrderLineDTO();
        orderLineDTO.setQuantity(2);
        orderLineDTO.setPrice(19.99);

        // Hent den oprettede ordre og pizza fra databasen
        Orders order = em.find(Orders.class, 1); // Antag at ID 1 er den oprettede ordre
        Pizza pizza = em.find(Pizza.class, 1); // Antag at ID 1 er den oprettede pizza
        orderLineDTO.setOrders(order);
        orderLineDTO.setPizza(pizza);

        em.getTransaction().begin();
        OrderLineDTO createdOrderLine = orderLineDAO.create(orderLineDTO);
        em.getTransaction().commit();

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

        // Hent den eksisterende ordre og pizza fra databasen
        Orders order = em.find(Orders.class, 1); // Antag at ID 1 er den oprettede ordre
        Pizza pizza = em.find(Pizza.class, 1); // Antag at ID 1 er den oprettede pizza
        orderLineDTO.setOrders(order);
        orderLineDTO.setPizza(pizza);

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