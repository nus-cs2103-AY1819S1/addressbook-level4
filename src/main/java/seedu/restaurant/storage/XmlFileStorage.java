package seedu.restaurant.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.restaurant.commons.exceptions.DataConversionException;
import seedu.restaurant.commons.util.XmlUtil;

/**
 * Stores restaurantbook data in an XML file
 */
public class XmlFileStorage {

    /**
     * Saves the given restaurantbook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableRestaurantBook restaurantBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, restaurantBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns restaurant book in the file or an empty restaurant book
     */
    public static XmlSerializableRestaurantBook loadDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableRestaurantBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
