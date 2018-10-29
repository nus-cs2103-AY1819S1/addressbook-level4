package seedu.scheduler.commons.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.api.client.util.DateTime;

import seedu.scheduler.logic.parser.ParserUtil;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;

/**
 * A class for converting the format between Google Event and local Event.
 */
public class EventFormatUtil {

    /**
     * Convert a List Google Event format to a list of Event in local Format.
     *
     * @param listOfGoogleEvents  A list of Google event.
     *
     * @return A list of local Event.
     */
    public List<Event> convertGoogleListToLocalList(
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
     * @param googleEvent The Google event.
     *
     * @return Event   The local Event.
     */
    public Event googleEventToLocalEventConverter(com.google.api.services.calendar.model.Event googleEvent) {

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
        return new Event(eventName, startDateTime, endDateTime,
                description,
                venue, repeatType, repeatUntilDateTime, tags);
    }
}
