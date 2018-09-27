package seedu.address.model.doctor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.BEN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.doctor.exceptions.DoctorNotFoundException;
import seedu.address.model.doctor.exceptions.DuplicateDoctorException;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.DoctorBuilder;

//@@author jjlee050
public class UniqueDoctorListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueDoctorList uniqueDoctorList = new UniqueDoctorList();

    @Test
    public void contains_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDoctorList.contains(null);
    }

    @Test
    public void contains_doctorNotInList_returnsFalse() {
        assertFalse(uniqueDoctorList.contains(ADAM));
    }

    @Test
    public void contains_doctorInList_returnsTrue() {
        uniqueDoctorList.add(ADAM);
        assertTrue(uniqueDoctorList.contains(ADAM));
    }

    @Test
    public void contains_doctorWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDoctorList.add(ADAM);
        Doctor editedAdam = new DoctorBuilder(ADAM).withPassword("doctor1")
                .build();
        assertTrue(uniqueDoctorList.contains(editedAdam));
    }

    @Test
    public void add_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDoctorList.add(null);
    }

    @Test
    public void add_duplicateDoctor_throwsDuplicateDoctorException() {
        uniqueDoctorList.add(ADAM);
        thrown.expect(DuplicateDoctorException.class);
        uniqueDoctorList.add(ADAM);
    }

    @Test
    public void setDoctor_nullTargetDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDoctorList.setDoctor(null, ADAM);
    }

    @Test
    public void setDoctor_nullEditedDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDoctorList.setDoctor(ADAM, null);
    }

    @Test
    public void setDoctor_targetDoctorNotInList_throwsDoctorNotFoundException() {
        thrown.expect(DoctorNotFoundException.class);
        uniqueDoctorList.setDoctor(ADAM, ADAM);
    }

    @Test
    public void setDoctor_editedDoctorIsSameDoctor_success() {
        uniqueDoctorList.add(ADAM);
        uniqueDoctorList.setDoctor(ADAM, ADAM);
        UniqueDoctorList expectedUniqueDoctorList = new UniqueDoctorList();
        expectedUniqueDoctorList.add(ADAM);
        assertEquals(expectedUniqueDoctorList, uniqueDoctorList);
    }

    @Test
    public void setDoctor_editedDoctorHasSameIdentity_success() {
        uniqueDoctorList.add(ADAM);
        Doctor editedAdam = new DoctorBuilder(ADAM).withPassword("doctor1")
                .build();
        uniqueDoctorList.setDoctor(ADAM, editedAdam);
        UniqueDoctorList expectedUniqueDoctorList = new UniqueDoctorList();
        expectedUniqueDoctorList.add(editedAdam);
        assertEquals(expectedUniqueDoctorList, uniqueDoctorList);
    }

    @Test
    public void setDoctor_editedDoctorHasDifferentIdentity_success() {
        uniqueDoctorList.add(ADAM);
        uniqueDoctorList.setDoctor(ADAM, BEN);
        UniqueDoctorList expectedUniqueDoctorList = new UniqueDoctorList();
        expectedUniqueDoctorList.add(BEN);
        assertEquals(expectedUniqueDoctorList, uniqueDoctorList);
    }

    @Test
    public void setDoctor_editedDoctorHasNonUniqueIdentity_throwsDuplicateDoctorException() {
        uniqueDoctorList.add(ADAM);
        uniqueDoctorList.add(BEN);
        thrown.expect(DuplicateDoctorException.class);
        uniqueDoctorList.setDoctor(ADAM, BEN);
    }

    @Test
    public void remove_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDoctorList.remove(null);
    }

    @Test
    public void remove_doctorDoesNotExist_throwsDoctorNotFoundException() {
        thrown.expect(DoctorNotFoundException.class);
        uniqueDoctorList.remove(ADAM);
    }

    @Test
    public void remove_existingDoctor_removesDoctor() {
        uniqueDoctorList.add(ADAM);
        uniqueDoctorList.remove(ADAM);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniqueDoctorList);
    }

    @Test
    public void setDoctors_nullUniqueDoctorList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDoctorList.setDoctors((UniqueDoctorList) null);
    }

    @Test
    public void setDoctors_uniqueDictirList_replacesOwnListWithProvidedUniqueDoctorList() {
        uniqueDoctorList.add(ADAM);
        UniqueDoctorList expectedUniqueDoctorList = new UniqueDoctorList();
        expectedUniqueDoctorList.add(BEN);
        uniqueDoctorList.setDoctors(expectedUniqueDoctorList);
        assertEquals(expectedUniqueDoctorList, uniqueDoctorList);
    }

    @Test
    public void setDoctors_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDoctorList.setDoctors((List<Doctor>) null);
    }

    @Test
    public void setDoctors_list_replacesOwnListWithProvidedList() {
        uniqueDoctorList.add(ADAM);
        List<Doctor> personList = Collections.singletonList(BEN);
        uniqueDoctorList.setDoctors(personList);
        UniqueDoctorList expectedUniqueDoctorList = new UniqueDoctorList();
        expectedUniqueDoctorList.add(BEN);
        assertEquals(expectedUniqueDoctorList, uniqueDoctorList);
    }

    @Test
    public void setDoctors_listWithDuplicateDoctors_throwsDuplicateDoctorException() {
        List<Doctor> listWithDuplicatePersons = Arrays.asList(ADAM, ADAM);
        thrown.expect(DuplicateDoctorException.class);
        uniqueDoctorList.setDoctors(listWithDuplicatePersons);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueDoctorList.asUnmodifiableObservableList().remove(0);
    }
}
