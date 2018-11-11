package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.project.Assignment;
import seedu.address.testutil.PersonBuilder;

public class ArchiveListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ArchiveList archiveList = new ArchiveList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), archiveList.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        archiveList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyArchiveList_replacesData() {
        ArchiveList newData = getTypicalArchiveList();
        archiveList.resetData(newData);
        assertEquals(newData, archiveList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withSalary(VALID_SALARY_BOB)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ArchiveListStub newData = new ArchiveListStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        archiveList.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        archiveList.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInArchiveList_returnsFalse() {
        assertFalse(archiveList.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInArchiveList_returnsTrue() {
        archiveList.addPerson(ALICE);
        assertTrue(archiveList.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInArchiveList_returnsTrue() {
        archiveList.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withSalary(VALID_SALARY_BOB)
                .build();
        assertTrue(archiveList.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        archiveList.getPersonList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class ArchiveListStub implements ReadOnlyArchiveList {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Assignment> assignments = FXCollections.observableArrayList();
        private final ObservableList<LeaveApplicationWithEmployee> leaveApplications =
                FXCollections.observableArrayList();

        ArchiveListStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<LeaveApplicationWithEmployee> getLeaveApplicationList() {
            return leaveApplications;
        }
    }
}
