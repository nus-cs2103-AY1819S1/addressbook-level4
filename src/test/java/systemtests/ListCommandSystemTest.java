package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.CARL;
import static seedu.address.testutil.TypicalContacts.DANIEL;
import static seedu.address.testutil.TypicalContacts.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class ListCommandSystemTest extends AddressBookSystemTest {
    //TODO: add cases for serviceproviders and update the input commands

    @Test
    public void list() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " "
                + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where client list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find client where client list is not displaying the client we are finding -> 1 client found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 0 persons found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Benson Daniel";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 0 persons found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Daniel Daniel";
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find Benson to delete */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Benson";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find same persons in address book after deleting 1 of them -> 1 client found */
        executeCommand(String.format(DeleteCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT, "#" + BENSON.getId()));
        assertFalse(getModel().getAddressBook().getContactList().contains(BENSON));
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find client in address book, keyword is same as name but of different case -> 1 client found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/MeIeR";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find client in address book, keyword is substring of name -> 1 persons found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Mei";
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find client in address book, name is substring of keyword -> 0 persons found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find client not in address book -> 0 persons found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Mark";
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of client in address book -> 1 persons found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " p/" + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of client in address book -> 1 persons found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " a/"
                + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of client in address book -> 1 persons found */
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " e/" + DANIEL.getEmail().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of client in address book -> 2 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " t/" + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, ALICE, DANIEL);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a client is selected -> selected card deselected */
        showAllClients();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " n/Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccessClient(command, expectedModel);
        // TODO: right now will clear the browser panel after MOST (except help, history, and maybe one other) commands
        // assertSelectedCardDeselected();

        /* Case: find client in empty address book -> 0 persons found */
        deleteAllPersons();
        command = String.format(ListCommand.COMMAND_WORD_GENERAL, ContactType.CLIENT) + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccessClient(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccessClient(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredContactList().size(), ContactType.CLIENT);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
