package seedu.address.model;

import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This interface bounds all classes implementing it to provide an implementation for generating a document,
 */
public abstract class Document {
    private Name name;
    private IcNumber IcNumber;

    private static final String TAB_FORMATTING = "\t";
    private static final String DOCUMENT_PATH = "\t";

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

    public void makeDocument() {
        String fileName = this.getClass().getName() + "For"
                + this.name.toString().replaceAll("\\s","") + "_" + this.IcNumber.toString();
        String filePath = "/view/Documents";
        File file = new File(filePath + File.separator + fileName + ".html");


    }
}
