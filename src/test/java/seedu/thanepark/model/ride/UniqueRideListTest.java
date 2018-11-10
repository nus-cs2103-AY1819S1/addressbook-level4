package seedu.thanepark.model.ride;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_BOB;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.thanepark.model.ride.exceptions.DuplicateRideException;
import seedu.thanepark.model.ride.exceptions.RideNotFoundException;
import seedu.thanepark.testutil.RideBuilder;

public class UniqueRideListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRideList uniqueRideList = new UniqueRideList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueRideList.contains(ACCELERATOR));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueRideList.add(ACCELERATOR);
        assertTrue(uniqueRideList.contains(ACCELERATOR));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRideList.add(ACCELERATOR);
        Ride editedAlice = new RideBuilder(ACCELERATOR).withAddress(VALID_ZONE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueRideList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueRideList.add(ACCELERATOR);
        thrown.expect(DuplicateRideException.class);
        uniqueRideList.add(ACCELERATOR);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.setRide(null, ACCELERATOR);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.setRide(ACCELERATOR, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(RideNotFoundException.class);
        uniqueRideList.setRide(ACCELERATOR, ACCELERATOR);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueRideList.add(ACCELERATOR);
        uniqueRideList.setRide(ACCELERATOR, ACCELERATOR);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(ACCELERATOR);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueRideList.add(ACCELERATOR);
        Ride editedAlice = new RideBuilder(ACCELERATOR).withAddress(VALID_ZONE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueRideList.setRide(ACCELERATOR, editedAlice);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(editedAlice);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueRideList.add(ACCELERATOR);
        uniqueRideList.setRide(ACCELERATOR, BOB);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(BOB);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueRideList.add(ACCELERATOR);
        uniqueRideList.add(BOB);
        thrown.expect(DuplicateRideException.class);
        uniqueRideList.setRide(ACCELERATOR, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(RideNotFoundException.class);
        uniqueRideList.remove(ACCELERATOR);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueRideList.add(ACCELERATOR);
        uniqueRideList.remove(ACCELERATOR);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.setRides((UniqueRideList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueRideList.add(ACCELERATOR);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(BOB);
        uniqueRideList.setRides(expectedUniqueRideList);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.setRides((List<Ride>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueRideList.add(ACCELERATOR);
        List<Ride> rideList = Collections.singletonList(BOB);
        uniqueRideList.setRides(rideList);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(BOB);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Ride> listWithDuplicateRides = Arrays.asList(ACCELERATOR, ACCELERATOR);
        thrown.expect(DuplicateRideException.class);
        uniqueRideList.setRides(listWithDuplicateRides);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRideList.asUnmodifiableObservableList().remove(0);
    }
}
