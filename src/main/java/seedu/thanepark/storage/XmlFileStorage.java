package seedu.thanepark.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.thanepark.commons.exceptions.DataConversionException;
import seedu.thanepark.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given thanepark data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableThanePark addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns thanepark book in the file or an empty thanepark book
     */
    public static XmlSerializableThanePark loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableThanePark.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
