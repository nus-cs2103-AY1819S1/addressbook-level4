package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA3220;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditPersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_MA2101);
        assertTrue(DESC_MA2101.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MA2101.equals(DESC_MA2101));

        // null -> returns false
        assertFalse(DESC_MA2101.equals(null));

        // different types -> returns false
        assertFalse(DESC_MA2101.equals(5));

        // different values -> returns false
        assertFalse(DESC_MA2101.equals(DESC_MA3220));

        // different event name -> returns false
        EditEventDescriptor editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101)
                .withEventName(VALID_EVENT_NAME_MA3220).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));

        // different start date time -> returns false
        editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101).withStartDateTime(VALID_START_DATETIME_MA3220).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));

        // different end date time -> returns false
        editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101).withEndDateTime(VALID_END_DATETIME_MA3220).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));

        // different description -> returns false
        editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101).withDescription(VALID_DESCRIPTION_MA3220).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));

        // different priority -> returns false
        editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101).withPriority(VALID_PRIORITY_MA3220).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));

        // different venue -> returns false
        editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101).withVenue(VALID_VENUE_MA3220).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));

        // different repeat type -> returns false
        editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101).withRepeatType(VALID_REPEAT_TYPE_MA3220).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));

        // different repeat until date time -> returns false
        editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101)
                .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA3220).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));

        // different tags -> returns false
        editedMA2101 = new EditEventDescriptorBuilder(DESC_MA2101).withTags(VALID_TAG_SCHOOL).build();
        assertFalse(DESC_MA2101.equals(editedMA2101));
    }
}
