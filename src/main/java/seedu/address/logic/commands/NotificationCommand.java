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

    public static final String MESSAGE_NOTIFICATION_ENABLED_SUCCESS = "Notification: Enabled";
    public static final String MESSAGE_NOTIFICATION_DISABLED_SUCCESS = "Notification: Disabled";

    private final boolean set;

    public NotificationCommand(boolean set) {
        this.set = set;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.updateNotificationPref(set);
        model.commitAddressBook();

        if (set) {
            return new CommandResult(String.format(MESSAGE_NOTIFICATION_ENABLED_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_NOTIFICATION_DISABLED_SUCCESS));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof NotificationCommand)
                && (this.set == ((NotificationCommand)other).set));

    }
}
