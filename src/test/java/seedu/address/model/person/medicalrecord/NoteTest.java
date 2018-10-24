package seedu.address.model.person.medicalrecord;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static seedu.address.testutil.TypicalNotes.NOTE_ONE;
import static seedu.address.testutil.TypicalNotes.NOTE_TWO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.NoteBuilder;

public class NoteTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {

        // same values -> returns true
        Note noteOneCopy = new NoteBuilder(NOTE_ONE).build();
        assertTrue(NOTE_ONE.equals(noteOneCopy));

        // same object -> returns true
        assertTrue(NOTE_ONE.equals(NOTE_ONE));

        // null -> returns false
        assertFalse(NOTE_ONE.equals(null));

        // different type -> returns false
        assertFalse(NOTE_ONE.equals(5));

        // different note -> returns false
        assertFalse(NOTE_ONE.equals(NOTE_TWO));

        // different message -> returns false
        Note editedNoteOne = new NoteBuilder(NOTE_ONE).withMessage("This is an edited message!").build();
        assertFalse(NOTE_ONE.equals(editedNoteOne));

    }
}
