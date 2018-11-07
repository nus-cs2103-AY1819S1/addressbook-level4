package seedu.souschef.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.souschef.commons.core.Messages.MESSAGE_LISTED_OVERVIEW;
import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.recipe.RecipeContainsKeywordsPredicate;
import seedu.souschef.model.recipe.Recipe;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model<Recipe> model = new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
    private Model<Recipe> expectedModel =
            new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
    private History history = new History();

    @Test
    public void equals() {
        RecipeContainsKeywordsPredicate firstPredicate =
                new RecipeContainsKeywordsPredicate(Collections.singletonList("first"));
        RecipeContainsKeywordsPredicate secondPredicate =
                new RecipeContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand<Recipe> findFirstCommand = new FindCommand<Recipe>(model, firstPredicate);
        FindCommand<Recipe> findSecondCommand = new FindCommand<Recipe>(model, secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand<Recipe> findFirstCommandCopy = new FindCommand<Recipe>(model, firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_LISTED_OVERVIEW, 0, "recipe");
        RecipeContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand<Recipe> command = new FindCommand<Recipe>(model, predicate);
        expectedModel.updateFilteredList(predicate);
        assertCommandSuccess(command, model, history, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private RecipeContainsKeywordsPredicate preparePredicate(String userInput) {
        return new RecipeContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
