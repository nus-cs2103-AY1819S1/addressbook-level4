package seedu.clinicio.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.clinicio.commons.exceptions.DataConversionException;
import seedu.clinicio.commons.util.XmlUtil;

/**
 * Stores clinicio data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given clinicio data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableClinicIo clinicIo)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, clinicIo);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns ClinicIO in the file or an empty ClinicIO
     */
    public static XmlSerializableClinicIo loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableClinicIo.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
