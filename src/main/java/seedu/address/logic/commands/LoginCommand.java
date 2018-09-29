package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.exceptions.NonExistentUserException;
import seedu.address.model.user.Username;

//@@author JasonChong96
/**
 * Logs in a user in the model
 */
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "li";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Logs in to the user with the given USERNAME.\n"
            + "Parameters: USERNAME\n"
            + "Example: " + COMMAND_WORD + " examplename";

    public static final String MESSAGE_LOGIN_SUCCESS = "Logged in as %1$s";

    private final Username username;

    public LoginCommand(Username username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws NonExistentUserException {
        requireNonNull(model);
        model.loadUserData(this.username);
        return new CommandResult(String.format(MESSAGE_LOGIN_SUCCESS, this.username.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && username.equals(((LoginCommand) other).username)); // state check
    }
}
