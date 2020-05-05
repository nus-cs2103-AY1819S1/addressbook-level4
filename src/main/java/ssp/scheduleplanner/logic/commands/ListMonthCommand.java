package ssp.scheduleplanner.logic.commands;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.DateWeekSamePredicate;

/**
 * List all tasks till the end of current month.
 * Pre-condition: The year must be within the 21st century
 */
public class ListMonthCommand extends Command {
    public static final String COMMAND_WORD = "listmonth";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all task(s) till the end of the month.\n";
    public static final String MESSAGE_SUCCESS = "This month's task(s) are listed as follows.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        List<String> dateList = new ArrayList<String>();
        LocalDate currentDate = LocalDate.now();

        appendDateList(dateList, numDaysTillEndOfMonth(currentDate));
        model.updateFilteredTaskList(new DateWeekSamePredicate(dateList));
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.NORMAL));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * This method append the date of days till nearest sunday into 'dateList'
     */
    public void appendDateList (List<String> dateList, int numDays) {
        dateList.clear();

        //solution below adapted from:
        //https://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java
        //if current day is last day of month, just append current date into dateList
        //also doubled up to add the base current date into dateList
        Calendar c = Calendar.getInstance();
        dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));

        //for day 1 till last day of month, append the corresponding date(s) till the last day into dateList
        //will not append for last day as numDays is 0
        for (int i = 0; i < numDays; i++) {
            c.add(Calendar.DATE, 1);
            dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));
        }
    }
    /**
     * This method checks and returns the number of days left from current date till the end of the month.
     * @param currentDay the name of today date
     * @return numDays
     */
    public int numDaysTillEndOfMonth (LocalDate currentDay) {
        int numDays;
        int dayValue = currentDay.getDayOfMonth();
        int monthValue = currentDay.getMonthValue();
        int yearValue = LocalDate.now().getYear();

        switch(monthValue) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            numDays = 31 - dayValue;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            numDays = 30 - dayValue;
            break;
        case 2:
            if (((yearValue % 4 == 0) && !(yearValue % 100 == 0)) || (yearValue % 400 == 0)) {
                numDays = 29 - dayValue;
            } else {
                numDays = 28 - dayValue;
            }
            break;
        default:
            numDays = 0;
            System.out.println("Invalid month.");
            break;
        }
        return numDays;
    }
}
