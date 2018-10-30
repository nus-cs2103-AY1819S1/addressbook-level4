package seedu.address.model.ride;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RideStatusPredicateTest {

    @Test
    public void equals() {
        Status statusOpen = Status.OPEN;
        Status statusMaintenance = Status.MAINTENANCE;

        RideStatusPredicate predicateOpen = new RideStatusPredicate(statusOpen);
        RideStatusPredicate predicateOpenCopy = new RideStatusPredicate(statusOpen);
        RideStatusPredicate predicateMaintenance = new RideStatusPredicate(statusMaintenance);

        // same object -> returns true
        assertTrue(predicateOpen.equals(predicateOpen));

        // same value -> returns true
        assertTrue(predicateOpen.equals(predicateOpenCopy));

        // different type -> returns false
        assertFalse(predicateOpen.equals(1));

        // null -> returns false
        assertFalse(predicateOpen.equals(null));

        // different ride -> returns false
        assertFalse(predicateOpen.equals(predicateMaintenance));
    }
}
