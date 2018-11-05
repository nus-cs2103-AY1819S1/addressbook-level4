package seedu.restaurant.logic.commands.account;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.commons.events.ui.accounts.LogoutEvent;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;

//@@author AZhiKai
/**
 * Logs the user out, and destroy the {@code UserSession}.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout of the system. "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "You have been logged out and history is cleared";
    public static final String MESSAGE_NOT_AUTHENTICATED = "You are not logged in";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!UserSession.isAuthenticated()) {
            throw new CommandException(MESSAGE_NOT_AUTHENTICATED);
        }

        model.resetRestaurantBookVersion();
        EventsCenter.getInstance().post(new LogoutEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
