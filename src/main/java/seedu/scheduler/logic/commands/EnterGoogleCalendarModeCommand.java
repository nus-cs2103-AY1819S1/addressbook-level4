package seedu.scheduler.logic.commands;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.scheduler.commons.core.LogsCenter;
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
import seedu.scheduler.ui.UiManager;

/**
 * Get events from google calendar.
 */
public class EnterGoogleCalendarModeCommand extends Command {
    public static final String COMMAND_WORD = "EnterGoogleCalendarMode";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Get google calendar events.\n"
            + "download the events from primary google calendar.\n"
            + "Parameters: NONE "
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_NO_EVENTS = "No upcoming events found in Google Calender.";
    public static final String MESSAGE_EVENT_NOT_SUPORTED = "Event Format Not supported";
    public static final String MESSAGE_FORMAT_CONVERTION_ERROR =
            "Internet connection error. Please check your network.";
    public static final String MESSAGE_INITIALIZE_SUCCESS = "Events in google calendar downloaded.";
    public static final String MESSAGE_INTERNET_ERROR = "Internet connection error. Please check your network.";
    public static final String MESSAGE_REJECT_SECOND_INITIALIZE = "Note that you are only allowed"
            + " to initialize the app once. You have already initialized it before. Command rejected.";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();
    private final EventFormatUtil eventFormatUtil = new EventFormatUtil();

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!ConnectToGoogleCalendar.netIsAvailable("http://www.google.com")) {
            throw new CommandException(MESSAGE_INTERNET_ERROR);
        }
        if (ConnectToGoogleCalendar.isGoogleCalendarEnabled()) {
            throw new CommandException(MESSAGE_REJECT_SECOND_INITIALIZE);
        }

        //Get the Google Calendar calendar object
        logger.info("Getting a calendar calendar");
        Calendar calendar = connectToGoogleCalendar.getCalendar();

        //Obtain Google Events
        Events events = connectToGoogleCalendar.getEvents(calendar);
        if (events == null) {
            return new CommandResult(MESSAGE_INTERNET_ERROR);
        }

        //Get events from a specified calendar
        CommandResult commandResult;
        try {
            commandResult = pullEventFromGoogleCalendar(model, events, calendar);
        } catch (java.text.ParseException | ParseException e) {
            return new CommandResult(MESSAGE_FORMAT_CONVERTION_ERROR);
        } catch (NullPointerException e) {
            return new CommandResult(MESSAGE_INTERNET_ERROR);
        }

        return commandResult;
    }

    /**
     * Pulls events from google calendar
     * @param model the local model
     * @param events the events from Google Calendar
     * @param calendar the google calendar object
     *
     * @return a coomandResult indicating the result
     *
     * @throws CommandException wraps with appropriate message
     * @throws ParseException parser error
     * @throws java.text.ParseException parser exception
     */
    private CommandResult pullEventFromGoogleCalendar(
            Model model, Events events, Calendar calendar)
            throws CommandException, ParseException, java.text.ParseException {
        List<Event> eventsToadd;
        HashMap<String, RepeatType> googleiCalAndRepeatType = new HashMap<>();
        HashMap<String, DateTime> googleiCalAndRepeatUntilTime = new HashMap<>();

        //Obtain a list of Google Events
        List<com.google.api.services.calendar.model.Event> listOfGoogleEvents =
                events.getItems();

        //Retrieve any recurrence property if any
        //and then check whether we support its format
        retrieveRecurrenceEventProperty(googleiCalAndRepeatType, googleiCalAndRepeatUntilTime,
                listOfGoogleEvents);
        checkForRepeatTypeSupport(googleiCalAndRepeatType);

        //Obtain a list Of Google Events instances
        //Previously only Events (not
        events = connectToGoogleCalendar.getSingleEvents(calendar);
        //Retrieve the event instances
        listOfGoogleEvents = events.getItems();
        if (listOfGoogleEvents.isEmpty()) {
            ConnectToGoogleCalendar.setGoogleCalendarEnabled();
            return new CommandResult(MESSAGE_NO_EVENTS);
        } else {
            //Upcoming events
            eventsToadd = eventFormatUtil.convertGoogleListToLocalList(listOfGoogleEvents,
                    googleiCalAndRepeatType, googleiCalAndRepeatUntilTime);
        }

        //Add to local database
        for (Event event : eventsToadd) {
            model.addEvents(RepeatEventGenerator.getInstance().generateAllRepeatedEvents(event));
        }
        ConnectToGoogleCalendar.setGoogleCalendarEnabled();
        model.commitScheduler();
        return new CommandResult(MESSAGE_INITIALIZE_SUCCESS);
    }

    /**
     * Checks whether the Recurrence Event is supported by the app
     * @param googeliCalAndRepeatType the Map of Google Event and its RepeatType
     *
     * @throws CommandException if any
     */
    private void checkForRepeatTypeSupport(HashMap<String, RepeatType> googeliCalAndRepeatType)
            throws CommandException {
        for (RepeatType value : googeliCalAndRepeatType.values()) {
            int frequency = Collections.frequency(googeliCalAndRepeatType.values(), value);
            if (frequency > 1) {
                //More than one means the Google Event has two repeating properties
                //eg: repeat weekly and monthly
                throw new CommandException(MESSAGE_EVENT_NOT_SUPORTED);
            }
        }
    }

    /**
     * Retrieves recurrence Event Property from the list of Google Events
     * and save those properties to the maps
     * @param googleiCalAndRepeatType the map storing RepeatType
     * @param googleiCalAndRepeatUntilTime the map storing RepeatUntilDateTime
     * @param listOfGoogleEvents the list of Google Evnts
     *
     * @throws CommandException if any
     * @throws ParseException if any
     * @throws java.text.ParseException if any
     */
    private void retrieveRecurrenceEventProperty(HashMap<String, RepeatType> googleiCalAndRepeatType,
                                                 HashMap<String, DateTime> googleiCalAndRepeatUntilTime,
                                                 List<com.google.api.services.calendar.model.Event> listOfGoogleEvents)
            throws CommandException, ParseException, java.text.ParseException {
        for (com.google.api.services.calendar.model.Event googleEvent : listOfGoogleEvents) {
            //Expected Google Recurrence Rule (RRULE):FREQ=WEEKLY;BYDAY=SU
            List<String> recurrence = googleEvent.getRecurrence();
            boolean isRecurrentEvent = false;
            if (recurrence != null) {
                isRecurrentEvent = true;
            }

            if (isRecurrentEvent) {
                DateTime repeatUntilDateTime = getRepeatUntilDateTimeFromRecurrenceAttribute(
                        googleiCalAndRepeatType,
                        googleiCalAndRepeatUntilTime, googleEvent, recurrence);
                if (repeatUntilDateTime == null) {
                    throw new CommandException(MESSAGE_EVENT_NOT_SUPORTED);
                }
            }
        }
    }

    /**
     * @param googleiCalAndRepeatTypeMap the map storing RepeatType
     * @param googleiCalAndRepeatUntilTime the map storing RepeatUntilDateTime
     * @param googleEvent a google Event
     * @param recurrence the recurrence rule
     *
     * @return Repeat Until Date Time
     *
     * @throws CommandException  if any
     * @throws ParseException  if any
     * @throws java.text.ParseException  if any
     */
    private DateTime getRepeatUntilDateTimeFromRecurrenceAttribute(
            HashMap<String, RepeatType> googleiCalAndRepeatTypeMap,
            HashMap<String, DateTime> googleiCalAndRepeatUntilTime,
            com.google.api.services.calendar.model.Event googleEvent,
            List<String> recurrence)
            throws CommandException, ParseException, java.text.ParseException {
        String[] recurrenceText;
        RepeatType repeatType;
        recurrenceText = getRecurrenceRuleFromRecurrenceAttribute(recurrence);
        //Expected recurrence rule text:
        //RRULE:FREQ=WEEKLY;UNTIL=20181203T155959Z;BYDAY=MO
        //This is specified by Google
        if (recurrenceText.length != 3) {
            throw new CommandException(MESSAGE_EVENT_NOT_SUPORTED);
        }
        //Make sense of each field
        String recurrenceRuleTextField = recurrenceText[0];
        String recurrenceUntilTextField = recurrenceText[1];
        String recurrenceDayTextField = recurrenceText[2];
        int expectedLengthOfRecurrenceDay = 8;
        if (recurrenceDayTextField.length() > expectedLengthOfRecurrenceDay) {
            //beyond this means the event is repeated by more than one day. eg, Monday AND Tuesday
            //This is NOT supported by this app
            throw new CommandException(MESSAGE_EVENT_NOT_SUPORTED);
        }

        //Retrieve repeat type and repeat until DateTime
        retrieveRepeatTypeFromRecurrenceRuleTextField(
                googleiCalAndRepeatTypeMap, googleEvent, recurrenceRuleTextField);
        DateTime repeatUntilDateTime = convertToLocalRepeatUntilDateTimeFromTextField(
                googleiCalAndRepeatUntilTime, googleEvent, recurrenceUntilTextField);

        return repeatUntilDateTime;
    }

    /**
     * Converts the Google RepeatUntilDateTime in text field to local DateTime
     * @param googleiCalAndRepeatUntilTimeMap the map storing RepeatUntilDateTime
     * @param googleEvent a google Event
     * @param recurrenceUntilTextField the text field
     *
     * @return local repeat until DateTime
     *
     * @throws ParseException if any
     * @throws java.text.ParseException if any
     */
    private DateTime convertToLocalRepeatUntilDateTimeFromTextField(
            HashMap<String, DateTime> googleiCalAndRepeatUntilTimeMap,
            com.google.api.services.calendar.model.Event googleEvent,
            String recurrenceUntilTextField)
            throws ParseException, java.text.ParseException {

        DateTime repeatUntilDateTimeFromGoogleEvent =
                formatRepeatUntilDateTimeFrom(recurrenceUntilTextField);

        String eventTimeInString =
                adjustTheTimeZone(repeatUntilDateTimeFromGoogleEvent);

        //Pattern
        //Sun Nov 11 23:59:00 SRET 2018
        //EEE MMM dd HH:mm:ss Z yyyy
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        repeatUntilDateTimeFromGoogleEvent = ParserUtil
                .parseDateTime(format.parse(eventTimeInString)
                .toString());
        //Store in the map
        googleiCalAndRepeatUntilTimeMap.put(googleEvent.getICalUID(), repeatUntilDateTimeFromGoogleEvent);
        return repeatUntilDateTimeFromGoogleEvent;
    }

    /**
     * Corrects the time zone of the repeatUntilDateTime obtained from Google
     * @param repeatUntilDateTime repeat Until Date Time of a event
     *
     * @return the adjusted DateTime in String format
     */
    private String adjustTheTimeZone(DateTime repeatUntilDateTime) {
        Parser parser = new Parser();
        //+0 for setting the timezone (GMT)
        List<DateGroup> groups = parser.parse(
                repeatUntilDateTime.getPrettyString() + "+0");
        return groups.get(0).getDates().get(0).toString();
    }

    /**
     * Formats the RepeatUntilDateTime from the text field of recurrence
     * @param recurrenceUntilTextField the original text
     *
     * @return the epeatUntilDateTime
     */
    private DateTime formatRepeatUntilDateTimeFrom(String recurrenceUntilTextField) {
        DateTime newRepeatUntilDateTime = null;
        try {
            //local format:2018-10-20T17:00:00
            //UNTIL=20181108T155959Z
            String newRepeatUntil = recurrenceUntilTextField.substring(6, 19);
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

    /**
     * Retrieves RepeatType from RecurrenceRule TextField
     * @param googleiCalAndRepeatTypeMap the map storing RepeatType
     * @param googleEvent a google event
     * @param recurrenceRuleTextField the original text field containing recurrenceRule
     */
    private void retrieveRepeatTypeFromRecurrenceRuleTextField(
            HashMap<String, RepeatType> googleiCalAndRepeatTypeMap,
            com.google.api.services.calendar.model.Event googleEvent,
            String recurrenceRuleTextField) {
        RepeatType repeatType;
        //This position depends on Google's implementation
        int rulePosition = recurrenceRuleTextField.indexOf("=") + 1;
        String rRule = recurrenceRuleTextField
                .substring(rulePosition);
        repeatType = convertToRepeatTypeFromRecurrenceRule(rRule);
        googleiCalAndRepeatTypeMap.put(googleEvent.getICalUID(), repeatType);
    }

    private String[] getRecurrenceRuleFromRecurrenceAttribute(List<String> recurrence) throws CommandException {
        String[] recurrenceText;
        switch (recurrence.size()) {
        case 1:
            recurrenceText = recurrence.get(0).split(";");
            break;
        case 3:
            recurrenceText = recurrence.get(2).split(";");
            break;
        default:
            throw new CommandException(MESSAGE_EVENT_NOT_SUPORTED);
        }
        return recurrenceText;
    }

    /**
     * Covverts a recurrenceRule text to RepeatType
     * @param recurrenceRule the recurrence Rule text
     *
     * @return corresponding RepeatType
     */
    private RepeatType convertToRepeatTypeFromRecurrenceRule(String recurrenceRule) {
        RepeatType repeatType;
        switch (recurrenceRule) {
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
        return repeatType;
    }
}
