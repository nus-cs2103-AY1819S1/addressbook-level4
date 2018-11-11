package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2017;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewCalendarCommand.MESSAGE_NOT_EXISTING_CALENDAR;
import static seedu.address.logic.commands.ViewCalendarCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_MONTH;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_YEAR;
import static seedu.address.testutil.TypicalCalendars.DEFAULT_CALENDAR_NAME;
import static seedu.address.testutil.TypicalEmails.getTypicalExistingEmails;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.fortuna.ical4j.model.Calendar;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.testutil.TypicalCalendars;

//@@author GilgameshTC
public class ViewCalendarCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), new BudgetBook(), new UserPrefs(),
            getTypicalExistingEmails());

    private void updateExistingCalendarsInModel(Year year, Month month) {
        model.getCalendarModel().updateExistingCalendar(year, month);
        model.updateExistingCalendar();
    }

    private void loadCalendarInModel(Calendar calendar, String calendarName) {
        model.getCalendarModel().loadCalendar(calendar, calendarName);
    }

    @Test
    public void constructor_nullMonth_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ViewCalendarCommand(null, VALID_YEAR_2018);
    }

    @Test
    public void constructor_nullYear_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ViewCalendarCommand(VALID_MONTH_JAN, null);
    }

    @Test
    public void execute_validMonthYear_success() {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(DEFAULT_MONTH, DEFAULT_YEAR);

        String expectedMessage = String.format(MESSAGE_SUCCESS, DEFAULT_MONTH + "-" + DEFAULT_YEAR + ".ics");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
                model.getExistingEmails());
        // Update expected model's existing calendars
        expectedModel.getCalendarModel().updateExistingCalendar(DEFAULT_YEAR, DEFAULT_MONTH);
        expectedModel.updateExistingCalendar();

        assertCommandSuccess(viewCalendarCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notExistingCalendar_throwsCommandException() throws Exception {
        ViewCalendarCommand viewCalendarCommand = new ViewCalendarCommand(DEFAULT_MONTH, DEFAULT_YEAR);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NOT_EXISTING_CALENDAR);
        viewCalendarCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        ViewCalendarCommand viewJan2018CalendarCommand =
                new ViewCalendarCommand(VALID_MONTH_JAN, VALID_YEAR_2018);
        ViewCalendarCommand viewFeb2018CalendarCommand =
                new ViewCalendarCommand(VALID_MONTH_FEB, VALID_YEAR_2018);
        ViewCalendarCommand viewJan2017CalendarCommand =
                new ViewCalendarCommand(VALID_MONTH_JAN, VALID_YEAR_2017);

        // same object -> returns true
        assertTrue(viewJan2018CalendarCommand.equals(viewJan2018CalendarCommand));

        // same values -> returns true
        ViewCalendarCommand viewJan2018CalendarCommandCopy =
                new ViewCalendarCommand(VALID_MONTH_JAN, VALID_YEAR_2018);
        assertTrue(viewJan2018CalendarCommand.equals(viewJan2018CalendarCommandCopy));

        // different types -> returns false
        assertFalse(viewJan2018CalendarCommand.equals(1));

        // null -> returns false
        assertFalse(viewJan2018CalendarCommand.equals(null));

        // different month same year -> returns false
        assertFalse(viewJan2018CalendarCommand.equals(viewFeb2018CalendarCommand));

        // different year same month -> returns false
        assertFalse(viewJan2018CalendarCommand.equals(viewJan2017CalendarCommand));
    }
}
