package seedu.restaurant.commons.events.ui.ingredient;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;

public class IngredientPanelSelectionChangedEventTest {

    private final Ingredient ingredient = new IngredientBuilder().withName("Sugar").withNumUnits(100).withMinimum(1)
            .withUnit("kg").withPrice("0.05").build();

    @Test
    public void createEvent_success() {
        BaseEvent event = new IngredientPanelSelectionChangedEvent(ingredient);
        assertEquals("IngredientPanelSelectionChangedEvent", event.toString());
    }

    @Test
    public void createEvent_correctItem_success() {
        IngredientPanelSelectionChangedEvent event = new IngredientPanelSelectionChangedEvent(ingredient);
        assertEquals("Ingredient Name: Sugar Unit: kg Price: 0.05 Minimum: 1 Number of Units: 100",
                event.getNewSelection().toString());
    }
}
