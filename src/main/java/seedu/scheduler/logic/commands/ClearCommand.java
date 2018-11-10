package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.Scheduler;

/**
 * Clears all events in scheduler.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS_ONE = "clea";
    public static final String COMMAND_ALIAS_TWO = "cle";
    public static final String COMMAND_ALIAS_THREE = "cl";
    public static final String COMMAND_ALIAS_FOUR = "c";
    public static final String MESSAGE_SUCCESS = "Scheduler has been cleared!";
    public static final String MESSAGE_INTERNET_ERROR = "Only local changes. "
            + "no effects on your Google Calender.\n"
            + "Internet Error / Status File Does Not Exist.";
    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        boolean googleCalendarIsEnabled = ConnectToGoogleCalendar.isGoogleCalendarEnabled();
        requireNonNull(model);
        model.resetData(new Scheduler());
        boolean operationOnGoogleCalIsSuccessful =
                connectToGoogleCalendar.clear(googleCalendarIsEnabled);
        model.commitScheduler();

        if (operationOnGoogleCalIsSuccessful | !ConnectToGoogleCalendar.isGoogleCalendarEnabled()) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_SUCCESS
                    + "\n" + MESSAGE_INTERNET_ERROR);
        }
    }
}
