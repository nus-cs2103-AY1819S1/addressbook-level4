package seedu.address.model.google;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import com.google.auth.Credentials;
import com.google.auth.oauth2.UserCredentials;
import com.google.common.collect.ImmutableList;
import com.google.photos.library.v1.PhotosLibraryClient;
import com.google.photos.library.v1.PhotosLibrarySettings;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoginStatusEvent;
import seedu.address.commons.util.FileUtil;

//@@author chivent

/**
 * Factory to create instance of PhotosLibraryClient
 */
public class PhotosLibraryClientFactory {

    public static final File DATA_STORE = Paths.get("./src/main/resources/user_credentials").toFile();

    //to be created on testing. prevents tests from launching login process
    public static final File TEST_FILE = Paths.get("./src/main/resources/user_credentials/TEST_FILE.txt").toFile();

    //to be created on login process start and deleted only on login process end. prevents unnecessary redirects
    public static final File BLOCKER = Paths.get("./src/main/resources/user_credentials/BLOCKER.txt").toFile();

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPE_LIST =
            ImmutableList.of(
                    "https://www.googleapis.com/auth/photoslibrary",
                    "https://www.googleapis.com/auth/photoslibrary.sharing",
                    "email");

    private static final String CREDENTIAL_FILE = "client_credentials.json";

    private PhotosLibraryClientFactory() {
    }

    /**
     * Creates and returns a new PhotoHandler
     *
     * @return PhotoHandler
     * @throws IOException              when files cannot be read
     * @throws GeneralSecurityException when there is an error with authentication
     */
    public static PhotoHandler createClient() throws IOException, GeneralSecurityException {
        FileUtil.createDirectoriesIfMissing(DATA_STORE);

        //@@author chivent-reused
        //Reused from https://github.com/google/java-photoslibrary/blob/master/sample/src/main/
        //java/com/google/photos/library/sample/demos/AlbumDemo.java with minor modifications
        DataStoreFactory dataStoreFactory = new FileDataStoreFactory(DATA_STORE);

        InputStream credentialFile = PhotosLibraryClientFactory
                .class.getClassLoader().getResourceAsStream(CREDENTIAL_FILE);

        // load designated client secret/id
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        JSON_FACTORY, new InputStreamReader(credentialFile));

        String clientSecret = clientSecrets.getDetails().getClientSecret();
        String clientId = clientSecrets.getDetails().getClientId();

        // TODO: v2.0 develop a more intuitive way of avoiding this issue
        // Currently BLOCKER.TXT is created because Google automatically creates a blank credential file when login,
        // is launched even if user does not actually login. This causes our app to always redirect to google login
        // whenever a blank credential file is found on start up.
        try {
            FileUtil.createIfMissing(BLOCKER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TEST_FILE.exists()) {
            //google standard authorization flow
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                    clientSecrets, SCOPE_LIST).setDataStoreFactory(dataStoreFactory).build();

            // Credential is a google construct that wraps the access token and helps you to refresh periodically
            // AuthorizationCodeInstalledApp is another google standard that helps persist user end credentials
            Credential credential = new AuthorizationCodeInstalledApp(flow,
                    new LocalServerReceiver()).authorize("user");

            //UserCredentials is a specific credential type that stores user specific credentials
            UserCredentials userCredentials = UserCredentials.newBuilder().setClientId(clientId)
                    .setClientSecret(clientSecret).setRefreshToken(credential.getRefreshToken()).build();

            credentialFile.close();
            FileUtil.deleteIfAvaliable(BLOCKER);
            return new PhotoHandler(createPhotosLibraryClient(userCredentials), getUserEmail(credential));
        } else {
            FileUtil.deleteIfAvaliable(BLOCKER);
            return null;
        }
    }

    /**
     * Creates a PhotosLibraryClient instance from credentials
     *
     * @param credentials
     * @return PhotosLibraryClient new PhotosLibraryClient instance
     */
    private static PhotosLibraryClient createPhotosLibraryClient(Credentials credentials) throws IOException {
        PhotosLibrarySettings libSettings = PhotosLibrarySettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
        return PhotosLibraryClient.initialize(libSettings);
    }

    /**
     * Gets user's email from a Google+ instance
     *
     * @param credential credentials to create google+ instance with
     * @return a String of user email
     */
    private static String getUserEmail(Credential credential) throws GeneralSecurityException, IOException {
        Plus plus = new Plus.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential).setApplicationName("Piconso").build();

        List<Person.Emails> emails = plus.people().get("me").execute().getEmails();

        EventsCenter.getInstance().post(new LoginStatusEvent(emails.get(0).getValue()));
        return emails.get(0).getValue();
    }

    /**
     * Checks if a user has storedCredentials (did not logout previously), and auto log ins user if true.
     *
     * @return a PhotoHandler instance if user has storedCredentials, else null
     */
    public static PhotoHandler loginUserIfPossible() throws IOException, GeneralSecurityException {
        return checkUserLogin() ? createClient() : null;
    }

    /**
     * Logs user out of currently logged in account
     */
    public static boolean logoutUserIfPossible() {
        boolean userLoggedOut;
        if (userLoggedOut = checkUserLogin()) {
            File[] listFiles = DATA_STORE.listFiles();
            for (File file : listFiles) {
                file.delete();

            }
        }
        return userLoggedOut;
    }

    /**
     * Checks if a user has storedCredentials (did not logout previously).
     *
     * @return true if user has storedCredentials, else null
     */
    public static boolean checkUserLogin() {
        return new File(DATA_STORE, StoredCredential.DEFAULT_DATA_STORE_ID).exists();
    }
}


