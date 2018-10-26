package seedu.address.testutil;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.person.medicalrecord.Message;
import seedu.address.model.person.medicalrecord.Note;
import seedu.address.model.person.medicalrecord.Quantity;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Note objects.
 */
public class NoteBuilder {

    public static final String DEFAULT_MESSAGE = "This patient is crazy!";

    private Message message;
    private Map<MedicineName, Quantity> dispensedMedicines;

    public NoteBuilder() {
        message = new Message(DEFAULT_MESSAGE);
        dispensedMedicines = new HashMap<>();
    }

    /**
     * Initializes the NoteBuilder with the data of {@code noteToCopy}.
     */
    public NoteBuilder(Note noteToCopy) {
        message = noteToCopy.getMessage();
        dispensedMedicines = noteToCopy.getDispensedMedicines();
    }


    /**
     * Sets the {@code Message} of the {@code Note} that we are building.
     */
    public NoteBuilder withMessage(String message) {
        this.message = new Message(message);
        return this;
    }

    /**
     * Sets the {@code Map<MedicineName, Quantity>} of the {@code Note} that we are building.
     */
    public NoteBuilder withDispensedMedicines(Map.Entry<String, Integer>... dispensedMedicines) {
        this.dispensedMedicines = SampleDataUtil.getDispensedMedicines(dispensedMedicines);
        return this;
    }

    public Note build() {
        return new Note(message, dispensedMedicines);
    }

}
