package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT_WITH_FULL_STOP;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.updatePersonInModelWithNote;
import static seedu.address.logic.commands.EditNoteCommand.MESSAGE_SUCCESS;
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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EditNoteCommand}.
 */
public class EditNoteCommandTest {

    private static final String VALID_NOTE_TEXT_TWO_WITH_FULL_STOP = "Testing.";

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
    public void constructor_nullText_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditNoteCommand(INDEX_FIRST_PERSON, null);
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditNoteCommand(null, VALID_NOTE_TEXT);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        try {
            new EditNoteCommand(INDEX_FIRST_PERSON, VALID_NOTE_TEXT).execute(null, commandHistory);
        } catch (CommandException e) {
            throw new AssertionError("CommandException should not be thrown.");
        }
    }

    @Test
    public void execute_editExistingNote_success() {
        model = createNewModelWithExistingNote();
        Person firstPerson = TestUtil.getPerson(model, INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        updatePersonInModelWithNote(firstPerson, expectedModel, VALID_NOTE_TEXT_TWO_WITH_FULL_STOP);
        String expectedMessage = String.format(MESSAGE_SUCCESS, firstPerson);
        expectedModel.commitAddressBook();

        Command command = new EditNoteCommand(INDEX_FIRST_PERSON, VALID_NOTE_TEXT_TWO_WITH_FULL_STOP);
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
        new EditNoteCommand(INDEX_LARGE, VALID_NOTE_TEXT).execute(model, commandHistory);
    }

    @Test
    public void execute_editEmptyNote_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        new EditNoteCommand(INDEX_FIRST_PERSON, VALID_NOTE_TEXT).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        EditNoteCommand sampleEditNoteCommand = new EditNoteCommand(INDEX_FIRST_PERSON, VALID_NOTE_TEXT_WITH_FULL_STOP);
        EditNoteCommand secondIndexEditNoteCommand = new EditNoteCommand(INDEX_SECOND_PERSON,
            VALID_NOTE_TEXT_WITH_FULL_STOP);
        EditNoteCommand noFullStopEditNoteCommand = new EditNoteCommand(INDEX_FIRST_PERSON, VALID_NOTE_TEXT);

        // same object -> returns true
        assertTrue(sampleEditNoteCommand.equals(sampleEditNoteCommand));

        // same values -> returns true
        EditNoteCommand sampleEditNoteCommandCopy = new EditNoteCommand(INDEX_FIRST_PERSON,
            VALID_NOTE_TEXT_WITH_FULL_STOP);
        assertTrue(sampleEditNoteCommand.equals(sampleEditNoteCommandCopy));

        // different types -> returns false
        assertFalse(sampleEditNoteCommand.equals(1));

        // null -> returns false
        assertFalse(sampleEditNoteCommand.equals(null));

        // different index -> returns false
        assertFalse(sampleEditNoteCommand.equals(secondIndexEditNoteCommand));

        // different text -> returns false
        assertFalse(sampleEditNoteCommand.equals(noFullStopEditNoteCommand));
    }

    private ModelManager createNewModelWithExistingNote() {
        Person samplePerson = new PersonBuilder().withNote(VALID_NOTE_TEXT).build();
        AddressBook addressBook = new AddressBookBuilder().withPerson(samplePerson).build();
        return new ModelManager(addressBook, new UserPrefs());
    }
}
