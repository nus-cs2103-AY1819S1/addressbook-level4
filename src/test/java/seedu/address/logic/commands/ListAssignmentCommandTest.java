package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAssignmentAtIndex;
import static seedu.address.testutil.TypicalAssignment.getTypicalAssignmentList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAssignmentCommand.
 */
public class ListAssignmentCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalAssignmentList(),
                getTypicalArchiveList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getAssignmentList(),
                model.getArchiveList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAssignmentCommand(), model,
                commandHistory, ListAssignmentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showAssignmentAtIndex(model, INDEX_FIRST_ASSIGNMENT);
        assertCommandSuccess(new ListAssignmentCommand(), model,
                commandHistory, ListAssignmentCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
