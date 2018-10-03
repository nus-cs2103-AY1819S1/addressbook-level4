package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalCarparks.ALICE;
import static seedu.address.testutil.TypicalCarparks.BOB;

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
        assertFalse(uniqueCarparkList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueCarparkList.add(ALICE);
        assertTrue(uniqueCarparkList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCarparkList.add(ALICE);
        Carpark editedAlice = new CarparkBuilder(ALICE).withAddress(VALID_ADDRESS_2).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCarparkList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueCarparkList.add(ALICE);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarpark(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.setCarpark(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(CarparkNotFoundException.class);
        uniqueCarparkList.setCarpark(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueCarparkList.add(ALICE);
        uniqueCarparkList.setCarpark(ALICE, ALICE);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(ALICE);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueCarparkList.add(ALICE);
        Carpark editedAlice = new CarparkBuilder(ALICE).withAddress(VALID_ADDRESS_2).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueCarparkList.setCarpark(ALICE, editedAlice);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueCarparkList.add(ALICE);
        uniqueCarparkList.setCarpark(ALICE, BOB);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueCarparkList.add(ALICE);
        uniqueCarparkList.add(BOB);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.setCarpark(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCarparkList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(CarparkNotFoundException.class);
        uniqueCarparkList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueCarparkList.add(ALICE);
        uniqueCarparkList.remove(ALICE);
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
        uniqueCarparkList.add(ALICE);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(BOB);
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
        uniqueCarparkList.add(ALICE);
        List<Carpark> personList = Collections.singletonList(BOB);
        uniqueCarparkList.setCarparks(personList);
        UniqueCarparkList expectedUniquePersonList = new UniqueCarparkList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueCarparkList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Carpark> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateCarparkException.class);
        uniqueCarparkList.setCarparks(listWithDuplicatePersons);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCarparkList.asUnmodifiableObservableList().remove(0);
    }
}
