package seedu.address.calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;

import seedu.address.model.appointment.Appointment;

/**
 * The API of the Calendar component.
 */
public interface GoogleCalendar {
    /**
     * Creates an authorized Credential object.
     * @param httpTransport The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public Credential getCredentials(final NetHttpTransport httpTransport, String userName) throws IOException;

    /**
     * Register the oAuth token for the respective doctors.
     */
    /** */
    public void registerDoctor(String userName) throws IOException, GeneralSecurityException;

    /**
     * Adds an appointment event to the primary calendar of the respective doctors.
     * This includes an encoded calendar event Id based on the {@code appointment}'s id
     */
    /** */
    public void addAppointment(String userName, Appointment appointment) throws IOException, GeneralSecurityException;

    /**
     * Delete an appointment event to the primary calendar of the respective doctors.
     * Delete using an encoded calendar event Id based on the {@code appointment}'s id
     */
    public void deleteAppointment(String userName, Appointment appointment) throws IOException,
            GeneralSecurityException;
}
