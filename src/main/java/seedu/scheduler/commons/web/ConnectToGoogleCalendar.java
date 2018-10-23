package seedu.scheduler.commons.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;

import seedu.scheduler.logic.commands.GetGoogleCalendarEventsCommand;

/**
 * Methods related to the connection with Google Calendar.
 */
public class ConnectToGoogleCalendar {
    /**
     * Global instance of the scopes required.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials/credentials.json";

    private boolean googleCalendarEnabled = false;

    public boolean isGoogleCalendarEnabled() {
        return googleCalendarEnabled;
    }

    public void setGoogleCalendarEnabled() {
        this.googleCalendarEnabled = true;
    }


    public Calendar getCalendar() {
        NetHttpTransport httpTransport = getNetHttpTransport();

        // Build Google calender service object.
        Calendar service = null;
        try {
            //re-direct the stdout
            //One of the Google APIs outputs to stdout which causes warning
            System.setOut(new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    //no code needed here
                }
            }));

            service = new Calendar.Builder(httpTransport, JSON_FACTORY,
                    getCredentials(httpTransport))
                    .setApplicationName("iScheduler Xs Max")
                    .build();
            //re-direct back to the stdout
            System.setOut(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return service;
    }

    public NetHttpTransport getNetHttpTransport() {
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
        InputStream in = GetGoogleCalendarEventsCommand.class
                .getResourceAsStream(CREDENTIALS_FILE_PATH);
        return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    }

    /**
     * To check whether internet is available.
     *
     * @return true if available, false otherwise.
     */
    public static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
