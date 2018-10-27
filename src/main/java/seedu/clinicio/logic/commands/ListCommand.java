package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.Analytics;

/**
 * Lists all persons in the ClinicIO to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
