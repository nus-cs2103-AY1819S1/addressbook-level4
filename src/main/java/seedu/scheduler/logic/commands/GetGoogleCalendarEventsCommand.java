package seedu.scheduler.logic.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.ParserUtil;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.Description;
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

    private static final String CALENDAR_NAME = "primary";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required.
     */
    private static final List <String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials/credentials.json";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        //Get the Google Calendar service object
        Calendar service = getCalendar();

        //Get events from a specified calendar
        Events events = getEvents(service);

        //Extract the items from the events object
        List <Event> items = events.getItems();
        if (items.isEmpty()) {
            return new CommandResult(MESSAGE_NO_EVENTS);
        } else {
            //Upcoming events
            for (Event googleEvent : items) {
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

                addGcEventToLocal(model, newEventname, newEventStartDate, newEventStartTime, newEventEndDate,
                        newEventEndTime);
            }
        }
        return new CommandResult(MESSAGE_GGEVENTS_SUCCESS);
    }

    /**
     * Parser the Google Event format to local Format.
     *
     * @param model The current scheduler model.
     * @param newEventname The Google event name.
     * @param newEventStartDate The Google event start date.
     * @param newEventStartTime The Google event start timing.
     * @param newEventEndDate The Google event end date.
     * @param newEventEndTime The Google event end timing.
     */
    private void addGcEventToLocal(Model model, String newEventname, String newEventStartDate, String newEventStartTime,
                                   String newEventEndDate, String newEventEndTime) {
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
        seedu.scheduler.model.event.Event
                event =
                new seedu.scheduler.model.event.Event(UUID.randomUUID(), eventName, startDateTime, endDateTime,
                        description,
                        venue, repeatType, repeatUntilDateTime, tags);

        model.addEvents(seedu.scheduler.model.event.Event.generateAllRepeatedEvents(event));
        model.commitScheduler();
    }

    private Events getEvents(Calendar service) {
        //TODO:Currently number is hardcoded, maybe can ask user to imputthis.
        //max 2500 by Google
        //default value is 250 if not specified
        int numberOfEventsToBeDownloaded = 999;

        // List the next [userinput] events from calendar name specified by CALENDAR_NAME.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = null;
        try {
            events = service.events().list(CALENDAR_NAME)//set the source calendar on google
                    .setMaxResults(numberOfEventsToBeDownloaded) //set upper limit for number of events
                    .setTimeMin(now)//set the starting time
                    .setOrderBy("startTime")//if not specified, stable order
                    //TODO: further development can be done for repeated event, more logic must be written
                    .setSingleEvents(true)//not the repeated ones
                    //TODO: how to use setSynctoken, to prevent adding the same event multiples times
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    private Calendar getCalendar() {
        NetHttpTransport httpTransport = getNetHttpTransport();

        // Build Google calender service object.
        Calendar service = null;
        try {
            service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                    .setApplicationName("iScheduler Xs Max")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return service;
    }

    private NetHttpTransport getNetHttpTransport() {
        // Build a new authorized API client service.
        NetHttpTransport httpTransport = null;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpTransport;
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param httpTransport The network HTTP Transport.
     *
     * @return An authorized Credential object.
     *
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
        GoogleClientSecrets clientSecrets = getGoogleClientSecrets();

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private static GoogleClientSecrets getGoogleClientSecrets() throws IOException {
        // Load client secrets.
        InputStream in = GetGoogleCalendarEventsCommand.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    }
}
