package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.entry.XmlSerializableEntryBook;

/**
 * Stores entryBook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given entrybook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableEntryBook entryBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, entryBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableEntryBook loadEntryBookDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableEntryBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
