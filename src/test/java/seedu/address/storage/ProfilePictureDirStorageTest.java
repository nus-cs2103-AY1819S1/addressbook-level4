package seedu.address.storage;

import seedu.address.model.UserPrefs;
import seedu.address.storage.ProfilePictureDirStorage;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

//@@author javenseow
public class ProfilePictureDirStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readImageFiles_nullFile_throwsIllegalArgumentException() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        readProfilePicture(null);
    }

    private BufferedImage readProfilePicture(File file) throws IOException{
        UserPrefs userPrefs = new UserPrefs();
        try {
            return new ProfilePictureDirStorage(userPrefs.getProfilePicturePath(), userPrefs.getOutputProfilePicturePath())
                    .readProfilePicture(file);
        } catch (IOException e) {
            throw e;
        }
    }
}