package seedu.parking.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.parking.commons.exceptions.DataConversionException;
import seedu.parking.commons.util.XmlUtil;

/**
 * Stores carparkfinder data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given carparkfinder data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableCarparkFinder carparkFinder)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, carparkFinder);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns car park finder in the file or an empty car park finder
     */
    public static XmlSerializableCarparkFinder loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCarparkFinder.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
