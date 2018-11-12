package seedu.restaurant.commons.events.ui.sales;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_THREE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.sales.Date;

//@@author HyperionNKJ
public class DisplaySalesChartEventTest {
    private Map<Date, Double> salesData;

    @Before
    public void setUp() {
        salesData = new TreeMap<>();
        salesData.put(new Date(VALID_DATE_RECORD_TWO), 200.0);
        salesData.put(new Date(VALID_DATE_RECORD_THREE), 300.0);
        salesData.put(new Date(VALID_DATE_RECORD_ONE), 100.0);
    }

    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplaySalesChartEvent(salesData);
        assertEquals("DisplaySalesChartEvent", event.toString());
    }

    @Test
    public void getSalesData_success() {
        DisplaySalesChartEvent event = new DisplaySalesChartEvent(salesData);
        assertEquals(event.getSalesData(), salesData);
    }
}
