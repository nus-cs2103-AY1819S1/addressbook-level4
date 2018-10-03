package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;


/**
 * Analyze grades of students
 */
public class GradesCommand extends Command {
    public static final String COMMAND_WORD = "gradesSort";

    public static final String MESSAGE_SUCCESS = "Analyze grades.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}



