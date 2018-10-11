package seedu.address.testutil;

import seedu.address.model.route.Route;
import seedu.address.model.route.RouteList;

/**
 * A utility class to help with building Route list objects.
 * Example usage: <br>
 *     {@code RouteList ab = new RouteListBuilder().withRoute("John", "Doe").build();}
 */
public class RouteListBuilder {

    private RouteList routeList;

    public RouteListBuilder() {
        routeList = new RouteList();
    }

    public RouteListBuilder(RouteList routeList) {
        this.routeList = routeList;
    }

    /**
     * Adds a new {@code Route} to the {@code RouteList} that we are building.
     */
    public RouteListBuilder withRoute(Route route) {
        routeList.addRoute(route);
        return this;
    }

    public RouteList build() {
        return routeList;
    }
}
