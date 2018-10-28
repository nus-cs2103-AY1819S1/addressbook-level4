package seedu.address.storage;

import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.person.medicalrecord.Message;
import seedu.address.model.person.medicalrecord.Note;
import seedu.address.model.person.medicalrecord.Quantity;

/**
 * JAXB-friendly adapted version of the Note.
 */
public class XmlAdaptedNote {

    @XmlElement (required = true)
    private String noteMessage;

    @XmlElement
    private Map<String, String> dispensedMedicines;

    /**
     * Constructs an XmlAdaptedNote.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNote() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedNote(String noteMessage, Map<MedicineName, Quantity> dispensedMedicines) {
        this.noteMessage = noteMessage;
        if (dispensedMedicines != null) {
            this.dispensedMedicines = new HashMap<>();
            dispensedMedicines.forEach(((medicineName, quantity) -> {
                this.dispensedMedicines.put(medicineName.fullName, quantity.value);
            }));
        }
    }

    /**
     * Converts a given Note into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedNote(Note source) {
        noteMessage = source.getMessage().value;
        Map<MedicineName, Quantity> map = source.getDispensedMedicines();
        this.dispensedMedicines = new HashMap<>();
        map.forEach(((medicineName, quantity) -> {
            dispensedMedicines.put(medicineName.fullName, quantity.value);
        }));
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient
     */
    public Note toModelType() throws IllegalValueException {

        if (noteMessage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Message.class.getSimpleName()));
        }
        if (!Message.isValidMessage(noteMessage)) {
            throw new IllegalValueException(Message.MESSAGE_MESSAGE_CONSTRAINTS);
        }
        final Message modelMessage = new Message(noteMessage);

        final Map<MedicineName, Quantity> modelDispensedMedicines = new HashMap<>();
        this.dispensedMedicines.forEach((medicineName, quantity) -> {
            modelDispensedMedicines.put(new MedicineName(medicineName), new Quantity(quantity));
        });

        return new Note(modelMessage, modelDispensedMedicines);
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
