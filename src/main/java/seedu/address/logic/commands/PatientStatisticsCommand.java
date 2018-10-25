package seedu.address.logic.commands;

//@@author arsalanc-v2

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.analytics.StatisticType;

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
