package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONSUMPTION_PER_DAY_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSAGE_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatientsAndDoctorsWithAppt.getTypicalAddressBookWithPatientAndDoctorWithAppt;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PrescriptionBuilder;



/**
 * Contains Integration tests and unit tests for DeletePrescriptionCommand
 */

public class DeletePrescriptionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctorWithAppt(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecified_success() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(0);
        Prescription toDelete = firstAppointment.getPrescriptions().get(0);

        Appointment editedAppointment = new AppointmentBuilder(firstAppointment).build();
        editedAppointment.deletePrescription(toDelete.getMedicineName().toString());

        List<Person> personList = model.getFilteredPersonList();
        Doctor doctorToEdit = null;
        Patient patientToEdit = null;

        for (Person person : personList) {
            if (person instanceof Doctor) {
                if (firstAppointment.getDoctor().equals(person.getName().toString())) {
                    doctorToEdit = (Doctor) person;
                }
            }
            if (person instanceof Patient) {
                if (firstAppointment.getPatient().equals(person.getName().toString())) {
                    patientToEdit = (Patient) person;
                }
            }
            if (doctorToEdit != null && patientToEdit != null) {
                break;
            }
        }

        Patient editedPatient = new PatientBuilder(patientToEdit).build();
        editedPatient.setAppointment(firstAppointment, editedAppointment);

        Doctor editedDoctor = new DoctorBuilder(doctorToEdit).build();
        editedDoctor.setAppointment(firstAppointment, editedAppointment);

        DeletePrescriptionCommand deletePrescriptionCommand = new DeletePrescriptionCommand(toDelete.getId(),
                toDelete.getMedicineName());

        String expectedMessage = String.format(DeletePrescriptionCommand.MESSAGE_DELETE_PRESCRIPTION_SUCCESS,
                toDelete.getMedicineName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateAppointment(firstAppointment, editedAppointment);
        expectedModel.updatePerson(patientToEdit, editedPatient);
        expectedModel.updatePerson(doctorToEdit, editedDoctor);
        expectedModel.commitAddressBook();
        assertCommandSuccess(deletePrescriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_prescriptionDoesNotExist_failure() {
        Appointment appointmentInList = model.getFilteredAppointmentList().get(0);
        Prescription toDelete = new PrescriptionBuilder()
                .withMedicineName("invalid name")
                .withAppointmentId(appointmentInList.getAppointmentId()).build();
        DeletePrescriptionCommand deletePrescriptionCommand = new DeletePrescriptionCommand(toDelete.getId(),
                toDelete.getMedicineName());

        assertCommandFailure(deletePrescriptionCommand, model, commandHistory,
                deletePrescriptionCommand.MESSAGE_INVALID_DELETE_PRESCRIPTION);
    }

    @Test
    public void execute_invalidAppointmentId_failure() {
        int outOfBoundsIndex = 1000000;
        Prescription toDelete = new PrescriptionBuilder().withAppointmentId(outOfBoundsIndex).build();
        DeletePrescriptionCommand deletePrescriptionCommand = new DeletePrescriptionCommand(outOfBoundsIndex,
                toDelete.getMedicineName());

        assertCommandFailure(deletePrescriptionCommand, model, commandHistory,
                deletePrescriptionCommand.MESSAGE_APPOINTMENT_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        Prescription toDelete = new PrescriptionBuilder().build();
        final DeletePrescriptionCommand standardCommand = new DeletePrescriptionCommand(toDelete.getId(),
                toDelete.getMedicineName());

        // same values -> returns true
        DeletePrescriptionCommand commandWithSameValues = new DeletePrescriptionCommand(toDelete.getId(),
                toDelete.getMedicineName());
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new FilterDoctorCommand()));

        // different values -> returns false
        Prescription other = new PrescriptionBuilder()
                .withAppointmentId(VALID_APPOINTMENT_ID_SECOND)
                .withMedicineName(VALID_MEDICINE_NAME_VICODIN)
                .withDosage(VALID_DOSAGE_VICODIN)
                .withConsumptionPerDay(VALID_CONSUMPTION_PER_DAY_VICODIN).build();
        assertFalse(standardCommand.equals(other));
    }
}
