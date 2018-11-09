package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddAllDayEventCommand.MESSAGE_EXISTING_EVENT;
import static seedu.address.logic.commands.AddEventCommand.MESSAGE_NOT_EXISTING_CALENDAR;
import static seedu.address.logic.commands.AddEventCommand.MESSAGE_NOT_VALID_DATE;
import static seedu.address.logic.commands.AddEventCommand.MESSAGE_NOT_VALID_TIME;
import static seedu.address.logic.commands.AddEventCommand.MESSAGE_NOT_VALID_TIMEFRAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CALENDAR_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOUR_NEG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOUR_OUTOFBOUND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_NEG;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MIN_OUTOFBOUND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_HACK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_OCAMP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EHOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SHOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2017;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_END_DAY;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_END_HOUR;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_END_MIN;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_MONTH;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_START_DAY;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_START_HOUR;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_START_MIN;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_TITLE;
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
public class AddEventCommandTest {
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
        new AddEventCommand(null, VALID_YEAR_2018, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
    }

    @Test
    public void constructor_nullYear_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(VALID_MONTH_JAN, null, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
    }

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, null);
    }

    @Test
    public void execute_validArguments_success() {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddEventCommand addEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);

        // Expected result
        String expectedMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, VALID_CALENDAR_DATE_1 + "/"
                + DEFAULT_MONTH + "/" + DEFAULT_YEAR + " - " + VALID_CALENDAR_DATE_2 + "/" + DEFAULT_MONTH + "/"
                + DEFAULT_YEAR + " [" + VALID_CALENDAR_TITLE_OCAMP + "]");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
                model.getExistingEmails());
        // Update expected model's existing calendars
        expectedModel.getCalendarModel().updateExistingCalendar(DEFAULT_YEAR, DEFAULT_MONTH);
        expectedModel.updateExistingCalendar();

        assertCommandSuccess(addEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingEvent_success() {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddEventCommand addEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, DEFAULT_START_DAY, DEFAULT_START_HOUR,
                        DEFAULT_START_MIN, DEFAULT_END_DAY, DEFAULT_END_HOUR, DEFAULT_END_MIN, DEFAULT_TITLE);

        // Expected result
        String expectedMessage = String.format(MESSAGE_EXISTING_EVENT, DEFAULT_MONTH + "-" + DEFAULT_YEAR);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
                model.getExistingEmails());
        // Update expected model's existing calendars
        expectedModel.getCalendarModel().updateExistingCalendar(DEFAULT_YEAR, DEFAULT_MONTH);
        expectedModel.updateExistingCalendar();

        assertCommandSuccess(addEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notExistingCalendar_throwsCommandException() throws Exception {
        AddEventCommand addEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NOT_EXISTING_CALENDAR);
        addEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidStartDate_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddEventCommand addEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, INVALID_CALENDAR_DATE, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_NOT_VALID_DATE, INVALID_CALENDAR_DATE + "/" + DEFAULT_MONTH + "/"
                + DEFAULT_YEAR));
        addEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidEndDate_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddEventCommand addEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        INVALID_CALENDAR_DATE, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_NOT_VALID_DATE, INVALID_CALENDAR_DATE + "/" + DEFAULT_MONTH + "/"
                + DEFAULT_YEAR));
        addEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidStartTime_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddEventCommand addEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, INVALID_HOUR_OUTOFBOUND,
                        INVALID_MIN_OUTOFBOUND, VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN,
                        VALID_CALENDAR_TITLE_OCAMP);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_NOT_VALID_TIME, INVALID_HOUR_OUTOFBOUND + ":"
                + INVALID_MIN_OUTOFBOUND));
        addEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidEndTime_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddEventCommand addEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR,
                        VALID_SMIN, VALID_CALENDAR_DATE_2, INVALID_HOUR_NEG, INVALID_MIN_NEG,
                        VALID_CALENDAR_TITLE_OCAMP);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_NOT_VALID_TIME, INVALID_HOUR_NEG + ":"
                + INVALID_MIN_NEG));
        addEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidTimeFrame_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddEventCommand addEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_EHOUR,
                        VALID_EMIN, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_TITLE_OCAMP);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NOT_VALID_TIMEFRAME);
        addEventCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        AddEventCommand addJan2018010800021730OcampEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        AddEventCommand addFeb2018010800021730OcampEventCommand =
                new AddEventCommand(VALID_MONTH_FEB, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        AddEventCommand addJan2017010800021730OcampEventCommand =
                new AddEventCommand(DEFAULT_MONTH, VALID_YEAR_2017, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        AddEventCommand addJan2018020800021730OcampEventCommand =
                new AddEventCommand(VALID_MONTH_FEB, VALID_YEAR_2017, VALID_CALENDAR_DATE_2, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        AddEventCommand addJan2018010800011730OcampEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_1, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        AddEventCommand addJan2018010800021730HackEventCommand =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_HACK);

        // same object -> returns true
        assertTrue(addJan2018010800021730OcampEventCommand.equals(addJan2018010800021730OcampEventCommand));

        // same values -> returns true
        AddEventCommand addJan2018010800021730OcampEventCommandCopy =
                new AddEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1, VALID_SHOUR, VALID_SMIN,
                        VALID_CALENDAR_DATE_2, VALID_EHOUR, VALID_EMIN, VALID_CALENDAR_TITLE_OCAMP);
        assertTrue(addJan2018010800021730OcampEventCommand.equals(addJan2018010800021730OcampEventCommandCopy));

        // different types -> returns false
        assertFalse(addJan2018010800021730OcampEventCommand.equals(1));

        // null -> returns false
        assertFalse(addJan2018010800021730OcampEventCommand.equals(null));

        // different month -> returns false
        assertFalse(addJan2018010800021730OcampEventCommand.equals(addFeb2018010800021730OcampEventCommand));

        // different year -> returns false
        assertFalse(addJan2018010800021730OcampEventCommand.equals(addJan2017010800021730OcampEventCommand));

        // different start date -> returns false
        assertFalse(addJan2018010800021730OcampEventCommand.equals(addJan2018020800021730OcampEventCommand));

        // different end date -> returns false
        assertFalse(addJan2018010800021730OcampEventCommand.equals(addJan2018010800011730OcampEventCommand));

        // different title -> returns false
        assertFalse(addJan2018010800021730OcampEventCommand.equals(addJan2018010800021730HackEventCommand));
    }
}
