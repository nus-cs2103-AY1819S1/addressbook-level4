package seedu.address.model;

import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the receipt for the served patients.
 */
public class Receipt implements Document {
    private final Name name;
    private final IcNumber icNumber;
    private final String noteContent;

    /**
     * Creates a receipt object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public Receipt(ServedPatient servedPatient) {
        this.name = servedPatient.getName();
        this.icNumber = servedPatient.getIcNumber();
        this.noteContent = servedPatient.getNoteContent();
    }

    /**
     * Generates the content of the receipt object generated for the specified servedPatient.
     *
     */
    @Override
    public String generate() {
        //dissect contents of note to extract medicines dispensed
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + name + "\n");
        sb.append("NRIC: " + icNumber + "\n");
        sb.append("Medicine dispensed: \n");
        return sb.substring(0, sb.length() - 1);
    }
}
