package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONDATE_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONLOCATION_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONNAME_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONDATE_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONDATE_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONLOCATION_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONLOCATION_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONNAME_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONNAME_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.TAG_DESC_SLEEP;
import static seedu.address.logic.commands.CommandOccasionTestUtil.TAG_DESC_STUDY;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_SLEEP;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_STUDY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OCCASION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OCCASION;
import static seedu.address.testutil.TypicalOccasions.KEYWORD_MATCHING_CONCERT;
import static seedu.address.testutil.TypicalOccasions.OCCASION_ONE;
import static seedu.address.testutil.TypicalOccasions.OCCASION_TWO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditOccasionCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.OccasionBuilder;
import seedu.address.testutil.OccasionUtil;

public class EditOccasionCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown -------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_OCCASION;
        String command = " " + EditOccasionCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + OCCASIONNAME_DESC_ONE + "  " + OCCASIONDATE_DESC_ONE + " " + OCCASIONLOCATION_DESC_ONE
                + " " + TAG_DESC_STUDY + " ";
        Occasion editedOccasion = new OccasionBuilder(OCCASION_ONE).withTags(VALID_TAG_STUDY).build();
        assertCommandSuccess(command, index, editedOccasion);

        /* Case: undo editing the last occasion in the list -> last occasion restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last occasion in the list -> last occasion edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateOccasion(
                getModel().getFilteredOccasionList().get(INDEX_FIRST_OCCASION.getZeroBased()), editedOccasion);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a occasion with new values same as existing values -> edited */
        command = EditOccasionCommand.COMMAND_WORD + " " + index.getOneBased() + OCCASIONNAME_DESC_ONE
                + OCCASIONDATE_DESC_ONE + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY;
        assertCommandSuccess(command, index, OCCASION_ONE);

        /* Case: edit a occasion with new values same as another occasion's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getOccasionList().contains(OCCASION_ONE));
        index = INDEX_SECOND_OCCASION;
        assertNotEquals(getModel().getFilteredOccasionList().get(index.getZeroBased()), OCCASION_ONE);
        command = EditOccasionCommand.COMMAND_WORD + " " + index.getOneBased() + OCCASIONNAME_DESC_TWO
                + OCCASIONDATE_DESC_ONE + OCCASIONLOCATION_DESC_ONE + TAG_DESC_SLEEP;
        editedOccasion = new OccasionBuilder(OCCASION_ONE).withOccasionName(VALID_OCCASIONNAME_TWO)
                .withTags(VALID_TAG_SLEEP).build();
        assertCommandSuccess(command, index, editedOccasion);

        /* Case: edit a occasion with new values same as another occasion's values but with different location and date
         * -> edited
         */
        index = INDEX_SECOND_OCCASION;
        command = EditOccasionCommand.COMMAND_WORD + " " + index.getOneBased() + OCCASIONNAME_DESC_ONE
                + OCCASIONDATE_DESC_TWO
                + OCCASIONLOCATION_DESC_TWO + TAG_DESC_STUDY;
        editedOccasion = new OccasionBuilder(OCCASION_ONE).withOccasionDate(VALID_OCCASIONDATE_TWO)
                .withOccasionLocation(VALID_OCCASIONLOCATION_TWO)
                .withTags(VALID_TAG_STUDY).build();
        assertCommandSuccess(command, index, editedOccasion);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_OCCASION;
        command = EditOccasionCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Occasion occasionToEdit = getModel().getFilteredOccasionList().get(index.getZeroBased());
        editedOccasion = new OccasionBuilder(occasionToEdit).withTags().build();
        assertCommandSuccess(command, index, editedOccasion);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------ */

        /* Case: filtered occasion list, edit index within bounds of address book and occasion list -> edited */
        /* TODO: fix bug
        showOccasionsWithName(KEYWORD_MATCHING_CONCERT);
        index = INDEX_FIRST_OCCASION;
        assertTrue(index.getZeroBased() < getModel().getFilteredOccasionList().size());
        command = EditOccasionCommand.COMMAND_WORD + " " + index.getOneBased() + " " + OCCASIONNAME_DESC_ONE;
        occasionToEdit = getModel().getFilteredOccasionList().get(index.getZeroBased());
        editedOccasion = new OccasionBuilder(occasionToEdit).withOccasionName(VALID_OCCASIONNAME_ONE).build();
        assertCommandSuccess(command, index, editedOccasion);
        */

        /* Case: filtered occasion list, edit index within bounds of address book but out of bounds of occasion list
         * -> rejected
         */
        showOccasionsWithName(KEYWORD_MATCHING_CONCERT);
        int invalidIndex = getModel().getAddressBook().getOccasionList().size();
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " " + invalidIndex + OCCASIONNAME_DESC_ONE,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a occasion card is selected --------------------- */

        /* Case: selects first card in the occasion list, edit a occasion -> edited, card selection remains
         * unchanged but browser url changes
         */
        showAllOccasions();
        index = INDEX_FIRST_OCCASION;
        selectOccasion(index);
        command = EditOccasionCommand.COMMAND_WORD + " " + index.getOneBased() + OCCASIONNAME_DESC_TWO
                + OCCASIONDATE_DESC_TWO
                + OCCASIONLOCATION_DESC_TWO + TAG_DESC_SLEEP;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new occasion's name
        assertCommandSuccess(command, index, OCCASION_TWO, index);

        /* --------------------------------- Performing invalid edit operation -------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " 0" + OCCASIONNAME_DESC_ONE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOccasionCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " -1" + OCCASIONNAME_DESC_ONE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOccasionCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredOccasionList().size() + 1;
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " " + invalidIndex + OCCASIONNAME_DESC_ONE,
                Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + OCCASIONNAME_DESC_ONE,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOccasionCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " " + INDEX_FIRST_OCCASION.getOneBased(),
                EditOccasionCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " " + INDEX_FIRST_OCCASION.getOneBased()
                + INVALID_OCCASIONNAME_DESC, OccasionName.MESSAGE_OCCASIONNAME_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " " + INDEX_FIRST_OCCASION.getOneBased()
                + INVALID_OCCASIONDATE_DESC, OccasionDate.MESSAGE_OCCASIONDATE_CONSTRAINTS);

        /* Case: invalid location -> rejected */
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " " + INDEX_FIRST_OCCASION.getOneBased()
                + INVALID_OCCASIONLOCATION_DESC, OccasionLocation.MESSAGE_OCCASIONLOCATION_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditOccasionCommand.COMMAND_WORD + " " + INDEX_FIRST_OCCASION.getOneBased()
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a occasion with new values same as another occasion's values -> rejected */
        executeCommand(OccasionUtil.getAddCommand(OCCASION_ONE));
        assertTrue(getModel().getAddressBook().getOccasionList().contains(OCCASION_ONE));
        index = INDEX_FIRST_OCCASION;
        assertFalse(getModel().getFilteredOccasionList().get(index.getZeroBased()).equals(OCCASION_ONE));
        command = EditOccasionCommand.COMMAND_WORD + " " + index.getOneBased() + OCCASIONNAME_DESC_ONE
                + OCCASIONDATE_DESC_ONE + OCCASIONLOCATION_DESC_ONE + TAG_DESC_STUDY;
        assertCommandFailure(command, EditOccasionCommand.MESSAGE_DUPLICATE_OCCASION);

        /* Case: edit a occasion with new values same as another occasion's values but with different tags ->
        rejected */
        // TODO: fix bug.
        command = EditOccasionCommand.COMMAND_WORD + " " + index.getOneBased() + OCCASIONNAME_DESC_ONE
                + OCCASIONDATE_DESC_ONE + OCCASIONLOCATION_DESC_ONE + TAG_DESC_SLEEP;
        assertCommandFailure(command, EditOccasionCommand.MESSAGE_DUPLICATE_OCCASION);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Occasion, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditOccasionCommandSystemTest#assertCommandSuccess(String, Index, Occasion, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Occasion editedOccasion) {
        assertCommandSuccess(command, toEdit, editedOccasion, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,
     * <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditOccasionCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the occasion at index {@code toEdit} being
     * updated to values specified {@code editedOccasion}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditOccasionCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Occasion editedOccasion,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateOccasion(expectedModel.getFilteredOccasionList().get(toEdit.getZeroBased()),
                editedOccasion);
        expectedModel.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditOccasionCommand.MESSAGE_EDIT_OCCASION_SUCCESS, editedOccasion),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditOccasionCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpectedOccasion(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpectedOccasion(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
        assertApplicationDisplaysExpectedOccasion("", expectedResultMessage, expectedModel);
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpectedOccasion(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpectedOccasion(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpectedOccasion(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
