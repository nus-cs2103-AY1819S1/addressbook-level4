package seedu.souschef.logic.commands;

import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.souschef.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.souschef.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.recipe.Recipe;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model<Recipe> model;
    private Model<Recipe> expectedModel;
    private History history = new History();

    @Before
    public void setUp() {
        model = new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
        expectedModel = new ModelSetCoordinator(model.getAppContent(), new UserPrefs()).getRecipeModel();
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand<Recipe>(model), model, history,
                String.format(ListCommand.MESSAGE_SUCCESS, "recipe"),
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_RECIPE);
        assertCommandSuccess(new ListCommand<Recipe>(model), model, history,
                String.format(ListCommand.MESSAGE_SUCCESS, "recipe"),
                expectedModel);
    }
}
