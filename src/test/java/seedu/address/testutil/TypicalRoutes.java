package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.route.Route;
import seedu.address.model.route.RouteList;

/**
 * A utility class containing a list of {@code Route} objects to be used in tests.
 */
public class TypicalRoutes {
    public static final Route ANGMOKIO = new RouteBuilder()
            .withDestination("123 Ang Mo Kio St").build();

    public static final Route BEDOK = new RouteBuilder()
            .withDestination("456 Bedok St").build();

    public static final Route CHINATOWN = new RouteBuilder()
            .withDestination("78 Chinatown St").build();

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static RouteList getTypicalRouteList() {
        RouteList rl = new RouteList();
        for (Route route : getTypicalRoutes()) {
            rl.addRoute(route);
        }
        return rl;
    }

    public static List<Route> getTypicalRoutes() {
        return new ArrayList<>(Arrays.asList(ANGMOKIO, BEDOK, CHINATOWN));
    }
}
