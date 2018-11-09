package seedu.restaurant.commons.events.ui.ingredient;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;

public class DisplayIngredientListRequestEventTest {
    @Test
    public void createEvent_success() {
        BaseEvent event = new DisplayIngredientListRequestEvent();
        assertEquals("DisplayIngredientListRequestEvent", event.toString());
    }
}
