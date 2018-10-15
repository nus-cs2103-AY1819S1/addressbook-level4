package seedu.address.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This interface bounds all classes implementing it to provide an implementation for generating a document,
 */
public abstract class Document {

    private final String TAB_FORMATTING = "\t";

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
}
