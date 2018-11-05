package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_MAINTENANCE_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.MAINTENANCE_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.MAINTENANCE_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.thanepark.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_WAIT_TIME_AMY;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.model.Model.PREDICATE_SHOW_ALL_RIDES;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_SECOND_RIDE;
import static seedu.thanepark.testutil.TypicalRides.AMY;
import static seedu.thanepark.testutil.TypicalRides.BOB;
import static seedu.thanepark.testutil.TypicalRides.KEYWORD_MATCHING_THE;

import java.io.IOException;

import org.junit.Test;

import seedu.thanepark.commons.core.Messages;
import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.RedoCommand;
import seedu.thanepark.logic.commands.UndoCommand;
import seedu.thanepark.logic.commands.UpdateCommand;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;
import seedu.thanepark.testutil.RideBuilder;
import seedu.thanepark.testutil.RideUtil;

public class UpdateCommandSystemTest extends ThaneParkSystemTest {

    @Test
    public void update() throws IOException {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + MAINTENANCE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Ride editedRide = new RideBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedRide);

        /* Case: undo editing the last ride in the list -> last ride restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last ride in the list -> last ride edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateRide(
                getModel().getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased()), editedRide);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a ride with new values same as existing values -> edited */
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + MAINTENANCE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a ride with new values same as another ride's values but with different name -> edited */
        assertTrue(getModel().getThanePark().getRideList().contains(BOB));
        index = INDEX_SECOND_RIDE;
        assertNotEquals(getModel().getFilteredRideList().get(index.getZeroBased()), BOB);
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_AMY + MAINTENANCE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedRide = new RideBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedRide);

        /* Case: edit a ride with new values same as another ride's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_RIDE;
        String editedName = "Different";
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + " " + PREFIX_NAME + editedName + MAINTENANCE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedRide = new RideBuilder(BOB).withName(editedName)
                .withMaintenance(VALID_MAINTENANCE_AMY).withWaitTime(VALID_WAIT_TIME_AMY).build();
        assertCommandSuccess(command, index, editedRide);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Ride rideToEdit = getModel().getFilteredRideList().get(index.getZeroBased());
        editedRide = new RideBuilder(rideToEdit).withTags().build();
        assertCommandSuccess(command, index, editedRide);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered ride list, edit index within bounds of thanepark book and ride list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_THE);
        index = INDEX_FIRST_PERSON;
        editedName = "Another name";
        assertTrue(index.getZeroBased() < getModel().getFilteredRideList().size());
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_NAME + editedName;
        rideToEdit = getModel().getFilteredRideList().get(index.getZeroBased());
        editedRide = new RideBuilder(rideToEdit).withName(editedName).build();
        assertCommandSuccess(command, index, editedRide);

        /* Case: filtered ride list, edit index within bounds of thanepark book but out of bounds of ride list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_THE);
        int invalidIndex = getModel().getThanePark().getRideList().size();
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a ride card is selected -------------------------- */

        /* Case: selects first card in the ride list, edit a ride -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_AMY + MAINTENANCE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new ride's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredRideList().size() + 1;
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                UpdateCommand.MESSAGE_NOT_UPDATED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_MAINTENANCE_DESC,
                Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_EMAIL_DESC,
                WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS);

        /* Case: invalid thanepark -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_ADDRESS_DESC,
                Zone.MESSAGE_ZONE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(UpdateCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a ride with new values same as another ride's values -> rejected */
        executeCommand(RideUtil.getAddCommand(BOB));
        assertTrue(getModel().getThanePark().getRideList().contains(BOB));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredRideList().get(index.getZeroBased()).equals(BOB));
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + MAINTENANCE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: edit a ride with new values same as another ride's values but with different tags -> rejected */
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + MAINTENANCE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: edit a ride with new values same as another ride's values but with different thanepark -> rejected */
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + MAINTENANCE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: edit a ride with new values same as another ride's values but with different phone -> rejected */
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + MAINTENANCE_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_RIDE);

        /* Case: edit a ride with new values same as another ride's values but with different email -> rejected */
        command = UpdateCommand.COMMAND_WORD + " " + index.getOneBased()
                + NAME_DESC_BOB + MAINTENANCE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, UpdateCommand.MESSAGE_DUPLICATE_RIDE);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Ride, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see UpdateCommandSystemTest#assertCommandSuccess(String, Index, Ride, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Ride editedRide) throws IOException {
        assertCommandSuccess(command, toEdit, editedRide, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the ride at index {@code toEdit} being
     * updated to values specified {@code editedRide}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see UpdateCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Ride editedRide,
            Index expectedSelectedCardIndex) throws IOException {
        Model expectedModel = getModel();
        expectedModel.updateRide(expectedModel.getFilteredRideList().get(toEdit.getZeroBased()), editedRide);
        expectedModel.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);

        assertCommandSuccess(command, expectedModel,
                String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see UpdateCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage)
        throws IOException {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ThaneParkSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see ThaneParkSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) throws IOException {
        executeCommand(command);
        expectedModel.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
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
