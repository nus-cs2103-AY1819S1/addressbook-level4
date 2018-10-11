package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.route.Route;

/**
 * A utility class to help with building Route objects.
 */
public class RouteBuilder {

    public static final String DEFAULT_SOURCE = "12 Clementi Rd";
    public static final String DEFAULT_DESTINATION = "123, Jurong West Ave 6, #08-111";

    private Address source;
    private Address destination;

    public RouteBuilder() {
        source = new Address(DEFAULT_SOURCE);
        destination = new Address(DEFAULT_DESTINATION);
    }

    /**
     * Initializes the RouteBuilder with the data of {@code routeToCopy}.
     */
    public RouteBuilder(Route routeToCopy) {
        source = routeToCopy.getSource();
        destination = routeToCopy.getDestination();
    }

    /**
     * Sets the {@code source} of the {@code Route} that we are building.
     */
    public RouteBuilder withSource(String source) {
        this.source = new Address(source);
        return this;
    }

    /**
     * Sets the {@code source} of the {@code Route} that we are building.
     */
    public RouteBuilder withDestination(String destination) {
        this.destination = new Address(destination);
        return this;
    }

    public Route build() {
        return new Route(source, destination);
    }

}
