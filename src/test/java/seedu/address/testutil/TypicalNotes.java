package seedu.address.testutil;

import seedu.address.model.person.medicalrecord.Note;

/**
 * A class containing typical note objects used for testing purposes.
 */
public class TypicalNotes {

    public static final Note NOTE_ONE = new NoteBuilder().withMessage("This guy is very sick").build();
    public static final Note NOTE_TWO = new NoteBuilder().withMessage("This guy is very sicked").build();

}
