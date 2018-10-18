package seedu.address.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.MainApp;
import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;

/**
 * This interface bounds all classes implementing it to provide an implementation for generating a document,
 */
public abstract class Document {
    private static final int FILENAME_CLASS_SLICING = 20;
    private static final int FILENAME_INITIAL_SLICING = 6;
    private static final int FILENAME_END_SLICING = 22;

    //Formatting the path to the directory all documents should be saved in
    private static final String DUMMY_PATH = "/view/Documents/DocumentTemplate.html";
    private static final String COMPLETE_TEMPLATE_NAME = MainApp.class.getResource(DUMMY_PATH).toExternalForm()
            .substring(FILENAME_INITIAL_SLICING).replace("out/production", "src/main");
    private static final String DIRECTORY_PATH = COMPLETE_TEMPLATE_NAME
            .substring(0, COMPLETE_TEMPLATE_NAME.length() - FILENAME_END_SLICING);

    private final String FAILURE_TO_LOCATE_TEMPLATE_ERROR_MESSAGE =
            "Unable to find DocumentTemplate.html to use as template!";
    private final String FAILURE_TO_WRITE_INTO_FILE_ERROR_MESSAGE =
            "Unable to write contents into ";

    private Name name;
    private IcNumber icNumber;
    private String completeFilePath;
    private String typeOfFile;

    /**
     * Generates the relevant information that is on the printout of all the documents
     * and formats them neatly.
     * @return neatly formatted headers, with general information of the document and the clinic.
     */
    private String generateHeaders() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return "Date/Time: " + formatter.format(date);
    }

    /**
     * Generates the document.
     */
    abstract String generate();

    /**
     * Formats the file name of the object that implements Document.
     * @return name of the file for the object
     * */
    private String makeDocument() {
        //Creation of the file name using string slicing
        typeOfFile = this.getClass().getName().substring(FILENAME_CLASS_SLICING);
        String fileName =  typeOfFile + "_For_" + this.getName().toString().replaceAll("\\s", "")
                            + "_" + this.getIcNumber().toString();
        completeFilePath = DIRECTORY_PATH + File.separator + fileName + ".html";
        return completeFilePath;
    }

    /**
     * Writing contents into a document
     * */
    public void writeContentsIntoDocument() {
        String filePath = this.makeDocument();
        String htmlContent = convertHtmlIntoString();
        String title = typeOfFile + " for " + this.name;
        String body = this.generate();
        htmlContent = htmlContent.replace("$headers", generateHeaders());
        htmlContent = htmlContent.replace("$title", title);
        htmlContent = htmlContent.replace("$body", body);
        File newDocument = new File(filePath);
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(newDocument);
            fileWriter.write(htmlContent);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(FAILURE_TO_WRITE_INTO_FILE_ERROR_MESSAGE + filePath +"!");
        }
    }

    private String convertHtmlIntoString() {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(COMPLETE_TEMPLATE_NAME));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str).append("\n");
            }
            in.close();
        } catch (IOException e) {
            System.out.println(FAILURE_TO_LOCATE_TEMPLATE_ERROR_MESSAGE);
        }
        return contentBuilder.toString();
    }


    public Name getName() {
        return name;
    }

    public IcNumber getIcNumber() {
        return icNumber;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setIcNumber(IcNumber icNumber) {
        this.icNumber = icNumber;
    }
}
