package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.EarningsCommandParser.MESSAGE_INVALID_DATE;

import java.time.LocalDate;
import java.time.Year;

import org.junit.Test;

import seedu.address.logic.commands.EarningsCommand;

public class EarningsCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void earnings() {

        /*
         * Test Cases with a populated addressbook containing all students with
         * no timeslots
         */
        // Case: Input is a valid date
        String expectedMessage = String.format(EarningsCommand.MESSAGE_SUCCESS, 0.00,
                convertStringToLocalDate("0101"), convertStringToLocalDate("0701"));
        assertCommandSuccess("earnings 0101 0701", expectedMessage);

        expectedMessage = String.format(EarningsCommand.MESSAGE_SUCCESS, 0.00,
                convertStringToLocalDate("0701"), convertStringToLocalDate("2304"));
        assertCommandSuccess("earnings 0701 2304", expectedMessage);
        assertCommandSuccess("     earnings 0701 2304   ", expectedMessage);

        // Case: Input date format is an invalid
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EarningsCommand.MESSAGE_USAGE);

        assertCommandFailure("earnings 0701", expectedMessage);
        assertCommandFailure("earnings 0!05 !^07", expectedMessage);
        assertCommandFailure("earnings !@$%@^# ", expectedMessage);
        assertCommandFailure("earnings 3501 1012", expectedMessage);
        assertCommandFailure("earnings -1105 -2709", expectedMessage);

        // Case: Input beginning date is after the ending date
        assertCommandFailure("earnings 0207 2503", MESSAGE_INVALID_DATE);

        /*
         * Test Cases with addressbook containing some students with timeslots
         */
        addTimeslotsForSomeStudents();
        // Case: Input is a valid date
        expectedMessage = String.format(EarningsCommand.MESSAGE_SUCCESS, 184.00,
                convertStringToLocalDate("0101"), convertStringToLocalDate("0701"));
        assertCommandSuccess("earnings 0101 0701", expectedMessage);

        expectedMessage = String.format(EarningsCommand.MESSAGE_SUCCESS, 2760.00,
                convertStringToLocalDate("0701"), convertStringToLocalDate("2304"));
        assertCommandSuccess("earnings 0701 2304", expectedMessage);
        assertCommandSuccess("     earnings 0701 2304   ", expectedMessage);
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

    /**
     * provide some students with timeslots for test
     */
    private void addTimeslotsForSomeStudents() {
        executeCommand("addTime 3 ts/fri 1000 1200");
        executeCommand("addTime 7 ts/wed 1800 2000");
        executeCommand("addTime 5 ts/tue 0900 1100");
    }

    /**
     * Converts string userInput dates into date format to be used
     * in MESSAGE_SUCCESS
     **/
    private LocalDate convertStringToLocalDate(String dateToConvert) {
        int day = Integer.parseInt(dateToConvert.substring(0, 2));
        int month = Integer.parseInt(dateToConvert.substring(2, 4));

        return Year.now().atMonth(month).atDay(day);

    }
}
