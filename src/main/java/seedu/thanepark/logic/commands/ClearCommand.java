package seedu.thanepark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ThanePark;

/**
 * Clears the thanepark book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Data in Thane Park has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new ThanePark());
        model.commitThanePark();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
