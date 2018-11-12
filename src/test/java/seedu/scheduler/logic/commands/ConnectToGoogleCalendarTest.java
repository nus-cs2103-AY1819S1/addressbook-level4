package seedu.scheduler.logic.commands;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.scheduler.logic.commands.AddCommand.MESSAGE_SUCCESS;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.CommandTestUtil.disable;
import static seedu.scheduler.logic.commands.CommandTestUtil.enable;
import static seedu.scheduler.logic.commands.CommandTestUtil.helperCommand;
import static seedu.scheduler.testutil.TypicalEvents.CHRISTMAS;
import static seedu.scheduler.testutil.TypicalEvents.CHRISTMASEVE;
import static seedu.scheduler.testutil.TypicalEvents.EXAM_CS2103;
import static seedu.scheduler.testutil.TypicalEvents.EXAM_CS2103_UPDATED;
import static seedu.scheduler.testutil.TypicalEvents.FRIDAY_LECTURE;
import static seedu.scheduler.testutil.TypicalEvents.LOCAL_REPEAT_EVENT;
import static seedu.scheduler.testutil.TypicalEvents.LOCAL_SINGLE_EVENT;
import static seedu.scheduler.testutil.TypicalEvents.THURDSDAY_LECTURE;
import static seedu.scheduler.testutil.TypicalEvents.WEDNESDAY_LECTURE;
import static seedu.scheduler.testutil.TypicalEvents.WEDNESDAY_LECTURE_UPDATED;
import static seedu.scheduler.testutil.TypicalEvents.WEDNESDAY_LECTURE_UPDATED2;
import static seedu.scheduler.testutil.TypicalEvents.WEDNESDAY_LECTURE_UPDATED_ONE;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.testing.auth.oauth2.MockTokenServerTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.Events;

import seedu.scheduler.commons.util.EventFormatUtil;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.testutil.EventBuilder;

/**
 * Contains tests for ConnectToGoogleCalendar.
 */
public class ConnectToGoogleCalendarTest {
    private static final String CALENDAR_NAME = "primary";
    private static final String MESSAGE_READ_WRITE_ERROR =
            "Unable to write/read the status to the local status indicator file";
    private static final String MESSAGE_IO_ERROR = "Unable to retrieve event from Google Calendar."
            + "Please check your network and check its existence on Google Calendar.";
    private static final String SAMPLE = "123\u05D9\u05e0\u05D9\u05D1";
    private static final String TEMP_EVENT_NAME = "tempEventName";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());
    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();

    @Test
    public void isGoogleCalendarEnabled() throws CommandException {
        //Status is "Enabled" -> ok
        enable();
        assertTrue(ConnectToGoogleCalendar.isGoogleCalendarEnabled());
        //Status is not "Enabled" -> fail
        disable();
        assertFalse(ConnectToGoogleCalendar.isGoogleCalendarEnabled());
    }

    @Test
    public void isGoogleCalendarDisabled() throws CommandException {
        //Status is not "Disabled" -> fail
        enable();
        assertFalse(ConnectToGoogleCalendar.isGoogleCalendarDisabled());
        //Status is "Disabled" -> ok
        disable();
        assertTrue(ConnectToGoogleCalendar.isGoogleCalendarDisabled());
    }

    @Test
    public void checkStatus() throws CommandException {
        //Prepare the test file where the status indicates Enabled
        enable();
        //Test whether returns Enabled
        assertTrue(ConnectToGoogleCalendar.checkStatus("Enabled"));
        assertFalse(ConnectToGoogleCalendar.checkStatus("Disabled"));

        //Prepare the test file where the status indicates Disabled
        disable();
        //Test whether returns Disabled
        assertFalse(ConnectToGoogleCalendar.checkStatus("Enabled"));
        assertTrue(ConnectToGoogleCalendar.checkStatus("Disabled"));
    }

    @Test
    public void setGoogleCalendarEnabled() throws CommandException {
        //execute the command
        ConnectToGoogleCalendar.setGoogleCalendarEnabled();
        //get the result from the status file
        String contents = getModeContent();
        //check
        assertNotNull(contents);
        assertEquals("Enabled" + System.getProperty("line.separator"), contents);
        //clean up test
        disable();
    }

    @Test
    public void setGoogleCalendarDisabled() throws CommandException {
        //execute the command
        ConnectToGoogleCalendar.setGoogleCalendarDisabled();
        //get the result from the mode.txt
        String contents = getModeContent();
        //check
        assertNotNull(contents);
        assertEquals("Disabled" + System.getProperty("line.separator"), contents);
    }

    @Test(expected = CommandException.class)
    public void setGoogleCalendarStatus() throws CommandException {
        //Set Enabled
        ConnectToGoogleCalendar.setGoogleCalendarStatus("Enabled");
        String contents = getModeContent();
        assertNotNull(contents);
        assertEquals("Enabled" + System.getProperty("line.separator"), contents);

        //Set Disabled
        ConnectToGoogleCalendar.setGoogleCalendarStatus("Disabled");
        contents = getModeContent();
        assertNotNull(contents);
        assertEquals("Disabled" + System.getProperty("line.separator"), contents);

        //Try to write to a read-only file
        String path = "./tokens/mode.txt";
        final File file = new File(path);
        //Pre-condition: the file was writable before
        assertTrue(file.canWrite());
        file.setWritable(false);
        try {
            //We force an exception
            ConnectToGoogleCalendar.setGoogleCalendarStatus("");
        } catch (CommandException e) {
            //This will be captured by Junit test
            //This catch is not needed actually, put here for completeness
            throw new CommandException(MESSAGE_READ_WRITE_ERROR);
        } finally {
            //set back the file to writable
            file.setWritable(true);
            disable();
        }
    }

    /**
     * Retrieves an status for testing purpose.
     *
     * @return String in the status file
     */
    private String getModeContent() throws CommandException {
        //Get the file
        File file = new File("./tokens/mode.txt");
        //Read the content
        StringBuilder contents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String text;
            String lineSeparator = System.getProperty("line.separator");
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(lineSeparator);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommandException(MESSAGE_READ_WRITE_ERROR);
        }
        //convert to string and return
        return contents.toString();
    }

    @Test
    public void getCalendar() throws CommandException {
        //get a connection to Google
        final ConnectToGoogleCalendar connectToGoogleCalendar =
                new ConnectToGoogleCalendar();
        //get a service
        com.google.api.services.calendar.Calendar service =
                connectToGoogleCalendar.getCalendar();
        //create a calendar for testing
        Calendar getFromGoogleCalendar = null;
        try {
            getFromGoogleCalendar = service.calendars().get(CALENDAR_NAME).execute();
            //check whether this is the test calender
            assertEquals("cs21031819f111tester@gmail.com", getFromGoogleCalendar.getId());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommandException(MESSAGE_IO_ERROR);
        }
        //create an expected exception
        //Try to retrieve the deleted exception
        //Confirm the an exception will be thrown
        String fakeId = "fakeId";
        assertThrows(
                com.google.api.client.googleapis.json.GoogleJsonResponseException.class, (
                ) -> service.calendars().get(fakeId).execute());
        disable();
    }

    /**
     * Tests {@link GoogleCredential}.
     *
     * @author Yaniv Inbar
     */
    @Test
    public void getNetHttpTransport() throws Exception {
        //Test Download
        HttpTransport transport = new MockHttpTransport() {
            @Override
            public LowLevelHttpRequest buildRequest(String method, String url) {
                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() {
                        MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
                        result.setContentType(Json.MEDIA_TYPE);
                        result.setContent(SAMPLE);
                        return result;
                    }
                };
            }
        };
        HttpRequest request =
                transport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
        HttpResponse response = request.execute();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        response.download(outputStream);
        assertEquals(SAMPLE, outputStream.toString("UTF-8"));
    }

    /**
     * Tests {@link GoogleCredential}.
     *
     * @author Yaniv Inbar
     */
    @Test
    public void getCredentials() throws IOException {
        //testRefreshToken_User()
        final String accessToken = "1/MkSJoj1xsli0AccessToken_NKPY2";
        final String clientSecret = "jakuaL9YyieakhECKL2SwZcu";
        final String clientId = "ya29.1.AADtN_UtlxN3PuGAxrN2XQnZTVRvDyVWnYq4I6dws";
        final String refreshToken = "1/Tl6awhpFjkMkSJoj1xsli0H2eL5YsMgU_NKPY2TyGWY";
        MockTokenServerTransport transport = new MockTokenServerTransport();
        transport.addClient(clientId, clientSecret);
        transport.addRefreshToken(refreshToken, accessToken);
        GoogleCredential credential = new GoogleCredential.Builder()
                .setClientSecrets(clientId, clientSecret)
                .setTransport(transport)
                .setJsonFactory(JSON_FACTORY)
                .build();
        credential.setRefreshToken(refreshToken);
        assertTrue(credential.refreshToken());
        assertEquals(accessToken, credential.getAccessToken());
    }

    @Test
    public void netIsAvailable() {
        //assumption: Google is always online -> true
        assertTrue(ConnectToGoogleCalendar.netIsAvailable("http://www.google.com"));
        //Invalid URL -> false
        assertFalse(ConnectToGoogleCalendar.netIsAvailable("NoUrlWillFail"));
        disable();
    }

    @Test
    public void pushToGoogleCal() throws InterruptedException, CommandException {
        Event validEvent = new EventBuilder(CHRISTMAS).build();
        /* Case: No internet -> Not added */
        assertFalse(
                connectToGoogleCalendar
                        .pushToGoogleCal(false, Collections.singletonList(validEvent)
                        ));
        //set up the google-enabled environment
        enable();
        /* Case: add a single event -> added */
        assertCommandSuccess(new AddCommand(validEvent), model, commandHistory,
                String.format(MESSAGE_SUCCESS, validEvent.getEventName()));
        disable();
        //Prevent triggering Google's limit
        sleep(5000);

        /* Case: add a repeated eventSet -> added */
        validEvent = new EventBuilder(THURDSDAY_LECTURE).build();
        enable();
        //added
        helperCommand(new AddCommand(validEvent), model, commandHistory);
        //verify whether added
        String id = EventFormatUtil.getEventSetUidInGoogleFormatFromLocalEvent(validEvent);
        com.google.api.services.calendar.Calendar service =
                connectToGoogleCalendar.getCalendar();
        Events events = connectToGoogleCalendar.getSingleEvents(service);
        //prevent disable too early
        sleep(5000);

        boolean found = false;
        for (com.google.api.services.calendar.model.Event event : events.getItems()) {
            if (event.getICalUID().equals(id)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
        //close the google-enabled environment
        disable();
        sleep(5000);
    }

    @Test
    public void deleteOnGoogleCal() throws InterruptedException, CommandException {
        //set up the google-enabled environment
        enable();

        //-----------delete single non-repeating event -> pass -------
        //Add a new event on Google Cal
        Event validEvent = new EventBuilder(CHRISTMASEVE).build();
        helperCommand(new AddCommand(validEvent), model, commandHistory);
        //Prevent triggering Google's limit
        disable();
        sleep(1000);
        enable();
        //execute the delete command
        assertTrue(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, validEvent, 0, true, false));
        //check
        //close the google-enabled environment
        disable();

        //-----------delete single repeating event instance -> pass -------
        validEvent = new EventBuilder(FRIDAY_LECTURE).build();
        enable();
        helperCommand(new AddCommand(validEvent), model, commandHistory);
        disable();
        sleep(1000);
        enable();
        assertTrue(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, validEvent, 0, true, false));
        sleep(1000);
        //-----------delete multiple upcoming repeating event in the EventSet--> pass -------
        assertTrue(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, validEvent, 2, false, false));
        sleep(1000);
        //-----------delete all repeating event in the EventSet-> pass -------
        assertTrue(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, validEvent, 0, false, false));

        sleep(1000);
        //-----------delete an single Event not in Google Calender-> fail -------
        Event eventNotOnGoogle = new EventBuilder(LOCAL_SINGLE_EVENT).build();
        assertFalse(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, eventNotOnGoogle, 0, false, false));
        sleep(1000);
        //-----------delete a Repeat Event instance not in Google Calender-> fail -------
        eventNotOnGoogle = new EventBuilder(LOCAL_REPEAT_EVENT).build();
        assertFalse(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, eventNotOnGoogle, 0, true, false));
        sleep(1000);
        //-----------delete upcoming Repeat Event instances not in Google Calender-> fail -------
        eventNotOnGoogle = new EventBuilder(LOCAL_REPEAT_EVENT).build();
        assertFalse(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, eventNotOnGoogle, 1, false, false));
        sleep(1000);
        //-----------delete all Repeat Event instances not in Google Calender-> fail -------
        eventNotOnGoogle = new EventBuilder(LOCAL_REPEAT_EVENT).build();
        assertFalse(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, eventNotOnGoogle, 0, false, true));
        sleep(1000);
        //-----------delete an Event with invalid index-> fail -------
        eventNotOnGoogle = new EventBuilder(LOCAL_REPEAT_EVENT).build();
        assertFalse(
                connectToGoogleCalendar.deleteEventOnGoogleCal(
                        true, eventNotOnGoogle, 999, false, true));
        sleep(1000);
        disable();
        sleep(5000);
    }

    @Test
    public void updateGoogleEvent() throws InterruptedException, CommandException {
        //----------Offline edit single non repeat event-> fail ---------------
        Event targetEvent = new EventBuilder(EXAM_CS2103).build();
        Event editedEvent = new EventBuilder(EXAM_CS2103_UPDATED).build();
        assertFalse(
                connectToGoogleCalendar.updateSingleGoogleEvent(
                        false, targetEvent, editedEvent, 0));

        //----------edit single non repeat event-> ok ---------------
        //Add a new event on Google Cal
        enable();
        helperCommand(new AddCommand(targetEvent), model, commandHistory);
        disable();
        sleep(2000);
        enable();
        //execute the edit command
        assertTrue(
                connectToGoogleCalendar.updateSingleGoogleEvent(
                        true, targetEvent, editedEvent, 0));
        disable();
        sleep(2000);

        //----------edit single repeat event instance-> ok ---------------
        targetEvent = new EventBuilder(WEDNESDAY_LECTURE).build();
        Event editedEventOne = new EventBuilder(WEDNESDAY_LECTURE_UPDATED_ONE).build();

        enable();
        helperCommand(new AddCommand(targetEvent), model, commandHistory);
        sleep(2000);
        assertTrue(
                connectToGoogleCalendar.updateSingleGoogleEvent(
                        true, targetEvent, editedEventOne, 1));
        disable();
        sleep(2000);

        //----------edit upcoming repeat event instances-> ok ---------------
        //case: Second Instance is selected
        enable();
        List<Event> editedList = WEDNESDAY_LECTURE_UPDATED;

        int instanceIndex = 1;

        assertTrue(
                connectToGoogleCalendar.updateRangeGoogleEvent(
                        true, targetEvent,
                        editedList,
                        instanceIndex, instanceIndex));
        disable();
        sleep(2000);
        //case: First Instance is selected
        instanceIndex = 0;
        enable();
        assertTrue(
                connectToGoogleCalendar.updateRangeGoogleEvent(
                        true, targetEvent,
                        editedList,
                        instanceIndex, instanceIndex));
        disable();
        sleep(2000);
        //----------edit all repeat event instances-> ok ---------------
        editedList = WEDNESDAY_LECTURE_UPDATED2;
        //case: Second Instance is selected
        enable();
        instanceIndex = 1;
        int effectRangeStartingIndex = 0;
        assertTrue(
                connectToGoogleCalendar.updateRangeGoogleEvent(
                        true, targetEvent,
                        editedList,
                        instanceIndex, effectRangeStartingIndex));
        disable();
        sleep(2000);
        //case: First Instance is selected
        instanceIndex = 0;
        enable();
        assertTrue(
                connectToGoogleCalendar.updateRangeGoogleEvent(
                        true, targetEvent,
                        editedList,
                        instanceIndex, effectRangeStartingIndex));
        disable();
        sleep(2000);
    }


    @Test
    public void getEvents() throws CommandException {
        enable();
        com.google.api.services.calendar.Calendar calendar =
                connectToGoogleCalendar.getCalendar();
        Events events = connectToGoogleCalendar.getEvents(calendar);
        assertNotNull(events);
        disable();
    }

    @Test
    public void getSingleEvents() throws CommandException {
        enable();
        com.google.api.services.calendar.Calendar calendar =
                connectToGoogleCalendar.getCalendar();
        Events events = connectToGoogleCalendar.getSingleEvents(calendar);
        assertNotNull(events);
        disable();
    }
}
