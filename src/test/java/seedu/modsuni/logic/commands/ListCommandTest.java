package seedu.modsuni.logic.commands;

import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.logic.commands.CommandTestUtil.showModuleAtIndex;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;

import org.junit.Before;
import org.junit.Test;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.CredentialStore;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(
            getTypicalModuleList(),
            new UserPrefs(),
            new CredentialStore());
        expectedModel = new ModelManager(
            model.getModuleList(),
            new UserPrefs(),
            new CredentialStore());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
