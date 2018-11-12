package tutorhelper.storage;

import static tutorhelper.commons.util.XmlUtil.getDataFromFile;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import tutorhelper.commons.exceptions.DataConversionException;
import tutorhelper.commons.util.XmlUtil;

/**
 * Stores TutorHelper data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given TutorHelper data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableTutorHelper tutorHelper)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, tutorHelper);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns TutorHelper in the file or an empty TutorHelper
     */
    public static XmlSerializableTutorHelper loadDataFromSaveFile(Path file)
            throws DataConversionException, FileNotFoundException {
        try {
            return getDataFromFile(file, XmlSerializableTutorHelper.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
