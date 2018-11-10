package seedu.thanepark.model.ride;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_WAIT_TIME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_BOB;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.thanepark.testutil.RideBuilder;

public class RideTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Ride ride = new RideBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        ride.getTags().remove(0);
    }

    @Test
    public void isSameRide() {
        // same object -> returns true
        assertTrue(ACCELERATOR.isSameRide(ACCELERATOR));

        // null -> returns false
        assertFalse(ACCELERATOR.isSameRide(null));

        // different phone and email but same name -> returns true
        Ride editedAlice = new RideBuilder(ACCELERATOR)
                .withMaintenance(VALID_MAINTENANCE_BOB)
                .withWaitTime(VALID_WAIT_TIME_BOB)
                .build();
        assertTrue(ACCELERATOR.isSameRide(editedAlice));

        // different name -> returns false
        editedAlice = new RideBuilder(ACCELERATOR).withName(VALID_NAME_BOB).build();
        assertFalse(ACCELERATOR.isSameRide(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new RideBuilder(ACCELERATOR).withWaitTime(VALID_WAIT_TIME_BOB).withAddress(VALID_ZONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ACCELERATOR.isSameRide(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new RideBuilder(ACCELERATOR).withMaintenance(VALID_MAINTENANCE_BOB).withAddress(VALID_ZONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ACCELERATOR.isSameRide(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new RideBuilder(ACCELERATOR).withAddress(VALID_ZONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ACCELERATOR.isSameRide(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Ride aliceCopy = new RideBuilder(ACCELERATOR).build();
        assertTrue(ACCELERATOR.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ACCELERATOR.equals(ACCELERATOR));

        // null -> returns false
        assertFalse(ACCELERATOR.equals(null));

        // different type -> returns false
        assertFalse(ACCELERATOR.equals(5));

        // different ride -> returns false
        assertFalse(ACCELERATOR.equals(BOB));

        // different name -> returns false
        Ride editedAlice = new RideBuilder(ACCELERATOR).withName(VALID_NAME_BOB).build();
        assertFalse(ACCELERATOR.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new RideBuilder(ACCELERATOR).withMaintenance(VALID_MAINTENANCE_BOB).build();
        assertFalse(ACCELERATOR.equals(editedAlice));

        // different email -> returns false
        editedAlice = new RideBuilder(ACCELERATOR).withWaitTime(VALID_WAIT_TIME_BOB).build();
        assertFalse(ACCELERATOR.equals(editedAlice));

        // different thanepark -> returns false
        editedAlice = new RideBuilder(ACCELERATOR).withAddress(VALID_ZONE_BOB).build();
        assertFalse(ACCELERATOR.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new RideBuilder(ACCELERATOR).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ACCELERATOR.equals(editedAlice));
    }
}
