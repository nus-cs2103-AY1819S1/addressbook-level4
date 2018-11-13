package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_COOKTIME_HR;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_DIFFICULTY_1;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_NAME_BEE;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_STAPLE;
import static seedu.souschef.testutil.TypicalRecipes.APPLE;
import static seedu.souschef.testutil.TypicalRecipes.BEE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.souschef.testutil.RecipeBuilder;

public class RecipeTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recipe recipe = new RecipeBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        recipe.getTags().remove(0);
    }

    @Test
    public void isSameRecipe() {
        // same object -> returns true
        assertTrue(APPLE.isSame(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSame(null));

        // different difficulty and cooktime -> returns false
        Recipe editedApple = new RecipeBuilder(APPLE).withDifficulty(VALID_DIFFICULTY_1)
                .withCooktime(VALID_COOKTIME_HR).build();
        assertFalse(APPLE.isSame(editedApple));

        // different name -> returns false
        editedApple = new RecipeBuilder(APPLE).withName(VALID_NAME_BEE).build();
        assertFalse(APPLE.isSame(editedApple));

        // same name, same difficulty, same cooktime, different tags -> returns true
        editedApple = new RecipeBuilder(APPLE).withTags(VALID_TAG_STAPLE).build();
        assertTrue(APPLE.isSame(editedApple));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recipe applePie = new RecipeBuilder(APPLE).build();
        assertTrue(APPLE.equals(applePie));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different recipe -> returns false
        assertFalse(APPLE.equals(BEE));

        // different name -> returns false
        Recipe editedApplePie = new RecipeBuilder(APPLE).withName(VALID_NAME_BEE).build();
        assertFalse(APPLE.equals(editedApplePie));

        // different phone -> returns false
        editedApplePie = new RecipeBuilder(APPLE).withDifficulty(VALID_DIFFICULTY_1).build();
        assertFalse(APPLE.equals(editedApplePie));

        // different email -> returns false
        editedApplePie = new RecipeBuilder(APPLE).withCooktime(VALID_COOKTIME_HR).build();
        assertFalse(APPLE.equals(editedApplePie));

        // different tags -> returns false
        editedApplePie = new RecipeBuilder(APPLE).withTags(VALID_TAG_STAPLE).build();
        assertFalse(APPLE.equals(editedApplePie));
    }
}
