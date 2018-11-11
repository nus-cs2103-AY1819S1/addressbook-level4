package systemtests;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_MAINTENANCE_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_WAIT_TIME_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_ZONE_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.MAINTENANCE_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.MAINTENANCE_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.thanepark.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_JESSIE;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_SYMBOLS;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_WAIT_TIME_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_WAIT_TIME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.WAIT_TIME_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.WAIT_TIME_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.ZONE_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.ZONE_DESC_BOB;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.AMY;
import static seedu.thanepark.testutil.TypicalRides.BOB;
import static seedu.thanepark.testutil.TypicalRides.CASTLE;
import static seedu.thanepark.testutil.TypicalRides.HAUNTED;
import static seedu.thanepark.testutil.TypicalRides.IDA;
import static seedu.thanepark.testutil.TypicalRides.KEYWORD_MATCHING_THE;

import org.junit.Test;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.AddCommand;
import seedu.thanepark.logic.commands.RedoCommand;
import seedu.thanepark.logic.commands.UndoCommand;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;
import seedu.thanepark.testutil.RideBuilder;
import seedu.thanepark.testutil.RideUtil;

public class AddCommandSystemTest extends ThaneParkSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a ride without tags to a non-empty thanepark book, command with leading spaces and trailing spaces
         * -> added
         */
        Ride toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + MAINTENANCE_DESC_AMY + " "
                + WAIT_TIME_DESC_AMY + "   " + ZONE_DESC_AMY + "   " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addRide(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a ride with all fields same as another ride in the thanepark book except name -> added */
        toAdd = new RideBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + MAINTENANCE_DESC_AMY + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a ride with all fields same as another ride in the thanepark book except phone and email
         * -> added
         */
        toAdd = new RideBuilder(AMY).withName("Different")
                .withMaintenance(VALID_MAINTENANCE_BOB).withWaitTime(VALID_WAIT_TIME_BOB).build();
        command = RideUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty thanepark book -> added */
        deleteAllPersons();
        assertCommandSuccess(ACCELERATOR);

        /* Case: add a ride with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + MAINTENANCE_DESC_BOB + ZONE_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + WAIT_TIME_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a ride, missing tags -> added */
        assertCommandSuccess(HAUNTED);

        /* Case: valid name with symbols -> accepted */
        toAdd = new RideBuilder(AMY).withName(VALID_NAME_JESSIE).withMaintenance(VALID_MAINTENANCE_AMY)
                .withAddress(VALID_ZONE_AMY).withWaitTime(VALID_WAIT_TIME_AMY).withTags().build();
        command = AddCommand.COMMAND_WORD + VALID_NAME_SYMBOLS
                + MAINTENANCE_DESC_AMY + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the ride list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_THE);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a ride card is selected --------------------------- */

        /* Case: selects first card in the ride list, add a ride -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CASTLE);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate ride -> rejected */
        command = RideUtil.getAddCommand(HAUNTED);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: add a duplicate ride except with different phone -> rejected */
        toAdd = new RideBuilder(HAUNTED).withMaintenance(VALID_MAINTENANCE_BOB).build();
        command = RideUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: add a duplicate ride except with different email -> rejected */
        toAdd = new RideBuilder(HAUNTED).withWaitTime(VALID_WAIT_TIME_BOB).build();
        command = RideUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: add a duplicate ride except with different thanepark -> rejected */
        toAdd = new RideBuilder(HAUNTED).withAddress(VALID_ZONE_BOB).build();
        command = RideUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: add a duplicate ride except with different tags -> rejected */
        command = RideUtil.getAddCommand(HAUNTED) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + MAINTENANCE_DESC_AMY + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + MAINTENANCE_DESC_AMY + ZONE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing thanepark -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + MAINTENANCE_DESC_AMY + WAIT_TIME_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + RideUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC
                + MAINTENANCE_DESC_AMY + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid maintenance -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + INVALID_MAINTENANCE_DESC + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY;
        assertCommandFailure(command, Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + MAINTENANCE_DESC_AMY + INVALID_WAIT_TIME_DESC + ZONE_DESC_AMY;
        assertCommandFailure(command, WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS);

        /* Case: invalid zone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + MAINTENANCE_DESC_AMY + WAIT_TIME_DESC_AMY + INVALID_ZONE_DESC;
        assertCommandFailure(command, Zone.MESSAGE_ZONE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + MAINTENANCE_DESC_AMY + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code RideListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Ride toAdd) {
        assertCommandSuccess(RideUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Ride)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Ride)
     */
    private void assertCommandSuccess(String command, Ride toAdd) {
        Model expectedModel = getModel();
        expectedModel.addRide(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Ride)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code RideListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Ride)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code RideListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
