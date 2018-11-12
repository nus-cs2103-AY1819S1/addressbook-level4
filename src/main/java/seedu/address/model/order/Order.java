package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.TaggedObject;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.exceptions.OrdersLimitExceededException;

/**
 * Represents an Order in the order book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order extends TaggedObject {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Address address;
    private final OrderDate orderDate;
    private final Set<Food> food = new HashSet<>();
    private OrderStatus orderStatus;
    private Deliveryman deliveryman;

    /**
     * Every field must be present and not null.
     */
    public Order(Name name, Phone phone, Address address, OrderDate orderDate,
                 Set<Food> food) {
        this(null, name, phone, address, orderDate, new OrderStatus(), food, null);
    }

    /**
     * Every field must be present and not null.
     */
    public Order(Name name, Phone phone, Address address, OrderDate orderDate, OrderStatus orderStatus,
                 Set<Food> food) {
        this(null, name, phone, address, orderDate, orderStatus, food, null);
    }

    /**
     * Every field must be present and not null besides deliveryman.
     */
    public Order(Name name, Phone phone, Address address, OrderDate orderDate, OrderStatus orderStatus, Set<Food> food,
                 Deliveryman deliveryman) {
        this(null, name, phone, address, orderDate, orderStatus, food, deliveryman);
    }

    /**
     * This constructor is used to create an {@code order} with a specified id.
     */
    public Order(UUID id, Name name, Phone phone, Address address, OrderDate orderDate, OrderStatus orderStatus,
                 Set<Food> food, Deliveryman deliveryman) {
        super(id);
        requireAllNonNull(name, phone, address, orderDate, food);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.food.addAll(food);
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.deliveryman = deliveryman;
    }

    /**
     * This constructor is used to create a new copy of {@code order}.
     */
    public Order(Order order) {
        this(null, order.name, order.phone, order.address, order.orderDate, order.orderStatus, order.food,
                order.deliveryman);
    }

    public Name getName() {
        return name;
    }

    public OrderDate getDate() {
        return this.orderDate;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Deliveryman getDeliveryman() {
        return deliveryman;
    }

    public void setStatusCompleted() {
        orderStatus = new OrderStatus("COMPLETED");
    }
    /**
     * Returns a food set
     */
    public Set<Food> getFood() {
        return food;
    }

    public void setDeliveryman(Deliveryman newDeliveryman) throws OrdersLimitExceededException {
        assert(!isAlreadyAssignedDeliveryman());
        deliveryman = newDeliveryman;
        updateStatusOngoing();
        newDeliveryman.addOrder(this);
    }

    private void updateStatusOngoing() {
        orderStatus = new OrderStatus("ONGOING");
    }

    public boolean isAlreadyAssignedDeliveryman() {
        return deliveryman != null;
    }

    public boolean isCompleted() {
        return orderStatus.isCompletedStatus();
    }

    public boolean isOngoing() {
        return orderStatus.isOngoingStatus();
    }

    /**
     * Returns true if both orders of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getName().equals(getName())
                && (otherOrder.getPhone().equals(getPhone()))
                && (otherOrder.getDate().equals(getDate()));
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return otherOrder.getName().equals(getName())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getAddress().equals(getAddress())
                && (otherOrder.getDate().equals(getDate()))
                && otherOrder.getFood().equals(getFood())
                && otherOrder.getOrderStatus().equals(getOrderStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, orderStatus, food);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Address: ")
                .append(getAddress())
                .append(" Date: ")
                .append(getDate())
                .append(" Status: ")
                .append(getOrderStatus())
                .append(" Food: ");
        getFood().forEach(builder::append);
        return builder.toString();
    }

}
