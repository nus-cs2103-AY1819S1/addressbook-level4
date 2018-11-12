package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandOccasionTestUtil.DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONNAME_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_STUDY;

import org.junit.Test;

import seedu.address.testutil.OccasionDescriptorBuilder;

public class OccasionDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        OccasionDescriptor descriptorWithSameValues = new OccasionDescriptor(DESC_ONE);
        assertTrue(DESC_ONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ONE.equals(DESC_ONE));

        // null -> returns false
        assertFalse(DESC_ONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_ONE.equals(5));

        // different values -> returns false
        assertFalse(DESC_ONE.equals(OCCASIONNAME_DESC_ONE));

        // different occasion name -> returns false
        OccasionDescriptor editedAmy = new OccasionDescriptorBuilder().withOccasionName(VALID_OCCASIONNAME_ONE).build();
        assertFalse(DESC_ONE.equals(editedAmy));

        // different occasion date -> returns false
        editedAmy = new OccasionDescriptorBuilder().withOccasionDate(VALID_OCCASIONDATE_ONE).build();
        assertFalse(DESC_ONE.equals(editedAmy));

        // different occasion location -> returns false
        editedAmy = new OccasionDescriptorBuilder().withOccasionLocation(VALID_OCCASIONLOCATION_ONE).build();
        assertFalse(DESC_ONE.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new OccasionDescriptorBuilder().withTags(VALID_TAG_STUDY).build();
        assertFalse(DESC_ONE.equals(editedAmy));
    }
}
