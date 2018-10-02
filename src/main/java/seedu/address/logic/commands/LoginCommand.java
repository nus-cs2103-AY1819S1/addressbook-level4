package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author jjlee050
/**
 * Authenticate user and provide them access to ClinicIO based on the role.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Authenticate user to provide"
        + "user access to ClinicIO based on the roles."
        + "Parameters: " + COMMAND_WORD
        + "[" + PREFIX_ROLE + "/ROLE]"
        + "[" + PREFIX_NAME + "/NAME]"
        + "[" + PREFIX_PASSWORD + "/PASSWORD]"
        + "Example: login r/doctor n/Adam Bell pass/doctor1";

    public static final String MESSAGE_SUCCESS = "Login successful.";
    public static final String MESSAGE_FAILURE = "Login failed. Please try again.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
