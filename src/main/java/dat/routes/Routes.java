package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final PizzaRoute pizzaRoute = new PizzaRoute();
    private final OrderRoute orderRoute = new OrderRoute();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/pizzas", pizzaRoute.getRoutes());
            path("/orders", orderRoute.getRoutes());

        };
    }
}