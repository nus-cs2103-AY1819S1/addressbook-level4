package seedu.souschef.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.RecipeCardHandle;
import guitests.guihandles.RecipeListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.souschef.model.recipe.Recipe;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(RecipeCardHandle expectedCard, RecipeCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRecipe}.
     */
    public static void assertCardDisplaysRecipe(Recipe expectedRecipe, RecipeCardHandle actualCard) {
        assertEquals(expectedRecipe.getName().fullName, actualCard.getName());
        assertEquals(expectedRecipe.getPhone().value, actualCard.getPhone());
        assertEquals(expectedRecipe.getEmail().value, actualCard.getEmail());
        assertEquals(expectedRecipe.getAddress().value, actualCard.getAddress());
        assertEquals(expectedRecipe.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code recipeListPanelHandle} displays the details of {@code recipes} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RecipeListPanelHandle recipeListPanelHandle, Recipe... recipes) {
        for (int i = 0; i < recipes.length; i++) {
            recipeListPanelHandle.navigateToCard(i);
            assertCardDisplaysRecipe(recipes[i], recipeListPanelHandle.getRecipeCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code recipeListPanelHandle} displays the details of {@code recipes} correctly and
     * in the correct order.
     */
    public static void assertListMatching(RecipeListPanelHandle recipeListPanelHandle, List<Recipe> recipes) {
        assertListMatching(recipeListPanelHandle, recipes.toArray(new Recipe[0]));
    }

    /**
     * Asserts the size of the list in {@code recipeListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(RecipeListPanelHandle recipeListPanelHandle, int size) {
        int numberOfPeople = recipeListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
