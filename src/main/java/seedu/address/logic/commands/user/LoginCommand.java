package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;

/**
 * Signs a user up into the address book.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows a to login to use Makan Book features.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "johnnydoe "
            + PREFIX_PASSWORD + "pAssw0rd";

    public static final String MESSAGE_SUCCESS = "Successfully Logged In";
    public static final String MESSAGE_ALREADY_LOGGEDIN = "User is already logged in";
    public static final String MESSAGE_USERNAME_DOES_NOT_EXIST = "Either Username or Password is Incorrect";
    public static final String MESSAGE_INCORRECT_PASSWORD = "Either Username or Password is Incorrect";

    private final Username toLogin;
    private final Password password;

    /**
     * Creates an LoginCommand to allow the User with {@code Usename} to login, provided {@code Password} matches.
     */
    public LoginCommand(Username username, Password password) {
        requireNonNull(username);
        requireNonNull(password);
        toLogin = username;
        this.password = password;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_ALREADY_LOGGEDIN);
        }

        if (!model.hasUser(toLogin)) {
            throw new CommandException(MESSAGE_USERNAME_DOES_NOT_EXIST);
        }

        if (!model.verifyLogin(toLogin, password)) {
            throw new CommandException(MESSAGE_INCORRECT_PASSWORD);
        }

        model.loginUser(toLogin);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toLogin.equals(((LoginCommand) other).toLogin)
                && password.equals(((LoginCommand) other).password));
    }
}
