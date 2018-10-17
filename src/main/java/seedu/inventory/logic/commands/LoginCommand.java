package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.staff.Staff;

/**
 * Logs in the user into the system.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login an user into the system."
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "user2"
            + PREFIX_PASSWORD + "12345678";

    public static final String MESSAGE_SUCCESS = "You have successfully logged in as %s";
    public static final String MESSAGE_FAILED = "Seems like you have entered a wrong username or password";

    private final Staff toLogin;

    public LoginCommand(Staff staff) {
        requireNonNull(staff);
        toLogin = staff;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasStaff(toLogin)) {
            throw new CommandException(MESSAGE_FAILED);
        }

        model.authenticateUser(toLogin);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin.getStaffName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toLogin.equals(((LoginCommand) other).toLogin));
    }
}
