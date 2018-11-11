package seedu.restaurant.ui.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.ui.testutil.GuiTestAssert.assertStackPanelDisplaysIngredient;

import org.junit.Test;

import guitests.guihandles.ingredient.IngredientStackPanelHandle;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;
import seedu.restaurant.ui.GuiUnitTest;

//@@author rebstan97
public class IngredientStackPanelTest extends GuiUnitTest {

    @Test
    public void display() {
        Ingredient ingredient = new IngredientBuilder().build();
        IngredientStackPanel ingredientStackPanel = new IngredientStackPanel(ingredient);
        uiPartRule.setUiPart(ingredientStackPanel);
        assertStackPanelDisplay(ingredientStackPanel, ingredient);
    }

    @Test
    public void equals() {
        Ingredient ingredient = new IngredientBuilder().build();
        IngredientStackPanel ingredientStackPanel = new IngredientStackPanel(ingredient);

        // same ingredient, same index -> returns true
        IngredientStackPanel copy = new IngredientStackPanel(ingredient);
        assertTrue(ingredientStackPanel.equals(copy));

        // same object -> returns true
        assertTrue(ingredientStackPanel.equals(ingredientStackPanel));

        // null -> returns false
        assertFalse(ingredientStackPanel.equals(null));

        // different types -> returns false
        assertFalse(ingredientStackPanel.equals(0));

        // different ingredient, same index -> returns false
        // different name
        Ingredient differentIngredient = new IngredientBuilder().withName("differentName").build();
        assertFalse(ingredientStackPanel.equals(new IngredientStackPanel(differentIngredient)));

        // different unit
        differentIngredient = new IngredientBuilder().withUnit("packet").build();
        assertFalse(ingredientStackPanel.equals(new IngredientStackPanel(differentIngredient)));

        // different price
        differentIngredient = new IngredientBuilder().withPrice("999").build();
        assertFalse(ingredientStackPanel.equals(new IngredientStackPanel(differentIngredient)));

        // different minimum
        differentIngredient = new IngredientBuilder().withMinimum(123).build();
        assertFalse(ingredientStackPanel.equals(new IngredientStackPanel(differentIngredient)));

        // different numUnits
        differentIngredient = new IngredientBuilder().withNumUnits(456).build();
        assertFalse(ingredientStackPanel.equals(new IngredientStackPanel(differentIngredient)));
    }

    /**
     * Asserts that {@code ingredientStackPanelHandle} displays the details of {@code expectedIngredient} correctly.
     */
    private void assertStackPanelDisplay(IngredientStackPanel ingredientStackPanel, Ingredient expectedIngredient) {
        guiRobot.pauseForHuman();

        IngredientStackPanelHandle ingredientStackPanelHandle =
                new IngredientStackPanelHandle(ingredientStackPanel.getRoot());

        // verify item details are displayed correctly
        assertStackPanelDisplaysIngredient(expectedIngredient, ingredientStackPanelHandle);
    }
}
