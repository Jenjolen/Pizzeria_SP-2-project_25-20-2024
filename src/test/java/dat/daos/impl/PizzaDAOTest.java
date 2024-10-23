package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.entities.Pizza;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PizzaDAOTest {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final PizzaDAO pizzaDAO = PizzaDAO.getInstance(emf);

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
                em.createQuery("DELETE FROM Pizza").executeUpdate();
                em.createQuery("DELETE FROM Pizza").executeUpdate();
            em.getTransaction().commit();

        }

    }

    @Test
    void getInstance() {
    }

    @Test
    void read() {
    }

    @Test
    void readAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void validatePrimaryKey() {
    }
}