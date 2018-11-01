package seedu.souschef.logic.commands;

import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.souschef.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.recipe.Recipe;

public class RedoCommandTest {

    private final Model<Recipe> model = new ModelSetCoordinator(getTypicalAddressBook(),
            new UserPrefs()).getRecipeModel();
    private final Model<Recipe> expectedModel =
            new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
    private final History history = new History();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);
        model.undoAppContent();
        model.undoAppContent();

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        expectedModel.undoAppContent();
        expectedModel.undoAppContent();
    }

    @Test
    public void execute() {
        // multiple redoable states in recipeModel
        expectedModel.redoAppContent();
        assertCommandSuccess(new RedoCommand(model), model, history, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in recipeModel
        expectedModel.redoAppContent();
        assertCommandSuccess(new RedoCommand(model), model, history, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in recipeModel
        assertCommandFailure(new RedoCommand(model), model, history, RedoCommand.MESSAGE_FAILURE);
    }
}
