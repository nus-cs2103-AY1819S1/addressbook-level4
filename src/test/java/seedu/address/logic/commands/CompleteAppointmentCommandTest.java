package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.FIONA_DOCTOR;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.ALICE_PATIENT_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FIONA_DOCTOR_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FIRST;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt
        .getTypicalAddressBookWithPatientAndDoctorWithAppt;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;


/**
 * Contains integration tests and unit tests for CompleteAppointmentCommand.
 */
public class CompleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctorWithAppt(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_completeAppointment_success() {
        Appointment editedAppointment = new AppointmentBuilder(FIRST).build();
        Patient editedPatient = new PatientBuilder(ALICE_PATIENT).withAppointment(editedAppointment).build();
        Doctor editedDoctor = new DoctorBuilder(FIONA_DOCTOR).withAppointment(editedAppointment).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(ALICE_PATIENT_APPT, editedPatient);
        expectedModel.updatePerson(FIONA_DOCTOR_APPT, editedDoctor);
        expectedModel.updateAppointment(FIRST, editedAppointment);
        expectedModel.completeAppointment(editedAppointment, editedPatient, editedDoctor);
        expectedModel.commitAddressBook();

        CompleteAppointmentCommand completeAppointmentCommand =
                new CompleteAppointmentCommand(FIRST.getAppointmentId());

        String expectedMessage = completeAppointmentCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(completeAppointmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAppointmentId_throwsCommandException() {
        // negative appointmentId
        CompleteAppointmentCommand completeAppointmentCommand =
                new CompleteAppointmentCommand(-100);

        assertCommandFailure(completeAppointmentCommand,
                model, commandHistory, completeAppointmentCommand.MESSAGE_INVALID_APPOINTMENT_INDEX);

        // appointmentId not in HealthBook
        completeAppointmentCommand = new CompleteAppointmentCommand(1);

        assertCommandFailure(completeAppointmentCommand,
                model, commandHistory, completeAppointmentCommand.MESSAGE_INVALID_APPOINTMENT_INDEX);
    }

    @Test
    public void equals() {
        CompleteAppointmentCommand completeAppointmentFirstCommand =
                new CompleteAppointmentCommand(10000);
        CompleteAppointmentCommand completeAppointmentSecondCommand =
                new CompleteAppointmentCommand(10001);

        // same object -> returns true
        assertTrue(completeAppointmentFirstCommand.equals(completeAppointmentFirstCommand));

        // same values -> returns true
        CompleteAppointmentCommand completeAppointmentFirstCommandCopy =
                new CompleteAppointmentCommand(10000);
        assertTrue(completeAppointmentFirstCommand.equals(completeAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeAppointmentFirstCommand.equals("ABC"));

        // null -> returns false
        assertFalse(completeAppointmentFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(completeAppointmentFirstCommand.equals(completeAppointmentSecondCommand));
    }

}
