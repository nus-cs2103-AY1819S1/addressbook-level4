package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.patient.PatientNameContainsKeywordsPredicate;

/**
 * Finds and lists all patients in ClinicIO whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPatientCommand extends Command {

    public static final String COMMAND_WORD = "findpatient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alex benny chen";

    public static final String MESSAGE_NOT_LOGIN = "You are not login. Please login before viewing.";

    private final PatientNameContainsKeywordsPredicate predicate;

    public FindPatientCommand(PatientNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!UserSession.isLogin()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }

        model.updateFilteredPatientList(predicate);
        model.switchTab(0);
        return new CommandResult(
                String.format(Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindPatientCommand // instanceof handles nulls
                && predicate.equals(((FindPatientCommand) other).predicate)); // state check
    }
}
