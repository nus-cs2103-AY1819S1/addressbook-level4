package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.collections.transformation.FilteredList;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.DateSamePredicate;
import ssp.scheduleplanner.model.task.Task;

/**
 * Displays the percentage of tasks done for today.
 */
public class ProgressTodayCommand extends Command {

    public static final String COMMAND_WORD = "progresstoday";
    public static final String MESSAGE_SUCCESS = "You have completed %.2f%% of your tasks for today!";
    private static final String systemDate =
            new SimpleDateFormat("ddMMyy").format(Calendar.getInstance().getTime());

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTaskList(new DateSamePredicate(systemDate));
        model.updateFilteredArchivedTaskList(new DateSamePredicate(systemDate));
        int uncompleted = model.getFilteredTaskList().size();
        int completed = model.getFilteredArchivedTaskList().size();
        int total = uncompleted + completed;
        float percentage = (float) completed * 100.0f / (float) total;
        return new CommandResult(String.format(MESSAGE_SUCCESS, percentage));
    }
}
