package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.PizzaDAO;
import dat.dtos.PizzaDTO;
import dat.entities.Pizza;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PizzaController implements IController<PizzaDTO, Integer> {

    private final PizzaDAO dao;

    public PizzaController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = PizzaDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx)  {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // DTO
        PizzaDTO pizzaDTO = dao.read(id);
        // response
        ctx.res().setStatus(200);
        ctx.json(pizzaDTO, PizzaDTO.class);

    }

    @Override
    public void readAll(Context ctx) {
        // List of DTOS
        List<PizzaDTO> pizzaDTOS = dao.readAll();
        // response
        ctx.res().setStatus(200);
        ctx.json(pizzaDTOS, PizzaDTO.class);
    }

    @Override
    public void create(Context ctx) {
        // request
        PizzaDTO jsonRequest = ctx.bodyAsClass(PizzaDTO.class);
        // DTO
        PizzaDTO pizzaDTO = dao.create(jsonRequest);
        // response
        ctx.res().setStatus(201);
        ctx.json(pizzaDTO, PizzaDTO.class);
    }

    @Override
    public void update(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        // dto
        PizzaDTO pizzaDTO = dao.update(id, validateEntity(ctx));
        // response
        ctx.res().setStatus(200);
        ctx.json(pizzaDTO, Pizza.class);
    }

    @Override
    public void delete(Context ctx) {
        // request
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        // response
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public PizzaDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(PizzaDTO.class)
                .check( p -> p.getName() != null && !p.getName().isEmpty(), "Pizza name must be set")
                .check( p -> p.getDescription() != null && !p.getDescription().isEmpty(), "Pizza description must be set")
                .check( p -> p.getPrice() != null, "Price must be set")
                .get();
    }
}



