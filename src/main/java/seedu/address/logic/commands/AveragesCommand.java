package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.AverageStatistics;

/**
 * Returns statistics that are averages.
 */
public class AveragesCommand extends Command {

    public static final String COMMAND_WORD = "averages";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the following statistics as a bulleted list\n"
            + AverageStatistics.displayTypes()
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(String.format("", " "));
    }
}
