package seedu.address.model.person.medicalrecord;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a note object belonging to a patient.
 */
public class Note {

    public static final String MESSAGE_NOTE_CONSTRAINTS =
            "Notes should not be blank, and first character should not be a whitespace.";
    public static final String NOTE_VALIDATION_REGEX = "[^\\s].*";
    public final String value;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        checkArgument(isValidNote(note), MESSAGE_NOTE_CONSTRAINTS);
        value = note;
    }

    /**
     * Returns true if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        return test.matches(NOTE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
