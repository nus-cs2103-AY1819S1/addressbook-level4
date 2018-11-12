package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import org.junit.Test;

import seedu.address.logic.commands.AddTimeCommand;
import seedu.address.model.person.Time;

public class AddTimeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void testAddTime() {
        /*
         * Test Cases with a populated addressbook containing all students with
         * no timeslots
         */
        // Case: Input with valid index, day , start time and end time
        String expectedMessage = AddTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("addTime 1 ts/mon 0800 1000", expectedMessage);

        //Case: Input with invalid negative index, valid day, start time and end time
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimeCommand.MESSAGE_USAGE);
        assertCommandFailure("addTime -1 ts/mon 1000 1200", expectedMessage);

        //Case: Input with invalid zero index, valid day, start time and end time
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimeCommand.MESSAGE_USAGE);
        assertCommandFailure("addTime 0 ts/mon 1000 1200", expectedMessage);

        //Case: Input with invalid index (>7) not found in addressbook with size of 7
        expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure("addTime 8 ts/mon 1000 1200", expectedMessage);
        assertCommandFailure("addTime 20 ts/mon 1000 1200", expectedMessage);
        assertCommandFailure("addTime 100 ts/mon 1000 1200", expectedMessage);

        //Case: Ensure that the invalid index is the error for the previous test cases
        expectedMessage = AddTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("addTime 1 ts/mon 1000 1200", expectedMessage);

        //Case: Input with invalid day, valid index, start time and end time;
        expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        assertCommandFailure("addTime 2 ts/m 0600 0800", expectedMessage);
        assertCommandFailure("addTime 2 ts/Mon 0600 0800", expectedMessage);
        assertCommandFailure("addTime 2 ts/monday 0600 0800", expectedMessage);
        assertCommandFailure("addTime 2 ts/t 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/Tue 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/tuesday 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/w 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/Wed 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/wednesday 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/Thu 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/thursday 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/f 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/Fri 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/friday 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/s 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/Sat 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/saturday 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/Sun 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/sunday 0800 1000", expectedMessage);

        //Case: Ensure that the invalid day is the error for the previous test cases
        expectedMessage = AddTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("addTime 2 ts/mon 0600 0800", expectedMessage);
        assertCommandSuccess("addTime 2 ts/tue 0800 1000", expectedMessage);
        assertCommandSuccess("addTime 2 ts/wed 0800 1000", expectedMessage);
        assertCommandSuccess("addTime 2 ts/thu 0800 1000", expectedMessage);
        assertCommandSuccess("addTime 2 ts/fri 0800 1000", expectedMessage);
        assertCommandSuccess("addTime 2 ts/sat 0800 1000", expectedMessage);
        assertCommandSuccess("addTime 2 ts/sun 0800 1000", expectedMessage);

        //Case: Input with invalid start time, valid index, day and end time
        expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        assertCommandFailure("addTime 3 ts/wed 10 1200", expectedMessage);
        assertCommandFailure("addTime 3 ts/wed 10.00 1200", expectedMessage);

        //Case: Ensure that invalid start time is the error for the previous test cases
        expectedMessage = AddTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("addTime 3 ts/wed 1000 1200", expectedMessage);

        //Case: Input with invalid end time, valid index, day and start time
        expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;
        assertCommandFailure("addTime 4 ts/thu 1000 12000", expectedMessage);
        assertCommandFailure("addTime 4 ts/thu 1000 2459", expectedMessage);
        assertCommandFailure("addTime 4 ts/thu 1000 2400", expectedMessage);

        //Case: Ensure that invalid end time is the error for the previous test cases
        expectedMessage = AddTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("addTime 4 ts/thu 1000 1200", expectedMessage);

        //Case: Input with valid index, day, start time and end time, however, start time is bigger than end time
        expectedMessage = AddTimeCommand.MESSAGE_INVALID_START_END_TIME;
        assertCommandFailure("addTime 5 ts/fri 1000 0800", expectedMessage);

        //Case: Ensure that the bigger start time is the error for the previous test case
        expectedMessage = AddTimeCommand.MESSAGE_SUCCESS;
        assertCommandSuccess("addTime 5 ts/fri 1000 1200", expectedMessage);

        //Case: Input with the timings that have already been taken
        expectedMessage = AddTimeCommand.MESSAGE_TIME_IS_NOT_AVAILABLE;
        assertCommandFailure("addTime 1 ts/mon 0800 1000", expectedMessage);
        assertCommandFailure("addTime 1 ts/mon 1000 1200", expectedMessage);
        assertCommandFailure("addTime 2 ts/mon 0600 0800", expectedMessage);
        assertCommandFailure("addTime 2 ts/tue 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/wed 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/thu 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/fri 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/sat 0800 1000", expectedMessage);
        assertCommandFailure("addTime 2 ts/sun 0800 1000", expectedMessage);
        assertCommandFailure("addTime 3 ts/wed 1000 1200", expectedMessage);
        assertCommandFailure("addTime 4 ts/thu 1000 1200", expectedMessage);
        assertCommandFailure("addTime 5 ts/fri 1000 1200", expectedMessage);

        //Case: Input with timings that clashes with others already in the addressbook
        expectedMessage = AddTimeCommand.MESSAGE_TIME_CLASH;
        assertCommandFailure("addTime 1 ts/mon 0800 1400", expectedMessage);
        assertCommandFailure("addTime 2 ts/tue 0800 1400", expectedMessage);
        assertCommandFailure("addTime 3 ts/wed 0800 1400", expectedMessage);
        assertCommandFailure("addTime 4 ts/thu 0800 1400", expectedMessage);
        assertCommandFailure("addTime 5 ts/fri 0800 1400", expectedMessage);
        assertCommandFailure("addTime 6 ts/sat 0800 1400", expectedMessage);
        assertCommandFailure("addTime 7 ts/sun 0800 1400", expectedMessage);
    }

    private void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
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
