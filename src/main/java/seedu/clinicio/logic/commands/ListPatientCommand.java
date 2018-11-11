package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;

/**
 * Lists all patients in the ClinicIO to the user.
 */
public class ListPatientCommand extends Command {

    public static final String COMMAND_WORD = "listpatient";

    public static final String MESSAGE_SUCCESS = "Listed all patients";

    public static final String MESSAGE_NOT_LOGIN = "You are not login. Please login before viewing.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!UserSession.isLogin()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }

        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        model.switchTab(0);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
