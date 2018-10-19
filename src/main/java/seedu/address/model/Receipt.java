package seedu.address.model;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the receipt for the served patients.
 */
public class Receipt extends Document {
    private static final String FILE_TYPE = "Receipt";
    private String noteContent;
    private final Medicine medicine = null; //should be extractable from noteContent
    private final int medicineQuantity = 0; //should be extractable from noteContent

    /**
     * Creates a receipt object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public Receipt(ServedPatient servedPatient) {
        setFileType(FILE_TYPE);
        setName(servedPatient.getName());
        setIcNumber(servedPatient.getIcNumber());
        this.noteContent = servedPatient.getNoteContent();
    }
}
