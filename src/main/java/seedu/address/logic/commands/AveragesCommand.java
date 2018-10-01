package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.Model;

/**
 * Returns statistics that are averages.
 */
public class AveragesCommand extends Command {

    public static final String COMMAND_WORD = "averages";

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) {
        requireAllNonNull(model, analytics);
        return new CommandResult(analytics.getAverageStatistics());
    }
}
