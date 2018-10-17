package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_JULIETT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOME;
import static seedu.address.testutil.TypicalCarparks.ALFA;
import static seedu.address.testutil.TypicalCarparks.BRAVO;
import static seedu.address.testutil.TypicalCarparks.JULIETT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.UniqueCarparkList;
import seedu.address.model.carpark.exceptions.CarparkNotFoundException;
import seedu.address.model.carpark.exceptions.DuplicateCarparkException;
import seedu.address.testutil.CarparkBuilder;

public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueCarparkList uniqueCarparkList = new UniqueCarparkList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueCarparkList.contains(ALFA));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueCarparkList.add(ALFA);
        assertTrue(uniqueCarparkList.contains(ALFA));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCarparkList.add(JULIETT);
        Carpark editedJuliett = new CarparkBuilder(JULIETT).withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME)
                .build();
        assertTrue(uniqueCarparkList.contains(editedJuliett));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueCarparkList.add(ALFA);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.add(ALFA);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarpark(null, ALFA);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarpark(ALFA, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(CarparkNotFoundException.class);
        uniqueCarparkList.setCarpark(ALFA, ALFA);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueCarparkList.add(ALFA);
        uniqueCarparkList.setCarpark(ALFA, ALFA);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(ALFA);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueCarparkList.add(ALFA);
        Carpark editedAlfa = new CarparkBuilder(ALFA).withAddress(VALID_ADDRESS_KILO).withTags(VALID_TAG_HOME)
                .build();
        uniqueCarparkList.setCarpark(ALFA, editedAlfa);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(editedAlfa);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueCarparkList.add(ALFA);
        uniqueCarparkList.setCarpark(ALFA, BRAVO);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(BRAVO);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCarparkList.add(ALFA);
        uniqueCarparkList.add(BRAVO);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.setCarpark(ALFA, BRAVO);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(CarparkNotFoundException.class);
        uniqueCarparkList.remove(ALFA);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueCarparkList.add(ALFA);
        uniqueCarparkList.remove(ALFA);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarparks((UniqueCarparkList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueCarparkList.add(ALFA);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(BRAVO);
        uniqueCarparkList.setCarparks(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarparks((List<Carpark>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueCarparkList.add(ALFA);
        List<Carpark> personList = Collections.singletonList(BRAVO);
        uniqueCarparkList.setCarparks(personList);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(BRAVO);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Carpark> listWithDuplicatePersons = Arrays.asList(ALFA, ALFA);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.setCarparks(listWithDuplicatePersons);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCarparkList.asUnmodifiableObservableList().remove(0);
    }
}
