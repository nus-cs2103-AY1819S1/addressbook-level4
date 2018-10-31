package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LogoutEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Command to log the user out.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs the current user out\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_LOGOUT_MESSAGE = "User Logged out";

    @Override
    public CommandResult runBody(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new LogoutEvent());
        history.resetHistory();
        return new CommandResult(SHOWING_LOGOUT_MESSAGE);
    }
}
