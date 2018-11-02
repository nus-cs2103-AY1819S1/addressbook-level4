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

public class AddTimeCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        String testName1 = "John Doe";
        String testName2 = "Mary Ray";
        Time testTime1 = new Time("mon 1300 1500");
        Time testTime2 = new Time("tue 1500 1700");

        AddTimeCommand firstAddTimeCommand = new AddTimeCommand(testName1, testTime1);
        AddTimeCommand secondAddTimeCommand = new AddTimeCommand(testName2, testTime1);
        AddTimeCommand thirdAddTimeCommand = new AddTimeCommand(testName1, testTime2);

        //same object -> returns true
        assertTrue(firstAddTimeCommand.equals(firstAddTimeCommand));

        //different person with same time slot -> returns false;
        assertFalse(firstAddTimeCommand.equals(secondAddTimeCommand));

        //same person with different time slot -> returns false;
        assertFalse(firstAddTimeCommand.equals(thirdAddTimeCommand));
    }
}
