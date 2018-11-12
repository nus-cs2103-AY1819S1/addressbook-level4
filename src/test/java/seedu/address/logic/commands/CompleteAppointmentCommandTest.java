package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.BENSON_PATIENT_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FOURTH;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.GEORGE_DOCTOR_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.SECOND;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt
        .getTypicalAddressBookWithPatientAndDoctorWithAppt;

import java.util.ArrayList;
import java.util.Arrays;

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
import seedu.address.testutil.PrescriptionBuilder;


/**
 * Contains integration tests and unit tests for CompleteAppointmentCommand.
 */
public class CompleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctorWithAppt(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_completeAppointment_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withAppointmentId(10001)
                .withDoctor("George Best").withPatient("Benson Meier").withDateTime("2018-10-12 12:00")
                .withComments("Flu check up")
                .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                .withAppointmentId(10001)
                .withMedicineName("Tamiflu").build()))).build();
        Patient expectedPatient = new PatientBuilder().withName("Benson Meier")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withRemark("")
                .withTags("Patient", "friends")
                .withMedicalHistory("", "having cold").build();
        Doctor expectedDoctor = new DoctorBuilder()
                .withName("George Best")
                .withPhone("9482442")
                .withEmail("anna@example.com")
                .withAddress("4th street")
                .withRemark("")
                .withTags("Doctor").build();

        expectedPatient.addUpcomingAppointment(expectedAppointment);
        expectedDoctor.addUpcomingAppointment(expectedAppointment);
        expectedDoctor.addUpcomingAppointment(FOURTH);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(BENSON_PATIENT_APPT, expectedPatient);
        expectedModel.updatePerson(GEORGE_DOCTOR_APPT, expectedDoctor);
        expectedModel.setAppointment(SECOND, expectedAppointment);
        expectedModel.completeAppointment(expectedAppointment, expectedPatient, expectedDoctor);
        expectedModel.commitAddressBook();

        CompleteAppointmentCommand completeAppointmentCommand =
                new CompleteAppointmentCommand(SECOND.getAppointmentId());

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
