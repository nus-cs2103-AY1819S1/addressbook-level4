package seedu.thanepark.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.model.Model.PREDICATE_SHOW_ALL_RIDES;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.BIG;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.thanepark.model.ride.RideContainsKeywordsPredicate;
import seedu.thanepark.testutil.ThaneParkBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasRide_nullRide_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRide(null);
    }

    @Test
    public void hasRide_rideNotInThanePark_returnsFalse() {
        assertFalse(modelManager.hasRide(ACCELERATOR));
    }

    @Test
    public void hasRide_rideInThanePark_returnsTrue() {
        modelManager.addRide(ACCELERATOR);
        assertTrue(modelManager.hasRide(ACCELERATOR));
    }

    @Test
    public void getFilteredRideList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRideList().remove(0);
    }

    @Test
    public void equals() {
        ThanePark thanePark = new ThaneParkBuilder().withRide(ACCELERATOR).withRide(BIG).build();
        ThanePark differentThanePark = new ThanePark();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(thanePark, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(thanePark, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different thanePark -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentThanePark, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ACCELERATOR.getName().fullName.split("\\s+");
        modelManager.updateFilteredRideList(new RideContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(thanePark, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setThaneParkFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(thanePark, differentUserPrefs)));
    }
}
