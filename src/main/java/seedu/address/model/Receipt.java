package seedu.address.model;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the receipt for the served patients.
 */
public class Receipt extends Document {
    private String noteContent;
    private final Medicine medicine = null; //should be extractable from noteContent
    private final int medicineQuantity = 0; //should be extractable from noteContent

    /**
     * Creates a receipt object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public Receipt(ServedPatient servedPatient) {
        setName(servedPatient.getName());
        setIcNumber(servedPatient.getIcNumber());
        this.noteContent = servedPatient.getNoteContent();
    }

    /**
     * Generates the content of the receipt object generated for the specified servedPatient.
     */
    @Override
    public String generate() {
        //dissect contents of note to extract medicines dispensed
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + super.getName() + "<br>");
        sb.append("NRIC: " + super.getIcNumber() + "<br>");
        sb.append("Medicine dispensed: <br>");
        return sb.toString();
    }
}
