package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.user.Username;

import static java.util.Objects.requireNonNull;

public class SignupCommand extends Command {
    public static final String COMMAND_WORD = "signup";
    public static final String COMMAND_ALIAS = "su";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a user with the given USERNAME.\n"
            + "Parameters: USERNAME\n"
            + "Example: " + COMMAND_WORD + " examplename";

    private static final String MESSAGE_SIGNUP_SUCCESS = "User \"%1$s\" has successfully been created.";

    private final Username username;

    public SignupCommand(Username username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.loadUserData(this.username);
        return new CommandResult(String.format(MESSAGE_SIGNUP_SUCCESS, this.username.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignupCommand // instanceof handles nulls
                && username.equals(((SignupCommand) other).username)); // state check
    }
}
