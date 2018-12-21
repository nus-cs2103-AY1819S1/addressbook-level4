
package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCAL_FILE_NONIMAGE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCAL_FILE_NONIMAGE_WITH_IMAGE_FILETYPE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_BIGGER_THAN_5MB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_JPG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_PNG;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProfilePhotoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ProfilePhoto(null));
    }

    @Test
    public void constructorForXml_invalidPath_setPathToDefaultPath() {
        String invalidName =
                "RandomRandomThisIsTooLongToBeValidButToBeSureLetsMakeThisLonger_MakePicturesGreatAgain!?>>:?"
                        + "SurelyThisCantBeValidRight?WellLifeIsUnpredictableSoToBeSafeIWillMakeThisEvenLonger";
        // if input file invalid, then we will set path to be Picture.DEFAULT_PATH. In this case, both constructors
        // are the same
        assertEquals(new ProfilePhoto(invalidName), new ProfilePhoto());
    }

    @Test
    public void constructor_invalidPath_throwsIllegalArgumentException() {
        String invalidName =
                "RandomRandomThisIsTooLongToBeValidButToBeSureLetsMakeThisLonger_MakePicturesGreatAgain!?>>:?"
                        + "SurelyThisCantBeValidRight?WellLifeIsUnpredictableSoToBeSafeIWillMakeThisEvenLonger";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ProfilePhoto(invalidName,
                "randomNameDoesntMatter"));
    }



    @Test
    public void isValidPath() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ProfilePhoto.isValidPath(null));

        // invalid name
        assertFalse(ProfilePhoto.isValidPath("")); // empty string
        assertFalse(ProfilePhoto.isValidPath(" ")); // spaces only
        // points to non image file with image filetype (jpg)
        assertFalse(ProfilePhoto.isValidPath(INVALID_LOCAL_FILE_NONIMAGE_WITH_IMAGE_FILETYPE));
        assertFalse(ProfilePhoto.isValidPath(INVALID_LOCAL_FILE_NONIMAGE)); // points to non image file
        assertFalse(ProfilePhoto.isValidPath(VALID_LOCAL_IMAGE_BIGGER_THAN_5MB)); // points to image file > 5mb

        // valid name
        assertTrue(ProfilePhoto.isValidPath(VALID_LOCAL_IMAGE_PNG)); // valid png file
        assertTrue(ProfilePhoto.isValidPath(VALID_LOCAL_IMAGE_JPG)); // valid jpg file
    }

}
