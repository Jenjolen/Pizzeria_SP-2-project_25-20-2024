package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final PizzaRoute pizzaRoute = new PizzaRoute();
    private final HotelRoute hotelRoute = new HotelRoute();
    private final RoomRoute roomRoute = new RoomRoute();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/pizzas", pizzaRoute.getRoutes());
            path("/hotels", hotelRoute.getRoutes());
            path("/rooms", roomRoute.getRoutes());
        };
    }
}