package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.TotalStatistics;

/**
 * Returns statistics that are totals.
 */
public class TotalsCommand extends Command {

    public static final String COMMAND_WORD = "totals";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the following statistics as a bulleted list\n"
            + TotalStatistics.displayTypes()
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "totals:\n%1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(String.format("", " "));
    }
}
