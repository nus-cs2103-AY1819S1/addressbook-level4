package seedu.scheduler.logic.commands;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.scheduler.commons.util.EventFormatUtil;
import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.RepeatEventGenerator;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.ParserUtil;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.RepeatType;

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
        DateTime repeatUntilDateTime = null;
        HashMap<String, RepeatType> googleiCalAndRepeatType = new HashMap<>();
        HashMap<String, DateTime> googleiCalAndRepeatUntilTime = new HashMap<>();
        try {
            events = connectToGoogleCalendar.getEvents(service);
            List<com.google.api.services.calendar.model.Event> listOfGoogleEvents =
                    events.getItems();


            for (com.google.api.services.calendar.model.Event googleEvent : listOfGoogleEvents) {
                //RRULE:FREQ=WEEKLY;BYDAY=SU
                String[] recurrenceText = null;
                RepeatType repeatType;
                List<String> recurrence = new ArrayList<>();
                recurrence = googleEvent.getRecurrence();

                if (recurrence != null) {
                    switch (recurrence.size()) {
                    case 1:
                        recurrenceText = recurrence.get(0).split(";");
                        break;
                    case 3:
                        recurrenceText = recurrence.get(2).split(";");
                        break;
                    default:
                        //nothing
                    }


                    int rrulePosition = recurrenceText[0].indexOf("=") + 1;
                    String rRule = recurrenceText[0].substring(rrulePosition); //RRULE:FREQ=WEEKLY

                    switch (rRule) {
                    case "YEARLY":
                        repeatType = RepeatType.YEARLY;
                        break;
                    case "MONTHLY":
                        repeatType = RepeatType.MONTHLY;
                        break;
                    case "WEEKLY":
                        repeatType = RepeatType.WEEKLY;
                        break;
                    case "DAILY":
                        repeatType = RepeatType.DAILY;
                        break;
                    default:
                        repeatType = RepeatType.NONE;
                        break;
                    }

                    googleiCalAndRepeatType.put(googleEvent.getICalUID(), repeatType);

                    switch (recurrenceText.length) {
                    case 2:
                        //RRULE:FREQ=WEEKLY;BYDAY=MO
                        //ignore in local implementation
                        repeatUntilDateTime = getRepeatUntilDateTime(
                                repeatUntilDateTime, recurrenceText[1]);
                        googleiCalAndRepeatUntilTime.put(googleEvent.getICalUID(), repeatUntilDateTime);
                        break;
                    case 3:
                        //RRULE:FREQ=WEEKLY;UNTIL=20181108T155959Z;BYDAY=MO,TU,WE,TH
                        repeatUntilDateTime = getRepeatUntilDateTime(
                                repeatUntilDateTime, recurrenceText[1]);
                        Parser parser = new Parser();
                        List<DateGroup> groups = parser.parse(
                                repeatUntilDateTime.getPrettyString() + "+0");
                        String temp = groups.get(0).getDates().get(0).toString();

                        //Sun Nov 11 23:59:00 SRET 2018
                        //EEE MMM dd HH:mm:ss Z yyyy
                        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
                        repeatUntilDateTime = ParserUtil.parseDateTime(format.parse(temp).toString());

                        googleiCalAndRepeatUntilTime.put(googleEvent.getICalUID(), repeatUntilDateTime);
                        break;
                    default:
                        //TODO://Event not supported

                    }
                }
            }
            for (RepeatType value : googleiCalAndRepeatType.values()) {
                int frequency = Collections.frequency(googleiCalAndRepeatType.values(), value);
                if (frequency > 1) {
                    //TODO:show not supported
                    throw new CommandException("Event type on Google Not supported.");
                }
            }


        } catch (NullPointerException | UnknownHostException e) {
            return new CommandResult(MESSAGE_INTERNET_ERROR);
        } catch (java.text.ParseException | ParseException e) {
            e.printStackTrace();
        }

        try {
            events = connectToGoogleCalendar.getSingleEvents(service);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //Extract the listOfGoogleEvents from the events object
        List<com.google.api.services.calendar.model.Event> listOfGoogleEvents = events.getItems();
        if (listOfGoogleEvents.isEmpty()) {
            return new CommandResult(MESSAGE_NO_EVENTS);
        } else {
            //Upcoming events
            eventsToadd = eventFormatUtil.convertGoogleListToLocalList(listOfGoogleEvents,
                    googleiCalAndRepeatType, googleiCalAndRepeatUntilTime);
        }

        for (Event event : eventsToadd) {
            model.addEvents(RepeatEventGenerator.getInstance().generateAllRepeatedEvents(event));
        }
        connectToGoogleCalendar.setGoogleCalendarEnabled();
        model.commitScheduler();
        return new CommandResult(MESSAGE_GGEVENTS_SUCCESS);
    }

    private DateTime getRepeatUntilDateTime(DateTime repeatUntilDateTime, String s) {
        DateTime newRepeatUntilDateTime = null;
        try {
            //local format:2018-10-20T17:00:00
            //UNTIL=20181108T155959Z
            String newRepeatUntil = s.substring(6, 19);
            StringBuilder newRepeatUntil2 = new StringBuilder(newRepeatUntil);
            newRepeatUntil2.insert(4, "-")
                    .insert(7, "-")
                    .insert(13, ":")
                    .append(":00");
            newRepeatUntilDateTime = ParserUtil.parseDateTime(newRepeatUntil2.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newRepeatUntilDateTime;
    }
}
