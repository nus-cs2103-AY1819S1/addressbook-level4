package ssp.scheduleplanner.logic.commands;

import static ssp.scheduleplanner.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.DateWeekSamePredicate;

public class SortCommand extends Command{
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "The tasks has been sorted by priority.";

    public CommandResult execute(Model model, CommandHistory history) {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
