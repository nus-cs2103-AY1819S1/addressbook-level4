package seedu.address.model.Document;

import java.util.Map;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.person.ServedPatient;

/**
 * Represents the receipt for the served patients.
 */
public class Receipt extends Document {
    private static final String FILE_TYPE = "Receipt";
    private static final String RECEIPT_HEADER_CONTENT = "<table ID = \"contentTable\" width = 100%>"
            + "<col width = \"700\"><tr ID = \"receiptHeader\"><div class=\"contentHeader\"><th>Prescription</th>"
            + "<th>Quantity</th><th>Unit Price</th><th>Total Price</th></div></tr>";
    private static final String RECEIPT_END_CONTENT = "<tr ID = \"receiptEnd\">"
            + "<td>Grand Total:</td><td></td><td></td><td>a lot of money</td></tr></table>";
    private String noteContent;
    private Map<Medicine, Integer> allocatedMedicine;

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
    //private String unpackConsultationInformation(Map<String, Integer> treatmentsReceived) {
    String unpackConsultationInformation() {
        String treatments = "<tr><td>Consultation</td><td>1</td><td>30.00</td><td>30.00</td></tr>";
        return RECEIPT_HEADER_CONTENT + treatments + RECEIPT_END_CONTENT;
    }
    private String unpackMedicineAllocation(Map<Medicine, Integer> medicineAllocated) {
        //String
        return "lo";
    }
}
