package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_WAIT_TIME_BOB;

import org.junit.Test;

import seedu.thanepark.testutil.UpdateRideDescriptorBuilder;

public class UpdateRideDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdateCommand.UpdateRideDescriptor descriptorWithSameValues = new UpdateCommand.UpdateRideDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        UpdateCommand.UpdateRideDescriptor editedAmy =
                new UpdateRideDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new UpdateRideDescriptorBuilder(DESC_AMY).withMaintenance(VALID_MAINTENANCE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new UpdateRideDescriptorBuilder(DESC_AMY).withWaitTime(VALID_WAIT_TIME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different thanepark -> returns false
        editedAmy = new UpdateRideDescriptorBuilder(DESC_AMY).withAddress(VALID_ZONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new UpdateRideDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
