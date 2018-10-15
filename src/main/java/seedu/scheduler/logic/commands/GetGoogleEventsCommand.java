package seedu.scheduler.logic.commands;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;

/**
 * Get events from google calendar.
 */
public class GetGoogleEventsCommand extends Command {
    public static final String COMMAND_WORD = "ggEvents";
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
        return new CommandResult(MESSAGE_GGEVENTS_SUCCESS);
    }

    private NetHttpTransport getNetHttpTransport() {
        // Build a new authorized API client service.
        NetHttpTransport HTTP_TRANSPORT = null;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return HTTP_TRANSPORT;
    }


}
