package seedu.restaurant.ui.ingredient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.ui.testutil.GuiTestAssert.assertCardDisplaysIngredient;

import org.junit.Test;

import guitests.guihandles.ingredient.IngredientCardHandle;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;
import seedu.restaurant.ui.GuiUnitTest;

//@@author rebstan97
public class IngredientCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Ingredient ingredient = new IngredientBuilder().build();
        IngredientCard ingredientCard = new IngredientCard(ingredient, 1);
        uiPartRule.setUiPart(ingredientCard);
        assertCardDisplay(ingredientCard, ingredient, 1);
    }

    @Test
    public void equals() {
        Ingredient ingredient = new IngredientBuilder().build();
        IngredientCard ingredientCard = new IngredientCard(ingredient, 0);

        // same item, same index -> returns true
        IngredientCard copy = new IngredientCard(ingredient, 0);
        assertTrue(ingredientCard.equals(copy));

        // same object -> returns true
        assertTrue(ingredientCard.equals(ingredientCard));

        // null -> returns false
        assertFalse(ingredientCard.equals(null));

        // different types -> returns false
        assertFalse(ingredientCard.equals(0));

        // different ingredient, same index -> returns false
        Ingredient differentIngredient = new IngredientBuilder().withName("differentName").build();
        assertFalse(ingredientCard.equals(new IngredientCard(differentIngredient, 0)));

        // same ingredient, different index -> returns false
        assertFalse(ingredientCard.equals(new IngredientCard(ingredient, 1)));
    }

    /**
     * Asserts that {@code ingredientCard} displays the details of {@code expectedIngredient} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(IngredientCard ingredientCard, Ingredient expectedIngredient, int expectedId) {
        guiRobot.pauseForHuman();

        IngredientCardHandle ingredientCardHandle = new IngredientCardHandle(ingredientCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", ingredientCardHandle.getId());

        // verify item details are displayed correctly
        assertCardDisplaysIngredient(expectedIngredient, ingredientCardHandle);
    }
}
