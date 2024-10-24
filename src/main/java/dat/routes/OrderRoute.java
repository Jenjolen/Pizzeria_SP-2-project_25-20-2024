package dat.routes;

import dat.controllers.impl.OrderController;
import dat.controllers.impl.PizzaController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class OrderRoute {

    private final OrderController orderController = new OrderController();

    protected EndpointGroup getRoutes() {

        return () -> {
            //  get("/populate", orderController::populate);
            // Order routes
            post("/", orderController::create, Role.USER);
            get("/", orderController::readAll);
            get("/{id}", orderController::read);
            put("/{id}", orderController::update, Role.ADMIN, Role.USER);
            delete("/{id}", orderController::delete, Role.ADMIN);

            // OrderLine routes
            post("/{id}/orderline", orderController::addOrderLine, Role.USER); // vi laver en ny orderline på en order med et bestemt orderId
            put("/orderline/{id}", orderController::updateOrderLine, Role.USER, Role.ADMIN); // orderLineId er hvad Id refererer til
            delete("/orderline/{id}", orderController::deleteOrderLine, Role.USER, Role.ADMIN); // orderLineId er hvad Id refererer til
            get("/orderline/{id}", orderController::readOrderLine, Role.USER, Role.ADMIN); // orderLineId er hvad Id refererer til - vi henter en orderline med et bestemt id
            get("/orderline", orderController::readAllOrderLines, Role.USER, Role.ADMIN); // vi henter alle orderlines uanset order
            get("/{id}/orderline", orderController::readAllOrderLinesByOrder, Role.USER, Role.ADMIN); // orderId er hvad Id refererer til - vi henter alle orderlines på en order med et bestemt orderId

        };
    }



}
