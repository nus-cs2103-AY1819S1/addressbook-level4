package seedu.thanepark.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_BOB;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.exceptions.DuplicateRideException;
import seedu.thanepark.testutil.RideBuilder;

public class ThaneParkTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ThanePark thanePark = new ThanePark();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), thanePark.getRideList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        thanePark.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        ThanePark newData = getTypicalThanePark();
        thanePark.resetData(newData);
        assertEquals(newData, thanePark);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two rides with the same identity fields
        Ride editedAlice = new RideBuilder(ACCELERATOR).withAddress(VALID_ZONE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Ride> newRides = Arrays.asList(ACCELERATOR, editedAlice);
        ThaneParkStub newData = new ThaneParkStub(newRides);

        thrown.expect(DuplicateRideException.class);
        thanePark.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        thanePark.hasRide(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(thanePark.hasRide(ACCELERATOR));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        thanePark.addRide(ACCELERATOR);
        assertTrue(thanePark.hasRide(ACCELERATOR));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        thanePark.addRide(ACCELERATOR);
        Ride editedAlice = new RideBuilder(ACCELERATOR).withAddress(VALID_ZONE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(thanePark.hasRide(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        thanePark.getRideList().remove(0);
    }

    /**
     * A stub ReadOnlyThanePark whose rides list can violate interface constraints.
     */
    private static class ThaneParkStub implements ReadOnlyThanePark {
        private final ObservableList<Ride> rides = FXCollections.observableArrayList();

        ThaneParkStub(Collection<Ride> rides) {
            this.rides.setAll(rides);
        }

        @Override
        public ObservableList<Ride> getRideList() {
            return rides;
        }
    }

}
