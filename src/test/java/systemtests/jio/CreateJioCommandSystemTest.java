package systemtests.jio;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.jio.JioTestUtil.ADDRESS_DESC_MALA;
import static seedu.address.model.jio.JioTestUtil.DAY_DESC_MALA;
import static seedu.address.model.jio.JioTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.model.jio.JioTestUtil.INVALID_DAY_DESC;
import static seedu.address.model.jio.JioTestUtil.INVALID_NAME_DESC;
import static seedu.address.model.jio.JioTestUtil.INVALID_TIME_DESC;
import static seedu.address.model.jio.JioTestUtil.INVALID_WEEK_DESC;
import static seedu.address.model.jio.JioTestUtil.MALA;
import static seedu.address.model.jio.JioTestUtil.NAME_DESC_MALA;
import static seedu.address.model.jio.JioTestUtil.TIME_DESC_MALA;
import static seedu.address.model.jio.JioTestUtil.WEEK_DESC_MALA;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.jio.CreateJioCommand;
import seedu.address.model.Model;
import seedu.address.model.Name;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Address;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;
import seedu.address.model.user.User;
import seedu.address.testutil.TypicalUsers;
import systemtests.AddressBookSystemTest;

public class CreateJioCommandSystemTest extends AddressBookSystemTest {

    private Model expectedModel;

    @Test
    public void add() {
        User currentUser = TypicalUsers.getTypicalUsers().get(0);
        addUser(currentUser);
        login(currentUser);
        clearJio();

        expectedModel = getModel();
        expectedModel.addUser(currentUser);
        expectedModel.loginUser(currentUser);
        expectedModel.clearJio();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a jio to user data, command with
         * leading spaces and trailing spaces
         * -> added
         */
        Jio toAdd = MALA;
        String command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a jio with all fields same as another jio in the address book except name -> added */
        toAdd = new Jio(new Name("notmala"), MALA.getDate(), MALA.getAddress());
        command = CreateJioCommand.COMMAND_WORD + "  " + "n/notmala" + "  " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a jio, command with parameters in random order -> added */
        toAdd = new Jio(new Name("thirdjio"), MALA.getDate(), MALA.getAddress());
        command = CreateJioCommand.COMMAND_WORD + "  " + TIME_DESC_MALA + "   " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + "n/thirdjio" + "  " + ADDRESS_DESC_MALA + " ";
        assertCommandSuccess(command, toAdd);


        /* ------------------- Perform invalid createJio operations ----------------------- */

        /* Case: add a duplicate jio -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandFailure(command, CreateJioCommand.MESSAGE_DUPLICATE_JIO);

        /* Case: add a duplicate jio except with different address -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + "a/notmala" + " ";
        assertCommandFailure(command, CreateJioCommand.MESSAGE_DUPLICATE_JIO);

        /* Case: missing name -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + "a/notmala" + " ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJioCommand.MESSAGE_USAGE));

        /* Case: missing week -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJioCommand.MESSAGE_USAGE));

        /* Case: missing day -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  "
                + WEEK_DESC_MALA + "   " + TIME_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJioCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  "
                + WEEK_DESC_MALA + "   " + DAY_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJioCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  "
                + WEEK_DESC_MALA + "   " + DAY_DESC_MALA + "   " + TIME_DESC_MALA + " ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJioCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "   " + "creatingjio" + "  " + NAME_DESC_MALA + "  "
                + WEEK_DESC_MALA + "   " + DAY_DESC_MALA + "   " + TIME_DESC_MALA + " ";
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + INVALID_NAME_DESC + "  " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid week -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  " + INVALID_WEEK_DESC + " "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandFailure(command, Week.MESSAGE_WEEK_CONSTRAINTS);

        /* Case: invalid day -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  " + WEEK_DESC_MALA + " "
                + INVALID_DAY_DESC + "   " + TIME_DESC_MALA + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandFailure(command, Day.MESSAGE_DAY_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + INVALID_TIME_DESC + "   " + ADDRESS_DESC_MALA + " ";
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = "   " + CreateJioCommand.COMMAND_WORD + "  " + NAME_DESC_MALA + "  " + WEEK_DESC_MALA + " "
                + DAY_DESC_MALA + "   " + TIME_DESC_MALA + "   " + INVALID_ADDRESS_DESC + " ";
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(Jio)}.
     * Executes {@code command}
     * instead.
     * @see CreateJioCommandSystemTest#assertCommandSuccess(Jio)
     */
    private void assertCommandSuccess(String command, Jio toAdd) {
        expectedModel.createJio(toAdd);
        String expectedResultMessage = String.format(CreateJioCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as
     * {@code assertCommandSuccess(String, Jio)} except asserts that the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code JioListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see CreateJioCommandSystemTest#assertCommandSuccess(String, Jio)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("",
                expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code JioListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
