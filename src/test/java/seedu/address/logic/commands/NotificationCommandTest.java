package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code NotificationCommand}.
 */
public class NotificationCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_disableNotification_success() {
        NotificationCommand notificationCommand = new NotificationCommand(false);

        String expectedMessage = String.format(NotificationCommand.MESSAGE_NOTIFICATION_DISABLED_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateNotificationPref(false);
        expectedModel.commitAddressBook();

        assertCommandSuccess(notificationCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_enableNotification_success() {
        NotificationCommand notificationCommand = new NotificationCommand(true);

        String expectedMessage = String.format(NotificationCommand.MESSAGE_NOTIFICATION_ENABLED_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateNotificationPref(true);
        expectedModel.commitAddressBook();

        assertCommandSuccess(notificationCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_success() {

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateNotificationPref(true);
        expectedModel.commitAddressBook();

        model.updateNotificationPref(true);
        model.commitAddressBook();

        // undo -> reverts addressbook back to previous state
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same boolean value set as notificationPref
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

    }
}
