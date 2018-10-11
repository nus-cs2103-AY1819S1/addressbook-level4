package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyOrderBook;

/**
 * Indicates the OrderBook in the model has changed
 */
public class OrderBookChangedEvent extends BaseEvent {

    public final ReadOnlyOrderBook data;

    public OrderBookChangedEvent(ReadOnlyOrderBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of orders " + data.getOrderList().size();
    }
}
