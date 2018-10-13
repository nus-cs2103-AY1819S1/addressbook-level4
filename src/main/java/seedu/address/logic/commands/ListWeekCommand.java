package seedu.address.logic.commands;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        //List<String> dateList = new ArrayList<String>();
        //dateList.add(systemDate);

        /*
        String datename = LocalDate.now().getDayOfWeek().name();
        System.out.println("Today is:" + datename);
        System.out.println(numDaysTillSunday(datename));
        System.out.println();

        datename = LocalDate.now().getDayOfWeek().plus(1).name();
        System.out.println("Today is:" + datename);
        System.out.println(numDaysTillSunday(datename));
        System.out.println();
        */

        //Calendar c = Calendar.getInstance();
        //System.out.println(c);
        //c.add(Calendar.DATE, 0);
        //systemDate = new SimpleDateFormat("ddMMyy").format(c.getTime());
        //System.out.println(systemDate);
        //dateList.add(systemDate);

        /*
        String testdate = "141018";
        if (testdate.equals("081018")) {
            dateList.add("081018");
            dateList.add("091018");
            dateList.add("101018");
            dateList.add("111018");
            dateList.add("121018");
            dateList.add("131018");
            dateList.add("141018");
        }

        if (testdate.equals("091018")) {
            dateList.add("091018");
            dateList.add("101018");
            dateList.add("111018");
            dateList.add("121018");
            dateList.add("131018");
            dateList.add("141018");
        }

        if (testdate.equals("101018")) {
            dateList.add("101018");
            dateList.add("111018");
            dateList.add("121018");
            dateList.add("131018");
            dateList.add("141018");
        }

        if (testdate.equals("111018")) {
            dateList.add("111018");
            dateList.add("121018");
            dateList.add("131018");
            dateList.add("141018");
        }

        if (testdate.equals("121018")) {
            dateList.add("121018");
            dateList.add("131018");
            dateList.add("141018");
        }

        if (testdate.equals("131018")) {
            dateList.add("131018");
            dateList.add("141018");
        }

        if (testdate.equals("141018")) {
            dateList.add("141018");
        }
        */

        //dateList.add("131018");
        //dateList.add("141018");

        /*
        for (int i = 0; i < dateList.size(); i++) {
            System.out.println(dateList.get(i));
        }
        */
        List<String> dateList = new ArrayList<String>();
        String datename = LocalDate.now().getDayOfWeek().name();
        appendDateList(dateList, numDaysTillSunday(datename));
        model.updateFilteredTaskList(new DateWeekSamePredicate(dateList));

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * This method append the date of days till nearest sunday into 'dateList'
     */
    private void appendDateList (List<String> dateList, int numDays) {
        dateList.clear();

        if (numDays==0) System.out.println("today sunday");
        if (numDays==1) System.out.println("today saturday");
        if (numDays==2) System.out.println("today friday");
        if (numDays==3) System.out.println("today thursday");
        if (numDays==4) System.out.println("today wednesday");
        if (numDays==5) System.out.println("today tuesday");
        if (numDays==6) System.out.println("today monday");

        //if current day is sunday, just append current date into dateList
        //also doubled up to add the base current date into dateList
        Calendar c = Calendar.getInstance();
        dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));

        //for monday to saturday, append the corresponding date(s) till sunday into dateList
        //will not work for sunday as intended as numDays for sunday till sunday is 0
        for (int i = 0; i < numDays; i++) {
            c.add(Calendar.DATE, 1);
            dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));
        }
    }

    /**
     * This method check and return the number of positive days left from current date till the nearest sunday.
     * @param currentDate the name of today date
     * @return numDays
     */
    private int numDaysTillSunday (String currentDate) {
        int numDays;

        switch(currentDate) {
        case "SUNDAY":
            numDays = 0;
            break;
        case "MONDAY":
            numDays = 6;
            break;
        case "TUESDAY":
            numDays = 5;
            break;
        case "WEDNESDAY":
            numDays = 4;
            break;
        case "THURSDAY":
            numDays = 3;
            break;
        case "FRIDAY":
            numDays = 2;
            break;
        case"SATURDAY":
            numDays = 1;
            break;
        default:
            numDays = 0;
            break;
        }
        return numDays;
    }
}
