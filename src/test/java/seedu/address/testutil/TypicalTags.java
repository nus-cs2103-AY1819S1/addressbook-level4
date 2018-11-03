package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEETING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {

    // Manually added - Tag's details found in {@code CommandTestUtil}
    public static final Tag APPOINTMENT_TAG = new Tag(VALID_TAG_APPOINTMENT);
    public static final Tag MEETING_TAG = new Tag(VALID_TAG_MEETING);

    private TypicalTags() {} // prevents instantiation

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(APPOINTMENT_TAG, MEETING_TAG));
    }
}
