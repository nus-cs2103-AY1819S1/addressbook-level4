package seedu.clinicio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.AMY_APPT;
import static seedu.clinicio.testutil.TypicalPersons.CARL_APPT;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

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

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.exceptions.DuplicatePersonException;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.staff.exceptions.DuplicateStaffException;

import seedu.clinicio.testutil.AppointmentBuilder;
import seedu.clinicio.testutil.PersonBuilder;
import seedu.clinicio.testutil.StaffBuilder;

public class ClinicIoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClinicIo clinicIo = new ClinicIo();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clinicIo.getPersonList());
        //@@author jjlee050
        assertEquals(Collections.emptyList(), clinicIo.getStaffList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyClinicIo_replacesData() {
        ClinicIo newData = getTypicalClinicIo();
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
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons,
                new ArrayList<>(), new ArrayList<>());

        thrown.expect(DuplicatePersonException.class);
        clinicIo.resetData(newData);
    }

    //@@author jjlee050
    @Test
    public void resetData_withDuplicateStaffs_throwsDuplicateStaffException() {
        // Two staff with the same identity fields
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
        Staff editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        List<Staff> newStaffs = Arrays.asList(ADAM, editedAdam);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, new ArrayList<>(),
                new ArrayList<>(), newStaffs);

        thrown.expect(DuplicateStaffException.class);
        clinicIo.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasPerson(null);
    }

    //@@author jjlee050
    @Test
    public void hasStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasStaff(null);
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasAppointment(null);
    }

    @Test
    public void hasPerson_personNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasStaff_staffNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasStaff(ADAM));
    }

    @Test
    public void hasAppointment_appointmentNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasAppointment(AMY_APPT));
    }

    @Test
    public void hasPerson_personInClinicIo_returnsTrue() {
        clinicIo.addPerson(ALICE);
        assertTrue(clinicIo.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasStaff_staffInClinicIo_returnsTrue() {
        clinicIo.addStaff(ADAM);
        assertTrue(clinicIo.hasStaff(ADAM));
    }

    @Test
    public void hasAppointment_appointmentInClinicIo_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        assertTrue(clinicIo.hasAppointment(AMY_APPT));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(clinicIo.hasPerson(editedAlice));
    }

    //@@author jjlee050
    @Test
    public void hasStaff_staffWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addStaff(ADAM);
        Staff editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        assertTrue(clinicIo.hasStaff(editedAdam));
    }

    @Test
    public void hasAppointment_appointmentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withTime(13, 00).build();
        assertTrue(clinicIo.hasAppointment(editedAmy));
    }

    @Test
    public void hasAppointment_appointmentWithDifferentIdentityFieldsInClinicIo_returnsFalse() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withTime(12, 00).build();
        assertFalse(clinicIo.hasAppointment(editedAmy));
    }

    @Test
    public void hasAppointmentClash_appointmentWithSameTimingsInClinicIo_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment newAppt = new AppointmentBuilder(CARL_APPT).withTime(13, 00).build();
        assertTrue(clinicIo.hasAppointmentClash(newAppt));
    }

    //@@author jjlee050
    @Test
    public void getStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.checkStaffCredentials(null);
    }

    @Test
    public void getStaff_validStaff_returnDoctor() {
        clinicIo.addStaff(ADAM);
        assertNotNull(clinicIo.checkStaffCredentials(ADAM));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getPersonList().remove(0);
    }

    //@@author jjlee050
    @Test
    public void getStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getStaffList().remove(0);
    }

    //@@author gingivitiss
    @Test
    public void getAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getAppointmentList().remove(0);
    }

    /**
     * A stub ReadOnlyClinicIo whose persons list and staff list can violate interface constraints.
     */
    private static class ClinicIoStub implements ReadOnlyClinicIo {
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Staff> staffs = FXCollections.observableArrayList();

        ClinicIoStub(Collection<Appointment> appointments, Collection<Person> persons,
                Collection<Patient> patients, Collection<Staff> staffs) {
            this.appointments.setAll(appointments);
            this.persons.setAll(persons);
            this.patients.setAll(patients);
            this.staffs.setAll(staffs);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }

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
