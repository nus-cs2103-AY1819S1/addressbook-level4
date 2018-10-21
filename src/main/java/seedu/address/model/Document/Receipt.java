package seedu.address.model.Document;

import java.util.Map;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the receipt for the served patients. This class is responsible for extracting information that is
 * relevant to the receipt.
 */
public class Receipt extends Document {
    private static final String FILE_TYPE = "Receipt";

    protected float totalPrice = 0;
    protected Map<Medicine, Integer> allocatedMedicine;

    /**
     * Creates a receipt object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public Receipt(ServedPatient servedPatient) {
        setFileType(FILE_TYPE);
        setName(servedPatient.getName());
        setIcNumber(servedPatient.getIcNumber());
        allocatedMedicine = servedPatient.getMedicineAllocated();
    }
}
