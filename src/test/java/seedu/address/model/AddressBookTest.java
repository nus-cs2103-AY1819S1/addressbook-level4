package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.AMY_APPT;
import static seedu.clinicio.testutil.TypicalPersons.CARL_APPT;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;
||||||| merged common ancestors
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ID_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.AMY_APPT;
import static seedu.clinicio.testutil.TypicalPersons.CARL_APPT;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;
=======
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_ADAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY_APPT;
import static seedu.address.testutil.TypicalPersons.CARL_APPT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java

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

<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.exceptions.DuplicatePersonException;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.staff.exceptions.DuplicateStaffException;
||||||| merged common ancestors
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.doctor.exceptions.DuplicateDoctorException;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.exceptions.DuplicatePersonException;
=======
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.exceptions.DuplicateDoctorException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java

<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
import seedu.clinicio.testutil.AppointmentBuilder;
import seedu.clinicio.testutil.PersonBuilder;
import seedu.clinicio.testutil.StaffBuilder;
||||||| merged common ancestors
import seedu.clinicio.testutil.AppointmentBuilder;
import seedu.clinicio.testutil.DoctorBuilder;
import seedu.clinicio.testutil.PersonBuilder;
=======
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PersonBuilder;
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        //@@author jjlee050
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
        assertEquals(Collections.emptyList(), clinicIo.getStaffList());
||||||| merged common ancestors
        assertEquals(Collections.emptyList(), clinicIo.getDoctorList());
=======
        assertEquals(Collections.emptyList(), addressBook.getDoctorList());
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
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
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons,
                new ArrayList<>());
||||||| merged common ancestors
        //@@author jjlee050
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons, new ArrayList<>());
=======
        //@@author jjlee050
        AddressBookStub newData = new AddressBookStub(newAppointments, newPersons, new ArrayList<>());
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    //@@author jjlee050
    @Test
    public void resetData_withDuplicateStaffs_throwsDuplicateStaffException() {
        // Two staff with the same identity fields
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
        Staff editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        List<Staff> newStaffs = Arrays.asList(ADAM, editedAdam);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, new ArrayList<>(),
                newStaffs);
||||||| merged common ancestors
        Doctor editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        List<Doctor> newDoctors = Arrays.asList(ADAM, editedAdam);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, new ArrayList<>(), newDoctors);
=======
        Doctor editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        List<Doctor> newDoctors = Arrays.asList(ADAM, editedAdam);
        AddressBookStub newData = new AddressBookStub(newAppointments, new ArrayList<>(), newDoctors);
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java

<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
        thrown.expect(DuplicateStaffException.class);
        clinicIo.resetData(newData);
||||||| merged common ancestors
        thrown.expect(DuplicateDoctorException.class);
        clinicIo.resetData(newData);
=======
        thrown.expect(DuplicateDoctorException.class);
        addressBook.resetData(newData);
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    //@@author jjlee050
    @Test
    public void hasStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
        clinicIo.hasStaff(null);
||||||| merged common ancestors
        clinicIo.hasDoctor(null);
=======
        addressBook.hasDoctor(null);
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasAppointment(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
    public void hasStaff_staffNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasStaff(ADAM));
||||||| merged common ancestors
    public void hasDoctor_doctorNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasDoctor(ADAM));
=======
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasDoctor(ADAM));
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
    }

    @Test
    public void hasAppointment_appointmentNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAppointment(AMY_APPT));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
    public void hasStaff_staffInClinicIo_returnsTrue() {
        clinicIo.addStaff(ADAM);
        assertTrue(clinicIo.hasStaff(ADAM));
||||||| merged common ancestors
    public void hasDoctor_doctorInClinicIo_returnsTrue() {
        clinicIo.addDoctor(ADAM);
        assertTrue(clinicIo.hasDoctor(ADAM));
=======
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        addressBook.addDoctor(ADAM);
        assertTrue(addressBook.hasDoctor(ADAM));
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
    }

    @Test
    public void hasAppointment_appointmentInAddressBook_returnsTrue() {
        addressBook.addAppointment(AMY_APPT);
        assertTrue(addressBook.hasAppointment(AMY_APPT));
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
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
    public void hasStaff_staffWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addStaff(ADAM);
        Staff editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        assertTrue(clinicIo.hasStaff(editedAdam));
||||||| merged common ancestors
    public void hasDoctor_doctorWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addDoctor(ADAM);
        Doctor editedAdam = new DoctorBuilder(ADAM).withId(VALID_ID_ADAM).build();
        assertTrue(clinicIo.hasDoctor(editedAdam));
=======
    public void hasDoctor_doctorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addDoctor(ADAM);
        Doctor editedAdam = new DoctorBuilder(ADAM).withId(VALID_ID_ADAM).build();
        assertTrue(addressBook.hasDoctor(editedAdam));
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
    }

    @Test
    public void hasAppointment_appointmentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addAppointment(AMY_APPT);
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withTime(13, 00).build();
        assertTrue(addressBook.hasAppointment(editedAmy));
    }

    @Test
    public void hasAppointment_appointmentWithDifferentIdentityFieldsInAddressBook_returnsFalse() {
        addressBook.addAppointment(AMY_APPT);
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withTime(12, 00).build();
        assertFalse(addressBook.hasAppointment(editedAmy));
    }

    @Test
    public void hasAppointmentClash_appointmentWithSameTimingsInAddressBook_returnsTrue() {
        addressBook.addAppointment(AMY_APPT);
        Appointment newAppt = new AppointmentBuilder(CARL_APPT).withTime(13, 00).build();
        assertTrue(addressBook.hasAppointmentClash(newAppt));
    }

    //@@author jjlee050
    @Test
    public void getStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.getStaff(null);
    }

    @Test
    public void getStaff_validStaff_returnDoctor() {
        clinicIo.addStaff(ADAM);
        assertNotNull(clinicIo.getStaff(ADAM));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    //@@author jjlee050
    @Test
    public void getStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
        clinicIo.getStaffList().remove(0);
||||||| merged common ancestors
        clinicIo.getDoctorList().remove(0);
=======
        addressBook.getDoctorList().remove(0);
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
    }

    //@@author gingivitiss
    @Test
    public void getAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getAppointmentList().remove(0);
    }

    /**
<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
     * A stub ReadOnlyClinicIo whose persons list and staff list can violate interface constraints.
||||||| merged common ancestors
     * A stub ReadOnlyClinicIo whose persons list and doctors list can violate interface constraints.
=======
     * A stub ReadOnlyAddressBook whose persons list and doctors list can violate interface constraints.
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Staff> staffs = FXCollections.observableArrayList();

<<<<<<< HEAD:src/test/java/seedu/clinicio/model/ClinicIoTest.java
        ClinicIoStub(Collection<Appointment> appointments, Collection<Person> persons,
                Collection<Staff> staffs) {
||||||| merged common ancestors
        ClinicIoStub(Collection<Appointment> appointments, Collection<Person> persons, Collection<Doctor> doctors) {
=======
        AddressBookStub(Collection<Appointment> appointments, Collection<Person> persons, Collection<Doctor> doctors) {
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/model/AddressBookTest.java
            this.appointments.setAll(appointments);
            this.persons.setAll(persons);
            //@@author jjlee050
            this.staffs.setAll(staffs);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        //@@author jjlee050
        @Override
        public ObservableList<Staff> getStaffList() {
            return staffs;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

    }

}
