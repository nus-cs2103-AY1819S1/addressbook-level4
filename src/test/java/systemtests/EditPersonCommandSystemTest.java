package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandPersonTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandPersonTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandPersonTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandPersonTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandPersonTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandPersonTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandPersonTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandPersonTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandPersonTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandPersonTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandPersonTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class EditPersonCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown -------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_PERSON;
        String command = " " + EditPersonCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB
                + "  " + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  " + ADDRESS_DESC_BOB + " " + TAG_DESC_HUSBAND
                + " ";
        Person editedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: undo editing the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
                getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a person with new values same as existing values -> edited */
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a person with new values same as another person's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getPersonList().contains(BOB));
        index = INDEX_SECOND_PERSON;
        assertNotEquals(getModel().getFilteredPersonList().get(index.getZeroBased()), BOB);
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedPerson = new PersonBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: edit a person with new values same as another person's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_PERSON;
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedPerson = new PersonBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_PERSON;
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Person personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withTags().build();
        assertCommandSuccess(command, index, editedPerson);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------ */

        /* Case: filtered person list, edit index within bounds of address book and person list -> edited */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedPerson);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a person card is selected --------------------- */

        /* Case: selects first card in the person list, edit a person -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllPersons();
        index = INDEX_FIRST_PERSON;
        selectPerson(index);
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new person's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredPersonList().size() + 1;
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                EditPersonCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_EMAIL_DESC, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditPersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a person with new values same as another person's values -> rejected */
        executeCommand(PersonUtil.getAddCommand(BOB));
        assertTrue(getModel().getAddressBook().getPersonList().contains(BOB));
        index = INDEX_FIRST_PERSON;
        assertFalse(getModel().getFilteredPersonList().get(index.getZeroBased()).equals(BOB));
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different tags -> rejected */
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different address
        -> rejected */
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different
        phone -> rejected */
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: edit a person with new values same as another person's values but with different email
        -> rejected */
        command = EditPersonCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditPersonCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditPersonCommandSystemTest#assertCommandSuccess(String, Index, Person, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Person editedPerson) {
        assertCommandSuccess(command, toEdit, editedPerson, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,
     * <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditPersonCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditPersonCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Person editedPerson,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updatePerson(expectedModel.getFilteredPersonList().get(toEdit.getZeroBased()), editedPerson);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditPersonCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpectedPerson(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpectedPerson(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedPersonCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpectedPerson("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedPersonCardChanged(expectedSelectedCardIndex);
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpectedPerson(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpectedPerson(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpectedPerson(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
