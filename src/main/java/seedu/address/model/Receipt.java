package seedu.address.model;

import seedu.address.model.person.IcNumber;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.person.Name;
import seedu.address.model.person.servedPatient;

/**
 * Represents the receipt for the served patients.
 */
public class Receipt implements Document {
    private final Name name;
    private final IcNumber icNumber;
    private MedicineName medicineName;
    private int quantity;

    /**
     * Creates a receipt object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public Receipt(servedPatient servedPatient) {
        this.name = servedPatient.getName();
        this.icNumber = servedPatient.getIcNumber();
    }

    @Override
    public void generate() {

        //need to know how the medicine and quantities are stored
    }

    /**
     * Previews the content of the receipt object generated for the specified servedPatient.
     *
     */
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + name + "\n");
        sb.append("NRIC: " + icNumber + "\n");
        sb.append("Medicine dispensed: \n");
        return sb.substring(0, sb.length() - 1);
    }
}
