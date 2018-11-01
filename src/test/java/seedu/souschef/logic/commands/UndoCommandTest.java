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

public class UndoCommandTest {

    private final Model<Recipe> model = new ModelSetCoordinator(getTypicalAddressBook(),
            new UserPrefs()).getRecipeModel();
    private final Model<Recipe> expectedModel = new ModelSetCoordinator(getTypicalAddressBook(),
            new UserPrefs()).getRecipeModel();
    private final History history = new History();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in recipeModel
        expectedModel.undoAppContent();
        assertCommandSuccess(new UndoCommand(model), model, history, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in recipeModel
        expectedModel.undoAppContent();
        assertCommandSuccess(new UndoCommand(model), model, history, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in recipeModel
        assertCommandFailure(new UndoCommand(model), model, history, UndoCommand.MESSAGE_FAILURE);
    }
}
