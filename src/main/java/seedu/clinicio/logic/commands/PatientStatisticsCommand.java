package seedu.clinicio.logic.commands;

//@@author arsalanc-v2

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.StatisticType;

/**
 * Shows patient statistics and visualizations.
 */
public class PatientStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "patientstats";
    public static final String MESSAGE_SUCCESS = "%1$s statistics";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model, history);
        model.requestAnalyticsDisplay(StatisticType.PATIENT);
        return new CommandResult(String.format(MESSAGE_SUCCESS, StatisticType.PATIENT));
    }
}
