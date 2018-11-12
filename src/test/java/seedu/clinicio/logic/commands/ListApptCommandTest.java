package seedu.clinicio.logic.commands;

//@@author gingivitiss

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.appointment.AppointmentContainsDatePredicate;


public class ListApptCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
        expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
    }

    @Test
    public void execute_listIsFiltered_showsSameList() {
        String[] date = {"3", "10", "2018"};
        AppointmentContainsDatePredicate predicate = new AppointmentContainsDatePredicate(date);

        expectedModel.updateFilteredAppointmentList(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW,
                expectedModel.getFilteredAppointmentList().size());

        assertCommandSuccess(new ListApptCommand(predicate), model, commandHistory, expectedMessage, expectedModel,
                analytics);
    }

    @Test
    public void equals() {
        String[] firstDate = {"3", "10", "2018"};
        String[] secondDate = {"2", "10", "2018"};

        AppointmentContainsDatePredicate firstPredicate =
                new AppointmentContainsDatePredicate(firstDate);
        AppointmentContainsDatePredicate secondPredicate =
                new AppointmentContainsDatePredicate(secondDate);

        ListApptCommand listApptFirstCommand = new ListApptCommand(firstPredicate);
        ListApptCommand listApptSecondCommand = new ListApptCommand(secondPredicate);

        // same object -> returns true
        assertTrue(listApptFirstCommand.equals(listApptFirstCommand));

        // same values -> returns true
        ListApptCommand listApptFirstCommandCopy = new ListApptCommand(firstPredicate);
        assertTrue(listApptFirstCommand.equals(listApptFirstCommandCopy));

        // different types -> returns false
        assertFalse(listApptFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listApptFirstCommand.equals(null));

        // different appointment -> returns false
        assertFalse(listApptFirstCommand.equals(listApptSecondCommand));
    }
}
