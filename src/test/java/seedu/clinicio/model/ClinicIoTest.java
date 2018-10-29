package seedu.clinicio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ALAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALAN;
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
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.staff.exceptions.DuplicateStaffException;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.exceptions.DuplicatePersonException;
import seedu.clinicio.model.receptionist.Receptionist;
import seedu.clinicio.model.receptionist.exceptions.DuplicateReceptionistException;

import seedu.clinicio.testutil.AppointmentBuilder;
import seedu.clinicio.testutil.DoctorBuilder;
import seedu.clinicio.testutil.PersonBuilder;
import seedu.clinicio.testutil.ReceptionistBuilder;

public class ClinicIoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClinicIo clinicIo = new ClinicIo();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clinicIo.getPersonList());
        //@@author jjlee050
        assertEquals(Collections.emptyList(), clinicIo.getStaffList());
        assertEquals(Collections.emptyList(), clinicIo.getReceptionistList());

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
        //@@author jjlee050
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons,
                new ArrayList<>(), new ArrayList<>());

        thrown.expect(DuplicatePersonException.class);
        clinicIo.resetData(newData);
    }

    //@@author jjlee050
    @Test
    public void resetData_withDuplicateDoctors_throwsDuplicateDoctorException() {
        // Two staff with the same identity fields
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
        Staff editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        List<Staff> newStaffs = Arrays.asList(ADAM, editedAdam);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, new ArrayList<>(),
                newStaffs, new ArrayList<>());

        thrown.expect(DuplicateStaffException.class);
        clinicIo.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateReceptionists_throwsDuplicateReceptionistException() {
        // Two receptionists with the same identity fields
        List<Appointment> newAppointments = new ArrayList<Appointment>(); //TODO
        Receptionist editedAlan = new ReceptionistBuilder(ALAN).withName(VALID_NAME_ALAN).build();
        List<Receptionist> newReceptionists = Arrays.asList(ALAN, editedAlan);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, new ArrayList<>(),
                new ArrayList<>(), newReceptionists);

        thrown.expect(DuplicateReceptionistException.class);
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
        clinicIo.hasStaff(null);
    }

    @Test
    public void hasReceptionist_nullReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasReceptionist(null);
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
    public void hasDoctor_doctorNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasStaff(ADAM));
    }

    @Test
    public void hasReceptionist_receptionistNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasReceptionist(ALAN));
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
    public void hasDoctor_doctorInClinicIo_returnsTrue() {
        clinicIo.addStaff(ADAM);
        assertTrue(clinicIo.hasStaff(ADAM));
    }

    @Test
    public void hasReceptionist_receptionistInClinicIo_returnsTrue() {
        clinicIo.addReceptionist(ALAN);
        assertTrue(clinicIo.hasReceptionist(ALAN));
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
    public void hasDoctor_doctorWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addStaff(ADAM);
        Staff editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        assertTrue(clinicIo.hasStaff(editedAdam));
    }

    @Test
    public void hasReceptionist_receptionistWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addReceptionist(ALAN);
        Receptionist editedAlan = new ReceptionistBuilder(ALAN).withName(VALID_NAME_ALAN).build();
        assertTrue(clinicIo.hasReceptionist(editedAlan));
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
    public void getDoctor_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.getStaff(null);
    }

    @Test
    public void getDoctor_validDoctor_returnDoctor() {
        clinicIo.addStaff(ADAM);
        assertNotNull(clinicIo.getStaff(ADAM));
    }

    @Test
    public void getReceptionist_nullReceptionist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.getReceptionist(null);
    }

    @Test
    public void getReceptionist_validReceptionist_returnReceptionist() {
        clinicIo.addReceptionist(ALAN);
        assertNotNull(clinicIo.getReceptionist(ALAN));
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
        clinicIo.getStaffList().remove(0);
    }

    @Test
    public void getReceptionistList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getReceptionistList().remove(0);
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
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Staff> staff = FXCollections.observableArrayList();
        private final ObservableList<Receptionist> receptionists = FXCollections.observableArrayList();

        ClinicIoStub(Collection<Appointment> appointments, Collection<Person> persons,
                Collection<Staff> staff, Collection<Receptionist> receptionists) {
            this.appointments.setAll(appointments);
            this.persons.setAll(persons);
            //@@author jjlee050
            this.staff.setAll(staff);
            this.receptionists.setAll(receptionists);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        //@@author jjlee050
        @Override
        public ObservableList<Staff> getStaffList() {
            return staff;
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
