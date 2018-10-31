package seedu.address.calendar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;

import seedu.address.model.appointment.Appointment;

/**
 * Manages all information transaction between doctor's google calendar and HealthBook.
 */
public class CalendarManager {
    private static final String APPLICATION_NAME = "HealthBook";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param httpTransport The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public Credential getCredentials(final NetHttpTransport httpTransport, String userName) throws IOException {
        /// Load client secrets.
        InputStream in = getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH);
        InputStreamReader inStreamReader = new InputStreamReader(in);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, inStreamReader);

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("online")
            .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(userName);
    }

    /**
     * Register the oAuth token for the respective doctors.
     */
    /** */
    public void registerDoctor(String userName) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport, userName))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Adds an appointment event to the primary calendar of the respective doctors.
     * This includes an encoded calendar event Id based on the {@code appointment}'s id
     */
    /** */
    public void addAppointment(String userName, Appointment appointment) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport, userName))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Configure Start Date and Time
        LocalDateTime appointmentDateTime = appointment.getDateTime();
        ZonedDateTime zdt = appointmentDateTime.atZone(ZoneId.systemDefault());
        Date startOutput = Date.from(zdt.toInstant());
        Date endOutput = new Date(startOutput.getTime() + 3600 * 1000);
        EventDateTime startDateTime = new EventDateTime();
        startDateTime.setDateTime(new DateTime(startOutput));
        EventDateTime endDateTime = new EventDateTime();
        endDateTime.setDateTime(new DateTime(endOutput));

        // Configure Event Id
        String eventId = "healthbook" + Integer.toString(appointment.getAppointmentId());
        String eventIdEncoded = BaseEncoding.base32Hex().encode(eventId.getBytes(Charsets.US_ASCII)).toLowerCase();

        // Configure Event
        Event appointmentToAdd = new Event();
        appointmentToAdd.setStart(startDateTime);
        appointmentToAdd.setEnd(endDateTime);
        appointmentToAdd.setId(eventIdEncoded);
        appointmentToAdd.setDescription("Comments: " + appointment.getComments());
        appointmentToAdd.setSummary("Appointment with " + appointment.getPatient());

        // Find existing event with same eventId. Deleted eventId is not deleted entirely but cancelled and hidden
        Event event;
        try {
            event = service.events().get("primary", eventIdEncoded).execute();
        } catch (GoogleJsonResponseException e) {
            event = null;
        }

        if (event == null) {
            // Insert new Event
            service.events().insert("primary", appointmentToAdd).execute();
        } else {
            service.events().update("primary", eventIdEncoded, appointmentToAdd).execute();
        }
    }

    /**
     * Delete an appointment event to the primary calendar of the respective doctors.
     * Delete using an encoded calendar event Id based on the {@code appointment}'s id
     */
    /** */
    public void deleteAppointment(String userName, Appointment appointment)
            throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport, userName))
                .setApplicationName(APPLICATION_NAME)
                .build();

        // Configure Event Id
        String eventId = "healthbook" + Integer.toString(appointment.getAppointmentId());
        String eventIdEncoded = BaseEncoding.base32Hex().encode(eventId.getBytes(Charsets.US_ASCII)).toLowerCase();

        // Delete Event
        service.events().delete("primary", eventIdEncoded).execute();
    }
}
