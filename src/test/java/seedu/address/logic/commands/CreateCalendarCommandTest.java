package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2017;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmails.getTypicalExistingEmails;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author GilgameshTC
public class CreateCalendarCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model = new ModelManager(getTypicalAddressBook(), new BudgetBook(), new UserPrefs(),
            getTypicalExistingEmails());

    @Test
    public void constructor_nullMonth_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateCalendarCommand(null, VALID_YEAR_2018);
    }

    @Test
    public void constructor_nullYear_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateCalendarCommand(VALID_MONTH_JAN, null);
    }

    @Test
    public void execute_validMonthYear_success() {
        CreateCalendarCommand createCalendarCommand = new CreateCalendarCommand(VALID_MONTH_JAN, VALID_YEAR_2018);

        String expectedMessage = String.format(CreateCalendarCommand.MESSAGE_SUCCESS, VALID_MONTH_JAN + "-"
                + VALID_YEAR_2018 + ".ics");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new BudgetBook(), new UserPrefs(),
                model.getExistingEmails());
        expectedModel.createCalendar(VALID_YEAR_2018, VALID_MONTH_JAN);

        assertCommandSuccess(createCalendarCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingCalendar_throwsCommandException() throws Exception {
        model.createCalendar(VALID_YEAR_2018, VALID_MONTH_FEB);
        CreateCalendarCommand createCalendarCommand = new CreateCalendarCommand(VALID_MONTH_FEB, VALID_YEAR_2018);
        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateCalendarCommand.MESSAGE_EXISTING_CALENDAR);
        createCalendarCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        CreateCalendarCommand createJan2018CalendarCommand =
                new CreateCalendarCommand(VALID_MONTH_JAN, VALID_YEAR_2018);
        CreateCalendarCommand createFeb2018CalendarCommand =
                new CreateCalendarCommand(VALID_MONTH_FEB, VALID_YEAR_2018);
        CreateCalendarCommand createJan2017CalendarCommand =
                new CreateCalendarCommand(VALID_MONTH_JAN, VALID_YEAR_2017);

        // same object -> returns true
        assertTrue(createJan2018CalendarCommand.equals(createJan2018CalendarCommand));

        // same values -> returns true
        CreateCalendarCommand createJan2018CalendarCommandCopy =
                new CreateCalendarCommand(VALID_MONTH_JAN, VALID_YEAR_2018);
        assertTrue(createJan2018CalendarCommand.equals(createJan2018CalendarCommandCopy));

        // different types -> returns false
        assertFalse(createJan2018CalendarCommand.equals(1));

        // null -> returns false
        assertFalse(createJan2018CalendarCommand.equals(null));

        // different month same year -> returns false
        assertFalse(createJan2018CalendarCommand.equals(createFeb2018CalendarCommand));

        // different year same month -> returns false
        assertFalse(createJan2018CalendarCommand.equals(createJan2017CalendarCommand));

    }
}
