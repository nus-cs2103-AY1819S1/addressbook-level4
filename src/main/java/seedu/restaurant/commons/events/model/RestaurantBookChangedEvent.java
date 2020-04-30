package seedu.restaurant.commons.events.model;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.ReadOnlyRestaurantBook;

/**
 * Indicates the RestaurantBook in the model has changed
 */
public class RestaurantBookChangedEvent extends BaseEvent {

    public final ReadOnlyRestaurantBook data;

    public RestaurantBookChangedEvent(ReadOnlyRestaurantBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of accounts " + data.getAccountList().size()
                + ", number of items " + data.getItemList().size()
                + ", number of reservations " + data.getReservationList().size()
                + ", number of sales record " + data.getRecordList().size()
                + ", number of ingredients " + data.getIngredientList().size();
    }
}
