package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores SSENISUB data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given SSENISUB data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableSsenisub ssenisub)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, ssenisub);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableSsenisub loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableSsenisub.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
