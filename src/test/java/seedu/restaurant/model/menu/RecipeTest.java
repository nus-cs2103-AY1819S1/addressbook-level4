package seedu.restaurant.model.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.restaurant.testutil.Assert;

//@@author yican95
public class RecipeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Recipe(null));
    }

    @Test
    public void equals() {
        Recipe recipe = new Recipe("Some recipe");

        // same object -> returns true
        assertTrue(recipe.equals(recipe));

        // same values -> returns true
        Recipe recipeCopy = new Recipe(recipe.toString());
        assertTrue(recipe.equals(recipeCopy));

        // different types -> returns false
        assertFalse(recipe.equals(1));

        // different recipe -> returns false
        Recipe differentRecipe = new Recipe("Other recipe");
        assertFalse(recipe.equals(differentRecipe));
    }
}
