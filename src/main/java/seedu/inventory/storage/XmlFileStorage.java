package seedu.inventory.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.util.XmlUtil;

/**
 * Stores inventory data in an XML file
 */
public class XmlFileStorage {
    /**
     * Returns inventory book in the file or an empty inventory book
     */
    public static XmlSerializableInventory loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableInventory.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given inventory data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableInventory inventory)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, inventory);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Saves the given sale list data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableSaleList saleList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, saleList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns sale list in the file or an empty sale list
     */
    public static XmlSerializableSaleList loadSaleListFromSaveFile(Path file) throws
            DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableSaleList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * @author darren96
     */

    /**
     * Saves the given staff list data to the specified file.
     *
     * @param file the file path that data to be saved to
     * @param staffList the staff list to be saved
     *
     * @throws FileNotFoundException if file path provided is not found
     */
    public static void saveStaffsDataToFile(Path file, XmlSerializableStaffList staffList)
        throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, staffList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns staff list in the file or an empty staff list.
     *
     * @param file the file consists of the staffs data
     *
     * @return the staff list which is serializable
     *
     * @throws DataConversionException if errors occur during conversion of data
     * @throws FileNotFoundException if file path provided is not found
     */
    public static XmlSerializableStaffList loadStaffsDataFromSaveFile(Path file)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableStaffList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
