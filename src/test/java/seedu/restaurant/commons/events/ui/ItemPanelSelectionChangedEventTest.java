package seedu.restaurant.commons.events.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.commons.events.ui.menu.ItemPanelSelectionChangedEvent;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.testutil.menu.ItemBuilder;

//@@author yican95
public class ItemPanelSelectionChangedEventTest {

    private final Item item = new ItemBuilder().withName("Ice Cream").withPrice("2.15").withTags("Dessert").build();

    @Test
    public void createEvent_success() {
        BaseEvent event = new ItemPanelSelectionChangedEvent(item);
        assertEquals("ItemPanelSelectionChangedEvent", event.toString());
    }

    @Test
    public void createEvent_correctItem_success() {
        ItemPanelSelectionChangedEvent event = new ItemPanelSelectionChangedEvent(item);
        assertEquals("Name: Ice Cream Price: 2.15 Tags: [Dessert]", event.getNewSelection().toString());
    }
}
