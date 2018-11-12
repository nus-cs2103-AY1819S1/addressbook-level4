package seedu.superta.model.student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.superta.testutil.TypicalSuperTaClient.ALICE;
import static seedu.superta.testutil.TypicalSuperTaClient.BENSON;
import static seedu.superta.testutil.TypicalSuperTaClient.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.model.student.exceptions.DuplicateStudentException;
import seedu.superta.model.student.exceptions.StudentNotFoundException;
import seedu.superta.testutil.StudentBuilder;

public class UniqueStudentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueStudentList uniqueStudentList = new UniqueStudentList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStudentList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueStudentList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueStudentList.add(ALICE);
        assertTrue(uniqueStudentList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudentList.add(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueStudentList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStudentList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueStudentList.add(ALICE);
        thrown.expect(DuplicateStudentException.class);
        uniqueStudentList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStudentList.setStudent(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStudentList.setStudent(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(StudentNotFoundException.class);
        uniqueStudentList.setStudent(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.setStudent(ALICE, ALICE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(ALICE);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueStudentList.add(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueStudentList.setStudent(ALICE, editedAlice);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(editedAlice);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.setStudent(ALICE, BOB);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(BOB);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.add(BOB);
        thrown.expect(DuplicateStudentException.class);
        uniqueStudentList.setStudent(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStudentList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(StudentNotFoundException.class);
        uniqueStudentList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueStudentList.add(ALICE);
        uniqueStudentList.remove(ALICE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStudentList.setStudents((UniqueStudentList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueStudentList.add(ALICE);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(BOB);
        uniqueStudentList.setStudents(expectedUniqueStudentList);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStudentList.setStudents((List<Student>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueStudentList.add(ALICE);
        List<Student> studentList = Collections.singletonList(BOB);
        uniqueStudentList.setStudents(studentList);
        UniqueStudentList expectedUniqueStudentList = new UniqueStudentList();
        expectedUniqueStudentList.add(BOB);
        assertEquals(expectedUniqueStudentList, uniqueStudentList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Student> listWithDuplicateStudents = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateStudentException.class);
        uniqueStudentList.setStudents(listWithDuplicateStudents);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueStudentList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void removeById_success() {
        // no students -> false
        assertFalse(uniqueStudentList.removeById(BENSON));
        uniqueStudentList.add(BENSON);

        // with student -> true
        assertTrue(uniqueStudentList.removeById(BENSON));
    }
}
