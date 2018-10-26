package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALAN;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
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
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.exceptions.DuplicateDoctorException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import seedu.address.model.receptionist.Receptionist;
import seedu.address.model.receptionist.exceptions.DuplicateReceptionistException;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ReceptionistBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        //@@author jjlee050
        assertEquals(Collections.emptyList(), addressBook.getDoctorList());
        assertEquals(Collections.emptyList(), addressBook.getReceptionistList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        //@@author jjlee050
        AddressBookStub newData = new AddressBookStub(newAppointments, newPersons,
                new ArrayList<>(), new ArrayList<>());

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    //@@author jjlee050
    @Test
    public void resetData_withDuplicateDoctors_throwsDuplicateDoctorException() {
        // Two doctors with the same identity fields
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
        Doctor editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        List<Doctor> newDoctors = Arrays.asList(ADAM, editedAdam);
        AddressBookStub newData = new AddressBookStub(newAppointments, new ArrayList<>(),
                newDoctors, new ArrayList<>());

        thrown.expect(DuplicateDoctorException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateReceptionists_throwsDuplicateReceptionistException() {
        // Two receptionists with the same identity fields
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
        Receptionist editedAlan = new ReceptionistBuilder(ALAN).withName(VALID_NAME_ALAN).build();
        List<Receptionist> newReceptionists = Arrays.asList(ALAN, editedAlan);
        AddressBookStub newData = new AddressBookStub(newAppointments, new ArrayList<>(),
                new ArrayList<>(), newReceptionists);

        thrown.expect(DuplicateReceptionistException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasDoctor(null);
    }

    @Test
    public void hasReceptionist_nullReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasReceptionist(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasDoctor(ADAM));
    }

    @Test
    public void hasReceptionist_receptionistNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasReceptionist(ALAN));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        addressBook.addDoctor(ADAM);
        assertTrue(addressBook.hasDoctor(ADAM));
    }

    @Test
    public void hasReceptionist_receptionistInAddressBook_returnsTrue() {
        addressBook.addReceptionist(ALAN);
        assertTrue(addressBook.hasReceptionist(ALAN));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_doctorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addDoctor(ADAM);
        Doctor editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        assertTrue(addressBook.hasDoctor(editedAdam));
    }

    @Test
    public void hasReceptionist_receptionistWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addReceptionist(ALAN);
        Receptionist editedAlan = new ReceptionistBuilder(ALAN).withName(VALID_NAME_ALAN).build();
        assertTrue(addressBook.hasReceptionist(editedAlan));
    }

    //@@author jjlee050
    @Test
    public void getDoctor_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.getDoctor(null);
    }

    @Test
    public void getDoctor_validDoctor_returnDoctor() {
        addressBook.addDoctor(ADAM);
        assertNotNull(addressBook.getDoctor(ADAM));
    }

    @Test
    public void getReceptionist_nullReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.getReceptionist(null);
    }

    @Test
    public void getReceptionist_validReceptionist_returnReceptionist() {
        addressBook.addReceptionist(ALAN);
        assertNotNull(addressBook.getReceptionist(ALAN));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    //@@author jjlee050
    @Test
    public void getDoctorList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getDoctorList().remove(0);
    }

    @Test
    public void getReceptionistList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getReceptionistList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list and doctors list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();
        private final ObservableList<Receptionist> receptionists = FXCollections.observableArrayList();

        AddressBookStub(Collection<Appointment> appointments, Collection<Person> persons,
                Collection<Doctor> doctors, Collection<Receptionist> receptionists) {
            this.appointments.setAll(appointments);
            this.persons.setAll(persons);
            //@@author jjlee050
            this.doctors.setAll(doctors);
            this.receptionists.setAll(receptionists);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        //@@author jjlee050
        @Override
        public ObservableList<Doctor> getDoctorList() {
            return doctors;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

        @Override
        public ObservableList<Receptionist> getReceptionistList() {
            return receptionists;
        }
    }

}
