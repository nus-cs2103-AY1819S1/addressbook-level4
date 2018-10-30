package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Enable / disable notifications
 * To do : command exception
 */

public class NotificationCommand extends Command {

    public static final String COMMAND_WORD = "notification";
    public static final String COMMAND_WORD_ALIAS = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_WORD_ALIAS + ")"
            + ": Enable / Disable notification in " + "the address book. "
            + "Parameters: enable/disable\n"
            + "Example: " + COMMAND_WORD + " enable";

    private String messageNotificationSuccess;

    private final boolean set;

    public NotificationCommand(boolean set) {
        this.set = set;
    }

    public String getMessageNotificationSuccess() {
        return messageNotificationSuccess;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        ModelManager.updateNotificationPref(set);

        if (set) {
            messageNotificationSuccess = "Notification:" + " enabled";
        } else {
            messageNotificationSuccess = "Notification:" + " disabled";
        }

        model.commitAddressBook();

        return new CommandResult(String.format(messageNotificationSuccess));
    }
}
