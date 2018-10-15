package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author denzelchung
public class PictureTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Picture(null));
    }

    @Test
    public void constructor_invalidPicture_throwsIllegalArgumentException() {
        String invalidPicture = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Picture(invalidPicture));
    }

    @Test
    public void isValidPicture() {
        // null picture
        Assert.assertThrows(NullPointerException.class, () -> Picture.isValidPicture(null));

        // invalid pictures
        assertFalse(Picture.isValidPicture("")); // empty string
        assertFalse(Picture.isValidPicture(" ")); // spaces only
        assertFalse(Picture.isValidPicture("/images/test.exe")); // path not ending with jpg/png
        assertFalse(Picture.isValidPicture("C:/Documents/Pictures/test.png")); // invalid path
        assertFalse(Picture.isValidPicture("https://")); // invalid url

        // valid pictures
        assertTrue(Picture.isValidPicture("/images/test.jpg")); // jpg
        assertTrue(Picture.isValidPicture("/images/test.png")); // png
        assertTrue(Picture.isValidPicture("https://cdn.business2community.com"
            + "/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")); // url image
    }
}
