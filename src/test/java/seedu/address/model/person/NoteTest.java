package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_NUMBERS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_SPECIALCHAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT_WITH_EXCLAMATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT_WITH_FULL_STOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_TEXT_WITH_QUESTION;
import static seedu.address.model.person.Note.DEFAULT_NOTE;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NoteTest {

    @Test
    public void emptyConstructor_setTextAsDefault() {
        Note note = new Note();
        assertEquals(DEFAULT_NOTE, note.toString());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_validTextWithPunctuation() {
        Note note = new Note(VALID_NOTE_TEXT_WITH_EXCLAMATION);
        assertEquals(VALID_NOTE_TEXT_WITH_EXCLAMATION, note.toString());
    }

    @Test
    public void constructor_validTextNoPunctuation() {
        Note note = new Note(VALID_NOTE_TEXT);
        assertEquals(VALID_NOTE_TEXT_WITH_FULL_STOP, note.toString());
    }

    @Test
    public void constructor_validText() {
        Note note = new Note(VALID_NOTE_TEXT_WITH_FULL_STOP);
        assertEquals(VALID_NOTE_TEXT_WITH_FULL_STOP, note.toString());
    }

    @Test
    public void equals() {
        Note sampleNote = new Note(VALID_NOTE_TEXT_WITH_FULL_STOP);
        Note sampleNoteWithFullStop = new Note(VALID_NOTE_NUMBERS + ".");

        // same obj -> return true
        assertTrue(sampleNote.equals(sampleNote));

        // same fields -> return true
        Note sampleNoteCopy = new Note(VALID_NOTE_TEXT_WITH_FULL_STOP);
        assertTrue(sampleNote.equals(sampleNoteCopy));

        // null -> return false
        assertFalse(sampleNote.equals(null));

        // diff types -> return false
        assertFalse(sampleNote.equals(5));

        // diff fields -> return false
        assertFalse(sampleNote.equals(sampleNoteWithFullStop));
    }

    @Test
    public void add_delete_hasChanged() {
        Note note = new Note();
        assertFalse(note.hasChanged());

        // add to empty text with full stop
        note = note.add(VALID_NOTE_TEXT_WITH_FULL_STOP);
        assertEquals(VALID_NOTE_TEXT_WITH_FULL_STOP, note.toString());
        assertTrue(note.hasChanged());

        // add to existing text with exclamation mark -> existing full stop changed to comma
        note = note.add(VALID_NOTE_TEXT_WITH_EXCLAMATION);
        assertEquals(VALID_NOTE_TEXT + "," + VALID_NOTE_TEXT_WITH_EXCLAMATION, note.toString());
        assertTrue(note.hasChanged());

        // delete note -> reset note text to default
        note = note.delete();
        assertEquals(DEFAULT_NOTE, note.toString());
        assertFalse(note.hasChanged());

        // delete an empty note -> same result as deleting an existing note
        note = note.delete();
        assertEquals(DEFAULT_NOTE, note.toString());
        assertFalse(note.hasChanged());

        // add to empty text ending with question mark
        note = note.add(VALID_NOTE_TEXT_WITH_QUESTION);
        assertEquals(VALID_NOTE_TEXT_WITH_QUESTION, note.toString());

        // add to existing text with no punctuation -> existing question mark changed to comma
        note = note.add(VALID_NOTE_TEXT);
        assertEquals(VALID_NOTE_TEXT + "," + VALID_NOTE_TEXT_WITH_FULL_STOP, note.toString());
    }

    @Test
    public void isPunctuated() {
        assertFalse(Note.isPunctuated(VALID_NOTE_TEXT));
        assertTrue(Note.isPunctuated(VALID_NOTE_TEXT_WITH_FULL_STOP));
        assertTrue(Note.isPunctuated(VALID_NOTE_TEXT_WITH_EXCLAMATION));
        assertTrue(Note.isPunctuated(VALID_NOTE_TEXT_WITH_QUESTION));
    }

    @Test
    public void isValid() {
        // null text
        Assert.assertThrows(NullPointerException.class, () -> Note.isValid(null));

        // invalid text
        assertFalse(Note.isValid("")); // empty string
        assertFalse(Note.isValid(" ")); // spaces only
        assertFalse(Note.isValid("\n")); // next line char
        assertFalse(Note.isValid("\t")); // tab space
        assertFalse(Note.isValid(" " + VALID_NOTE_TEXT_WITH_FULL_STOP)); // whitespace prefix in front of text

        // valid phone numbers
        assertTrue(Note.isValid(VALID_NOTE_TEXT)); // text with no punctuation
        assertTrue(Note.isValid(VALID_NOTE_TEXT_WITH_FULL_STOP)); // text with punctuation
        assertTrue(Note.isValid(VALID_NOTE_NUMBERS)); // numbers
        assertTrue(Note.isValid(VALID_NOTE_SPECIALCHAR)); // special char
    }
}
