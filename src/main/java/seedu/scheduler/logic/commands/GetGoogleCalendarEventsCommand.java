package seedu.scheduler.logic.commands;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.ParserUtil;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;

/**
 * Get events from google calendar.
 */
public class GetGoogleCalendarEventsCommand extends Command {
    public static final String COMMAND_WORD = "getGCEvents";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Get google calendar events.\n"
            + "download the events from primary google calendar.\n"
            + "Parameters: NONE "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_GGEVENTS_SUCCESS = "Events in google calendar downloaded.";
    public static final String MESSAGE_NO_EVENTS = "No upcoming events found in Google Calender.";
    public static final String MESSAGE_INTERNET_ERROR = "Internet connection error. Please check your network.";

    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!connectToGoogleCalendar.netIsAvailable()) {
            return new CommandResult(MESSAGE_INTERNET_ERROR);
        }

        //Get the Google Calendar service object
        Calendar service = connectToGoogleCalendar.getCalendar();

        //Get events from a specified calendar
        Events events = null;
        List<Event> eventsToadd = new ArrayList<>();
        try {
            events = connectToGoogleCalendar.getEvents(service);
        } catch (UnknownHostException e) {
            return new CommandResult(MESSAGE_INTERNET_ERROR);
        }

        //Extract the listOfGoogleEvents from the events object
        List<com.google.api.services.calendar.model.Event> listOfGoogleEvents = events.getItems();
        if (listOfGoogleEvents.isEmpty()) {
            return new CommandResult(MESSAGE_NO_EVENTS);
        } else {
            //Upcoming events
            eventsToadd = convertGoogleListToLocalList(listOfGoogleEvents);
        }
        connectToGoogleCalendar.setGoogleCalendarEnabled();
        model.addEvents(eventsToadd);
        model.commitScheduler();
        return new CommandResult(MESSAGE_GGEVENTS_SUCCESS);
    }

    /**
     * Convert a List Google Event format to a list of Event in local Format.
     *
     * @param listOfGoogleEvents  A list of Google event.
     *
     * @return A list of local Event.
     */
    private List<Event> convertGoogleListToLocalList(
            List<com.google.api.services.calendar.model.Event> listOfGoogleEvents) {
        List<Event> eventsToAddToLocal = new ArrayList<>();

        for (com.google.api.services.calendar.model.Event googleEvent : listOfGoogleEvents) {
            Event newEvent = googleEventToLocalEventConverter(googleEvent);
            eventsToAddToLocal.add(newEvent);
        }
        return eventsToAddToLocal;
    }

    /**
     * Convert the Google Event format to local Format.
     *
     * @param googleEvent      The Google event.
     *
     * @return Event   The local Event.
     */
    private Event googleEventToLocalEventConverter(com.google.api.services.calendar.model.Event googleEvent) {

        DateTime start = googleEvent.getStart().getDateTime();
        if (start == null) {
            //if no time, only date
            start = googleEvent.getStart().getDate();
        }
        String newEventname = googleEvent.getSummary(); //Summary==title in GoogleAPI

        //TODO:Simply the string processing code (OOP possible here)
        String newEventStart = String.valueOf(start); //eg:2018-10-16T22:30:00.000+08:00
        //TODO:Try not to use the migc number 0,10,11,19. Use other ways of string processing
        String newEventStartDate = newEventStart.substring(0, 10); //2018-10-16
        String newEventStartTime = newEventStart.substring(11, 19); //22:30:00
        newEventStartTime = newEventStartTime.replaceAll(":", ""); //223000

        DateTime end = googleEvent.getEnd().getDateTime();
        if (end == null) {
            //if no time, only date
            start = googleEvent.getEnd().getDate();
        }
        String newEventEnd = String.valueOf(end);
        String newEventEndDate = newEventEnd.substring(0, 10);
        String newEventEndTime = newEventEnd.substring(11, 19);
        newEventEndTime = newEventEndTime.replaceAll(":", "");


        EventName eventName = null;
        try {
            eventName = ParserUtil.parseEventName(newEventname);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        seedu.scheduler.model.event.DateTime startDateTime = null;
        try {
            startDateTime = ParserUtil.parseDateTime(newEventStartDate + " " + newEventStartTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        seedu.scheduler.model.event.DateTime endDateTime = null;
        try {
            endDateTime = ParserUtil.parseDateTime(newEventEndDate + " " + newEventEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Description description = ParserUtil.parseDescription("test description");

        Venue venue = ParserUtil.parseVenue("test venue");
        RepeatType repeatType = RepeatType.NONE;
        seedu.scheduler.model.event.DateTime
                repeatUntilDateTime = endDateTime;
        Set<Tag> tags = Collections.emptySet();
        return new seedu.scheduler.model.event.Event(eventName, startDateTime, endDateTime,
                description,
                venue, repeatType, repeatUntilDateTime, tags);
    }
}
