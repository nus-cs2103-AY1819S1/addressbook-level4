package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.recipe.XmlSerializableAddressBook;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {

    /**
     * Constants to access the specific portions of the xml files
     */
    public static final String TYPE_RECIPE = "recipe";



    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableGeneric xmlContent)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, xmlContent);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableGeneric loadDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableGeneric.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }


    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableGeneric loadDataFromSaveFile(Path file, String type) throws DataConversionException,
            FileNotFoundException {
        try {
            if (TYPE_RECIPE.equals(type)) {
                return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
            }

        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
        return null;
    }
}
