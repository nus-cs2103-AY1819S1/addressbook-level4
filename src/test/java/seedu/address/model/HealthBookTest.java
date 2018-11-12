package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class HealthBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final HealthBook healthBook = new HealthBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), healthBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        healthBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        HealthBook newData = getTypicalAddressBook();
        healthBook.resetData(newData);
        assertEquals(newData, healthBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        HealthBookStub newData = new HealthBookStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        healthBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        healthBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(healthBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        healthBook.addPerson(ALICE);
        assertTrue(healthBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        healthBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(healthBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        healthBook.getPersonList().remove(0);
    }

    /**
     * A stub ReadOnlyHealthBook whose persons list can violate interface constraints.
     */
    private static class HealthBookStub implements ReadOnlyHealthBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private int appointmentCounter;

        HealthBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

        @Override
        public int getAppointmentCounter() {
            return appointmentCounter;
        }
    }

}
