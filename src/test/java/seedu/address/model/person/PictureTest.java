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
    public void constructor_invalidPicture() {
        String invalidPicture = "";
        assertTrue(new Picture(invalidPicture).picture == Picture.DEFAULT_PICTURE_URL.getPath());
    }

    @Test
    public void isValidPicture() {
        // null picture
        Assert.assertThrows(NullPointerException.class, () -> Picture.isValidPicture(null));

        // invalid pictures
        assertFalse(Picture.isValidPicture("")); // empty string
        assertFalse(Picture.isValidPicture("/images/test.exe")); // path not ending with jpg/png
        assertFalse(Picture.isValidPicture("C:/Documents/Pictures/test.png")); // invalid path
        assertFalse(Picture.isValidPicture("/images/test.jpg"));
        assertFalse(Picture.isValidPicture("/images/test.png"));

        // valid picture
        assertTrue(Picture.isValidPicture(Picture.DEFAULT_PICTURE_URL.getPath()));
    }
}
