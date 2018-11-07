package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Time;

public class DeleteTimeCommandTest {

    @Test
    public void equals() {
        Index indexOne = INDEX_FIRST_PERSON;
        Index indexTwo = INDEX_SECOND_PERSON;
        Time testTimeOne = new Time("mon 1300 1500");
        Time testTimeTwo = new Time("tue 1500 1700");

        DeleteTimeCommand firstDeleteTimeCommand = new DeleteTimeCommand(indexOne, testTimeOne);
        DeleteTimeCommand secondDeleteTimeCommand = new DeleteTimeCommand(indexTwo, testTimeOne);
        DeleteTimeCommand thirdDeleteTimeCommand = new DeleteTimeCommand(indexOne, testTimeTwo);

        //same object -> returns true
        assertTrue(firstDeleteTimeCommand.equals(firstDeleteTimeCommand));

        // same values -> returns true
        DeleteTimeCommand firstDeleteTimeCommandCopy = new DeleteTimeCommand(indexOne, testTimeOne);
        assertTrue(firstDeleteTimeCommand.equals(firstDeleteTimeCommandCopy));

        //different person with same time slot -> returns false;
        assertFalse(firstDeleteTimeCommand.equals(secondDeleteTimeCommand));

        //same person with different time slot -> returns false;
        assertFalse(firstDeleteTimeCommand.equals(thirdDeleteTimeCommand));
    }
}
