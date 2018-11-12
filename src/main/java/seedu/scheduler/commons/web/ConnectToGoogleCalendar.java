package seedu.scheduler.commons.web;

import static com.google.api.client.util.DateTime.parseRfc3339;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

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
import com.google.api.services.calendar.model.Event.Reminders;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.util.EventFormatUtil;
import seedu.scheduler.logic.commands.EnterGoogleCalendarModeCommand;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.ui.UiManager;

/**
 * Methods related to the connection with Google Calendar.
 */
public class ConnectToGoogleCalendar {
    /**
     * Global instance of the scopes required.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials/credentials.json";
    private static final String CALENDAR_NAME = "primary";
    private static final String MESSAGE_NOT_FOUND = "Event not found on Google Calendar."
            + "Operation effect is only local.";
    private static final String MESSAGE_IO_ERROR = "Unable to retrieve event from Google Calendar."
            + "Please check your network and check its existence on Google Calendar.";
    private static final String MESSAGE_READ_WRITE_ERROR =
            "Unable to write/read the status to the local status indicator file";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    public static boolean isGoogleCalendarEnabled() throws CommandException {
        return checkStatus("Enabled");
    }

    public static boolean isGoogleCalendarDisabled() throws CommandException {
        return checkStatus("Disabled");
    }

    public static void setGoogleCalendarEnabled() throws CommandException {
        setGoogleCalendarStatus("Enabled");
    }

    public static void setGoogleCalendarDisabled() throws CommandException {
        setGoogleCalendarStatus("Disabled");
    }

    public static void setGoogleCalendarStatus(String wantedStatus) throws CommandException {
        File file = new File("./tokens/mode.txt");
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(wantedStatus);
            logger.info("Google Calendar status changed to " + wantedStatus
                    + " recoded in ./tokens/enabled.txt");
        } catch (IOException e) {
            logger.warning(MESSAGE_READ_WRITE_ERROR);
            throw new CommandException(MESSAGE_READ_WRITE_ERROR);
        }
    }

    /**
     * Checks the status of the Google Calender Feature
     *
     * @param expectedStatus the expected status
     *
     * @return true if the expected status is true
     */
    public static boolean checkStatus(String expectedStatus) throws CommandException {
        File file = new File("./tokens/mode.txt");
        StringBuilder contents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;
            String lineSeparator = System.getProperty("line.separator");
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(lineSeparator);
            }
        } catch (IOException e) {
            logger.info(MESSAGE_IO_ERROR);
            throw new CommandException(MESSAGE_IO_ERROR);
        }
        return (contents.toString().trim().equals(expectedStatus));
    }


    public Calendar getCalendar() throws CommandException {
        NetHttpTransport httpTransport = getNetHttpTransport();

        // Build Google calender service object.
        Calendar service = null;
        try {
            service = new Calendar.Builder(httpTransport, JSON_FACTORY,
                    getCredentials(httpTransport))
                    .setApplicationName("iScheduler Xs Max")
                    .build();
        } catch (IOException e) {
            logger.info(MESSAGE_IO_ERROR);
            throw new CommandException(MESSAGE_IO_ERROR);
        }
        return service;
    }

    public NetHttpTransport getNetHttpTransport() throws CommandException {
        // Build a new authorized API client service.
        NetHttpTransport httpTransport = null;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            logger.info(MESSAGE_IO_ERROR);
            throw new CommandException(MESSAGE_IO_ERROR);
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
    public static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {
        GoogleClientSecrets clientSecrets = getGoogleClientSecrets();

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static GoogleClientSecrets getGoogleClientSecrets() throws IOException {
        // Load client secrets.
        InputStream in = EnterGoogleCalendarModeCommand.class
                .getResourceAsStream(CREDENTIALS_FILE_PATH);
        return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    }

    /**
     * To check whether internet is available.
     *
     * @param address the URL provided
     *
     * @return true if available, false otherwise.
     */
    public static boolean netIsAvailable(String address) {
        try {
            final URL url = new URL(address);
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Pushes the event(s) to Google Calendar.
     */
    public boolean pushToGoogleCal(boolean enabled, List<Event> toAddList) throws CommandException {
        if (statusIsDisabled(enabled)) {
            return false;
        }

        logger.info("Starting to push events Google Calendar");
        Calendar service = getCalendar();

        com.google.api.services.calendar.model.Event gEvent = new com.google.api.services.calendar.model.Event();
        for (Event toAddEvent : toAddList) {
            gEvent = setCommonAttributes(gEvent, toAddEvent);

            if (toAddEvent.getRepeatType().equals(RepeatType.NONE)) {
                gEvent = gEvent.setId(toAddEvent.getEventUid()
                        .toString()
                        .replaceAll("-", ""));
            } else { //repeated event
                gEvent = setRepeatAttribute(gEvent, toAddEvent);
            }

            try {
                service.events().insert(CALENDAR_NAME, gEvent).execute();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private com.google.api.services.calendar.model.Event setRepeatAttribute(
            com.google.api.services.calendar.model.Event gEvent, Event toAddEvent) {
        String googleRecurringEventId =
                EventFormatUtil.getEventSetUidInGoogleFormatFromLocalEvent(toAddEvent);
        com.google.api.services.calendar.model.Event gEvent2 = gEvent.setICalUID(googleRecurringEventId);

        String eventRepeatType = String.valueOf(toAddEvent.getRepeatType());
        seedu.scheduler.model.event.DateTime eventUntilDt = toAddEvent.getRepeatUntilDateTime();
        String eventUntilDate = eventUntilDt.getPrettyString()
                //local:2019-01-01 18:51:52
                .replaceAll("-", "")
                //local:20190101 18:51:52
                .replaceFirst(" ", "T")
                //local:20190101T18:51:52
                .replaceAll(":", "")
                //local:20190101T185152
                .concat("Z");

        String commandMessage = "RRULE:FREQ="
                + eventRepeatType
                + ";UNTIL="
                + eventUntilDate;
        //Google format:20110701T170000Z
        gEvent2.setRecurrence(Arrays.asList(commandMessage));
        return gEvent;
    }

    private com.google.api.services.calendar.model.Event setCommonAttributes(
            com.google.api.services.calendar.model.Event gEvent, Event toAddEvent) {

        gEvent.setSummary(String.valueOf(toAddEvent.getEventName()));
        gEvent.setLocation(String.valueOf(toAddEvent.getVenue()));
        gEvent.setDescription(String.valueOf(toAddEvent.getDescription()));
        String startDateTime = EventFormatUtil.convertStartDateTimeToGoogleFormat(toAddEvent);

        DateTime start = parseRfc3339(startDateTime);
        gEvent.setStart(new EventDateTime().setDateTime(start).setTimeZone("Singapore"));

        String endDateTime = EventFormatUtil.convertEndDateTimeToGoogleFormat(toAddEvent);
        DateTime end = parseRfc3339(endDateTime);
        gEvent.setEnd(new EventDateTime().setDateTime(end).setTimeZone("Singapore"));


        ReminderDurationList reminderDurationList = toAddEvent.getReminderDurationList();
        List<EventReminder> reminderOverrides = new ArrayList<>();
        Set<Duration> reminderMap = reminderDurationList.get();
        Reminders reminder = new Reminders();

        reminder.setUseDefault(false);
        for (Duration key : reminderMap) {
            EventReminder eventReminder = new EventReminder();
            eventReminder.setMethod("popup");
            eventReminder.setMinutes(Math.toIntExact(key.toMinutes()));
            reminderOverrides.add(eventReminder);
        }

        reminder.setOverrides(reminderOverrides);
        gEvent.setReminders(reminder);
        return gEvent;
    }

    /**
     * Deletes a range of repeat event instances in the Google Calendar.
     *
     * @param eventToDelete a local Event.
     * @param instanceIndex the instance index for recurring event.
     * @param deleteSingle true if it is intended to deelete a single event
     * @param deleteAll true if it is intended to delete all instances
     */
    public boolean deleteEventOnGoogleCal(
            boolean enabled, Event eventToDelete, int instanceIndex, boolean deleteSingle, boolean deleteAll)
            throws CommandException {
        if (statusIsDisabled(enabled)) {
            return false;
        }

        Calendar service = getCalendar();
        List<String> eventIds = new ArrayList<>();
        String recurringEventId = null;
        boolean repeatedEventsFound = false;

        //Case1: delete non-repeat event
        //find EventId
        Boolean success = deleteSingleNonRepeatEvent(eventToDelete, service, eventIds);
        if (success != null) {
            return success;
        }

        //Case2: delete repeated events
        //Find the ICalUid
        if (eventToDelete.isRepeatEvent()) {
            FindIcalUid findIcalUid =
                    new FindIcalUid(eventToDelete, service, eventIds, recurringEventId, repeatedEventsFound).invoke();
            if (findIcalUid.getResult()) {
                return false;
            }
            recurringEventId = findIcalUid.getRecurringEventId();
            repeatedEventsFound = findIcalUid.isRepeatedEventsFound();
        }

        //No such event online
        if (eventToDelete.isRepeatEvent() && !repeatedEventsFound) {
            logger.info(MESSAGE_IO_ERROR);
            return false;
        } else { //EventSet Found
            try {
                assert recurringEventId != null;
                //Case 2.1: delete single instance
                if (deleteSingle) {
                    return deleteSingleInstance(instanceIndex, service, recurringEventId);
                } else if (deleteAll | instanceIndex == 0) {
                    //delete multiple instances
                    //Case2.2: Delete All
                    deleteAllInstances(service, eventIds);
                } else {
                    //Case2.3:delete upcoming (and this is not the first instance)
                    deleteUpcomingInstances(instanceIndex, service, recurringEventId);
                }
            } catch (IOException e) {
                logger.info(MESSAGE_IO_ERROR);
                return false;
            }
            return true;
        }
    }

    /**
     * Deletes 'on and after' repeat event instances in the Google Calendar
     *
     * @param instanceIndex the instance index for recurring event
     * @param service the calender service object
     * @param recurringEventId recurringEventId of the Google Event
     */
    private void deleteUpcomingInstances(int instanceIndex, Calendar service, String recurringEventId)
            throws IOException {
        Events instances = null;
        instances = service.events()
                .instances(CALENDAR_NAME, recurringEventId).execute();
        assert instances != null;
        for (int i = instanceIndex; i < instances.getItems().size(); i++) {
            com.google.api.services.calendar.model.Event instance =
                    instances.getItems().get(i);
            instance.setStatus("cancelled");
            service.events()
                    .update(CALENDAR_NAME, instance.getId(), instance).execute();
        }
    }

    /**
     * Deletes all repeat event instances in the Google Calendar
     *
     * @param service the calender service object
     * @param eventIds the list of events to be deleted
     */
    private void deleteAllInstances(Calendar service, List<String> eventIds) throws IOException {
        for (String eventId : eventIds) {
            service.events().delete(CALENDAR_NAME, eventId).execute();
        }
    }

    /**
     * Deletes a single repeat event instance in the Google Calendar
     *
     * @param instanceIndex the instance index for recurring event
     * @param service the calender service object
     * @param recurringEventId recurringEventId of the Google Event
     */
    private boolean deleteSingleInstance(int instanceIndex, Calendar service, String recurringEventId)
            throws IOException {
        Events instances = service.events().instances(CALENDAR_NAME, recurringEventId).execute();
        com.google.api.services.calendar.model.Event instance = instances.getItems().get(instanceIndex);
        instance.setStatus("cancelled");
        service.events().update(CALENDAR_NAME, instance.getId(), instance).execute();
        return true;
    }

    /**
     * Deletes a single non-repeat event instance in the Google Calendar
     *
     * @param eventToDelete the Event to be deleted
     * @param service the calender service object
     * @param eventIds the list of events to be deleted
     */
    private Boolean deleteSingleNonRepeatEvent(Event eventToDelete, Calendar service, List<String> eventIds) {
        if (!eventToDelete.isRepeatEvent()) {
            eventIds.add(eventToDelete.getEventUid()
                    .toString()
                    .replaceAll("-", ""));
            //delete on Google
            try {
                service.events().delete(CALENDAR_NAME, eventIds.get(0)).execute();
            } catch (IOException e) {
                logger.info(MESSAGE_IO_ERROR);
                return false;
            }
        }
        if (!eventToDelete.isRepeatEvent()) {
            return true;
        }
        return null;
    }

    /**
     * Deletes all events in the Google Calendar.
     *
     * @param enabled the enable status from caller
     */
    public boolean clear(boolean enabled) throws CommandException {
        if (statusIsDisabled(enabled)) {
            return false;
        }
        Calendar service = getCalendar();
        try {
            service.calendars().clear(CALENDAR_NAME).execute();
        } catch (IOException e) {
            logger.info(MESSAGE_IO_ERROR);
            return false;
        }
        return true;
    }

    /**
     * Edits the details of an existing event in the Google Calendar.
     *
     * @param eventToEdit a local Event.
     * @param editedEvent an edited local Event.
     */
    public boolean updateSingleGoogleEvent(boolean enabled,
                                           Event eventToEdit, Event editedEvent, int instanceIndex)
            throws CommandException {
        if (statusIsDisabled(enabled)) {
            return false;
        }
        assert eventToEdit != null;
        assert editedEvent != null;
        Calendar service = getCalendar();
        boolean operationOnGoogleCalIsSuccessful;

        //Case1: The event is an instance of repeated event
        //get the EventUid
        if (eventToEdit.isRepeatEvent()) {
            operationOnGoogleCalIsSuccessful =
                    updateSingleRepeatInstance(eventToEdit, editedEvent, instanceIndex, service);
        } else {
            //Case2: The event is not an instance of Repeated Event
            operationOnGoogleCalIsSuccessful =
                    updateSingleNonRepeatEvent(eventToEdit, editedEvent, service);
        }
        return operationOnGoogleCalIsSuccessful;
    }

    /**
     * Edits the details of an existing instance of a eventSet in the Google Calendar.
     *
     * @param eventToEdit   a local Event.
     * @param editedEvent   an edited local Event.
     * @param instanceIndex the index of the eventToEdit in the eventSet
     * @param service       the Google service object
     */
    private boolean updateSingleRepeatInstance(Event eventToEdit, Event editedEvent,
                                               int instanceIndex, Calendar service) throws CommandException {
        String recurringEventId = getRecurringEventId(eventToEdit, service);
        if (recurringEventId == null) {
            logger.info(MESSAGE_NOT_FOUND);
            return false;
        }
        //Update the repeated event
        Events instances = null;
        try {
            assert recurringEventId != null;
            instances = service.events()
                    .instances(CALENDAR_NAME, recurringEventId)
                    .execute();

            //Sort the instances according to DateTime
            List<com.google.api.services.calendar.model.Event> instanceSort = instances.getItems();
            Collections.sort(instanceSort, (
                    a, b) -> a.getStart().getDateTime().toString()
                    .compareTo(b.getStart().getDateTime().toString()));

            //Get the wanted instance, edit and update
            com.google.api.services.calendar.model.Event instance = instanceSort.get(instanceIndex);
            instance = setCommonAttributes(instance, editedEvent);
            instance = setRepeatAttribute(instance, editedEvent);
            service.events().update(CALENDAR_NAME, instance.getId(), instance).execute();
        } catch (IOException e) {
            logger.info(MESSAGE_IO_ERROR);
            return false;
        }
        return true;
    }


    /**
     * Edits the details of an existing event in the Google Calendar.
     *
     * @param eventToEdit a local Event.
     * @param editedEvent an edited local Event.
     * @param service     google service
     */
    private boolean updateSingleNonRepeatEvent(Event eventToEdit, Event editedEvent, Calendar service) {
        String gEventId;
        com.google.api.services.calendar.model.Event gEvent = null;
        com.google.api.services.calendar.model.Event updatedgEvent = null;
        gEventId = EventFormatUtil.getEventUidInGoogleFormatFromLocalEvent(eventToEdit);
        //retrieve event using event Uid
        boolean found = false;
        Events events = getSingleEvents(service);
        if (events == null) {
            logger.warning(MESSAGE_IO_ERROR);
            return false;
        }
        for (com.google.api.services.calendar.model.Event tempGEvent : events.getItems()) {
            if (Objects.equals(tempGEvent.getId(), gEventId)) {
                found = true;
                break;
            }
        }

        //Next action:Edit if found, return error if not found
        if (!found) {
            logger.warning(MESSAGE_NOT_FOUND);
            return false;
        } else {
            try {
                gEvent = service.events().get(CALENDAR_NAME, gEventId).execute();
                assert gEvent != null;
                gEvent = setCommonAttributes(gEvent, editedEvent);
                updatedgEvent = service.events().update(CALENDAR_NAME, gEventId, gEvent).execute();
                assert updatedgEvent != null;
                updatedgEvent.getUpdated();
            } catch (IOException e) {
                logger.info(MESSAGE_IO_ERROR);
                return false;
            }

        }
        return true;
    }

    /**
     * Edits the details of all existing repeated events in the Google Calendar.
     *
     * @param eventToEdit     a local Event.
     * @param editedEvents    an edited local Event.
     * @param rangeStartIndex the effect of update will start from this index
     */
    public boolean updateRangeGoogleEvent(boolean enabled,
                                          Event eventToEdit, List<Event> editedEvents, int instanceIndex,
                                          int rangeStartIndex)
            throws CommandException {
        if (statusIsDisabled(enabled)) {
            return false;
        }
        assert !editedEvents.isEmpty();
        assert editedEvents.get(0).isRepeatEvent();
        assert eventToEdit != null;
        Calendar service = getCalendar();

        String recurringEventId = getRecurringEventId(eventToEdit, service);
        if (recurringEventId == null) {
            logger.info(MESSAGE_NOT_FOUND);
            return false;
        }

        Events instances = null;

        if (instanceIndex > 0) {
            try {
                assert recurringEventId != null;
                instances = service.events()
                        .instances(CALENDAR_NAME, recurringEventId)
                        .execute();
                List<com.google.api.services.calendar.model.Event> instanceSort = instances.getItems();
                Collections.sort(instanceSort, (
                        a, b) -> a.getStart().getDateTime().toString()
                        .compareTo(b.getStart().getDateTime().toString()));

                for (int i = 0; i < rangeStartIndex; i++) {
                    com.google.api.services.calendar.model.Event instance = instanceSort.get(i);
                    service.events().update(CALENDAR_NAME, instance.getId(), instance).execute();
                }

                int editedEventIndex = 0;
                for (int i = rangeStartIndex; i < instanceSort.size(); i++) {
                    com.google.api.services.calendar.model.Event instance = instanceSort.get(i);
                    instance = setCommonAttributes(instance, editedEvents.get(editedEventIndex));
                    instance = setRepeatAttribute(instance, editedEvents.get(editedEventIndex));
                    service.events().update(CALENDAR_NAME, instance.getId(), instance).execute();
                    editedEventIndex++;
                }
            } catch (IOException e) {
                logger.info(MESSAGE_IO_ERROR);
                return false;
            }
        }
        //If the Event is the first instance of the EventSet
        //Google recommends to change the EventSet as a whole
        if (instanceIndex == 0) {
            try {
                com.google.api.services.calendar.model.Event gEvent = null;
                gEvent = service.events()
                        .get(CALENDAR_NAME,
                                Objects.requireNonNull(recurringEventId)).execute();
                gEvent = setCommonAttributes(gEvent, editedEvents.get(0));
                gEvent = setRepeatAttribute(gEvent, editedEvents.get(0));
                service.events()
                        .update(CALENDAR_NAME, recurringEventId, gEvent).execute();
            } catch (IOException e) {
                logger.info(MESSAGE_IO_ERROR);
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether in offline mode
     *
     * @param enabled status carried forward from default
     *
     * @return true if it is disabled
     */
    private boolean statusIsDisabled(boolean enabled) throws CommandException {
        return !enabled | isGoogleCalendarDisabled();
    }


    /**
     * Retrieves the recurring Event Id of a Google Event
     *
     * @param targetEvent the target Event
     * @param service the Google calender service object
     *
     * @return the Recurring EventId
     */
    private String getRecurringEventId(Event targetEvent, Calendar service) {
        String recurringEventId = null;
        Events allEventsOnGoogle = null;
        String eventSetUid =
                EventFormatUtil.getEventSetUidInGoogleFormatFromLocalEvent(targetEvent);
        logger.info("Trying to download Google Events from Calendar Service.");
        allEventsOnGoogle = getSingleEvents(service);

        logger.info("Trying to retrieve GoogleEvents from Events.");
        if (allEventsOnGoogle == null) {
            return null;
        }
        for (com.google.api.services.calendar.model.Event event : allEventsOnGoogle.getItems()) {
            if (Objects.equals(event.getICalUID(), eventSetUid)) {
                recurringEventId = event.getRecurringEventId();
            }
        }

        if (recurringEventId == null) {
            logger.warning("RecurringEventId is not found."
                    + "No such repeated event on Google Calendar.");
            return null;
        }
        return recurringEventId;
    }


    /**
     * Retrieve Google Events (excluding the single instances of repeat Event)
     *
     * @param service the Google Calender Service
     *
     * @return true if it is disabled
     */
    public Events getEvents(Calendar service) {
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
                    .execute();
        } catch (IOException e3) {
            logger.info(MESSAGE_IO_ERROR);
            return null;
        }
        return events;
    }

    /**
     * Retrieve Google Events (excluding the parent recurring event)
     *
     * @param service the Google Calender Service
     *
     * @return true if it is disabled
     */
    public Events getSingleEvents(Calendar service) {
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
                    //.setOrderBy("startTime")//if not specified, stable order
                    .setSingleEvents(true)//not the repeated ones
                    .execute();
        } catch (IOException e) {
            logger.warning(MESSAGE_IO_ERROR);
            return null;
        }
        return events;
    }

    /**
     * A internal class to find the IcalUid of a Google Event
     */
    private class FindIcalUid {
        private boolean myResult;
        private Event eventToDelete;
        private Calendar service;
        private List<String> eventIds;
        private String recurringEventId;
        private boolean repeatedEventsFound;

        public FindIcalUid(Event eventToDelete, Calendar service, List<String> eventIds, String recurringEventId,
                           boolean repeatedEventsFound) {
            this.eventToDelete = eventToDelete;
            this.service = service;
            this.eventIds = eventIds;
            this.recurringEventId = recurringEventId;
            this.repeatedEventsFound = repeatedEventsFound;
        }

        public boolean getResult() {
            return myResult;
        }

        public String getRecurringEventId() {
            return recurringEventId;
        }

        public boolean isRepeatedEventsFound() {
            return repeatedEventsFound;
        }

        /**
         * Retireves the iCalUid
         */
        public FindIcalUid invoke() {
            Events allEventsOnGoogle;
            String eventSetUid =
                    EventFormatUtil.getEventSetUidInGoogleFormatFromLocalEvent(eventToDelete);
            try {
                allEventsOnGoogle = getSingleEvents(service);
                for (com.google.api.services.calendar.model.Event event : allEventsOnGoogle.getItems()) {
                    if (Objects.equals(event.getICalUID(), eventSetUid)) {
                        eventIds.add(event.getId());
                        repeatedEventsFound = true;
                        recurringEventId = event.getRecurringEventId();
                    }
                }
            } catch (NullPointerException e) {
                logger.info(MESSAGE_IO_ERROR);
                myResult = true;
                return this;
            }
            myResult = false;
            return this;
        }
    }
}
