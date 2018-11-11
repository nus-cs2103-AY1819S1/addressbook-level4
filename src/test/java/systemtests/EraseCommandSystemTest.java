package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.EraseCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsTagPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

//@@author kengwoon
public class EraseCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void erase() {
        final Model defaultModel = getModel();

        /* Case: erase tag from persons in non-empty address book, command with leading spaces and trailing
         * alphanumeric characters and spaces -> erase
         */
        assertCommandSuccess("   " + EraseCommand.COMMAND_WORD + "   basketball");
        assertSelectedCardUnchanged();

        /* Case: erase multiple tags from persons in non-empty address book, command with leading spaces and trailing
         * alphanumeric characters and spaces -> erase
         */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        assertCommandSuccess("   " + EraseCommand.COMMAND_WORD + "   basketball soccer  ");
        assertSelectedCardUnchanged();

        /* Case: undo erasing address book -> original address book restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo erasing address book -> erased */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("eRaSe", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code EraseCommand#MESSAGE_ERASE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        String trimmedArgs = command.trim();
        String[] nameKeywords = trimmedArgs.split("\\s+");

        List<String> keywords = new ArrayList<>();
        for (int i = 1; i < nameKeywords.length; i++) {
            keywords.add(nameKeywords[i]);
        }

        Model expectedModel = getModel();
        List<Person> original = new ArrayList<>();
        List<Person> toErase = new ArrayList<>();
        Set<Tag> editedTags = new HashSet<>();
        ContactContainsTagPredicate predicateTag = new ContactContainsTagPredicate(keywords);
        for (Person p : expectedModel.getFilteredPersonList()) {
            editedTags.clear();
            if (predicateTag.test(p)) {
                original.add(p);
                editedTags.addAll(p.getTags());
                for (String s : keywords) {
                    for (Tag t : p.getTags()) {
                        if (s.equals(t.toStringOnly())) {
                            editedTags.remove(t);
                            break;
                        }
                    }
                }
                PersonBuilder editedPerson = new PersonBuilder(p);
                if (!editedTags.isEmpty()) {
                    String[] stringArray = new String[editedTags.size()];
                    Object[] tagArray = editedTags.toArray();
                    for (int j = 0; j < editedTags.size(); j++) {
                        stringArray[j] = ((Tag) tagArray[j]).toStringOnly();
                    }
                    toErase.add(editedPerson.withTags(stringArray).build());
                } else {
                    toErase.add(editedPerson.withTags().build());
                }
            }
        }
        expectedModel.updateMultiplePersons(original, toErase);
        assertCommandSuccess(command, String.format(EraseCommand.MESSAGE_ERASE_SUCCESS, keywords), expectedModel);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
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
