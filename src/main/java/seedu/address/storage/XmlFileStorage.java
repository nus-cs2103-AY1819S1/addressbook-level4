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
    public static void saveDataToFile(Path file, XmlSerializableAddressBook addressBook)
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
    public static XmlSerializableAddressBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves User data to the specified file.
     */
    public static void saveUsersToFile(Path file, XmlSerializableUsers users)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, users);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns users in the file or an empty address book
     */
    public static XmlSerializableUsers loadUsersFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            XmlSerializableUsers ret = XmlUtil.getDataFromFile(file, XmlSerializableUsers.class);
            return ret;
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
