package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEETING;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isSameTag() {
        Tag appointmentTag = new Tag(VALID_TAG_APPOINTMENT);
        Tag meetingTag = new Tag(VALID_TAG_MEETING);

        // same object -> returns true
        assertTrue(appointmentTag.isSameTag(appointmentTag));

        // null -> returns false
        assertFalse(appointmentTag.isSameTag(null));

        // different string values -> returns false
        assertFalse(appointmentTag.isSameTag(meetingTag));

        // same string values -> returns true
        Tag appointmentTagCopy = new Tag(VALID_TAG_APPOINTMENT);
        assertTrue(appointmentTag.isSameTag(appointmentTagCopy));

        // same string values, different case -> returns true
        Tag appointmentTagUpperCase = new Tag(VALID_TAG_APPOINTMENT.toUpperCase());
        assertTrue(appointmentTag.isSameTag(appointmentTagUpperCase));
    }

}
