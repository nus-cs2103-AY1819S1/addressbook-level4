package seedu.address.testutil;

import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymenList;

/**
 * A utility class to help with building DeliverymenList objects.
 */
public class DeliverymenListBuilder {

    private DeliverymenList deliverymenList;

    public DeliverymenListBuilder() {
        deliverymenList = new DeliverymenList();
    }

    public DeliverymenListBuilder(DeliverymenList deliverymenList) {
        this.deliverymenList = deliverymenList;
    }

    /**
     * Adds a new {@code Deliveryman} to the {@code DeliverymenList} that we are building.
     */
    public DeliverymenListBuilder withDeliveryman(Deliveryman deliveryman) {
        deliverymenList.addDeliveryman(deliveryman);
        return this;
    }

    public DeliverymenList build() {
        return deliverymenList;
    }
}
