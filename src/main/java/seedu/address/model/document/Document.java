package seedu.address.model.document;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import seedu.address.MainApp;
import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;

/**
 * The document class takes in all the information from the classes that extends it to generate a HTML file
 * for that class.
 * It is responsible for the HTML formatting of the data.
 */
public abstract class Document {

    //Formatting the path to the directory all documents should be saved in
    public static final String DIRECTORY_PATH = "src/main/resources/view/Documents/";
    public static final String FILE_NAME_DELIMITER = "_";
    static final String HTML_TABLE_DATA_DIVIDER = "</td><td>";
    private static final String TEMPLATE_PATH = "/view/Documents/DocumentTemplate.html";
    private static final String COMPLETE_TEMPLATE_PATH = MainApp.class.getResource(TEMPLATE_PATH).getFile();

    //Data placeholders in the HTML template from which all the document objects are extended from
    private static final String HEADER_PLACEHOLDER = "$headers";
    private static final String NAME_PLACEHOLDER = "$name";
    private static final String ICNUMBER_PLACEHOLDER = "$icNumber";
    private static final String CONTENT_PLACEHOLDER = "$content";

    //Formatting the contents of the receipt into a table
    private static final String RECEIPT_HEADER = "<table ID = \"contentTable\" width = 100%><col width = \"700\">";
    private static final String RECEIPT_HEADER_CONTENT = "<tr ID = \"receiptHeader\"><div class=\"contentHeader\">"
            + "<th>Prescription</th><th>Quantity</th><th>Unit Price</th><th>Total Price</th></div></tr>";
    private static final String RECEIPT_END_CONTENT_WITHOUT_PRICE = "<tr ID = \"receiptEnd\"><td>Total:"
            + HTML_TABLE_DATA_DIVIDER + "-" + HTML_TABLE_DATA_DIVIDER + "-" + HTML_TABLE_DATA_DIVIDER;
    private static final String RECEIPT_END = "</td></tr></table>";

    //Error message strings
    private static final String TEMPLATE_LOCATE_FAILURE_ERROR_MESSAGE = "Unable to find DocumentTemplate.html!";
    private static final String FILE_WRITE_FAILURE_ERROR_MESSAGE = "Unable to write contents into ";

    private static String documentFilePath;
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
        makeFile(fileName, writeContentsIntoDocumentTemplate());
    }

    /**
     * Formats the file name of the object that extends document.
     * */
    private String makeFileName() {
        return (fileType + FILE_NAME_DELIMITER + "For" + FILE_NAME_DELIMITER + name.toString()
                + FILE_NAME_DELIMITER + icNumber.toString())
                .replaceAll("\\s", FILE_NAME_DELIMITER)
                .replaceAll("(_)+", FILE_NAME_DELIMITER);
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
    private String writeContentsIntoDocumentTemplate() {
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
        informationFieldPairs.put(CONTENT_PLACEHOLDER, formatInformation());
        return informationFieldPairs;
    }

    /**
     * The actual generation of the file representing the document using the updated HTML code.
     */
    private void makeFile(String fileName, String htmlContent) {
        documentFilePath = DIRECTORY_PATH + fileName + ".html";
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
     * Formats all the relevant information of the document in HTML for the served patient.
     */
    public abstract String formatInformation();

    public void setName(Name name) {
        this.name = name;
    }

    public void setIcNumber(IcNumber icNumber) {
        this.icNumber = icNumber;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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
        return this.formatInformation();
    }
}
