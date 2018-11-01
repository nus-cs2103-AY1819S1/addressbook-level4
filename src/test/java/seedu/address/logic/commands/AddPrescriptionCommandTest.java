package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONSUMPTION_PER_DAY_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONSUMPTION_PER_DAY_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSAGE_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSAGE_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPatientsAndDoctors.getTypicalAddressBookWithPatientAndDoctor;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.ConsumptionPerDay;
import seedu.address.model.appointment.Dosage;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.PrescriptionBuilder;


/**
 * Contains Integration tests and unit tests for AddPrescriptionCommand
 */

public class AddPrescriptionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithPatientAndDoctor(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(0);
        Prescription toAdd = new PrescriptionBuilder().withAppointmentId(firstAppointment.getAppointmentId()).build();

        Appointment editedappointment = new AppointmentBuilder(firstAppointment).build();
        editedappointment.getPrescriptions().add(toAdd);

        AddPrescriptionCommand addPrescriptionCommand = new AddPrescriptionCommand(toAdd);

        String expectedMessage = String.format(AddPrescriptionCommand.MESSAGE_SUCCESS, toAdd.getMedicineName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateAppointment(firstAppointment, editedappointment);
        expectedModel.commitAddressBook();

        //assertCommandSuccess(addPrescriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePrescriptionUnfilteredList_failure() {
        Appointment appointmentInList = model.getAddressBook().getAppointmentList().get(0);
        Prescription toAdd = new PrescriptionBuilder().withAppointmentId(appointmentInList.getAppointmentId()).build();
        appointmentInList.addPrescription(toAdd);
        AddPrescriptionCommand addPrescriptionCommand = new AddPrescriptionCommand(toAdd);

        assertCommandFailure(addPrescriptionCommand, model, commandHistory,
                addPrescriptionCommand.MESSAGE_DUPLICATE_PRESCRIPTION);
    }

    @Test
    public void equals() {
        Prescription toAdd = new Prescription(VALID_APPOINTMENT_ID_FIRST,
                new MedicineName(VALID_MEDICINE_NAME_PARACETAMOL),
                new Dosage(VALID_DOSAGE_PARACETAMOL),
                new ConsumptionPerDay(VALID_CONSUMPTION_PER_DAY_PARACETAMOL));
        final AddPrescriptionCommand standardCommand = new AddPrescriptionCommand(toAdd);

        // same values -> returns true
        AddPrescriptionCommand commandWithSameValues = new AddPrescriptionCommand(toAdd);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new FilterDoctorCommand()));


        //different values -> returns false
        Prescription other = new Prescription(VALID_APPOINTMENT_ID_SECOND,
                new MedicineName(VALID_MEDICINE_NAME_VICODIN),
                new Dosage(VALID_DOSAGE_VICODIN),
                new ConsumptionPerDay(VALID_CONSUMPTION_PER_DAY_VICODIN));
        assertFalse(standardCommand.equals(other));
    }
}
