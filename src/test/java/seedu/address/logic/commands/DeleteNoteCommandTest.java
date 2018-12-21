package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT_WITH_FULL_STOP;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.updatePersonInModelWithNote;
import static seedu.address.logic.commands.DeleteNoteCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalAddressbook.getTypicalAddressBookCopy;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_LARGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteNoteCommand}.
 */
public class DeleteNoteCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager model;
    private CommandHistory commandHistory;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookCopy(), new UserPrefs());
        commandHistory = new CommandHistory();
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteNoteCommand(null);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        try {
            new DeleteNoteCommand(INDEX_FIRST_PERSON).execute(null, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("CommandException should not be thrown.");
        }
    }

    @Test
    public void execute_deleteNote_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.commitAddressBook();

        // add note to person first before deleting
        Person personToDelete = TestUtil.getPerson(model, INDEX_FIRST_PERSON);
        updatePersonInModelWithNote(personToDelete, model, VALID_NOTE_TEXT_WITH_FULL_STOP);

        Person personWithNote = TestUtil.getPerson(model, INDEX_FIRST_PERSON);
        String expectedMessage = String.format(MESSAGE_SUCCESS, personWithNote);
        Command command = new DeleteNoteCommand(INDEX_FIRST_PERSON);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // undo test
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo test
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        new DeleteNoteCommand(INDEX_LARGE).execute(model, commandHistory);
    }

    @Test
    public void execute_deleteEmptyNote_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        new DeleteNoteCommand(INDEX_FIRST_PERSON).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        DeleteNoteCommand sampleDeleteNoteCommand = new DeleteNoteCommand(INDEX_FIRST_PERSON);
        DeleteNoteCommand secondIndexDeleteNoteCommand = new DeleteNoteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(sampleDeleteNoteCommand.equals(sampleDeleteNoteCommand));

        // same values -> returns true
        DeleteNoteCommand sampleDeleteNoteCommandCopy = new DeleteNoteCommand(INDEX_FIRST_PERSON);
        assertTrue(sampleDeleteNoteCommand.equals(sampleDeleteNoteCommandCopy));

        // different types -> returns false
        assertFalse(sampleDeleteNoteCommand.equals(1));

        // null -> returns false
        assertFalse(sampleDeleteNoteCommand.equals(null));

        // different index -> returns false
        assertFalse(sampleDeleteNoteCommand.equals(secondIndexDeleteNoteCommand));
    }
}
