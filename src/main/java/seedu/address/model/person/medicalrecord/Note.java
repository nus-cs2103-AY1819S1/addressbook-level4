package seedu.address.model.person.medicalrecord;

import java.util.Map;
import java.util.Objects;

import seedu.address.model.medicine.SerialNumber;

/**
 * Represents a note object belonging to a patient.
 */
public class Note {

    // Items belonging to a note object
    private Message message;
    private Map<SerialNumber, Quantity> dispensedMedicines;

    public Note(Message message, Map<SerialNumber, Quantity> dispensedMedicines) {
        this.message = message;
        this.dispensedMedicines = dispensedMedicines;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Map<SerialNumber, Quantity> getDispensedMedicines() {
        return dispensedMedicines;
    }

    public void setDispensedMedicines(Map<SerialNumber, Quantity> dispensedMedicines) {
        this.dispensedMedicines = dispensedMedicines;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Note: ")
                .append(" Message: ")
                .append(getMessage())
                .append(" Dispensed Medicines: ")
                .append(getDispensedMedicines());
        return builder.toString();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(message, dispensedMedicines);
    }
}
