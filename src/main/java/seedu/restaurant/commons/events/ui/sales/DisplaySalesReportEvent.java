package seedu.restaurant.commons.events.ui.sales;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.sales.SalesReport;

//@@author HyperionNKJ
/**
 * An event requesting to display the sales report of a specified date
 */
public class DisplaySalesReportEvent extends BaseEvent {

    private SalesReport salesReportToDisplay;

    public DisplaySalesReportEvent(SalesReport salesReportToDisplay) {
        this.salesReportToDisplay = salesReportToDisplay;
    }

    public SalesReport getSalesReportToDisplay() {
        return salesReportToDisplay;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
