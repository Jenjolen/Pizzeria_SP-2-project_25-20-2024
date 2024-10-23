package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.OrderDAO;
import dat.dtos.OrderDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class OrderController implements IController<OrderDTO, Integer> {

    private final OrderDAO dao;

    public OrderController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = OrderDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        OrderDTO orderDTO = dao.read(id);
        ctx.res().setStatus(200);
        ctx.json(orderDTO, OrderDTO.class);
    }

    @Override
    public void readAll(Context ctx) {
        List<OrderDTO> orderDTOS = dao.readAll();
        ctx.res().setStatus(200);
        ctx.json(orderDTOS, OrderDTO.class);
    }

    @Override
    public void create(Context ctx) {
        OrderDTO jsonRequest = ctx.bodyAsClass(OrderDTO.class);
        OrderDTO orderDTO = dao.create(jsonRequest);
        ctx.res().setStatus(201);
        ctx.json(orderDTO, OrderDTO.class);
    }

    @Override
    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        OrderDTO orderDTO = dao.update(id, validateEntity(ctx));
        ctx.res().setStatus(200);
        ctx.json(orderDTO, OrderDTO.class); // Ændret fra Order.class til OrderDTO.class
    }

    @Override
    public void delete(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        dao.delete(id);
        ctx.res().setStatus(204);
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return dao.validatePrimaryKey(integer);
    }

    @Override
    public OrderDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(OrderDTO.class)
                .check(o -> o.getOrderDate() != null && !o.getOrderDate().isEmpty(), "Order date must be set")
                .check(o -> o.getOrderPrice() != null, "Order price must be set")
                .get();
    }
}