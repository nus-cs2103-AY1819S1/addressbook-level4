package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.ALICE_PATIENT_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FIONA_DOCTOR_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FIRST;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt
        .getTypicalAddressBookWithPatientAndDoctorWithAppt;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.HealthBook;
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
 * Contains integration tests and unit tests for DeleteAppointmentCommand.
 */
public class DeleteAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctorWithAppt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deleteAppointment_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withAppointmentId(10000)
                .withDoctor("Fiona Kunz").withPatient("Alice Pauline")
                .withDateTime("2018-10-11 12:00")
                .withComments("Heart check up")
                .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                .withAppointmentId(10000)
                .withMedicineName("Aspirin").build()))).build();
        Patient expectedPatient = new PatientBuilder()
                .withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withEmail("alice@example.com")
                .withPhone("94351253")
                .withRemark("")
                .withTags("Patient")
                .withMedicalHistory("egg", "subhealth").build();
        Doctor expectedDoctor = new DoctorBuilder()
                .withName("Fiona Kunz")
                .withPhone("9482427")
                .withEmail("lydia@example.com")
                .withAddress("little tokyo")
                .withRemark("")
                .withTags("Doctor").build();

        expectedPatient.addUpcomingAppointment(expectedAppointment);
        expectedDoctor.addUpcomingAppointment(expectedAppointment);

        Model expectedModel = new ModelManager(new HealthBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(ALICE_PATIENT_APPT, expectedPatient);
        expectedModel.updatePerson(FIONA_DOCTOR_APPT, expectedDoctor);
        expectedModel.setAppointment(FIRST, expectedAppointment);
        expectedModel.deleteAppointment(expectedAppointment, expectedPatient, expectedDoctor);
        expectedModel.commitAddressBook();

        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(FIRST.getAppointmentId());

        String expectedMessage = DeleteAppointmentCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(deleteAppointmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAppointment_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand =
                new DeleteAppointmentCommand(1);

        assertCommandFailure(deleteAppointmentCommand,
                model, commandHistory, deleteAppointmentCommand.MESSAGE_INVALID_APPOINTMENT_INDEX);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteAppointmentFirstCommand =
                new DeleteAppointmentCommand(10000);
        DeleteAppointmentCommand deleteAppointmentSecondCommand =
                new DeleteAppointmentCommand(10001);

        // same object -> returns true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy =
                new DeleteAppointmentCommand(10000);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals("ABC"));

        // null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));
    }

}
