package seedu.address.model;

import seedu.address.MainApp;
import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This interface bounds all classes implementing it to provide an implementation for generating a document,
 */
public abstract class Document {
    private Name name;
    private IcNumber icNumber;

    private static final String TAB_FORMATTING = "\t";
    private static final String DOCUMENT_PATH = "";
    private static final int FILENAME_CLASS_SLICING = 20;
    private static final int FILENAME_INITIAL_SLICING = 6;
    private static final int FILENAME_END_SLICING = 17;
    private static final String DUMMYPATH = "/view/PatientView.html";

    public String tabFormat(String information) {
        return TAB_FORMATTING + information;
    }

    /**
     * Generates the relevant information that is on the printout of all the documents
     * and formats them neatly.
     * @return neatly formatted headers, with general information of the document and the clinic.
     */
    public String generateHeaders() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return tabFormat("Date/Time: " + formatter.format(date) + "\n\n");
    }

    /**
     * Generates the document.
     */
    abstract String generate();

    public static void makeDocument(Document document) {
        String fileName = document.getClass().getName().substring(FILENAME_CLASS_SLICING) + "For"
                + document.getName().toString().replaceAll("\\s","")
                + "_" + document.getIcNumber().toString();
        String completeDummyPath = MainApp.class.getResource(DUMMYPATH).toExternalForm();
        String filePath = completeDummyPath.substring(FILENAME_INITIAL_SLICING , completeDummyPath.length()
                            - (FILENAME_END_SLICING)) + "/Documents";
        File file = new File(filePath + File.separator + fileName + ".html");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
