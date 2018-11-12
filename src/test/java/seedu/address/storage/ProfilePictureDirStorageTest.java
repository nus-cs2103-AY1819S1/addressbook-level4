package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.UserPrefs;

//@@author javenseow
public class ProfilePictureDirStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readImageFile_nullFile_throwsIllegalArgumentException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        readProfilePicture(null);
    }

    @Test
    public void readImageFile_missingFile_throwsIoException() throws Exception {
        thrown.expect(IOException.class);
        readProfilePicture(new File("MissingFile.jpg"));
    }

    /**
     * Reads {@code file} into the {@code readProfilePicture} method of
     * {@code ProfilePictureDirStorage}.
     */
    private BufferedImage readProfilePicture(File file) throws IOException {
        UserPrefs userPrefs = new UserPrefs();
        try {
            return new ProfilePictureDirStorage(userPrefs.getProfilePicturePath(),
                userPrefs.getOutputProfilePicturePath()).readProfilePicture(file);
        } catch (IOException e) {
            throw e;
        }
    }
}
