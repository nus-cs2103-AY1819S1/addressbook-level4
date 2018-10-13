package seedu.address.logic.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.DateWeekSamePredicate;

/**
 * List all tasks till Sunday.
 */
public class ListWeekCommand extends Command {
    public static final String COMMAND_WORD = "listweek";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all task(s) till Sunday.\n";
    public static final String MESSAGE_SUCCESS = "This week task(s) are listed as followed";

    //Solution below adapted from:
    //https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
    private static String systemDate =
            new SimpleDateFormat("ddMMyy").format(Calendar.getInstance().getTime());

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        List<String> dateList = new ArrayList<String>();
        //dateList.add(systemDate);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        systemDate = new SimpleDateFormat("ddMMyy").format(c.getTime());
        //dateList.add(systemDate);

        dateList.add("131018");
        dateList.add("141018");

        for (int i = 0; i< dateList.size(); i++)
            System.out.println(dateList.get(i));

        model.updateFilteredTaskList(new DateWeekSamePredicate(dateList));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
