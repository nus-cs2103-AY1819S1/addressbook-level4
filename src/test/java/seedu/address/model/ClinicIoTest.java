package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_ADAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY_APPT;
import static seedu.address.testutil.TypicalPersons.CARL_APPT;
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

import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PersonBuilder;

public class ClinicIoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClinicIo clinicIo = new ClinicIo();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clinicIo.getPersonList());
        //@@author jjlee050
        assertEquals(Collections.emptyList(), clinicIo.getDoctorList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        ClinicIo newData = getTypicalAddressBook();
        clinicIo.resetData(newData);
        assertEquals(newData, clinicIo);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        //@@author jjlee050
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons, new ArrayList<>());

        thrown.expect(DuplicatePersonException.class);
        clinicIo.resetData(newData);
    }

    //@@author jjlee050
    @Test
    public void resetData_withDuplicateDoctors_throwsDuplicateDoctorException() {
        // Two doctors with the same identity fields
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
        Doctor editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        List<Doctor> newDoctors = Arrays.asList(ADAM, editedAdam);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, new ArrayList<>(), newDoctors);

        thrown.expect(DuplicateDoctorException.class);
        clinicIo.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasPerson(null);
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasDoctor(null);
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasAppointment(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(clinicIo.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(clinicIo.hasDoctor(ADAM));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentNotInAddressBook_returnsFalse() {
        assertFalse(clinicIo.hasAppointment(AMY_APPT));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        clinicIo.addPerson(ALICE);
        assertTrue(clinicIo.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        clinicIo.addDoctor(ADAM);
        assertTrue(clinicIo.hasDoctor(ADAM));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentInAddressBook_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        assertTrue(clinicIo.hasAppointment(AMY_APPT));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        clinicIo.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(clinicIo.hasPerson(editedAlice));
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_doctorWithSameIdentityFieldsInAddressBook_returnsTrue() {
        clinicIo.addDoctor(ADAM);
        Doctor editedAdam = new DoctorBuilder(ADAM).withId(VALID_ID_ADAM).build();
        assertTrue(clinicIo.hasDoctor(editedAdam));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withTime(13, 00).build();
        assertTrue(clinicIo.hasAppointment(editedAmy));
    }

    @Test
    public void hasAppointment_appointmentWithDifferentIdentityFieldsInAddressBook_returnsFalse() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withTime(12, 00).build();
        assertFalse(clinicIo.hasAppointment(editedAmy));
    }

    @Test
    public void hasAppointmentClash_appointmentWithSameTimingsInAddressBook_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment newAppt = new AppointmentBuilder(CARL_APPT).withTime(13, 00).build();
        assertTrue(clinicIo.hasAppointmentClash(newAppt));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getPersonList().remove(0);
    }

    //@@author jjlee050
    @Test
    public void getDoctorList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getDoctorList().remove(0);
    }

    //@@author gingivitiss
    @Test
    public void getAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getAppointmentList().remove(0);
    }

    /**
     * A stub ReadOnlyClinicIo whose persons list and doctors list can violate interface constraints.
     */
    private static class ClinicIoStub implements ReadOnlyClinicIo {
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();

        ClinicIoStub(Collection<Appointment> appointments, Collection<Person> persons, Collection<Doctor> doctors) {
            this.appointments.setAll(appointments);
            this.persons.setAll(persons);
            //@@author jjlee050
            this.doctors.setAll(doctors);
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
    }

}
