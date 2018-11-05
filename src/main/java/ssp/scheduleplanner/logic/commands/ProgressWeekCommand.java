package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.DateWeekSamePredicate;

/**
 * Displays the percentage of tasks done for this week.
 */
public class ProgressWeekCommand extends Command {

    public static final String COMMAND_WORD = "progressweek";
    public static final String MESSAGE_SUCCESS = "You have completed %.2f%% of your tasks for this week!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<String> dateList = new ArrayList<String>();
        String dateName = LocalDate.now().getDayOfWeek().name();
        ListWeekCommand.appendDateList(dateList, ListWeekCommand.numDaysTillSunday(dateName));
        model.updateFilteredTaskList(new DateWeekSamePredicate(dateList));
        model.updateFilteredArchivedTaskList(new DateWeekSamePredicate(dateList));
        int uncompleted = model.getFilteredTaskList().size();
        int completed = model.getFilteredArchivedTaskList().size();
        int total = uncompleted + completed;
        float percentage = (float) completed * 100.0f / (float) total;
        return new CommandResult(String.format(MESSAGE_SUCCESS, percentage));
    }
}
