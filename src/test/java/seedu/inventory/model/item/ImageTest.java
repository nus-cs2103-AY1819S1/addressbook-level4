package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.inventory.testutil.Assert;

public class ImageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Image(null));
    }

    @Test
    public void constructor_invalidImage_throwsIllegalArgumentException() {
        String invalidImage = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Image(invalidImage));
    }

    @Test
    public void isValidImage() {
        // null inventory
        Assert.assertThrows(NullPointerException.class, () -> Image.isValidImage(null));

        // invalid images
        assertFalse(Image.isValidImage("")); // empty string
        assertFalse(Image.isValidImage(" ")); // spaces only
        assertFalse(Image.isValidImage("docs")); // directory, not a file
        assertFalse(Image.isValidImage("docs/AboutUs.adoc")); // not an image file
        assertFalse(Image.isValidImage("test.jpg")); // non-existing image file

        // valid images
        assertTrue(Image.isValidImage("docs/images/iphone.jpg")); // jpg image
        assertTrue(Image.isValidImage("docs/images/yao-tx.png")); // png image
    }
}
