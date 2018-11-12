package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists the user's visited restaurants.
 */
public class DisplayProfileCommand extends Command {
    public static final String COMMAND_WORD = "displayProfile";

    public static final String MESSAGE_SUCCESS = "Displaying User Profile";
    public static final String MESSAGE_NOT_CURRENTLY_LOGGEDIN = "User is not currently logged in";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_CURRENTLY_LOGGEDIN);
        }

        model.displayProfile();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
