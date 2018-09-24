package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.medicalrecord.Note;

/**
 * JAXB-friendly adapted version of the Note.
 */
public class XmlAdaptedNote {

    @XmlValue
    private String noteMessage;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNote() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedNote(String noteMessage) {
        this.noteMessage = noteMessage;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedNote(Note source) {
        noteMessage = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient
     */
    public Note toModelType() throws IllegalValueException {
        if (!Note.isValidNote(noteMessage)) {
            throw new IllegalValueException(Note.MESSAGE_NOTE_CONSTRAINTS);
        }
        return new Note(noteMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedNote)) {
            return false;
        }

        return noteMessage.equals(((XmlAdaptedNote) other).noteMessage);
    }
}
