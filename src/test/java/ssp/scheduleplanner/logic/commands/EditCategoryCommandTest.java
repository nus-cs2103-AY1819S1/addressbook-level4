package ssp.scheduleplanner.logic.commands;

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
import ssp.scheduleplanner.model.tag.Tag;

public class EditCategoryCommandTest {
    private Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_editDefaultCategories_throwsCommandException() {
        EditCategoryCommand modulesEditCommand = new EditCategoryCommand("Modules", "School");
        assertCommandFailure(modulesEditCommand, model, commandHistory, Messages.MESSAGE_EDIT_DEFAULT_CATEGORY);
        EditCategoryCommand othersEditCommand = new EditCategoryCommand("Others", "School");
        assertCommandFailure(othersEditCommand, model, commandHistory, Messages.MESSAGE_EDIT_DEFAULT_CATEGORY);
    }

    @Test
    public void execute_editEmptyCategory_success() throws CommandException {
        ModelManager expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        model.addCategory("School");
        model.commitSchedulePlanner();
        expectedModel.addCategory("School");
        expectedModel.commitSchedulePlanner();
        expectedModel.editCategory("School", "Work");
        expectedModel.commitSchedulePlanner();
        EditCategoryCommand editCommand = new EditCategoryCommand("School", "Work");
        String expectedMessage = String.format(EditCategoryCommand.MESSAGE_SUCCESS, "School", "Work");
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_editNonemptyCategory_success() throws CommandException {
        ModelManager expectedModel = new ModelManager(model.getSchedulePlanner(), new UserPrefs());
        model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
        CommandHistory commandHistory = new CommandHistory();
        model.addCategory("School");
        model.commitSchedulePlanner();
        model.addTag(new Tag("Tutorial"), "School");
        model.commitSchedulePlanner();
        model.addTag(new Tag("Lab"), "School");
        model.commitSchedulePlanner();
        expectedModel.addCategory("School");
        expectedModel.commitSchedulePlanner();
        expectedModel.addTag(new Tag("Tutorial"), "School");
        expectedModel.commitSchedulePlanner();
        expectedModel.addTag(new Tag("Lab"), "School");
        expectedModel.commitSchedulePlanner();

        expectedModel.editCategory("School", "Work");
        expectedModel.commitSchedulePlanner();

        String expectedMessage = String.format(EditCategoryCommand.MESSAGE_SUCCESS, "School", "Work");
        EditCategoryCommand editCommand = new EditCategoryCommand("School", "Work");
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);


    }
}
