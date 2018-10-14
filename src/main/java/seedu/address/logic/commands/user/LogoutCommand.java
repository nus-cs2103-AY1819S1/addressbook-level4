package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Signs a user up into the address book.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "Logout";

    public static final String MESSAGE_SUCCESS = "Successfully Logged Out";

    public static final String MESSAGE_NO_USER_CURRENTLY_LOGGEDIN = "There is no user currently logged in";

    /**
     * Creates an LogoutCommand to allow the User logout, provided {@code Password} matches.
     */
    public LogoutCommand() {}

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NO_USER_CURRENTLY_LOGGEDIN);
        }

        model.logoutUser();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogoutCommand); // instanceof handles nulls
    }
}
