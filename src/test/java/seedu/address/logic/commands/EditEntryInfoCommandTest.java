package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditEntryInfoCommand.EditEntryInfoDescriptor;
import seedu.address.model.EntryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.util.EntryBuilder;

/**
 * Test for EditEntryInfoCommand
 */
public class EditEntryInfoCommandTest {
    private Model model = new ModelManager(getTypicalEntryBook(), new UserPrefs(), new Awareness());
    private CommandHistory commandHistory = new CommandHistory();
    private ResumeEntry editedEntry = new EntryBuilder().withCategory("work")
            .withTitle("Facebook").withDuration("2010 - 2013")
            .withSubHeader("software engineering intern")
            .withTags("java").build();
    private EditEntryInfoDescriptor descriptor = new EditEntryInfoDescriptor(editedEntry);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        EditEntryInfoCommand editCommand = new EditEntryInfoCommand(INDEX_FIRST_ENTRY, descriptor);

        String expectedMessage = String.format(EditEntryInfoCommand.MESSAGE_EDIT_ENTRYINFO_SUCCESS, editedEntry);

        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());
        expectedModel.updateEntry(model.getFilteredEntryList().get(0), editedEntry);
        expectedModel.commitEntryBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEntryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEntryList().size() + 1);
        EditEntryInfoCommand editCommand = new EditEntryInfoCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_attemptToEditMinorEntry_failure() {
        Index indexForMinorEntry = INDEX_THIRD_ENTRY;
        EditEntryInfoCommand command = new EditEntryInfoCommand(indexForMinorEntry, descriptor);
        assertCommandFailure(command, model, commandHistory, EditEntryInfoCommand.MESSAGE_NON_MAJOR_ENTRY);
    }

    @Test
    public void execute_duplicateEntryUnfilteredList_failure() {
        ResumeEntry firstEntry = model.getFilteredEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        EditEntryInfoDescriptor descriptor = new EditEntryInfoDescriptor(firstEntry);
        Index entryWithSameCategAndTag = Index.fromOneBased(5);
        EditEntryInfoCommand editCommand = new EditEntryInfoCommand(entryWithSameCategAndTag, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditEntryInfoCommand.MESSAGE_DUPLICATE_ENTRY);
    }


    @Test
    public void equals() {

        final EditEntryInfoCommand standardCommand = new EditEntryInfoCommand(INDEX_FIRST_ENTRY, descriptor);

        // same values -> returns true
        EditEntryInfoDescriptor copyDescriptor = new EditEntryInfoDescriptor(descriptor);
        EditEntryInfoCommand commandWithSameValues = new EditEntryInfoCommand(INDEX_FIRST_ENTRY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new HelpCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEntryInfoCommand(INDEX_SECOND_ENTRY, descriptor)));

        // different descriptor -> returns false
        copyDescriptor.setTitle("Different title");
        assertFalse(standardCommand.equals(new EditEntryInfoCommand(INDEX_FIRST_ENTRY, copyDescriptor)));
    }
}
