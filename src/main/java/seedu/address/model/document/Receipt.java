package seedu.address.model.document;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Map;

import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.QuantityToDispense;
import seedu.address.model.person.ServedPatient;
import seedu.address.model.services.Service;

/**
 * Represents the receipt for the served patients. This class is responsible for extracting information that is
 * relevant to the receipt.
 */
public class Receipt extends Document {
    public static final String FILE_TYPE = "Receipt";

    //Formatting the contents of the receipt into a table
    private static final String RECEIPT_HEADER = "<table ID = \"contentTable\" width = 100%><col width = \"700\">";
    private static final String RECEIPT_HEADER_CONTENT = "<tr ID = \"receiptHeader\"><div class=\"contentHeader\">"
            + "<th>Prescription</th><th>Quantity</th><th>Unit Price</th><th>Total Price</th></div></tr>";
    private static final String RECEIPT_END_CONTENT_WITHOUT_PRICE = "<tr ID = \"receiptEnd\"><td>Grand Total:"
            + HTML_TABLE_DATA_DIVIDER + "-" + HTML_TABLE_DATA_DIVIDER + "-" + HTML_TABLE_DATA_DIVIDER;
    private static final String RECEIPT_END = "</td></tr></table>";

    private float totalPrice;
    private Map<Medicine, QuantityToDispense> allocatedMedicine;
    private HashSet<Service> servicesRendered;

    /**
     * Creates a receipt object for the specified servedPatient.
     * @param servedPatient the patient who has already consulted the doctor.
     */
    public Receipt(ServedPatient servedPatient) {
        totalPrice = 0;
        setFileType(FILE_TYPE);
        setName(servedPatient.getName());
        setIcNumber(servedPatient.getIcNumber());
        allocatedMedicine = servedPatient.getMedicineAllocated();
        servicesRendered = new HashSet<>();
        servicesRendered.add(Service.CONSULTATION);
    }

    /**
     * Formats all the relevant information of the receipt in HTML for the served patient. It formats all the
     * medicine dispensed and services rendered by the clinic during the consultation into a table, with entries for
     * each chargeable medicines/services offered. This information includes the price and quantity of the
     * medicines/services offered, along with the grand total price of the consultation.
     */
    public String formatInformation() {
        StringBuilder stringbuilder = new StringBuilder();
        resetPrice();
        stringbuilder.append(RECEIPT_HEADER)
                .append(RECEIPT_HEADER_CONTENT)
                .append(unpackTypesOfServices(getServicesRendered()))
                .append(unpackMedicineAllocation(getAllocatedMedicine()))
                .append(RECEIPT_END_CONTENT_WITHOUT_PRICE)
                .append(String.format("%.02f", getTotalPrice()))
                .append(RECEIPT_END);
        return stringbuilder.toString();
    }

    /**
     * Extracts all the types of services rendered by the clinic for the served patient and formats it into
     * a table to be reflected in the HTML file.
     */
    private String unpackTypesOfServices(HashSet<Service> servicesRendered) {
        requireNonNull(servicesRendered);
        StringBuilder stringBuilder = new StringBuilder();
        String serviceName;
        float servicePrice;
        for (Service s : servicesRendered) {
            serviceName = s.toString();
            servicePrice = Float.parseFloat(s.getPrice().toString());
            increaseTotalPriceBy(servicePrice);

            stringBuilder.append("<tr><td>")
                    .append(serviceName)
                    .append(super.HTML_TABLE_DATA_DIVIDER)
                    .append(1)
                    .append(super.HTML_TABLE_DATA_DIVIDER)
                    .append(String.format("%.02f", servicePrice))
                    .append(super.HTML_TABLE_DATA_DIVIDER)
                    .append(String.format("%.02f", servicePrice))
                    .append("</td></tr>");
        }
        return stringBuilder.toString();
    }

    /**
     * Extracts all the medicines dispensed by the clinic for the served patient and formats it into
     * a table to be reflected in the HTML file.
     * @param medicineAllocated Hashmap containing all the medicine dispensed to the served patient
     *                          and their individual respective quantities
     */
    private String unpackMedicineAllocation(Map<Medicine, QuantityToDispense> medicineAllocated) {
        StringBuilder stringBuilder = new StringBuilder();
        int quantity;
        float pricePerUnit;
        float totalPriceForSpecificMedicine;
        String medicineName;
        for (Map.Entry<Medicine, QuantityToDispense> entry : medicineAllocated.entrySet()) {
            Medicine medicine = entry.getKey();
            medicineName = medicine.getMedicineName().toString();
            quantity = entry.getValue().getValue();
            pricePerUnit = Float.parseFloat(medicine.getPricePerUnit().toString());
            totalPriceForSpecificMedicine = pricePerUnit * quantity;
            increaseTotalPriceBy(totalPriceForSpecificMedicine);

            stringBuilder.append("<tr><td>")
                    .append(medicineName)
                    .append(super.HTML_TABLE_DATA_DIVIDER)
                    .append(quantity)
                    .append(super.HTML_TABLE_DATA_DIVIDER)
                    .append(String.format("%.02f", pricePerUnit))
                    .append(super.HTML_TABLE_DATA_DIVIDER)
                    .append(String.format("%.02f", totalPriceForSpecificMedicine))
                    .append("</td></tr>");
        }
        return stringBuilder.toString();
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void increaseTotalPriceBy(float increment) {
        totalPrice += increment;
    }

    public void resetPrice() {
        totalPrice = 0;
    }

    public Map<Medicine, QuantityToDispense> getAllocatedMedicine() {
        return allocatedMedicine;
    }

    public HashSet<Service> getServicesRendered() {
        return servicesRendered;
    }
}
