package seedu.scheduler.commons.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event.Reminders;
import com.google.api.services.calendar.model.EventReminder;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.ParserUtil;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.ui.UiManager;

/**
 * A class for converting the format between Google Event and local Event.
 */
public class EventFormatUtil {
    public static final String MESSAGE_INVALID_EVENT_ERROR =
            "Invalid Event Format from Google Calendar";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);


    /**
     * Returns the eventUid in Google Calendar Format.
     *
     * @param localEvent A local Event.
     *
     * @return eventUid in Google Calendar Format.
     */
    public static String getEventUidInGoogleFormatFromLocalEvent(Event localEvent) {
        return localEvent.getEventUid()
                .toString()
                .replaceAll("-", "");
    }

    /**
     * Returns the eventSetUid in Google Calendar Format.
     *
     * @param localEvent A local Event.
     *
     * @return eventSetUid in Google Calendar Format.
     */
    public static String getEventSetUidInGoogleFormatFromLocalEvent(Event localEvent) {
        return localEvent.getEventSetUid()
                .toString()
                .replaceAll("-", "");
    }

    /**
     * Converts a List Google Event format to a list of Event in local Format.
     * @param listOfGoogleEvents a list of Googl Event
     * @param googleiCalAndRepeatType a map storing GoogleICalId and its RepeatType
     * @param googleiCalAndRepeatUntilTIme a map storing GoogleICalId and its dRepeatUntilTIme
     *
     * @return
     *
     * @throws CommandException
     */
    public List<Event> convertGoogleListToLocalList(
            List<com.google.api.services.calendar.model.Event> listOfGoogleEvents,
            HashMap<String, RepeatType> googleiCalAndRepeatType,
            HashMap<String, seedu.scheduler.model.event.DateTime> googleiCalAndRepeatUntilTIme)
            throws CommandException {

        List<Event> eventsToAddToLocal = new ArrayList<>();
        BiMap<String, com.google.api.services.calendar.model.Event> eventsToAddToLocalRemoveRuplicate;
        eventsToAddToLocalRemoveRuplicate = HashBiMap.create();
        listOfGoogleEvents.sort(Comparator.comparing(a -> String.valueOf(a.getStart().getDateTime().getValue())));
        Collections.reverse(listOfGoogleEvents); //so that the earlier event is put to the set later
        for (com.google.api.services.calendar.model.Event googleEvent : listOfGoogleEvents) {
            eventsToAddToLocalRemoveRuplicate.put(googleEvent.getICalUID(), googleEvent);
        }

        for (com.google.api.services.calendar.model.Event googleEvent : eventsToAddToLocalRemoveRuplicate.values()) {
            if (!googleEvent.getStatus().equals("confirmed")) {
                continue;
            }
            Event newEvent = convertGoogleEventToLocalEvent(
                    googleEvent, googleiCalAndRepeatType, googleiCalAndRepeatUntilTIme);
            eventsToAddToLocal.add(newEvent);
        }
        return eventsToAddToLocal;
    }

    /**
     * Converts a local Event's starting data and time to Google format.
     *
     * @param event a local Event.
     *
     * @return a String in Google format.
     */
    public static String convertStartDateTimeToGoogleFormat(Event event) {
        //local format:2018-10-20 17:00:00
        //target :2018-10-21T22:30:00+08:00
        return event.getStartDateTime()
                .getPrettyString()
                .substring(0, 19)
                .replaceFirst(" ", "T")
                + "+08:00";
    }

    /**
     * Converts a local Event's ending data and time to Google format.
     *
     * @param event a local Event.
     *
     * @return a String in Google format.
     */
    public static String convertEndDateTimeToGoogleFormat(Event event) {
        return event.getEndDateTime()
                .getPrettyString()
                .substring(0, 19)
                .replaceFirst(" ", "T")
                + "+08:00";
    }


    /**
     * Converts the Google Event format to local Format.
     *
     * @param googleEvent                  a Google Event object
     * @param googleICalAndRepeatType      a map storing iCalId and its RepeatType
     * @param googleiCalAndRepeatUntilTime a map storing iCalId and its RepeatUntilDateTime
     *
     * @return the converted Local Event
     *
     * @throws CommandException if any
     */
    private Event convertGoogleEventToLocalEvent(
            com.google.api.services.calendar.model.Event googleEvent,
            HashMap<String, RepeatType> googleICalAndRepeatType,
            HashMap<String, seedu.scheduler.model.event.DateTime> googleiCalAndRepeatUntilTime)
            throws CommandException {

        DateTime start = googleEvent.getStart().getDateTime();
        if (start == null) {
            //if no time, only date
            start = googleEvent.getStart().getDate();
        }
        //Converts the startDateTime
        //Location is designed by Google
        int yearMonthDateStartPosition = 0;
        int yearMonthDateEndPosition = 10;
        int hourMinStartPosition = 11;
        int hourMinDateEndPosition = 19;
        //Multiple output cannot refactor to a single method
        String convertedEventName = convertEventNameToLocalFormat(googleEvent);
        String convertedEventStart = String.valueOf(start); //eg:2018-10-16T22:30:00.000+08:00
        String convertedEventStartDate =
                convertedEventStart.substring(yearMonthDateStartPosition, yearMonthDateEndPosition); //2018-10-16
        String convertedEventStartTime =
                convertedEventStart.substring(hourMinStartPosition, hourMinDateEndPosition)//22:30:00
                        .replaceAll(":", ""); //223000

        //Converts the endDateTime
        DateTime end = googleEvent.getEnd().getDateTime();
        String convertedEventEnd = String.valueOf(end);
        String convertedEventEndDate =
                convertedEventEnd.substring(yearMonthDateStartPosition, yearMonthDateEndPosition);
        String convertedEventEndTime = convertedEventEnd.substring(hourMinStartPosition, hourMinDateEndPosition);
        convertedEventEndTime = convertedEventEndTime.replaceAll(":", "");
        String convertedDescription = googleEvent.getDescription() == null ? "" : googleEvent.getDescription();
        String convertedVenue = googleEvent.getLocation() == null ? "" : googleEvent.getLocation();

        //Converts other attributes
        //declaration
        EventName eventName = null;
        seedu.scheduler.model.event.DateTime startDateTime = null;
        seedu.scheduler.model.event.DateTime endDateTime = null;
        Description description = null;
        Venue venue = null;

        //Converts repeatUntilDateTime
        ReminderDurationList reminderDurationList = new ReminderDurationList();
        if (convertedEventName == null) {
            throw new CommandException(MESSAGE_INVALID_EVENT_ERROR);
        }
        try {
            eventName = convertsEventNameToLocalFormat(convertedEventName);
            startDateTime = convertTargetDateTimeToLocalFormat(convertedEventStartDate, convertedEventStartTime);
            endDateTime = convertTargetDateTimeToLocalFormat(convertedEventEndDate, convertedEventEndTime);
            description = convertDescriptionToLocalFormat(convertedDescription);
            venue = convertVenueToLocalFormat(convertedVenue);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new CommandException(MESSAGE_INVALID_EVENT_ERROR);
        }

        //Converts reminders
        Reminders reminder = googleEvent.getReminders();
        convertsReminderToLocalFormat(reminderDurationList, reminder);

        //Converts repeatUntilDateTime
        seedu.scheduler.model.event.DateTime repeatUntilDateTime;
        repeatUntilDateTime =
                convertsRepeatUntilDaTimeToLocalFormat(googleEvent, googleiCalAndRepeatUntilTime, endDateTime);

        //Converts repeatType
        RepeatType repeatType;
        repeatType = convertsRepeatTypeToLocalFormat(googleEvent, googleICalAndRepeatType);

        //dummy tags
        Set<Tag> tags = Collections.emptySet();
        return new Event(eventName, startDateTime, endDateTime,
                description,
                venue,
                repeatType,
                repeatUntilDateTime,
                tags, reminderDurationList);
    }

    private String convertEventNameToLocalFormat(com.google.api.services.calendar.model.Event googleEvent) {
        return googleEvent.getSummary();
    }

    private Venue convertVenueToLocalFormat(String convertedVenue) {
        return ParserUtil.parseVenue(convertedVenue);
    }

    private Description convertDescriptionToLocalFormat(String convertedDescription) {
        return ParserUtil.parseDescription(convertedDescription);
    }

    private seedu.scheduler.model.event.DateTime convertTargetDateTimeToLocalFormat(String convertedEventStartDate,
                                                                                    String convertedEventStartTime)
            throws ParseException {
        return ParserUtil.parseDateTime(convertedEventStartDate + " " + convertedEventStartTime);
    }

    private EventName convertsEventNameToLocalFormat(String convertedEventName) throws ParseException {
        return ParserUtil.parseEventName(convertedEventName);
    }

    /**
     * Converts a Reminder List To LocalFormat
     * @param reminderDurationList a list of reminders
     * @param reminder reminder from Google
     */
    private void convertsReminderToLocalFormat(ReminderDurationList reminderDurationList, Reminders reminder) {
        if (reminder.getUseDefault()) {
            reminderDurationList.add(Duration.ofMinutes(30));
        } else if (reminder.getOverrides() == null) {
            logger.info("No overrides found in Google Event.");
        } else {
            List<EventReminder> reminderOverrides = reminder.getOverrides();
            for (EventReminder eventReminder : reminderOverrides) {
                reminderDurationList.add(Duration.ofMinutes(eventReminder.getMinutes()));
            }
        }
    }

    /**
     * Converts a RepeatType To LocalFormat
     * @param googleEvent a Google Event
     * @param googleICalAndRepeatType a map storing iCalId and its RepeatType
     *
     * @return local RepeatType
     */
    private RepeatType convertsRepeatTypeToLocalFormat(com.google.api.services.calendar.model.Event googleEvent,
                                                       HashMap<String, RepeatType> googleICalAndRepeatType) {
        RepeatType repeatType;
        if (googleICalAndRepeatType.get(googleEvent.getICalUID()) == null) {
            repeatType = RepeatType.NONE;
        } else {
            repeatType = googleICalAndRepeatType.get(googleEvent.getICalUID());
        }
        return repeatType;
    }

    /**
     * Converts a RepeatUntilDateTime To LocalFormat
     * @param googleEvent a Google Event
     * @param googleiCalAndRepeatUntilTime a map storing iCalId and its RepeatUntilDaTime
     * @param endDateTime localendDateTime
     *
     * @return local RepeatUntilDaTime
     */
    private seedu.scheduler.model.event.DateTime convertsRepeatUntilDaTimeToLocalFormat(
            com.google.api.services.calendar.model.Event googleEvent,
            HashMap<String, seedu.scheduler.model.event.DateTime> googleiCalAndRepeatUntilTime,
            seedu.scheduler.model.event.DateTime endDateTime) {
        seedu.scheduler.model.event.DateTime repeatUntilDateTime;
        if (googleiCalAndRepeatUntilTime.get(googleEvent.getICalUID()) == null) {
            repeatUntilDateTime = endDateTime;
        } else {
            repeatUntilDateTime = googleiCalAndRepeatUntilTime.get(googleEvent.getICalUID());
        }
        return repeatUntilDateTime;
    }

    /**
     * Calculates the relative index of an existing recurring event instance to the first recurring event instance.
     *
     * @param lastShownList The last shown list of events.
     * @param eventToDelete The recurring event to be deleted.
     *
     * @return The relative index of the event to be deleted to the first recurring event.
     */
    public static int calculateInstanceIndex(List<Event> lastShownList, Event eventToDelete) {
        //Start from -1 is because encounter first instance will make it increment by 1
        //which is zero. We are using zero-based index.
        int counter = -1;
        List<Event> lastShownListSorted = new ArrayList<>();
        lastShownListSorted.addAll(lastShownList);
        lastShownListSorted.sort(Comparator.comparing(Event::getStartDateTime));

        for (Event event : lastShownListSorted) {
            if (event.getEventSetUid().equals(eventToDelete.getEventSetUid())) {
                counter++;
            }
            if (event.getEventUid().equals(eventToDelete.getEventUid())) {
                break;
            }
        }
        return counter;
    }
}
