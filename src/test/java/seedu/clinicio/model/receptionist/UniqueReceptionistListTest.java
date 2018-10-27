package seedu.address.model.receptionist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ALAN;
import static seedu.address.testutil.TypicalPersons.ALAN;
import static seedu.address.testutil.TypicalPersons.DAISY;
import static seedu.address.testutil.TypicalPersons.FRANK;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.HashUtil;

import seedu.address.model.receptionist.exceptions.DuplicateReceptionistException;
import seedu.address.model.receptionist.exceptions.ReceptionistNotFoundException;
import seedu.address.testutil.ReceptionistBuilder;

//@@author jjlee050
public class UniqueReceptionistListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueReceptionistList uniqueReceptionistList = new UniqueReceptionistList();

    @Test
    public void contains_nullReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReceptionistList.contains(null);
    }

    @Test
    public void contains_receptionistNotInList_returnsFalse() {
        assertFalse(uniqueReceptionistList.contains(ALAN));
    }

    @Test
    public void contains_receptionistInList_returnsTrue() {
        uniqueReceptionistList.add(ALAN);
        assertTrue(uniqueReceptionistList.contains(ALAN));
    }

    @Test
    public void contains_receptionistWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReceptionistList.add(ALAN);
        Receptionist editedAlan = new ReceptionistBuilder(ALAN).withName(VALID_NAME_ALAN)
                .build();
        assertTrue(uniqueReceptionistList.contains(editedAlan));
    }

    @Test
    public void add_nullReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReceptionistList.add(null);
    }

    @Test
    public void add_duplicateReceptionist_throwsDuplicateReceptionistException() {
        uniqueReceptionistList.add(ALAN);
        thrown.expect(DuplicateReceptionistException.class);
        uniqueReceptionistList.add(ALAN);
    }

    @Test
    public void setReceptionist_nullTargetReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReceptionistList.setReceptionist(null, ALAN);
    }

    @Test
    public void setReceptionist_nullEditedReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReceptionistList.setReceptionist(ALAN, null);
    }

    @Test
    public void setReceptionist_targetReceptionistNotInList_throwsReceptionistNotFoundException() {
        thrown.expect(ReceptionistNotFoundException.class);
        uniqueReceptionistList.setReceptionist(ALAN, ALAN);
    }

    @Test
    public void setReceptionist_editedReceptionistIsSameReceptionist_success() {
        uniqueReceptionistList.add(ALAN);
        uniqueReceptionistList.setReceptionist(ALAN, ALAN);
        UniqueReceptionistList expectedUniqueReceptionistList = new UniqueReceptionistList();
        expectedUniqueReceptionistList.add(ALAN);
        assertEquals(expectedUniqueReceptionistList, uniqueReceptionistList);
    }

    @Test
    public void setReceptionist_editedReceptionistHasSameIdentity_success() {
        uniqueReceptionistList.add(ALAN);
        Receptionist editedAlan = new ReceptionistBuilder(ALAN).withPassword(
                HashUtil.hashToString(VALID_PASSWORD_ALAN),
                true).build();
        uniqueReceptionistList.setReceptionist(ALAN, editedAlan);
        UniqueReceptionistList expectedUniqueReceptionistList = new UniqueReceptionistList();
        expectedUniqueReceptionistList.add(editedAlan);
        assertEquals(expectedUniqueReceptionistList, uniqueReceptionistList);
    }

    @Test
    public void setReceptionist_editedReceptionistHasDifferentIdentity_success() {
        uniqueReceptionistList.add(ALAN);
        uniqueReceptionistList.setReceptionist(ALAN, FRANK);
        UniqueReceptionistList expectedUniqueReceptionistList = new UniqueReceptionistList();
        expectedUniqueReceptionistList.add(FRANK);
        assertEquals(expectedUniqueReceptionistList, uniqueReceptionistList);
    }

    @Test
    public void setReceptionist_editedReceptionistHasNonUniqueIdentity_throwsDuplicateReceptionistException() {
        uniqueReceptionistList.add(ALAN);
        uniqueReceptionistList.add(FRANK);
        thrown.expect(DuplicateReceptionistException.class);
        uniqueReceptionistList.setReceptionist(ALAN, FRANK);
    }

    @Test
    public void remove_nullReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReceptionistList.remove(null);
    }

    @Test
    public void remove_receptionistDoesNotExist_throwsReceptionistNotFoundException() {
        thrown.expect(ReceptionistNotFoundException.class);
        uniqueReceptionistList.remove(ALAN);
    }

    @Test
    public void remove_existingReceptionist_removesReceptionist() {
        uniqueReceptionistList.add(ALAN);
        uniqueReceptionistList.remove(ALAN);
        UniqueReceptionistList expectedUniqueReceptionistList = new UniqueReceptionistList();
        assertEquals(expectedUniqueReceptionistList, uniqueReceptionistList);
    }

    @Test
    public void setReceptionists_nullUniqueReceptionistList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReceptionistList.setReceptionists((UniqueReceptionistList) null);
    }

    @Test
    public void setReceptionists_uniqueReceptionistList_replacesOwnListWithProvidedUniqueReceptionistList() {
        uniqueReceptionistList.add(ALAN);
        UniqueReceptionistList expectedUniqueReceptionistList = new UniqueReceptionistList();
        expectedUniqueReceptionistList.add(FRANK);
        uniqueReceptionistList.setReceptionists(expectedUniqueReceptionistList);
        assertEquals(expectedUniqueReceptionistList, uniqueReceptionistList);
    }

    @Test
    public void setReceptionists_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReceptionistList.setReceptionists((List<Receptionist>) null);
    }

    @Test
    public void setReceptionists_list_replacesOwnListWithProvidedList() {
        uniqueReceptionistList.add(ALAN);
        List<Receptionist> receptionistList = Collections.singletonList(FRANK);
        uniqueReceptionistList.setReceptionists(receptionistList);
        UniqueReceptionistList expectedUniqueDoctorList = new UniqueReceptionistList();
        expectedUniqueDoctorList.add(FRANK);
        assertEquals(expectedUniqueDoctorList, uniqueReceptionistList);
    }

    @Test
    public void setReceptionists_listWithDuplicateReceptionists_throwsDuplicateReceptionistException() {
        List<Receptionist> listWithDuplicateReceptionists = Arrays.asList(ALAN, ALAN);
        thrown.expect(DuplicateReceptionistException.class);
        uniqueReceptionistList.setReceptionists(listWithDuplicateReceptionists);
    }

    @Test
    public void getReceptionist_nullReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueReceptionistList.getReceptionist(null);
    }

    @Test
    public void getReceptionist_cannotFindReceptionist_throwsReceptionistNotFoundException() {
        thrown.expect(ReceptionistNotFoundException.class);
        uniqueReceptionistList.getReceptionist(DAISY);
    }

    @Test
    public void getReceptionist_validReceptionist_returnReceptionist() {
        uniqueReceptionistList.add(FRANK);
        assertNotNull(uniqueReceptionistList.getReceptionist(FRANK));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueReceptionistList.asUnmodifiableObservableList().remove(0);
    }
}
