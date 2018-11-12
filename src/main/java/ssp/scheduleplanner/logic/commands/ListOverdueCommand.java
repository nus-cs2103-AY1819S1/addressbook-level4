package ssp.scheduleplanner.logic.commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.OverduePredicate;

/**
 * This class encapsulates the information regarding the ListOverdueCommand.
 */
public class ListOverdueCommand extends Command {
    public static final String COMMAND_WORD = "listoverdue";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all task(s) that are overdue.\n";
    public static final String MESSAGE_SUCCESS = "The overdue task(s) are listed as follows.";
    // The system date in yyMMdd format.
    public static final int SYSTEM_DATE;

    // Set SYSTEM_DATE variable to be the current date in the yyMMdd format.
    static {
        SYSTEM_DATE =
                Integer.parseInt(new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime()));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        // Filter the task list using the OverduePredicate with the current date as the parameter.
        model.updateFilteredTaskList(new OverduePredicate(SYSTEM_DATE));
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.NORMAL));
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
