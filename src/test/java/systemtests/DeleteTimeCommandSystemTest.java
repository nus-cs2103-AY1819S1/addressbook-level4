package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTimeCommand;
import seedu.address.model.person.Time;

public class DeleteTimeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void testDeleteTime() {
        /*
         * Test Cases with a populated addressbook containing all students with
         * no timeslots
         */
        addTimeslotsForSomeStudents();

        // Case: Input with valid index, day, start time, end time and index person contains time
        String expectedMessage = DeleteTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("deleteTime 1 ts/mon 1000 1200", expectedMessage);

        //Case: Input with invalid negative index, valid day, start time and end time
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimeCommand.MESSAGE_USAGE);
        assertCommandFailure("deleteTime -2 ts/tue 1400 1600", expectedMessage);

        //Case: Input with invalid zero index, valid day, start time and end time
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTimeCommand.MESSAGE_USAGE);
        assertCommandFailure("deleteTime 0 ts/tue 1400 1600", expectedMessage);

        //Case: Input with invalid index (>7) not found in addressbook with size of 7
        expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure("deleteTime 8 ts/mon 1000 1200", expectedMessage);
        assertCommandFailure("deleteTime 20 ts/mon 1000 1200", expectedMessage);
        assertCommandFailure("deleteTime 100 ts/mon 1000 1200", expectedMessage);

        //Case: Ensure that the invalid index is the error for the previous test cases
        expectedMessage = DeleteTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("deleteTime 2 ts/tue 1400 1600", expectedMessage);

        //Case: Input with invalid day, valid index, start time and end time
        expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        assertCommandFailure("deleteTime 3 ts/Wed 1600 1800", expectedMessage);
        assertCommandFailure("deleteTime 3 ts/wednesday 1600 1800", expectedMessage);

        //Case: Ensure that the invalid day is the error for the previous test cases
        expectedMessage = DeleteTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("deleteTime 3 ts/wed 1600 1800", expectedMessage);

        //Case: Input with invalid start time, valid index, day and end time
        expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        assertCommandFailure("deleteTime 4 ts/thu 10 1200", expectedMessage);
        assertCommandFailure("deleteTime 4 ts/thu 10.00 1200", expectedMessage);

        //Case: Ensure that the invalid day is the error for the previous test cases
        expectedMessage = DeleteTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("deleteTime 4 ts/thu 1000 1200", expectedMessage);

        //Case: Input with invalid end time, valid index, day and start time
        expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        assertCommandFailure("deleteTime 5 ts/fri 1400 4", expectedMessage);

        //Case: Ensure that the invalid day is the error for the previous test cases
        expectedMessage = DeleteTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("deleteTime 5 ts/fri 1400 1600", expectedMessage);

        //Case: Input with valid index, day, start time, end time, but index person does not contains time
        expectedMessage = DeleteTimeCommand.MESSAGE_TIME_NOT_FOUND;
        assertCommandFailure("deleteTime 1 ts/mon 1000 1200", expectedMessage);
        assertCommandFailure("deleteTime 2 ts/tue 1400 1600", expectedMessage);
        assertCommandFailure("deleteTime 3 ts/wed 1600 1800", expectedMessage);
        assertCommandFailure("deleteTime 4 ts/thu 1000 1200", expectedMessage);
        assertCommandFailure("deleteTime 5 ts/fri 1400 1600", expectedMessage);
    }
    private void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
    }

    /**
     * provide some students with timeslots for test
     */
    private void addTimeslotsForSomeStudents() {
        executeCommand("addTime 1 ts/mon 1000 1200");
        executeCommand("addTime 2 ts/tue 1400 1600");
        executeCommand("addTime 3 ts/wed 1600 1800");
        executeCommand("addTime 4 ts/thu 1000 1200");
        executeCommand("addTime 5 ts/fri 1400 1600");
        executeCommand("addTime 6 ts/sat 1600 1800");
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
