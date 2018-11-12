package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Time;

public class AddTimeCommandTest {

    @Test
    public void equals() {
        Index indexOne = INDEX_FIRST_PERSON;
        Index indexTwo = INDEX_SECOND_PERSON;
        Time testTimeOne = new Time("mon 1300 1500");
        Time testTimeTwo = new Time("tue 1500 1700");

        AddTimeCommand firstAddTimeCommand = new AddTimeCommand(indexOne, testTimeOne);
        AddTimeCommand secondAddTimeCommand = new AddTimeCommand(indexTwo, testTimeOne);
        AddTimeCommand thirdAddTimeCommand = new AddTimeCommand(indexOne, testTimeTwo);

        //same object -> returns true
        assertTrue(firstAddTimeCommand.equals(firstAddTimeCommand));

        // same values -> returns true
        AddTimeCommand firstAddTimeCommandCopy = new AddTimeCommand(indexOne, testTimeOne);
        assertTrue(firstAddTimeCommand.equals(firstAddTimeCommandCopy));

        //different person with same time slot -> returns false;
        assertFalse(firstAddTimeCommand.equals(secondAddTimeCommand));

        //same person with different time slot -> returns false;
        assertFalse(firstAddTimeCommand.equals(thirdAddTimeCommand));
    }
}
