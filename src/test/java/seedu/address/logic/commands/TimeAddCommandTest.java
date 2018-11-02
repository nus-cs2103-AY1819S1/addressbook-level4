package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Time;

public class TimeAddCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String testName1 = "John Doe";
        String testName2 = "Mary Ray";
        Time testTime1 = new Time("mon 1300 1500");
        Time testTime2 = new Time("tue 1500 1700");

        TimeAddCommand firstTimeAddCommand = new TimeAddCommand(testName1, testTime1);
        TimeAddCommand secondTimeAddCommand = new TimeAddCommand(testName2, testTime1);
        TimeAddCommand thirdTimeAddCommand = new TimeAddCommand(testName1, testTime2);

        //same object -> returns true
        assertTrue(firstTimeAddCommand.equals(firstTimeAddCommand));

        //different person with same time slot -> returns false;
        assertFalse(firstTimeAddCommand.equals(secondTimeAddCommand));

        //same person with different time slot -> returns false;
        assertFalse(firstTimeAddCommand.equals(thirdTimeAddCommand));
    }
}
