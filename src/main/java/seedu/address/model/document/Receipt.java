package seedu.address.model.document;

import java.util.ArrayList;

import seedu.address.model.person.ServedPatient;

/**
 * Represents the receipt for the served patients. This class is responsible for extracting information that is
 * relevant to the receipt.
 */
public class Receipt extends Document {
    public static final String FILE_TYPE = "Receipt";

    /**
     * Creates a receipt object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public Receipt(ServedPatient servedPatient) {
        setFileType(FILE_TYPE);
        setName(servedPatient.getName());
        setIcNumber(servedPatient.getIcNumber());
        setAllocatedMedicine(servedPatient.getMedicineAllocated());
        setServicesRendered(new ArrayList<>());
    }
}
