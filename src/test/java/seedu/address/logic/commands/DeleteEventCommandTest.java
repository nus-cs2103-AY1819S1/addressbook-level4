package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CALENDAR_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_HACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_OCAMP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2017;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteEventCommand.MESSAGE_NOT_EXISTING_CALENDAR;
import static seedu.address.logic.commands.DeleteEventCommand.MESSAGE_NOT_EXISTING_EVENT;
import static seedu.address.logic.commands.DeleteEventCommand.MESSAGE_NOT_VALID_DATE;
import static seedu.address.logic.commands.DeleteEventCommand.MESSAGE_NOT_VALID_TIMEFRAME;
import static seedu.address.logic.commands.DeleteEventCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_END_DAY;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_MONTH;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_START_DAY;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_TITLE;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_YEAR;
import static seedu.address.testutil.TypicalCalendars.CHRISTMAS_CALENDAR_MONTH;
import static seedu.address.testutil.TypicalCalendars.CHRISTMAS_CALENDAR_NAME;
import static seedu.address.testutil.TypicalCalendars.CHRISTMAS_CALENDAR_YEAR;
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
public class DeleteEventCommandTest {
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
        new DeleteEventCommand(null, VALID_YEAR_2018, VALID_CALENDAR_DATE_1, VALID_CALENDAR_DATE_2,
                VALID_CALENDAR_TITLE_OCAMP);
    }

    @Test
    public void constructor_nullYear_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteEventCommand(VALID_MONTH_JAN, null, VALID_CALENDAR_DATE_1, VALID_CALENDAR_DATE_2,
                VALID_CALENDAR_TITLE_OCAMP);
    }

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1, VALID_CALENDAR_DATE_2,
                null);
    }

    @Test
    public void execute_validMonthYearStartDateEndDateTitle_success() {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        DeleteEventCommand deleteEventCommand =
                new DeleteEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, DEFAULT_START_DAY, DEFAULT_END_DAY,
                        DEFAULT_TITLE);

        // Expected result
        String expectedMessage = String.format(MESSAGE_SUCCESS, DEFAULT_START_DAY + "/" + DEFAULT_MONTH + "/"
                + DEFAULT_YEAR + " - " + DEFAULT_END_DAY + "/" + DEFAULT_MONTH + "/" + DEFAULT_YEAR
                + " [" + DEFAULT_TITLE + "]");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
                model.getExistingEmails());
        // Update expected model's existing calendars
        expectedModel.getCalendarModel().updateExistingCalendar(DEFAULT_YEAR, DEFAULT_MONTH);
        expectedModel.updateExistingCalendar();

        assertCommandSuccess(deleteEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notExistingEvent_success() {
        Calendar calendar = TypicalCalendars.CHRISTMAS_CALENDAR;
        updateExistingCalendarsInModel(CHRISTMAS_CALENDAR_YEAR, CHRISTMAS_CALENDAR_MONTH);
        loadCalendarInModel(calendar, CHRISTMAS_CALENDAR_NAME);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(CHRISTMAS_CALENDAR_MONTH,
                CHRISTMAS_CALENDAR_YEAR, DEFAULT_START_DAY, DEFAULT_END_DAY, DEFAULT_TITLE);

        // Expected result
        String expectedMessage = String.format(MESSAGE_NOT_EXISTING_EVENT, CHRISTMAS_CALENDAR_MONTH + "-"
                + CHRISTMAS_CALENDAR_YEAR);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
                model.getExistingEmails());
        // Update expected model's existing calendars
        expectedModel.getCalendarModel().updateExistingCalendar(CHRISTMAS_CALENDAR_YEAR, CHRISTMAS_CALENDAR_MONTH);
        expectedModel.updateExistingCalendar();

        assertCommandSuccess(deleteEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notExistingCalendar_throwsCommandException() throws Exception {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(CHRISTMAS_CALENDAR_MONTH,
                CHRISTMAS_CALENDAR_YEAR, DEFAULT_START_DAY, DEFAULT_END_DAY, DEFAULT_TITLE);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NOT_EXISTING_CALENDAR);
        deleteEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidStartDate_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(DEFAULT_MONTH, DEFAULT_YEAR,
                INVALID_CALENDAR_DATE, DEFAULT_END_DAY, DEFAULT_TITLE);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_NOT_VALID_DATE, INVALID_CALENDAR_DATE + "/"
                + DEFAULT_MONTH + "/" + DEFAULT_YEAR));
        deleteEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidEndDate_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(DEFAULT_MONTH, DEFAULT_YEAR,
                DEFAULT_START_DAY, INVALID_CALENDAR_DATE, DEFAULT_TITLE);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_NOT_VALID_DATE, INVALID_CALENDAR_DATE + "/"
                + DEFAULT_MONTH + "/" + DEFAULT_YEAR));
        deleteEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidTimeFrame_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(DEFAULT_MONTH, DEFAULT_YEAR,
                DEFAULT_END_DAY, DEFAULT_START_DAY, DEFAULT_TITLE);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NOT_VALID_TIMEFRAME);
        deleteEventCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteJan20180102OcampEventCommand =
                new DeleteEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_DATE_2, VALID_CALENDAR_TITLE_OCAMP);
        DeleteEventCommand deleteFeb20180102OcampEventCommand =
                new DeleteEventCommand(VALID_MONTH_FEB, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_DATE_2, VALID_CALENDAR_TITLE_OCAMP);
        DeleteEventCommand deleteJan20170102OcampEventCommand =
                new DeleteEventCommand(VALID_MONTH_JAN, VALID_YEAR_2017, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_DATE_2, VALID_CALENDAR_TITLE_OCAMP);
        DeleteEventCommand deleteJan20180202OcampEventCommand =
                new DeleteEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_2,
                        VALID_CALENDAR_DATE_2, VALID_CALENDAR_TITLE_OCAMP);
        DeleteEventCommand deleteJan20180101OcampEventCommand =
                new DeleteEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_DATE_1, VALID_CALENDAR_TITLE_OCAMP);
        DeleteEventCommand deleteJan20180102HackEventCommand =
                new DeleteEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_DATE_2, VALID_CALENDAR_TITLE_HACK);

        // same object -> returns true
        assertTrue(deleteJan20180102OcampEventCommand.equals(deleteJan20180102OcampEventCommand));

        // same values -> returns true
        DeleteEventCommand deleteJan20180102OcampEventCommandCopy =
                new DeleteEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_DATE_2, VALID_CALENDAR_TITLE_OCAMP);
        assertTrue(deleteJan20180102OcampEventCommand.equals(deleteJan20180102OcampEventCommandCopy));

        // different types -> returns false
        assertFalse(deleteJan20180102OcampEventCommand.equals(1));

        // null -> returns false
        assertFalse(deleteJan20180102OcampEventCommand.equals(null));

        // different month -> returns false
        assertFalse(deleteJan20180102OcampEventCommand.equals(deleteFeb20180102OcampEventCommand));

        // different year -> returns false
        assertFalse(deleteJan20180102OcampEventCommand.equals(deleteJan20170102OcampEventCommand));

        // different start date -> returns false
        assertFalse(deleteJan20180102OcampEventCommand.equals(deleteJan20180202OcampEventCommand));

        // different end date -> returns false
        assertFalse(deleteJan20180102OcampEventCommand.equals(deleteJan20180101OcampEventCommand));

        // different title -> returns false
        assertFalse(deleteJan20180102OcampEventCommand.equals(deleteJan20180102HackEventCommand));

    }
}
