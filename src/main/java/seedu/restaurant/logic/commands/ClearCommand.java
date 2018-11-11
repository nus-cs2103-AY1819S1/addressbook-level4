package seedu.restaurant.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.util.SampleDataUtil;

/**
 * Clears the restaurant book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Restaurant Book has been cleared";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(SampleDataUtil.getSampleRestaurantBook());
        model.commitRestaurantBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
