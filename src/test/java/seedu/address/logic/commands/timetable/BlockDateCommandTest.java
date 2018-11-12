package seedu.address.logic.commands.timetable;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDates.DATE_A;
import static seedu.address.testutil.TypicalDates.DATE_B;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserData;
import seedu.address.model.UserPrefs;
import seedu.address.model.timetable.Date;
import seedu.address.testutil.UserBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand, and RedoCommand) and unit tests for
 * {@code BlockDateCommand}
 */
public class BlockDateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserData(),
            new UserBuilder().build());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validDate_success() {
        Date dateToBlock = DATE_A;
        BlockDateCommand blockDateCommand = new BlockDateCommand(dateToBlock);

        String expectedMessage = String.format(BlockDateCommand.MESSAGE_SUCCESS, dateToBlock);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserData(),
                new UserBuilder().build());

        expectedModel.blockDateForCurrentUser(dateToBlock);

        assertCommandSuccess(blockDateCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_duplicateDate_throwsCommandException() {
        Date dateToBlock = DATE_A;
        model.blockDateForCurrentUser(dateToBlock);

        BlockDateCommand blockDateCommand = new BlockDateCommand(dateToBlock);

        assertCommandFailure(blockDateCommand, model, commandHistory,
                BlockDateCommand.MESSAGE_DUPLICATE_DATE);
    }


    @Test
    public void execute_withoutLogin_throwsNotLoggedInCommandException() {
        Date dateToBlock = DATE_A;
        ModelManager notLoggedInModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserData());

        BlockDateCommand blockDateCommand = new BlockDateCommand(dateToBlock);
        assertCommandFailure(blockDateCommand, notLoggedInModel, commandHistory,
                String.format(Messages.MESSAGE_USER_NOT_LOGGED_IN_FOR_COMMAND, BlockDateCommand.COMMAND_WORD));
    }

    @Test
    public void equals() {
        BlockDateCommand blockFirstCommand = new BlockDateCommand(DATE_A);
        BlockDateCommand blockSecondCommand = new BlockDateCommand(DATE_B);

        // same object --> returns true
        assertTrue(blockFirstCommand.equals(blockFirstCommand));

        // same values --> returns true
        BlockDateCommand blockFirstCommandCopy = new BlockDateCommand(DATE_A);
        assertTrue(blockFirstCommand.equals(blockFirstCommandCopy));

        // different types --> returns false
        assertFalse(blockFirstCommand.equals(1));
        assertFalse(blockFirstCommand.equals("random string"));

        // null --> returns false
        assertFalse(blockFirstCommand.equals(null));

        // different dates --> returns false.
        assertFalse(blockFirstCommand.equals(blockSecondCommand));
    }
}
