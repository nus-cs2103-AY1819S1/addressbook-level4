package seedu.address.testutil;

import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.person.Name;

/**
 * A utility class to help build a deliveryman
 */
public class DeliverymanBuilder {
    public static final String DEFAULT_NAME = "Deliver E";

    private Name name;

    public DeliverymanBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the DeliverymanBuilder with the data of {@code deliverymanToCopy}.
     */
    public DeliverymanBuilder(Deliveryman deliverymanToCopy) {
        name = deliverymanToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Deliveryman} that we are building.
     */
    public DeliverymanBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Deliveryman build() {
        return new Deliveryman(name);
    }
}
