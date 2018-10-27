package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalNotes.NOTE_ONE;

import java.util.Map;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.person.medicalrecord.Message;
import seedu.address.model.person.medicalrecord.Quantity;
import seedu.address.testutil.Assert;

public class XmlAdaptedNoteTest {

    private static final String INVALID_MESSAGE = " ";

    private static final Map<MedicineName, Quantity> VALID_DISPENSED_MEDICINES = NOTE_ONE.getDispensedMedicines();

    @Test
    public void toModelType_validNoteDetails_returnsNote() throws Exception {
        XmlAdaptedNote note = new XmlAdaptedNote(NOTE_ONE);
        assertEquals(NOTE_ONE, note.toModelType());
    }

    @Test
    public void toModelType_invalidMessage_throwsIllegalValueException() {
        XmlAdaptedNote note = new XmlAdaptedNote(INVALID_MESSAGE, VALID_DISPENSED_MEDICINES);
        String expectedMessage = Message.MESSAGE_MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }

    @Test
    public void toModelType_nullMessage_throwsIllegalValueException() {
        XmlAdaptedNote note = new XmlAdaptedNote(null, VALID_DISPENSED_MEDICINES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Message.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, note::toModelType);
    }


}
