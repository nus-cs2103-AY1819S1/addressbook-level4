package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalContacts.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

public class DeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT_CLIENT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, String.format(DeleteCommand.MESSAGE_USAGE,
                    ContactType.CLIENT, "#<ID>"));

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */
        /* Case: delete the first client in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        //TODO: update command string
        expectedModel.updateFilteredContactList(ContactType.CLIENT.getFilter());
        String command = "     " + String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT,
                "#" + INDEX_FIRST_PERSON.getOneBased()) + "      ";
        Contact deletedContact = removePerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedContact.getType(),
                deletedContact);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last client in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        modelBeforeDeletingLast.updateFilteredContactList(ContactType.CLIENT.getFilter());
        List<Contact> currentList = modelBeforeDeletingLast.getFilteredContactList();
        Index lastPersonIndex = Index.fromOneBased(currentList.get(currentList.size() - 1).getId());
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last client in the list -> last client restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last client in the list -> last client deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle client in the list -> deleted */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered client list, delete index within bounds of address book and client list -> deleted */
        // should delete relative to client list
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = Index.fromOneBased(getModel().getFilteredContactList().get(0).getId());
        assertCommandSuccess(index);

        /* Case: filtered client list, delete index within bounds of address book but out of bounds of client list
         * -> rejected
         */
        //TODO: update command
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getContactList().size();
        command = String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT, "#" + invalidIndex);
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, invalidIndex));

        /* --------------------- Performing delete operation while a client card is selected ------------------------ */

        //TODO: select command
        /* Case: delete the selected client -> client list panel selects the client before the deleted client */
        showAllClients();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        // selectPerson(selectedIndex);
        command = String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT,
                "#" + selectedIndex.getOneBased());
        deletedContact = removePerson(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedContact.getType(), deletedContact);
        executeCommand(command);
        // assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT, "#0");
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, 0));

        /* Case: invalid index (-1) -> rejected */
        command = String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT, "#-1");
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, -1));

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getContactList().size() + 1);
        command = String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT,
                "#" + outOfBoundsIndex.getOneBased());
        assertCommandFailure(command, String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                outOfBoundsIndex.getOneBased()));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT, "#abc"),
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, "abc"));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT, "#1 abc"),
                MESSAGE_UNKNOWN_COMMAND);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("client#1 DelETE", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Client} at the specified {@code index} in {@code model}'s address book.
     * @return the removed client
     */
    private Contact removePerson(Model model, Index index) {
        Contact targetContact = getPerson(model, index);
        model.deleteContact(targetContact);
        return targetContact;
    }

    /**
     * Deletes the client at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Contact deletedContact = removePerson(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedContact.getType(),
                deletedContact);

        //TODO: Update command input
        assertCommandSuccess(
                String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT, "#" + toDelete.getOneBased()),
                expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
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
