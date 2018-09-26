package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Recipe;
import seedu.address.testutil.PersonBuilder;

public class RecipeCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Recipe recipeWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(recipeWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, recipeWithNoTags, 1);

        // with tags
        Recipe recipeWithTags = new PersonBuilder().build();
        personCard = new PersonCard(recipeWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, recipeWithTags, 2);
    }

    @Test
    public void equals() {
        Recipe recipe = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(recipe, 0);

        // same recipe, same index -> returns true
        PersonCard copy = new PersonCard(recipe, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different recipe, same index -> returns false
        Recipe differentRecipe = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentRecipe, 0)));

        // same recipe, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(recipe, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedRecipe} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Recipe expectedRecipe, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify recipe details are displayed correctly
        assertCardDisplaysPerson(expectedRecipe, personCardHandle);
    }
}
