package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLeaveApplicationAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LeaveListCommand.
 */
public class LeaveListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new LeaveListCommand(), model, commandHistory, LeaveListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showLeaveApplicationAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new LeaveListCommand(), model, commandHistory, LeaveListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
