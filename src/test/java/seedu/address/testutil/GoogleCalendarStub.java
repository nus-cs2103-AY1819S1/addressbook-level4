package seedu.address.testutil;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import seedu.address.calendar.GoogleCalendar;
import seedu.address.model.appointment.Appointment;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * A Google Calendar stub
 */
public class GoogleCalendarStub implements GoogleCalendar {

    public GoogleCalendarStub() {}

    @Override
    public Credential getCredentials(NetHttpTransport httpTransport, String userName)
            throws IOException {
        return null;
    }

    @Override
    public void registerDoctor(String userName)
            throws IOException, GeneralSecurityException {}

    @Override
    public void addAppointment(String userName, Appointment appointment)
            throws IOException, GeneralSecurityException {}

    @Override
    public void deleteAppointment(String userName, Appointment appointment)
            throws IOException, GeneralSecurityException {}
}
