package seedu.restaurant.commons.events.ui.sales;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.testutil.salesrecords.RecordBuilder;

public class RecordPanelSelectionChangedEventTest {

    private final SalesRecord salesRecord = new RecordBuilder().withName("Fried Rice").withDate("25-09-2018")
            .withQuantitySold("35").withPrice("35").build();

    @Test
    public void createEvent_success() {
        BaseEvent event = new RecordPanelSelectionChangedEvent(salesRecord);
        assertEquals("RecordPanelSelectionChangedEvent", event.toString());
    }

    @Test
    public void createEvent_correctItem_success() {
        RecordPanelSelectionChangedEvent event = new RecordPanelSelectionChangedEvent(salesRecord);
        assertEquals("Date: 25-09-2018, Item Name:  Fried Rice, Quantity Sold: 35, Item Price: $35.0",
                event.getNewSelection().toString());
    }
}
