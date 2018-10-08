package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores scheduleplanner data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given scheduleplanner data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableSchedulePlanner schedulePlanner)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, schedulePlanner);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns schedule planner in the file or an empty schedule planner
     */
    public static XmlSerializableSchedulePlanner loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableSchedulePlanner.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
