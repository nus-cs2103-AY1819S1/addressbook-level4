package seedu.address.model.route;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Address;

/**
 * Represents a Route in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Route {
    private static final String DEFAULT_SOURCE = "12 Clementi Rd";

    // Identity fields
    private final Address source;
    private final Address destination;

    /**
     * Creates a new instance of Route with the destination.
     * @param destination The address of the restaurant.
     */
    public Route(Address destination) {
        requireNonNull(destination);
        this.source = new Address(DEFAULT_SOURCE);
        this.destination = destination;
    }

    public Route(Address source, Address destination) {
        requireAllNonNull(source, destination);
        this.source = source;
        this.destination = destination;
    }

    public Address getSource() {
        return source;
    }

    public Address getDestination() {
        return destination;
    }

    /**
     * Returns true if both routes have all the same identity fields.
     * This defines a weaker notion of equality between two routes.
     */
    public boolean isSameRoute(Route otherRoute) {
        if (otherRoute == this) {
            return true;
        }

        return otherRoute != null
                && otherRoute.getSource().equals(getSource())
                && otherRoute.getDestination().equals(getDestination());
    }

    /**
     * Returns true if both routes have the same identity and data fields.
     * This defines a stronger notion of equality between two routes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Route)) {
            return false;
        }

        Route otherRoute = (Route) other;
        return otherRoute.getSource().equals(getSource())
                && otherRoute.getDestination().equals(getDestination());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(source, destination);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Route")
                .append("Source: ")
                .append(getSource())
                .append(" Destination: ")
                .append(getDestination());
        return builder.toString();
    }
}
