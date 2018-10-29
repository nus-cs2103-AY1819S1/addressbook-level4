package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given addressbook data to the specified file.
     */
    public static <T> void saveDataToFile(Path file, T data)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, data);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static <T> T loadDataFromSaveFile(Path file, Class<T> classToConvert) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, classToConvert);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
