package ssp.scheduleplanner.logic.commands;

import static org.junit.Assert.assertEquals;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.assertCommandFailure;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static ssp.scheduleplanner.testutil.TypicalTasks.getTypicalSchedulePlanner;

import org.junit.Test;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;
import ssp.scheduleplanner.model.UserPrefs;

public class RemoveCategoryCommandTest {
    private Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidModulesRemoval_throwsCommandException() {
        //Modules is a default category, cannot be removed, test should fail
        RemoveCategoryCommand removeCommand = new RemoveCategoryCommand("Modules");
        assertCommandFailure(removeCommand, model, commandHistory, Messages.MESSAGE_DEFAULT_CATEGORY);
    }

    @Test
    public void execute_invalidOthersRemoval_throwsCommandException() {
        //Others is a default category, cannot be removed, test should fail
        RemoveCategoryCommand removeCommand = new RemoveCategoryCommand("Others");
        assertCommandFailure(removeCommand, model, commandHistory, Messages.MESSAGE_DEFAULT_CATEGORY);
    }

    @Test
    public void execute_nonexistentCategory_throwsCommandException() {
        ModelManager expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        //Category "work" does not exist in categories, test should fail
        RemoveCategoryCommand removeCommand = new RemoveCategoryCommand("work");
        assertCommandFailure(removeCommand, model, commandHistory, Messages.MESSAGE_NONEXISTENT_CATEGORY);
    }

    @Test
    public void execute_validCategoryRemoval_success() throws CommandException {
        ModelManager expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        assertEquals(model, expectedModel);
        model.addCategory("School");
        model.commitSchedulePlanner();

        expectedModel.addCategory("School");
        expectedModel.commitSchedulePlanner();
        assertEquals(model, expectedModel);

        expectedModel.removeCategory("School");
        expectedModel.commitSchedulePlanner();
        RemoveCategoryCommand removeCommand = new RemoveCategoryCommand("School");
        String expectedMessage = String.format(RemoveCategoryCommand.MESSAGE_SUCCESS, "School");
        assertCommandSuccess(removeCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getCategoryList(), expectedModel.getCategoryList());

        expectedModel.undoSchedulePlanner();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }



}
