package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.BENSON_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.CARL_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE_DOCTOR;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.ALICE_PATIENT_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FIFTH;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FIONA_DOCTOR_APPT;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.FIRST;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.THIRD;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt
        .getTypicalAddressBookWithPatientAndDoctorWithAppt;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.HealthBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;


/**
 * Contains integration tests and unit tests for AddAppointmentCommand.
 */
public class AddAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctorWithAppt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addAppointment_success() {
        Appointment expectedAppointment = new AppointmentBuilder()
                .withAppointmentId(10005)
                .withComments(null)
                .withDoctor("Fiona Kunz").withPatient("Alice Pauline")
                .withDateTime("2018-12-11 12:00").build();
        Patient expectedPatient = new PatientBuilder()
                .withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withEmail("alice@example.com")
                .withPhone("94351253")
                .withRemark("")
                .withTags("Patient")
                .withMedicalHistory("egg", "subhealth")
                .withAppointment(FIRST).build();
        Doctor expectedDoctor = new DoctorBuilder()
                .withName("Fiona Kunz")
                .withPhone("9482427")
                .withEmail("lydia@example.com")
                .withAddress("little tokyo")
                .withRemark("")
                .withTags("Doctor")
                .withAppointment(FIRST, THIRD, FIFTH).build();

        expectedPatient.addUpcomingAppointment(expectedAppointment);
        expectedDoctor.addUpcomingAppointment(expectedAppointment);

        Model expectedModel = new ModelManager(new HealthBook(model.getAddressBook()), new UserPrefs());

        for (int i = 0; i < 5; i++) {
            model.incrementAppointmentCounter();
            expectedModel.incrementAppointmentCounter();
        }

        expectedModel.incrementAppointmentCounter();
        expectedModel.updatePerson(ALICE_PATIENT_APPT, expectedPatient);
        expectedModel.updatePerson(FIONA_DOCTOR_APPT, expectedDoctor);
        expectedModel.addAppointment(expectedAppointment);
        expectedModel.commitAddressBook();

        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(ALICE_PATIENT_APPT.getName(), ALICE_PATIENT_APPT.getPhone(),
                        FIONA_DOCTOR_APPT.getName(), FIONA_DOCTOR_APPT.getPhone(), expectedAppointment.getDateTime());

        String expectedMessage = AddAppointmentCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(addAppointmentCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatient_throwsCommandException() {
        Name patientName = new Name("ASFASFASF");
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(patientName, null,
                        GEORGE_DOCTOR.getName(), GEORGE_DOCTOR.getPhone(),
                        LocalDateTime.of(2018, 10, 17, 18, 0));

        assertCommandFailure(addAppointmentCommand,
                model, commandHistory, AddAppointmentCommand.MESSAGE_INVALID_PATIENT);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient similarPatientDifferentNumber = new PatientBuilder().withName(CARL_PATIENT.getName().toString())
                .withPhone("12341234").build();

        model.addPatient(similarPatientDifferentNumber);
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(CARL_PATIENT.getName(), null,
                        GEORGE_DOCTOR.getName(), GEORGE_DOCTOR.getPhone(),
                        LocalDateTime.of(2018, 10, 17, 18, 0));

        assertCommandFailure(addAppointmentCommand,
                model, commandHistory, AddAppointmentCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidDoctor_throwsCommandException() {
        Name doctorName = new Name("ASFASFASF");
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(ALICE_PATIENT.getName(),
                        null, doctorName, null,
                        LocalDateTime.of(2018, 10, 17, 18, 0));

        assertCommandFailure(addAppointmentCommand,
                model, commandHistory, AddAppointmentCommand.MESSAGE_INVALID_DOCTOR);
    }

    @Test
    public void execute_duplicateDoctor_throwsCommandException() {
        Doctor similarDoctorDifferentNumber = new DoctorBuilder().withName(GEORGE_DOCTOR.getName().toString())
                .withPhone("12341234").build();
        model.addDoctor(similarDoctorDifferentNumber);
        AddAppointmentCommand addAppointmentCommand =
                new AddAppointmentCommand(CARL_PATIENT.getName(), CARL_PATIENT.getPhone(),
                        GEORGE_DOCTOR.getName(), null,
                        LocalDateTime.of(2018, 10, 17, 18, 0));

        assertCommandFailure(addAppointmentCommand,
                model, commandHistory, AddAppointmentCommand.MESSAGE_DUPLICATE_DOCTOR);
    }

    //    @Test
    //    public void execute_patientClashAppointment_throwsCommandException() {
    //        AddAppointmentCommand addAppointmentCommand =
    //                new AddAppointmentCommand(ALICE_PATIENT_APPT.getName(), ALICE_PATIENT_APPT.getPhone(),
    //                        GEORGE_DOCTOR_APPT.getName(), GEORGE_DOCTOR_APPT.getPhone(), FIRST.getDateTime());
    //
    //        assertCommandFailure(addAppointmentCommand, model, commandHistory,
    //                AddAppointmentCommand.MESSAGE_PATIENT_CLASH_APPOINTMENT);
    //    }
    //
    //    @Test
    //    public void execute_doctorClashAppointment_throwsCommandException() {
    //        AddAppointmentCommand addAppointmentCommand =
    //                new AddAppointmentCommand(DANIEL_PATIENT_APPT.getName(), DANIEL_PATIENT_APPT.getPhone(),
    //                        FIONA_DOCTOR_APPT.getName(), FIONA_DOCTOR_APPT.getPhone(), FIRST.getDateTime());
    //
    //        assertCommandFailure(addAppointmentCommand, model, commandHistory,
    //                AddAppointmentCommand.MESSAGE_DOCTOR_CLASH_APPOINTMENT);
    //    }

    @Test
    public void equals() {
        AddAppointmentCommand addAppointmentFirstCommand =
                new AddAppointmentCommand(ALICE_PATIENT.getName(), ALICE_PATIENT.getPhone(),
                        GEORGE_DOCTOR.getName(), GEORGE_DOCTOR.getPhone(), FIRST.getDateTime());
        AddAppointmentCommand addAppointmentSecondCommand =
                new AddAppointmentCommand(BENSON_PATIENT.getName(), BENSON_PATIENT.getPhone(),
                        GEORGE_DOCTOR.getName(), GEORGE_DOCTOR.getPhone(), FIRST.getDateTime());

        // same object -> returns true
        assertTrue(addAppointmentFirstCommand.equals(addAppointmentFirstCommand));

        // same values -> returns true
        AddAppointmentCommand addAppointmentFirstCommandCopy =
                new AddAppointmentCommand(ALICE_PATIENT.getName(), ALICE_PATIENT.getPhone(),
                        GEORGE_DOCTOR.getName(), GEORGE_DOCTOR.getPhone(), FIRST.getDateTime());
        assertTrue(addAppointmentFirstCommand.equals(addAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(addAppointmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addAppointmentFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addAppointmentFirstCommand.equals(addAppointmentSecondCommand));
    }

}
