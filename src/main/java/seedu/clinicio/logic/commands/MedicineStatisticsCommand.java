package seedu.clinicio.logic.commands;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.StatisticType;

//@@author arsalanc-v2

/**
 * Shows medicine statistics and visualizations.
 */
public class MedicineStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "medstats";
    public static final String MESSAGE_SUCCESS = "%1$s statistics";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model, history);
        model.requestAnalyticsDisplay(StatisticType.MEDICINE);
        return new CommandResult(String.format(MESSAGE_SUCCESS, StatisticType.MEDICINE));
    }

}
