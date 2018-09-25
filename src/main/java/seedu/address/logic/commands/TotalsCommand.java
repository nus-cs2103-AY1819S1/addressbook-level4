package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Analytics;
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

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) {
        requireAllNonNull(model, analytics);
        return new CommandResult(String.format(analytics.getTotalStatistics()));
    }
}
