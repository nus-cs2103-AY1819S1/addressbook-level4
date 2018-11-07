package seedu.scheduler.logic.commands;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import seedu.scheduler.commons.util.EventFormatUtil;
import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.RepeatEventGenerator;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Event;

/**
 * Get events from google calendar.
 */
public class GetGoogleCalendarEventsCommand extends Command {
    public static final String COMMAND_WORD = "getGCEvents";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Get google calendar events.\n"
            + "download the events from primary google calendar.\n"
            + "Parameters: NONE "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_INITIALIZE_SUCCESS = "Events in google calendar downloaded.";
    public static final String MESSAGE_NO_EVENTS = "No upcoming events found in Google Calender.";
    public static final String MESSAGE_INTERNET_ERROR = "Internet connection error. Please check your network.";
    public static final String MESSAGE_REJECT_SECOND_INITIALIZE = "Note that you are only allowed"
            + " to initialize the app once. You have already initialized it before. Command rejected.";

    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();
    private final EventFormatUtil eventFormatUtil = new EventFormatUtil();

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!connectToGoogleCalendar.netIsAvailable()) {
            throw new CommandException(MESSAGE_INTERNET_ERROR);
        }
        if (connectToGoogleCalendar.isGoogleCalendarEnabled()) {
            throw new CommandException(MESSAGE_REJECT_SECOND_INITIALIZE);
        }

        //Get the Google Calendar service object
        Calendar service = connectToGoogleCalendar.getCalendar();

        //Get events from a specified calendar
        Events events = null;
        List<Event> eventsToadd = new ArrayList<>();
        try {
            events = connectToGoogleCalendar.getEvents(service);
        } catch (UnknownHostException e) {
            throw new CommandException(MESSAGE_INTERNET_ERROR);
        }

        //Extract the listOfGoogleEvents from the events object
        List<com.google.api.services.calendar.model.Event> listOfGoogleEvents = events.getItems();
        if (listOfGoogleEvents.isEmpty()) {
            return new CommandResult(MESSAGE_NO_EVENTS);
        } else {
            //Upcoming events
            eventsToadd = eventFormatUtil.convertGoogleListToLocalList(listOfGoogleEvents);
        }

        for (Event event : eventsToadd) {
            model.addEvents(RepeatEventGenerator.getInstance().generateAllRepeatedEvents(event));
            model.commitScheduler();
        }
        connectToGoogleCalendar.setGoogleCalendarEnabled();
        return new CommandResult(MESSAGE_INITIALIZE_SUCCESS);
    }
}
