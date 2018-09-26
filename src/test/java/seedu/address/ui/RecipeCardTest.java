package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.recipe.Recipe;
import seedu.address.testutil.PersonBuilder;

public class RecipeCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Recipe recipeWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        RecipeCard recipeCard = new RecipeCard(recipeWithNoTags, 1);
        uiPartRule.setUiPart(recipeCard);
        assertCardDisplay(recipeCard, recipeWithNoTags, 1);

        // with tags
        Recipe recipeWithTags = new PersonBuilder().build();
        recipeCard = new RecipeCard(recipeWithTags, 2);
        uiPartRule.setUiPart(recipeCard);
        assertCardDisplay(recipeCard, recipeWithTags, 2);
    }

    @Test
    public void equals() {
        Recipe recipe = new PersonBuilder().build();
        RecipeCard recipeCard = new RecipeCard(recipe, 0);

        // same recipe, same index -> returns true
        RecipeCard copy = new RecipeCard(recipe, 0);
        assertTrue(recipeCard.equals(copy));

        // same object -> returns true
        assertTrue(recipeCard.equals(recipeCard));

        // null -> returns false
        assertFalse(recipeCard.equals(null));

        // different types -> returns false
        assertFalse(recipeCard.equals(0));

        // different recipe, same index -> returns false
        Recipe differentRecipe = new PersonBuilder().withName("differentName").build();
        assertFalse(recipeCard.equals(new RecipeCard(differentRecipe, 0)));

        // same recipe, different index -> returns false
        assertFalse(recipeCard.equals(new RecipeCard(recipe, 1)));
    }

    /**
     * Asserts that {@code recipeCard} displays the details of {@code expectedRecipe} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RecipeCard recipeCard, Recipe expectedRecipe, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(recipeCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify recipe details are displayed correctly
        assertCardDisplaysPerson(expectedRecipe, personCardHandle);
    }
}
