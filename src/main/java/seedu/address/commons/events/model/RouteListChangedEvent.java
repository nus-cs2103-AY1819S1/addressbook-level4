package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.route.ReadOnlyRouteList;

/** Indicates the OrderBook in the model has changed*/
public class RouteListChangedEvent extends BaseEvent {

    public final ReadOnlyRouteList data;

    public RouteListChangedEvent(ReadOnlyRouteList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of routes " + data.getRouteList().size();
    }
}
