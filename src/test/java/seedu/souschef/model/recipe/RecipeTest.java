package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_COOKTIME_BOB;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.souschef.testutil.TypicalRecipes.APPLE;
import static seedu.souschef.testutil.TypicalRecipes.BOB;

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

        // different phone and email -> returns false
        Recipe editedAlice = new RecipeBuilder(APPLE).withDifficulty(VALID_DIFFICULTY_BOB)
                .withCooktime(VALID_COOKTIME_BOB).build();
        assertFalse(APPLE.isSame(editedAlice));

        // different name -> returns false
        editedAlice = new RecipeBuilder(APPLE).withName(VALID_NAME_BOB).build();
        assertFalse(APPLE.isSame(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new RecipeBuilder(APPLE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(APPLE.isSame(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recipe aliceCopy = new RecipeBuilder(APPLE).build();
        assertTrue(APPLE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different recipe -> returns false
        assertFalse(APPLE.equals(BOB));

        // different name -> returns false
        Recipe editedAlice = new RecipeBuilder(APPLE).withName(VALID_NAME_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new RecipeBuilder(APPLE).withDifficulty(VALID_DIFFICULTY_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new RecipeBuilder(APPLE).withCooktime(VALID_COOKTIME_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new RecipeBuilder(APPLE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(APPLE.equals(editedAlice));
    }
}
