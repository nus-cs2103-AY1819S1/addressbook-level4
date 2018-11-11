package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author javenseow
public class ProfilePictureTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProfilePicture(null));
    }

    @Test
    public void constructor_invalidProfilePicture_throwsIllegalArgumentException() {
        Path invalidProfilePicture = Paths.get("");
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProfilePicture(invalidProfilePicture));
    }

    @Test
    public void isValidRoom() {
        // null room
        Assert.assertThrows(NullPointerException.class, () ->ProfilePicture.isValidProfilePicture(null));

        // invalid profile picture
        assertFalse(ProfilePicture.isValidProfilePicture(Paths.get(""))); // empty path
        assertFalse(ProfilePicture.isValidProfilePicture(Paths.get("^"))); // only non-alphanumeric characters
        assertFalse(ProfilePicture.isValidProfilePicture(Paths.get("image.png"))); // image file is in '.png'

        // valid path
        assertTrue(ProfilePicture.isValidProfilePicture(Paths.get("image.jpg")));
    }
}
