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

        assertCommandSuccess(notificationCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_enableNotification_success() {
        NotificationCommand notificationCommand = new NotificationCommand(true);

        String expectedMessage = String.format(NotificationCommand.MESSAGE_NOTIFICATION_ENABLED_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateNotificationPref(true);

        assertCommandSuccess(notificationCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
