package ssp.scheduleplanner.logic.commands;

import static org.junit.Assert.assertEquals;

import static ssp.scheduleplanner.logic.commands.CommandTestUtil.assertCommandFailure;
import static ssp.scheduleplanner.testutil.TypicalTasks.getTypicalSchedulePlanner;

import org.junit.Test;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.ModelManager;
import ssp.scheduleplanner.model.UserPrefs;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.Tag;

public class ClearCategoryCommandTest {
    private Model model = new ModelManager(getTypicalSchedulePlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeClearNonexistentCategory_throwsCommandException() {
        ClearCategoryCommand clearCommand = new ClearCategoryCommand("School");
        assertCommandFailure(clearCommand, model, commandHistory, Messages.MESSAGE_NONEXISTENT_CATEGORY);
    }

    @Test
    public void executeClearCategory_success() throws CommandException {
        model.addTag(new Tag("CS2101"), "Modules");
        model.commitSchedulePlanner();
        model.addTag(new Tag("CS2103"), "Modules");
        model.commitSchedulePlanner();

        ClearCategoryCommand clearCommand = new ClearCategoryCommand("Modules");
        clearCommand.execute(model, commandHistory);
        assertEquals(model.getCategory("Modules"), new Category("Modules"));

    }
}
