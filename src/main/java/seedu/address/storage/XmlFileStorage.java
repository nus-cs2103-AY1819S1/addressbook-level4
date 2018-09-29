package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores scheduler and address book data in an XML file
 */
public class XmlFileStorage {

    /**
     * Saves the given address book data to the specified file.
     */
    public static void saveAddressBookDataToFile(Path file, XmlSerializableAddressBook addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableAddressBook loadAddressBookDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given scheduler data to the specified file.
     */
    public static void saveSchedulerDataToFile(Path file, XmlSerializableScheduler scheduler)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, scheduler);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns scheduler in the file or an empty scheduler
     */
    public static XmlSerializableScheduler loadSchedulerDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableScheduler.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
