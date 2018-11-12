package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;

/**
 * Lists all persons in the SuperTA client to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
