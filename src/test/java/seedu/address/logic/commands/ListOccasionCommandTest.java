package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandOccasionTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandOccasionTestUtil.showOccasionAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OCCASION;
import static seedu.address.testutil.TypicalOccasions.getTypicalOccasionsAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListOccasionCommand.
 */
public class ListOccasionCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListOccasionCommand(), model, commandHistory,
                ListOccasionCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showOccasionAtIndex(model, INDEX_FIRST_OCCASION);
        assertCommandSuccess(new ListOccasionCommand(), model, commandHistory,
                ListOccasionCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
