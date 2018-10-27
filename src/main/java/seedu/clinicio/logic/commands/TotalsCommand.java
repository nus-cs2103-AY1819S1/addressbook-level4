package seedu.clinicio.logic.commands;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.Analytics;

/**
 * Returns statistics that are totals.
 */
public class TotalsCommand extends Command {

    public static final String COMMAND_WORD = "totals";

    @Override
    public CommandResult execute(Model model, CommandHistory history, Analytics analytics) {
        requireAllNonNull(model, analytics);
        return new CommandResult(analytics.getTotalStatistics());
    }
}
