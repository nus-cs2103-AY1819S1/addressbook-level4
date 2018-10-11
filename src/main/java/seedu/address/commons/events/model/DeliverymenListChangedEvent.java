package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.deliveryman.DeliverymenList;

/** Indicates the DeliverymenList in the model has changed*/
public class DeliverymenListChangedEvent extends BaseEvent {

    public final DeliverymenList data;

    public DeliverymenListChangedEvent(DeliverymenList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of deliverymen " + data.getDeliverymenList().size();
    }
}
