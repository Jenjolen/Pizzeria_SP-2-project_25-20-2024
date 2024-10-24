package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.daos.impl.OrderDAO;
import dat.daos.impl.OrderLineDAO;
import dat.dtos.OrderDTO;
import dat.dtos.OrderLineDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class OrderController implements IController<OrderDTO, Integer> {

    private final OrderDAO dao;
    private final OrderLineDAO orderLineDAO = OrderLineDAO.getInstance(HibernateConfig.getEntityManagerFactory());

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

    public void addOrderLine(Context ctx) {
        int orderId = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        OrderLineDTO orderLineDTO = orderLineDAO.create(ctx.bodyAsClass(OrderLineDTO.class));
        OrderDTO orderDTO = dao.read(orderId);
        orderDTO.getOrderLines().add(orderLineDTO);
        dao.update(orderId, orderDTO);
        ctx.res().setStatus(201);
        ctx.json(orderDTO, OrderDTO.class);
    }

    public void readOrderLine (Context ctx) {
        int orderLineId = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        OrderLineDTO orderLineDTO = orderLineDAO.read(orderLineId);
        ctx.res().setStatus(200);
        ctx.json(orderLineDTO, OrderLineDTO.class);
    }

    public void readAllOrderLines (Context ctx) {
        List<OrderLineDTO> orderLineDTOS = orderLineDAO.readAll();
        ctx.res().setStatus(200);
        ctx.json(orderLineDTOS, OrderLineDTO.class);
    }

    public void readAllOrderLinesByOrder (Context ctx) {
        int orderId = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        List<OrderLineDTO> orderLineDTOS = orderLineDAO.readAllOrderLinesByOrder(orderId);
        ctx.res().setStatus(200);
        ctx.json(orderLineDTOS, OrderLineDTO.class);
    }

    public void updateOrderLine(Context ctx) {
        int orderLineId = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        OrderLineDTO orderLineDTO = orderLineDAO.update(orderLineId, ctx.bodyAsClass(OrderLineDTO.class));
        int orderId = orderLineDTO.getOrder().getId();
        OrderDTO orderDTO = dao.read(orderId);
        dao.update(orderId, orderDTO);
        ctx.res().setStatus(200);
        ctx.json(orderDTO, OrderDTO.class);
    }

    public void deleteOrderLine(Context ctx) {
        int orderLineId = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        OrderLineDTO orderLineDTO = orderLineDAO.read(orderLineId);
        int orderId = orderLineDTO.getOrder().getId();
        OrderDTO orderDTO = dao.read(orderId);
        orderDTO.getOrderLines().removeIf(o -> orderLineDTO.getId().equals(orderLineId)); // tjekker om orderLineId matcher et id i orderDTO - løber alle Order's orderlines
        dao.update(orderId, orderDTO);
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