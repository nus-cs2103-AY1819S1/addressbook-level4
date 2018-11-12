package seedu.address.model.deliveryman;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.TaggedObject;
import seedu.address.model.common.Name;
import seedu.address.model.deliveryman.exceptions.OrdersLimitExceededException;
import seedu.address.model.order.Order;

/**
 * Represents a Deliveryman in FoodZoom.
 * Guarantees: has a name that is unique.
 */
public class Deliveryman extends TaggedObject {

    /** Limit for amount of orders a deliveryman can have at one point of time */
    public static final int ORDERS_LIMIT = 5;

    private final Name name;
    private final Set<Order> orders = new HashSet<>();

    public Deliveryman(Name name) {
        this(null, name, null);
    }

    public Deliveryman(Name name, Set<Order> orders) {
        this(null, name, orders);
    }

    /** This constructor is used when the {@code id} is specified. */
    public Deliveryman(UUID id, Name name, Set<Order> orders) {
        super(id);
        requireAllNonNull(name);
        this.name = name;
        if (orders != null) {
            this.orders.addAll(orders);
        }
    }

    /**
     * This constructor is used to create a new copy of {@code deliveryman}.
     */
    public Deliveryman(Deliveryman deliveryman) {
        this(null, deliveryman.name, deliveryman.orders);
    }

    public Name getName() {
        return name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    /**
     * Adds {@code order} to the set of orders for the deliveryman.
     * Throws {@code OrdersLimitExceededException} if the amount of orders assigned exceeds the limit for orders.
     */
    public void addOrder(Order order) throws OrdersLimitExceededException {
        if (orders.size() >= ORDERS_LIMIT) {
            throw new OrdersLimitExceededException();
        }
        orders.add(order);
    }

    public boolean canAccommodate(Collection<Order> orders) {
        return getOrders().size() + orders.size() <= ORDERS_LIMIT;
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public boolean hasOrders() {
        return !orders.isEmpty();
    }

    /**
     * Returns if this is the same common as {@code other}
     */
    public boolean isSameDeliveryman(Deliveryman other) {
        if (other == this) {
            return true;
        }

        return other != null && other.getName().equals(getName());
    }

    /**
     * Returns true if both deliverymen have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deliveryman)) {
            return false;
        }

        Deliveryman otherPerson = (Deliveryman) other;
        return otherPerson.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
