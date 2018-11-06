package seedu.address.model.document;

import static java.util.Objects.requireNonNull;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import seedu.address.MainApp;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.QuantityToDispense;
import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.services.Service;

/**
 * The document class takes in all the information from the classes that extends it to generate a HTML file
 * for that class.
 * It is responsible for the HTML formatting of the data.
 */
public class Document {

    //Formatting the path to the directory all documents should be saved in
    public static final String DIRECTORY_PATH = "src/main/resources/view/Documents/";
    public static final String DOCUMENT_WINDOW_PATH = "/view/Documents/";
    public static final String FILE_NAME_DELIMITER = "_For_";
    private static final String TEMPLATE_PATH = "/view/Documents/DocumentTemplate.html";
    private static final String COMPLETE_TEMPLATE_PATH = MainApp.class.getResource(TEMPLATE_PATH).getFile();

    private static final String TEMPLATE_LOCATE_FAILURE_ERROR_MESSAGE = "Unable to find DocumentTemplate.html!";
    private static final String FILE_WRITE_FAILURE_ERROR_MESSAGE = "Unable to write contents into ";

    //Data placeholders in the HTML template from which all the document objects are extended from
    private static final String HEADER_PLACEHOLDER = "$headers";
    private static final String NAME_PLACEHOLDER = "$name";
    private static final String ICNUMBER_PLACEHOLDER = "$icNumber";
    private static final String CONTENT_PLACEHOLDER = "$content";
    private static final String HTML_TABLE_DATA_DIVIDER = "</td><td>";

    //Formatting the contents of the receipt into a table
    private static final String RECEIPT_HEADER = "<table ID = \"contentTable\" width = 100%><col width = \"700\">";
    private static final String RECEIPT_HEADER_CONTENT = "<tr ID = \"receiptHeader\"><div class=\"contentHeader\">"
            + "<th>Prescription</th><th>Quantity</th><th>Unit Price</th><th>Total Price</th></div></tr>";
    private static final String RECEIPT_END_CONTENT_WITHOUT_PRICE = "<tr ID = \"receiptEnd\"><td>Total:"
            + HTML_TABLE_DATA_DIVIDER + "-" + HTML_TABLE_DATA_DIVIDER + "-" + HTML_TABLE_DATA_DIVIDER;
    private static final String RECEIPT_END = "</td></tr></table>";

    private static String documentFilePath;
    private static String documentWindowPath;
    private File file;
    private Name name;
    private String fileType;
    private IcNumber icNumber;
    private String mcDuration;
    private String noteContent;
    private String referralContent;

    /**
     * Method that calls the various methods that help in the generation of the HTML file
     * for the document.
     * This includes a method to make the file name, another method to update the HTML template
     * with the correct values specified by the object that extends the document and lastly the
     * actual writing of the bytes into a file.
     */
    public void generateDocument() {
        String fileName = makeFileName();
        makeFile(fileName, writeContentsIntoDocument());
    }

    /**
     * Formats the file name of the object that extends document.
     * */
    private String makeFileName() {
        return (fileType + FILE_NAME_DELIMITER + name.toString() + "_" + icNumber.toString())
                .replaceAll("\\s", "_")
                .replaceAll("(_)+", "_");
    }

    /**
     * Generates the relevant header information that is on the printout of all the Documents
     * and formats them neatly.
     * @return neatly formatted headers, with general information of the document and the clinic.
     */
    private String generateHeaders() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return fileType + "<br>Issued On: " + formatter.format(date);
    }

    /**
     * Writing contents of the document into the HTML file.
     * @return HTML code that has the "fillers" filled up with the appropriate values.
     * For example, $headers in the HTML file is now replaced with the actual header values.
     * */
    private String writeContentsIntoDocument() {
        String htmlContent = convertHtmlIntoString();
        String title = fileType + " for " + this.name;
        htmlContent = htmlContent.replace("$title", title);
        HashMap<String, String> fieldValues = this.generateContent();
        for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            htmlContent = htmlContent.replace(key, value);
        }
        return htmlContent;
    }

    /**
     * Fills in the information as required by the fields of the document.
     * @return returns a HashMap that maps all the fields to their own correct value.
     */
    private HashMap<String, String> generateContent() {
        HashMap<String, String> informationFieldPairs = new HashMap<>();
        informationFieldPairs.put(HEADER_PLACEHOLDER, generateHeaders());
        informationFieldPairs.put(NAME_PLACEHOLDER, name.toString());
        informationFieldPairs.put(ICNUMBER_PLACEHOLDER, icNumber.toString());
        if (this instanceof Receipt) {
            informationFieldPairs.put(CONTENT_PLACEHOLDER, formatReceiptInformation());
        } else if (this instanceof MedicalCertificate) {
            informationFieldPairs.put(CONTENT_PLACEHOLDER, formatMcInformation());
        } else if (this instanceof ReferralLetter) {
            informationFieldPairs.put(CONTENT_PLACEHOLDER, formatRlInformation());
        }
        return informationFieldPairs;
    }

    /**
     * The actual generation of the file representing the document using the updated HTML code.
     */
    private void makeFile(String fileName, String htmlContent) {
        documentFilePath = DIRECTORY_PATH + fileName + ".html";
        documentWindowPath = DOCUMENT_WINDOW_PATH + fileName + ".html";
        file = new File(documentFilePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(htmlContent.getBytes());
            bos.flush();
            bos.close();
        } catch (IOException e) {
            System.out.println(FILE_WRITE_FAILURE_ERROR_MESSAGE + fileName + "!");
        }
    }

    /**
     * Converting the template HTML into a string for modifications.
     * @return a string containing the template HTML code into a string for population of
     *          necessary fields required by the type of document.
     * */
    private String convertHtmlIntoString() {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(COMPLETE_TEMPLATE_PATH));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str).append("\n");
            }
            in.close();
        } catch (IOException e) {
            System.out.println(TEMPLATE_LOCATE_FAILURE_ERROR_MESSAGE);
        }
        return contentBuilder.toString();
    }

    /**
     * Formats all the relevant information of a receipt in HTML for the served patient.
     */
    String formatReceiptInformation() {
        Receipt receipt = (Receipt) this;
        StringBuilder stringbuilder = new StringBuilder();
        receipt.resetPrice();
        stringbuilder.append(RECEIPT_HEADER)
                .append(RECEIPT_HEADER_CONTENT)
                .append(unpackTypesOfServices(receipt.getServicesRendered()))
                .append(unpackMedicineAllocation(receipt.getAllocatedMedicine()))
                .append(RECEIPT_END_CONTENT_WITHOUT_PRICE)
                .append(String.format("%.02f", receipt.getTotalPrice()))
                .append(RECEIPT_END);
        return stringbuilder.toString();
    }

    /**
     * Formats all the relevant information of a MC in HTML for the served patient.
     */
    String formatMcInformation() {
        MedicalCertificate mc = (MedicalCertificate) this;
        int numMcDays = mc.getMcDays();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("This is to certify that the above-named patient is unfit for duty for a period of ")
                .append("<b>" + numMcDays + "</b>")
                .append(" <b>day(s)</b>, from ")
                .append("<b>" + LocalDate.now().format(formatter) + "</b>")
                .append(" <b>to</b> ")
                .append("<b>" + LocalDate.now().plusDays(numMcDays - 1).format(formatter) + "</b>")
                .append(" <b>inclusive.</b><br><br>")
                .append("This certificate is not valid for absence from court attendance.<br><br>")
                .append("<b>Issuing Doctor:</b> Dr Chester Sng" + "<br>");
        return stringbuilder.toString();
    }

    /**d
     * Formats all the relevant information of a RL in HTML for the served patient.
     */
    String formatRlInformation() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("This is to certify that the above-named patient has been referred to: ")
                .append("<b>" + referralContent.toUpperCase() + "</b>" + "<br><br>")
                .append("Dear Specialist, please assist the above-named patient in the following matter:<br>")
                .append(noteContent + "<br><br>")
                .append("Kindly do accept him under your care. Thank you very much.<br><br>")
                .append("<b>Issuing Doctor:</b> Dr Chester Sng" + "<br>");
        return stringbuilder.toString();
    }

    /**
     * Extracts all the types of services rendered by the clinic for the served patient and formats it into
     * a table to be reflected in the HTML file.
     */
    private String unpackTypesOfServices(ArrayList<Service> servicesRendered) {
        requireNonNull(servicesRendered);
        Receipt receipt = (Receipt) this;
        StringBuilder stringBuilder = new StringBuilder();
        String serviceName;
        float servicePrice;
        for (Service s : servicesRendered) {
            serviceName = s.toString();

            servicePrice = Float.parseFloat(s.getPrice().toString());
            receipt.increaseTotalPriceBy(servicePrice);

            stringBuilder.append("<tr><td>")
                    .append(serviceName)
                    .append(HTML_TABLE_DATA_DIVIDER)
                    .append(1)
                    .append(HTML_TABLE_DATA_DIVIDER)
                    .append(String.format("%.02f", servicePrice))
                    .append(HTML_TABLE_DATA_DIVIDER)
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
        Receipt receipt = (Receipt) this;
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
            receipt.increaseTotalPriceBy(totalPriceForSpecificMedicine);

            stringBuilder.append("<tr><td>")
                    .append(medicineName)
                    .append(HTML_TABLE_DATA_DIVIDER)
                    .append(quantity)
                    .append(HTML_TABLE_DATA_DIVIDER)
                    .append(String.format("%.02f", pricePerUnit))
                    .append(HTML_TABLE_DATA_DIVIDER)
                    .append(String.format("%.02f", totalPriceForSpecificMedicine))
                    .append("</td></tr>");
        }
        return stringBuilder.toString();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setIcNumber(IcNumber icNumber) {
        this.icNumber = icNumber;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setMcContent(String mcDuration) {
        this.mcDuration = mcDuration;
    }

    public void setReferralContent(String referralContent) {
        this.referralContent = referralContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // BELOW ARE EXTRA GETTERS FOR JAVASCRIPT PURPOSES
    // added by @blewjy

    public String getFileName() {
        return this.makeFileName();
    }

    public String getHeaders() {
        return this.generateHeaders();
    }

    public String getPatientName() {
        return name.toString();
    }

    public String getPatientIc() {
        return icNumber.toString();
    }

    public String getContent() {
        if (this instanceof Receipt) {
            return this.formatReceiptInformation();
        } else if (this instanceof MedicalCertificate) {
            return this.formatMcInformation();
        } else if (this instanceof ReferralLetter) {
            return this.formatRlInformation();
        } else {
            return "Lorem ipsum fake news";
        }
    }

}
