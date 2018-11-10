package seedu.scheduler.commons.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.ClearCommand;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.UserPrefs;

/**
 * Contains tests for ConnectToGoogleCalendar.
 */
public class ConnectToGoogleCalendarTest {
    private static final String CALENDAR_NAME = "primary";
    private static final String SAMPLE = "123\u05D9\u05e0\u05D9\u05D1";
    private static final String TEMP_EVENT_NAME = "tempEventName";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalScheduler(), new UserPrefs());

    /**
     * Enables the test environment
     */
    private void enable() {
        File file = new File("./tokens/mode.txt");
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            String contents = "Enabled";
            writer.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disables the test environment
     */
    private void disable() {
        File file = new File("./tokens/mode.txt");
        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            String contents = "Disabled";
            writer.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isGoogleCalendarEnabled() {
        //Status is "Enabled" -> ok
        enable();
        assertTrue(ConnectToGoogleCalendar.isGoogleCalendarEnabled());
        //Status is not "Enabled" -> fail
        disable();
        assertFalse(ConnectToGoogleCalendar.isGoogleCalendarEnabled());
    }

    @Test
    public void isGoogleCalendarDisabled() {
        //Status is not "Disabled" -> fail
        enable();
        assertFalse(ConnectToGoogleCalendar.isGoogleCalendarDisabled());
        //Status is "Disabled" -> ok
        disable();
        assertTrue(ConnectToGoogleCalendar.isGoogleCalendarDisabled());
    }

    @Test
    public void checkStatus() {
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
    public void clear() {
        //set up test environment
        enable();
        final ConnectToGoogleCalendar connectToGoogleCalendar =
                new ConnectToGoogleCalendar();
        com.google.api.services.calendar.Calendar service =
                connectToGoogleCalendar.getCalendar();

        //create an event in Google Calender
        try {
            service.events()
                    .quickAdd(CALENDAR_NAME, TEMP_EVENT_NAME)
                    .setText(TEMP_EVENT_NAME)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Retrieve the number of events (should be at least 1)
        int size = -1;
        try {
            size = connectToGoogleCalendar.getSingleEvents(service).getItems().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //check initial condition: not cleared
        assertNotEquals(0, size);
        //execute the clear command
        ClearCommand command = new ClearCommand();
        command.execute(model, commandHistory);
        //Retrieve the number of events after command
        try {
            size = connectToGoogleCalendar.getSingleEvents(service).getItems().size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //No event any more -> ok
        assertEquals(0, size);
        //clean up
        disable();
    }

    @Test
    public void setGoogleCalendarEnabled() {
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
    public void setGoogleCalendarDisabled() {
        //execute the command
        ConnectToGoogleCalendar.setGoogleCalendarDisabled();
        //get the result from the mode.txt
        String contents = getModeContent();
        //check
        assertNotNull(contents);
        assertEquals("Disabled" + System.getProperty("line.separator"), contents);
    }

    @Test
    public void setGoogleCalendarStatus() {
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
    }

    /**
     * Retrieves an status for testing purpose.
     *
     * @return String in the status file
     */
    private String getModeContent() {
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
        }
        //convert to string and return
        return contents.toString();
    }
    @Test
    public void getCalendar() throws IOException {
        //get a connection to Google
        final ConnectToGoogleCalendar connectToGoogleCalendar =
                new ConnectToGoogleCalendar();
        //get a service
        com.google.api.services.calendar.Calendar service =
                connectToGoogleCalendar.getCalendar();
        //create a calendar for testing
        Calendar expectedCalendar = new Calendar();
        expectedCalendar.setSummary("testCalendar");
        Calendar createdCalendar = service.calendars().insert(expectedCalendar).execute();
        //try to get the test calender from Google
        Calendar getFromGoogleCalendar =
                service.calendars().get(createdCalendar.getId()).execute();
        //check whether this is the test calender
        assertEquals(createdCalendar.getId(), getFromGoogleCalendar.getId());
        //remove the test calender from Google Calender
        service.calendars().delete(createdCalendar.getId()).execute();
        //create an expected exception
        //Try to retrieve the deleted exception
        //Confirm the an exception will be thrown
        assertThrows(
                com.google.api.client.googleapis.json.GoogleJsonResponseException.class, (
                ) -> service.calendars().get(createdCalendar.getId()).execute());
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
            public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                return new MockLowLevelHttpRequest() {
                    @Override
                    public LowLevelHttpResponse execute() throws IOException {
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
}
