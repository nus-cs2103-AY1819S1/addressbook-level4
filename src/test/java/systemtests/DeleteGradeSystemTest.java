package systemtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGradeCommand;

public class DeleteGradeSystemTest extends AddressBookSystemTest {
    @Test
    public void testDeleteTime() {
        /*
         * Test Cases with a populated addressbook containing all students with
         * only grade of an exam named as "Y1819S1_Mid"
         */
        addGradesForSomeStudents();

        // Case: Input with valid index, name -> deleted
        String expectedMessage = String.format(DeleteGradeCommand.MESSAGE_DELETE_GRADE_SUCCESS, "Y1819S1_Mid");
        assertCommandSuccess("deleteGrade 1 Y1819S1_Mid", expectedMessage);

        //Case: Input with invalid format
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);
        assertCommandFailure("deleteGrade", expectedMessage);
        assertCommandFailure("deleteGrade 1", expectedMessage);
        assertCommandFailure("deleteGrade 2 test test", expectedMessage);

        //Case: Input with invalid negative index
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);
        assertCommandFailure("deleteGrade -2 test1", expectedMessage);

        //Case: Input with invalid zero index
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);
        assertCommandFailure("deleteGrade 0 test1", expectedMessage);

        //Case: Input with invalid float index
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);
        assertCommandFailure("deleteGrade 1.5 test1", expectedMessage);

        //Case: Input with invalid string index
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);
        assertCommandFailure("deleteGrade abc test1", expectedMessage);

        //Case: Input with invalid index (>7) not found in addressbook with size of 7
        expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure("deleteGrade 8 test1", expectedMessage);
        assertCommandFailure("deleteGrade 20 test1", expectedMessage);
        assertCommandFailure("deleteGrade 100 test1", expectedMessage);

        //Case: Input an unexisted exam name
        expectedMessage = DeleteGradeCommand.MESSAGE_EXAM_NAME_FOUND;
        assertCommandFailure("deleteGrade 6 test2", expectedMessage);
    }

    private void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
    }

    /**
     * provide some students with timeslots for test
     */
    private void addGradesForSomeStudents() {
        executeCommand("edit 1 g/test1 100 g/test2 98");
        executeCommand("edit 2 g/test1 100 g/test2 98");
        executeCommand("edit 3 g/test1 100 g/test2 980");
        executeCommand("edit 4 g/test1 100 g/test2 98");
        executeCommand("edit 5 g/test1 100 g/test2 98");
        executeCommand("edit 6 g/test1 100");
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage}
     */
    private void assertCommandSuccess(String command, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage}
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
