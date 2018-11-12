package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.user.XmlSerializableUsersList;

/**
 * Stores orderbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the data for FoodZoom to the specified file.
     */
    public static void saveFoodZoomDataToFile(Path file, XmlFoodZoom foodZoomData) throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, foodZoomData);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns the FoodZoom data from file
     */
    public static XmlFoodZoom loadFoodZoomDataFromSaveFile(Path file) throws FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlFoodZoom.class);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }
    /**
     * Saves the given users list data to the specified file.
     */
    public static void saveUsersDataToFile(Path file, XmlSerializableUsersList usersList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, usersList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns users list in the file or an empty users list.
     */
    public static XmlSerializableUsersList loadUsersDataFromSaveFile(Path file) throws DataConversionException,
                                                                                       FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableUsersList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
