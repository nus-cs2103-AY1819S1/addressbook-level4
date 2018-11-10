package seedu.souschef.logic.commands;

import static seedu.souschef.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.recipe.Recipe;

public class ClearCommandTest {

    private History history = new History();

    @Test
    public void execute_emptyAddressBook_success() {
        Model<Recipe> model = new ModelSetCoordinator().getRecipeModel();
        Model<Recipe> expectedModel = new ModelSetCoordinator().getRecipeModel();
        expectedModel.commitAppContent();

        assertCommandSuccess(new ClearCommand<Recipe>(model), model, history,
                String.format(ClearCommand.MESSAGE_CLEAR_SUCCESS,
                history.getKeyword()),
                expectedModel);
    }

    /*
    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model<Recipe> model = new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
        Model<Recipe> expectedModel = new ModelSetCoordinator(getTypicalAddressBook(),
                new UserPrefs()).getRecipeModel();
        expectedModel.resetData(new AppContent());
        expectedModel.commitAppContent();

        assertCommandSuccess(new ClearCommand<Recipe>(model), model, history,
                String.format(ClearCommand.MESSAGE_CLEAR_SUCCESS,
                        history.getKeyword()),
                expectedModel);
    }*/

}
