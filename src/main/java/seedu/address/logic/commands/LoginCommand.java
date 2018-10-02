package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Authenticate user and provide them access to ClinicIO based on the role.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Authenticate user to provide"
        + "user access to ClinicIO based on the roles."
        + "Parameters: " + COMMAND_WORD
        + "[r/ROLE]"
        + "[n/NAME]"
        + "[p/PASSWORD]"    
        + "Example: login r/doctor n/Adam Bell p/doctor1";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return null;
    }
}
