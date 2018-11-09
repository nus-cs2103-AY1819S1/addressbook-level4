package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddAllDayEventCommand.MESSAGE_EXISTING_EVENT;
import static seedu.address.logic.commands.AddAllDayEventCommand.MESSAGE_NOT_EXISTING_CALENDAR;
import static seedu.address.logic.commands.AddAllDayEventCommand.MESSAGE_NOT_VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CALENDAR_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_Hack;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_TITLE_Ocamp;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2017;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_MONTH;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_YEAR;
import static seedu.address.testutil.TypicalCalendars.CHRISTMAS_CALENDAR_MONTH;
import static seedu.address.testutil.TypicalCalendars.CHRISTMAS_CALENDAR_NAME;
import static seedu.address.testutil.TypicalCalendars.CHRISTMAS_CALENDAR_YEAR;
import static seedu.address.testutil.TypicalCalendars.CHRISTMAS_EVENT_DATE;
import static seedu.address.testutil.TypicalCalendars.CHRISTMAS_EVENT_TITLE;
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
public class AddAllDayEventCommandTest {
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
        new AddAllDayEventCommand(null, VALID_YEAR_2018, VALID_CALENDAR_DATE_1, VALID_CALENDAR_TITLE_Ocamp);
    }

    @Test
    public void constructor_nullYear_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAllDayEventCommand(VALID_MONTH_JAN, null, VALID_CALENDAR_DATE_1, VALID_CALENDAR_TITLE_Ocamp);
    }

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAllDayEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1, null);
    }

    @Test
    public void execute_validMonthYearDateTitle_success() {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddAllDayEventCommand addAllDayEventCommand =
                new AddAllDayEventCommand(DEFAULT_MONTH, DEFAULT_YEAR, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_TITLE_Ocamp);

        // Expected result
        String expectedMessage = String.format(AddAllDayEventCommand.MESSAGE_SUCCESS, VALID_CALENDAR_DATE_1 + "/"
                + DEFAULT_MONTH + "/" + DEFAULT_YEAR + " - " + VALID_CALENDAR_TITLE_Ocamp);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
                model.getExistingEmails());
        // Update expected model's existing calendars
        expectedModel.getCalendarModel().updateExistingCalendar(DEFAULT_YEAR, DEFAULT_MONTH);
        expectedModel.updateExistingCalendar();

        assertCommandSuccess(addAllDayEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingEvent_success() {
        Calendar calendar = TypicalCalendars.CHRISTMAS_CALENDAR;
        updateExistingCalendarsInModel(CHRISTMAS_CALENDAR_YEAR, CHRISTMAS_CALENDAR_MONTH);
        loadCalendarInModel(calendar, CHRISTMAS_CALENDAR_NAME);
        AddAllDayEventCommand addAllDayEventCommand = new AddAllDayEventCommand(CHRISTMAS_CALENDAR_MONTH,
                CHRISTMAS_CALENDAR_YEAR, CHRISTMAS_EVENT_DATE, CHRISTMAS_EVENT_TITLE);

        // Expected result
        String expectedMessage = String.format(MESSAGE_EXISTING_EVENT, CHRISTMAS_CALENDAR_MONTH
                + "-" + CHRISTMAS_CALENDAR_YEAR);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
                model.getExistingEmails());
        // Update expected model's existing calendars
        expectedModel.getCalendarModel().updateExistingCalendar(CHRISTMAS_CALENDAR_YEAR, CHRISTMAS_CALENDAR_MONTH);
        expectedModel.updateExistingCalendar();

        assertCommandSuccess(addAllDayEventCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notExistingCalendar_throwsCommandException() throws Exception {
        AddAllDayEventCommand addAllDayEventCommand = new AddAllDayEventCommand(VALID_MONTH_FEB, VALID_YEAR_2018,
                VALID_CALENDAR_DATE_1, VALID_CALENDAR_TITLE_Ocamp);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NOT_EXISTING_CALENDAR);
        addAllDayEventCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notValidDate_throwsCommandException() throws Exception {
        Calendar calendar = TypicalCalendars.DEFAULT_CALENDAR;
        updateExistingCalendarsInModel(DEFAULT_YEAR, DEFAULT_MONTH);
        loadCalendarInModel(calendar, DEFAULT_CALENDAR_NAME);
        AddAllDayEventCommand addAllDayEventCommand = new AddAllDayEventCommand(DEFAULT_MONTH, DEFAULT_YEAR,
                INVALID_CALENDAR_DATE, VALID_CALENDAR_TITLE_Ocamp);
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(MESSAGE_NOT_VALID_DATE, DEFAULT_MONTH + " - " + DEFAULT_YEAR));
        addAllDayEventCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        AddAllDayEventCommand add1stJan2018OcampAllDayEventCommand =
                new AddAllDayEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_TITLE_Ocamp);
        AddAllDayEventCommand add2ndJan2018OcampAllDayEventCommand =
                new AddAllDayEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_2,
                        VALID_CALENDAR_TITLE_Ocamp);
        AddAllDayEventCommand add1stFeb2018OcampAllDayEventCommand =
                new AddAllDayEventCommand(VALID_MONTH_FEB, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_TITLE_Ocamp);
        AddAllDayEventCommand add1stJan2017OcampAllDayEventCommand =
                new AddAllDayEventCommand(VALID_MONTH_JAN, VALID_YEAR_2017, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_TITLE_Ocamp);
        AddAllDayEventCommand add1stJan2018HackathonAllDayEventCommand =
                new AddAllDayEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_TITLE_Hack);

        // same object -> returns true
        assertTrue(add1stJan2018OcampAllDayEventCommand.equals(add1stJan2018OcampAllDayEventCommand));

        // same values -> returns true
        AddAllDayEventCommand add1stJan2018OcampAllDayEventCopy =
                new AddAllDayEventCommand(VALID_MONTH_JAN, VALID_YEAR_2018, VALID_CALENDAR_DATE_1,
                        VALID_CALENDAR_TITLE_Ocamp);
        assertTrue(add1stJan2018OcampAllDayEventCommand.equals(add1stJan2018OcampAllDayEventCopy));

        // different types -> returns false
        assertFalse(add1stJan2018OcampAllDayEventCommand.equals(1));

        // null -> returns false
        assertFalse(add1stJan2018OcampAllDayEventCommand.equals(null));

        // different month -> returns false
        assertFalse(add1stJan2018OcampAllDayEventCommand.equals(add1stFeb2018OcampAllDayEventCommand));

        // different year -> returns false
        assertFalse(add1stJan2018OcampAllDayEventCommand.equals(add1stJan2017OcampAllDayEventCommand));

        // different date -> returns false
        assertFalse(add1stJan2018OcampAllDayEventCommand.equals(add2ndJan2018OcampAllDayEventCommand));

        // different title -> returns false
        assertFalse(add1stJan2018OcampAllDayEventCommand.equals(add1stJan2018HackathonAllDayEventCommand));
    }
}
