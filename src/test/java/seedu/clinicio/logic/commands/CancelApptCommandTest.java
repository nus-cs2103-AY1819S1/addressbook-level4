package seedu.clinicio.logic.commands;

//@@author gingivitiss

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Test;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.AppointmentContainsDatePredicate;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;

public class CancelApptCommandTest {
    private Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appointmentToCancel = model.getFilteredAppointmentList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Appointment expectedAppointment = appointmentToCancel;
        expectedAppointment.cancelAppointment();
        CancelApptCommand cancelApptCommand = new CancelApptCommand(INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(CancelApptCommand.MESSAGE_CANCEL_APPOINTMENT_SUCCESS,
                expectedAppointment);

        ModelManager expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.cancelAppointment(appointmentToCancel);
        expectedModel.commitClinicIo();

        assertCommandSuccess(cancelApptCommand, model, commandHistory, expectedMessage, expectedModel, analytics);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        CancelApptCommand cancelApptCommand = new CancelApptCommand(outOfBoundIndex);

        assertCommandFailure(cancelApptCommand, model, commandHistory, analytics,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        String[] date = {"3", "10", "2018"};

        model.updateFilteredAppointmentList(new AppointmentContainsDatePredicate(date));
        Appointment appointmentToCancel = model.getFilteredAppointmentList().get(INDEX_FIRST_PATIENT.getZeroBased());

        //duplicate for expectedMessage
        Appointment expectedAppointment = appointmentToCancel;

        Index index = Index.fromOneBased(1);
        CancelApptCommand cancelApptCommand = new CancelApptCommand(index);

        expectedAppointment.cancelAppointment();
        String expectedMessage = String.format(CancelApptCommand.MESSAGE_CANCEL_APPOINTMENT_SUCCESS,
                expectedAppointment);

        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.updateFilteredAppointmentList(new AppointmentContainsDatePredicate(date));
        expectedModel.cancelAppointment(appointmentToCancel);
        expectedModel.commitClinicIo();

        assertCommandSuccess(cancelApptCommand, model, commandHistory, expectedMessage, expectedModel, analytics);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        String[] date = {"3", "10", "2018"};
        Index outOfBoundIndex = INDEX_SECOND_PATIENT;

        model.updateFilteredAppointmentList(new AppointmentContainsDatePredicate(date));

        // ensures that outOfBoundIndex is still in bounds of ClinicIO list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getClinicIo().getAppointmentList().size());
        CancelApptCommand cancelApptCommand = new CancelApptCommand(outOfBoundIndex);

        assertCommandFailure(cancelApptCommand, model, commandHistory, analytics,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Appointment appointmentToCancel = model.getFilteredAppointmentList().get(INDEX_FIRST_PATIENT.getZeroBased());
        CancelApptCommand cancelApptCommand = new CancelApptCommand(INDEX_FIRST_PATIENT);
        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.cancelAppointment(appointmentToCancel);
        expectedModel.commitClinicIo();

        // delete -> first appointment cancelled
        cancelApptCommand.execute(model, commandHistory);

        // undo -> reverts ClinicIO back to previous state and filtered appointment list to show all appointments
        expectedModel.undoClinicIo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel,
                analytics);

        // redo -> same first appointment cancelled again
        expectedModel.redoClinicIo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel,
                analytics);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        CancelApptCommand cancelApptCommand = new CancelApptCommand(outOfBoundIndex);

        // execution failed -> ClinicIO state not added into model
        assertCommandFailure(cancelApptCommand, model, commandHistory, analytics,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);

        // single ClinicIO state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, analytics, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, analytics, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes an {@code Appointment} from a filtered list.
     * 2. Undo the cancellation.
     * 3. Verify that the index of the previously cancelled appointment in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the cancellation. This ensures {@code RedoCommand} deletes the appointment object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        String[] date = {"3", "10", "2018"};
        CancelApptCommand cancelApptCommand = new CancelApptCommand(INDEX_FIRST_PATIENT);
        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());

        model.updateFilteredAppointmentList(new AppointmentContainsDatePredicate(date));
        Appointment appointmentToCancel = model.getFilteredAppointmentList().get(INDEX_FIRST_PATIENT.getZeroBased());
        expectedModel.cancelAppointment(appointmentToCancel);
        expectedModel.commitClinicIo();

        // cancel -> cancels 1st appointment in the filtered appointment list
        cancelApptCommand.execute(model, commandHistory);

        // undo -> reverts ClinicIO back to previous state and filtered appointment list to show all appointments
        expectedModel.undoClinicIo();
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel,
                analytics);

        assertNotEquals(appointmentToCancel, model.getFilteredAppointmentList().get(INDEX_FIRST_PATIENT.getZeroBased()));

        // redo -> deletes same appointment in unfiltered appointment list
        expectedModel.redoClinicIo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel,
                analytics);
    }

    @Test
    public void equals() {
        CancelApptCommand cancelApptFirstCommand = new  CancelApptCommand(INDEX_FIRST_PATIENT);
        CancelApptCommand cancelApptSecondCommand = new  CancelApptCommand(INDEX_SECOND_PATIENT);

        // same object -> returns true
        assertTrue(cancelApptFirstCommand.equals(cancelApptFirstCommand));

        // same values -> returns true
        CancelApptCommand cancelApptFirstCommandCopy = new CancelApptCommand(INDEX_FIRST_PATIENT);
        assertTrue(cancelApptFirstCommand.equals(cancelApptFirstCommandCopy));

        // different types -> returns false
        assertFalse(cancelApptFirstCommand.equals(1));

        // null -> returns false
        assertFalse(cancelApptFirstCommand.equals(null));

        // different appointment -> returns false
        assertFalse(cancelApptFirstCommand.equals(cancelApptSecondCommand));
    }
}
