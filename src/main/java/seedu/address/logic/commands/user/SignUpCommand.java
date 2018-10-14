package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.User;

/**
 * Signs a user up into the address book.
 */
public class SignUpCommand extends Command {

    public static final String COMMAND_WORD = "SignUp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Signs a user up to use Makan Book features. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "johnnydoe "
            + PREFIX_PASSWORD + "p@ssw0rd "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com";

    public static final String MESSAGE_SUCCESS = "Successfully Registered";
    public static final String MESSAGE_ALREADY_LOGGEDIN = "User is already logged in";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists in the Makan Book";

    private final User toAdd;

    /**
     * Creates an SignUpCommand to add the specified {@code User}
     */
    public SignUpCommand(User user) {
        requireNonNull(user);
        toAdd = user;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_ALREADY_LOGGEDIN);
        }

        if (model.hasUser(toAdd.getUsername())) {
            throw new CommandException(MESSAGE_DUPLICATE_USERNAME);
        }

        model.addUser(toAdd);
        model.loginUser(toAdd);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignUpCommand // instanceof handles nulls
                && toAdd.equals(((SignUpCommand) other).toAdd));
    }
}
