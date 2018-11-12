package seedu.restaurant.commons.events.ui.sales;

import java.util.Map;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.sales.Date;

//@@author HyperionNKJ
/**
 * An event requesting to display the sales line chart using data given in the Map
 */
public class DisplaySalesChartEvent extends BaseEvent {

    private Map<Date, Double> salesData;

    public DisplaySalesChartEvent(Map<Date, Double> salesData) {
        this.salesData = salesData;
    }

    public Map<Date, Double> getSalesData() {
        return salesData;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
