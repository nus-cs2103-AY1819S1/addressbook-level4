package seedu.address.logic.commands.timetable;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDates.DATE_A;
import static seedu.address.testutil.TypicalDates.DATE_RECESS;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserData;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.UserBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListScheduleCommand.
 */
public class ListScheduleCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserData(), new UserBuilder().build());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserData(),
                new UserBuilder().build());
    }

    @Test
    public void execute_listScheduleNumber_showsSameListSchedule() {
        assertCommandSuccess(new ListScheduleCommand(DATE_A.getWeek()), model, commandHistory,
               String.format(ListScheduleCommand.MESSAGE_SUCCESS, DATE_A.getWeek()), expectedModel);
    }

    @Test
    public void execute_listScheduleRecess_showsSameListSchedule() {
        assertCommandSuccess(new ListScheduleCommand(DATE_RECESS.getWeek()), model, commandHistory,
                String.format(ListScheduleCommand.MESSAGE_SUCCESS, DATE_RECESS.getWeek()), expectedModel);
    }

}
