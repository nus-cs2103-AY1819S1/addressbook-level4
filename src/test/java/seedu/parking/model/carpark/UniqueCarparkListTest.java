package seedu.parking.model.carpark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_ADDRESS_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_ADDRESS_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TAG_HOME;
import static seedu.parking.testutil.TypicalCarparks.ALFA;
import static seedu.parking.testutil.TypicalCarparks.BRAVO;
import static seedu.parking.testutil.TypicalCarparks.JULIETT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.parking.model.carpark.exceptions.CarparkNotFoundException;
import seedu.parking.model.carpark.exceptions.DuplicateCarparkException;
import seedu.parking.testutil.CarparkBuilder;

public class UniqueCarparkListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueCarparkList uniqueCarparkList = new UniqueCarparkList();

    @Test
    public void contains_nullCarpark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.contains(null);
    }

    @Test
    public void contains_carparkNotInList_returnsFalse() {
        assertFalse(uniqueCarparkList.contains(ALFA));
    }

    @Test
    public void contains_carparkInList_returnsTrue() {
        uniqueCarparkList.add(ALFA);
        assertTrue(uniqueCarparkList.contains(ALFA));
    }

    @Test
    public void contains_carparkWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCarparkList.add(JULIETT);
        Carpark editedJuliett = new CarparkBuilder(JULIETT).withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME)
                .build();
        assertTrue(uniqueCarparkList.contains(editedJuliett));
    }

    @Test
    public void add_nullCarpark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.add(null);
    }

    @Test
    public void add_duplicateCarpark_throwsDuplicateCarparkException() {
        uniqueCarparkList.add(ALFA);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.add(ALFA);
    }

    @Test
    public void setCarpark_nullTargetCarpark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarpark(null, ALFA);
    }

    @Test
    public void setCarpark_nullEditedCarpark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarpark(ALFA, null);
    }

    @Test
    public void setCarpark_targetCarparkNotInList_throwsCarparkNotFoundException() {
        thrown.expect(CarparkNotFoundException.class);
        uniqueCarparkList.setCarpark(ALFA, ALFA);
    }

    @Test
    public void setCarpark_editedCarparkIsSameCarpark_success() {
        uniqueCarparkList.add(ALFA);
        uniqueCarparkList.setCarpark(ALFA, ALFA);
        UniqueCarparkList expectedUniqueCarparkList = new UniqueCarparkList();
        expectedUniqueCarparkList.add(ALFA);
        assertEquals(expectedUniqueCarparkList, uniqueCarparkList);
    }

    @Test
    public void setCarpark_editedCarparkHasSameIdentity_success() {
        uniqueCarparkList.add(ALFA);
        Carpark editedAlfa = new CarparkBuilder(ALFA).withAddress(VALID_ADDRESS_KILO).withTags(VALID_TAG_HOME)
                .build();
        uniqueCarparkList.setCarpark(ALFA, editedAlfa);
        UniqueCarparkList expectedUniqueCarparkList = new UniqueCarparkList();
        expectedUniqueCarparkList.add(editedAlfa);
        assertEquals(expectedUniqueCarparkList, uniqueCarparkList);
    }

    @Test
    public void setCarpark_editedCarparkHasDifferentIdentity_success() {
        uniqueCarparkList.add(ALFA);
        uniqueCarparkList.setCarpark(ALFA, BRAVO);
        UniqueCarparkList expectedUniqueCarparkList = new UniqueCarparkList();
        expectedUniqueCarparkList.add(BRAVO);
        assertEquals(expectedUniqueCarparkList, uniqueCarparkList);
    }

    @Test
    public void setCarpark_editedCarparkHasNonUniqueIdentity_throwsDuplicateCarparkException() {
        uniqueCarparkList.add(ALFA);
        uniqueCarparkList.add(BRAVO);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.setCarpark(ALFA, BRAVO);
    }

    @Test
    public void remove_nullCarpark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.remove(null);
    }

    @Test
    public void remove_carparkDoesNotExist_throwsCarparkNotFoundException() {
        thrown.expect(CarparkNotFoundException.class);
        uniqueCarparkList.remove(ALFA);
    }

    @Test
    public void remove_existingCarpark_removesCarpark() {
        uniqueCarparkList.add(ALFA);
        uniqueCarparkList.remove(ALFA);
        UniqueCarparkList expectedUniqueCarparkList = new UniqueCarparkList();
        assertEquals(expectedUniqueCarparkList, uniqueCarparkList);
    }

    @Test
    public void setCarparks_nullUniqueCarparkList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarparks((UniqueCarparkList) null);
    }

    @Test
    public void setCarparks_uniqueCarparkList_replacesOwnListWithProvidedUniqueCarparkList() {
        uniqueCarparkList.add(ALFA);
        UniqueCarparkList expectedUniqueCarparkList = new UniqueCarparkList();
        expectedUniqueCarparkList.add(BRAVO);
        uniqueCarparkList.setCarparks(expectedUniqueCarparkList);
        assertEquals(expectedUniqueCarparkList, uniqueCarparkList);
    }

    @Test
    public void setCarparks_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarparks((List<Carpark>) null);
    }

    @Test
    public void setCarparks_list_replacesOwnListWithProvidedList() {
        uniqueCarparkList.add(ALFA);
        List<Carpark> carparkList = Collections.singletonList(BRAVO);
        uniqueCarparkList.setCarparks(carparkList);
        UniqueCarparkList expectedUniqueCarparkList = new UniqueCarparkList();
        expectedUniqueCarparkList.add(BRAVO);
        assertEquals(expectedUniqueCarparkList, uniqueCarparkList);
    }

    @Test
    public void setCarparks_listWithDuplicateCarparks_throwsDuplicateCarparkException() {
        List<Carpark> listWithDuplicateCarparks = Arrays.asList(ALFA, ALFA);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.setCarparks(listWithDuplicateCarparks);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCarparkList.asUnmodifiableObservableList().remove(0);
    }
}
