package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.ALICE_PATIENT_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.BENSON_PATIENT_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FIONA_DOCTOR_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.GEORGE_DOCTOR_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.HELENA_DOCTOR_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.IONA_PATIENT_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.SIXTH;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt
        .getSmallerAddressBookWithPatientAndDoctorWithAppt;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.GoogleCalendarStub;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PrescriptionBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeletePersonCommandTest {

    private static final GoogleCalendarStub GOOGLE_CALENDAR_STUB = new GoogleCalendarStub();
    private Model model = new ModelManager(getSmallerAddressBookWithPatientAndDoctorWithAppt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_deletePatient_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withAppointmentId(10005)
                .withDoctor("Helena Sophia")
                .withPatient("Iona Porter")
                .withDateTime("2018-12-16 12:00")
                .withComments("Body check up")
                .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                .withAppointmentId(10005)
                .withMedicineName("Aspirin").build()))).build();
        Patient expectedPatient = new PatientBuilder()
                .withName("Iona Porter").withPhone("9482224")
                .withEmail("iona@example.com")
                .withAddress("24th ave")
                .withRemark("")
                .withTags("Patient")
                .withMedicalHistory("", "").build();
        Doctor expectedDoctor = new DoctorBuilder()
                .withName("Helena Sophia")
                .withPhone("95264283")
                .withEmail("helena@example.com")
                .withAddress("7th street")
                .withRemark("")
                .withTags("Doctor").build();

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(IONA_PATIENT_APPT, expectedPatient);
        expectedModel.updatePerson(HELENA_DOCTOR_APPT, expectedDoctor);
        expectedModel.setAppointment(SIXTH, expectedAppointment);
        expectedModel.deletePerson(expectedPatient);
        expectedModel.commitAddressBook();

        DeletePatientCommand deletePatientCommand =
                new DeletePatientCommand(IONA_PATIENT_APPT.getName(), IONA_PATIENT_APPT.getPhone());

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, IONA_PATIENT_APPT);

        assertCommandSuccess(deletePatientCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatient_throwsCommandException() {
        // invalid name
        assertCommandFailure(new DeletePatientCommand(new Name("JASKLFJA12412445"), null),
                model, commandHistory, String.format(DeletePatientCommand.MESSAGE_INVALID_DELETE_PERSON, "Patient"));

        // not patient
        assertCommandFailure(new DeletePatientCommand(HELENA_DOCTOR_APPT.getName(), HELENA_DOCTOR_APPT.getPhone()),
                model, commandHistory, String.format(DeletePatientCommand.MESSAGE_INVALID_DELETE_PERSON, "Patient"));
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        // duplicate patient
        model.addPatient(new PatientBuilder().withName(IONA_PATIENT_APPT.getName().toString())
                .withPhone("1234111").build());
        assertCommandFailure(new DeletePatientCommand(IONA_PATIENT_APPT.getName(), null),
                model, commandHistory, String.format(DeletePatientCommand.MESSAGE_DUPLICATE_DELETE_PERSON, "Patient",
                        "Patient", "Patient"));
    }

    @Test
    public void execute_deleteDoctor_success() {
        Appointment expectedAppointment = new AppointmentBuilder().withAppointmentId(10005)
                .withDoctor("Helena Sophia")
                .withPatient("Iona Porter")
                .withDateTime("2018-12-16 12:00")
                .withComments("Body check up")
                .withPrescriptions(new ArrayList<>(Arrays.asList(new PrescriptionBuilder()
                .withAppointmentId(10005)
                .withMedicineName("Aspirin").build()))).build();
        Patient expectedPatient = new PatientBuilder()
                .withName("Iona Porter").withPhone("9482224")
                .withEmail("iona@example.com")
                .withAddress("24th ave")
                .withRemark("")
                .withTags("Patient")
                .withMedicalHistory("", "").build();
        Doctor expectedDoctor = new DoctorBuilder()
                .withName("Helena Sophia")
                .withPhone("95264283")
                .withEmail("helena@example.com")
                .withAddress("7th street")
                .withRemark("")
                .withTags("Doctor").build();

        expectedPatient.addUpcomingAppointment(expectedAppointment);
        expectedDoctor.addUpcomingAppointment(expectedAppointment);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(IONA_PATIENT_APPT, expectedPatient);
        expectedModel.updatePerson(HELENA_DOCTOR_APPT, expectedDoctor);
        expectedModel.setAppointment(SIXTH, expectedAppointment);
        expectedModel.deletePerson(expectedDoctor);
        expectedModel.commitAddressBook();

        DeleteDoctorCommand deleteDoctorCommand =
                new DeleteDoctorCommand(HELENA_DOCTOR_APPT.getName(), HELENA_DOCTOR_APPT.getPhone());

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, HELENA_DOCTOR_APPT);

        assertCommandSuccess(deleteDoctorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidDoctor_throwsCommandException() {
        // invalid name
        assertCommandFailure(new DeleteDoctorCommand(new Name("JASKLFJA12412445"), null),
                model, commandHistory, String.format(DeleteDoctorCommand.MESSAGE_INVALID_DELETE_PERSON, "Doctor"));

        // not doctor
        assertCommandFailure(new DeleteDoctorCommand(IONA_PATIENT_APPT.getName(), null),
                model, commandHistory, String.format(DeleteDoctorCommand.MESSAGE_INVALID_DELETE_PERSON, "Doctor"));
    }

    @Test
    public void execute_duplicateDoctor_throwsCommandException() {
        // duplicate patient
        model.addDoctor(new DoctorBuilder().withName(HELENA_DOCTOR_APPT.getName().toString())
                .withPhone("1234111").build());
        assertCommandFailure(new DeleteDoctorCommand(HELENA_DOCTOR_APPT.getName(), null),
                model, commandHistory, String.format(DeletePatientCommand.MESSAGE_DUPLICATE_DELETE_PERSON, "Doctor",
                        "Doctor", "Doctor"));
    }

    @Test
    public void equals() {
        DeletePatientCommand deleteFirstCommand = new DeletePatientCommand(ALICE_PATIENT_APPT.getName(), null);
        DeletePatientCommand deleteSecondCommand = new DeletePatientCommand(BENSON_PATIENT_APPT.getName(), null);
        DeleteDoctorCommand deleteThirdCommand = new DeleteDoctorCommand(FIONA_DOCTOR_APPT.getName(), null);
        DeleteDoctorCommand deleteFourthCommand = new DeleteDoctorCommand(GEORGE_DOCTOR_APPT.getName(), null);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteThirdCommand.equals(deleteThirdCommand));

        // same values -> returns true
        DeletePatientCommand deleteFirstCommandCopy = new DeletePatientCommand(ALICE_PATIENT_APPT.getName(),
                null);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
        DeleteDoctorCommand deleteThirdCommandCopy = new DeleteDoctorCommand(FIONA_DOCTOR_APPT.getName(),
                null);
        assertTrue(deleteThirdCommandCopy.equals(deleteThirdCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteThirdCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));
        assertFalse(deleteThirdCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
        assertFalse(deleteThirdCommand.equals(deleteFourthCommand));
    }
}
