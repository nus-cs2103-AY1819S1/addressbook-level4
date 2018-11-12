package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;

public class DeleteGradeCommandTest {
    @Test
    public void equals() {
        Index indexOne = INDEX_FIRST_PERSON;
        Index indexTwo = INDEX_SECOND_PERSON;

        final String EXAM_NAME_ONE = "Y1819S1_Mid";
        final String EXAM_NAME_TWO = "test";

        DeleteGradeCommand firstDeleteGradeCommand = new DeleteGradeCommand(indexOne, EXAM_NAME_ONE);
        DeleteGradeCommand secondDeleteGradeCommand = new DeleteGradeCommand(indexTwo, EXAM_NAME_ONE);
        DeleteGradeCommand thirdDeleteGradeCommand = new DeleteGradeCommand(indexOne, EXAM_NAME_TWO);

        //same object -> returns true
        assertTrue(firstDeleteGradeCommand.equals(firstDeleteGradeCommand));

        // same values -> returns true
        DeleteGradeCommand firstDeleteGradeCommandCopy = new DeleteGradeCommand(indexOne, EXAM_NAME_ONE);
        assertTrue(firstDeleteGradeCommand.equals(firstDeleteGradeCommandCopy));

        //different person with same exam name -> returns false;
        assertFalse(firstDeleteGradeCommand.equals(secondDeleteGradeCommand));

        //same person with different exam name -> returns false;
        assertFalse(firstDeleteGradeCommand.equals(thirdDeleteGradeCommand));
    }
}
