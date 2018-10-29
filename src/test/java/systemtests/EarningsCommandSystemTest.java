package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.Year;

import org.junit.Test;

import seedu.address.logic.commands.EarningsCommand;
import seedu.address.model.Model;

public class EarningsCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void earnings() {

        /*
         * Test Cases with a populated addressbook containing all students with
         * no timeslots
         */
        Model defaultModel = getModel();

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


        // Case: Input beginning date is after the ending date


        /*
         * Test Cases with addressbook containing some students with timeslots
         */
        //addTimeslotsForSomeStudents();

        /* Case: Obtain earnings from non-empty addressbook, all students in
         * addressbook have timeslots
         */



    }

    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code EarningsCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, EarningsCommand.MESSAGE_SUCCESS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see EarningsCommandSystemTest#assertCommandSuccess(String)
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
        executeCommand("addtime n/Carl Kurz ts/fri 1000 1200");
        executeCommand("addtime n/George Best ts/wed 1800 2000");
        executeCommand("addtime n/Hoon Meier ts/tue 0900 1100");
    }

    /**
     * provide timeslots for all students
     */
    private void addTimeslotForAllStudents() {

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
