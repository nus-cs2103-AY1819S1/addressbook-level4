package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DRIVER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VOLUNTEERS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VOLUNTEER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VOLUNTEER;
import static seedu.address.testutil.TypicalVolunteers.AMY;
import static seedu.address.testutil.TypicalVolunteers.BOB;
import static seedu.address.testutil.TypicalVolunteers.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Address;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.VolunteerBuilder;
import seedu.address.testutil.VolunteerUtil;

public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_VOLUNTEER;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + GENDER_DESC_BOB + " " + BIRTHDAY_DESC_BOB + " " + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  "
                + ADDRESS_DESC_BOB + " " + TAG_DESC_DRIVER + " ";
        Volunteer editedVolunteer = new VolunteerBuilder(BOB).withTags(VALID_TAG_DRIVER).build();
        assertCommandSuccess(command, index, editedVolunteer);

        /* Case: undo editing the last volunteer in the list -> last volunteer restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last volunteer in the list -> last volunteer edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateVolunteer(
                getModel().getFilteredVolunteerList().get(INDEX_FIRST_VOLUNTEER.getZeroBased()), editedVolunteer);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a volunteer with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a volunteer with new values same as another volunteer's values
            but with different name -> edited */
        assertTrue(getModel().getAddressBook().getVolunteerList().contains(BOB));
        index = INDEX_SECOND_VOLUNTEER;
        assertNotEquals(getModel().getFilteredVolunteerList().get(index.getZeroBased()), BOB);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        editedVolunteer = new VolunteerBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedVolunteer);

        /* Case: edit a volunteer with new values same as another volunteer's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_VOLUNTEER;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        editedVolunteer = new VolunteerBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertCommandSuccess(command, index, editedVolunteer);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_VOLUNTEER;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Volunteer volunteerToEdit = getModel().getFilteredVolunteerList().get(index.getZeroBased());
        editedVolunteer = new VolunteerBuilder(volunteerToEdit).withTags().build();
        assertCommandSuccess(command, index, editedVolunteer);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered volunteer list, edit index within bounds of address book and volunteer list -> edited */
        showVolunteersWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_VOLUNTEER;
        assertTrue(index.getZeroBased() < getModel().getFilteredVolunteerList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        volunteerToEdit = getModel().getFilteredVolunteerList().get(index.getZeroBased());
        editedVolunteer = new VolunteerBuilder(volunteerToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedVolunteer);

        /* Case: filtered volunteer list, edit index within bounds of address book but out of bounds of volunteer list
         * -> rejected
         */
        showVolunteersWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getVolunteerList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a volunteer card is selected ---------------------- */

        /* Case: selects first card in the volunteer list, edit a volunteer -> edited,
         *   card selection remains unchanged but browser url changes
         */
        showAllVolunteers();
        index = INDEX_FIRST_VOLUNTEER;
        selectVolunteer(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + GENDER_DESC_AMY
                + BIRTHDAY_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + TAG_DESC_STUDENT;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new volunteer's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredVolunteerList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased()
                        + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid gender -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased()
                        + INVALID_GENDER_DESC, Gender.MESSAGE_GENDER_CONSTRAINTS);

        /* Case: invalid birthday -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased()
                        + INVALID_BIRTHDAY_DESC, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased()
                        + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased()
                        + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased()
                        + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_VOLUNTEER.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a volunteer with new values same as another volunteer's values -> rejected */
        executeCommand(VolunteerUtil.getAddCommand(BOB));
        assertTrue(getModel().getAddressBook().getVolunteerList().contains(BOB));
        index = INDEX_FIRST_VOLUNTEER;
        assertFalse(getModel().getFilteredVolunteerList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);

        /* Case: edit a volunteer with new values same as another volunteer's values
            but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_DRIVER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);

        /* Case: edit a volunteer with new values same as another volunteer's values
            but with different address -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);

        /* Case: edit a volunteer with new values same as another volunteer's values
            but with different gender -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_AMY
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);

        /* Case: edit a volunteer with new values same as another volunteer's values
            but with different birthday -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDAY_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);
        /* Case: edit a volunteer with new values same as another volunteer's values
            but with different phone -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);

        /* Case: edit a volunteer with new values same as another volunteer's values
            but with different email -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDAY_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_STUDENT + TAG_DESC_DRIVER;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_VOLUNTEER);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Volunteer, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Volunteer, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Volunteer editedVolunteer) {
        assertCommandSuccess(command, toEdit, editedVolunteer, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the volunteer at index {@code toEdit} being
     * updated to values specified {@code editedVolunteer}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Volunteer editedVolunteer,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateVolunteer(expectedModel.getFilteredVolunteerList().get(toEdit.getZeroBased()),
                editedVolunteer);
        expectedModel.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS, editedVolunteer), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
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
