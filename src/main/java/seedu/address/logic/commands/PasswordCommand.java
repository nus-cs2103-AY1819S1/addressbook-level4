package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Command to change the password
 */
public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "Password";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change your password\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_LOGOUT_MESSAGE = "Password changed!";

    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new LogoutEvent());
        return new CommandResult(SHOWING_LOGOUT_MESSAGE);
    }
}
