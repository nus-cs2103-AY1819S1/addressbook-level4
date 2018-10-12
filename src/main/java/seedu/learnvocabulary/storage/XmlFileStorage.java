package seedu.learnvocabulary.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.learnvocabulary.commons.exceptions.DataConversionException;
import seedu.learnvocabulary.commons.util.XmlUtil;

/**
 * Stores learnvocabulary data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given learnvocabulary data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableLearnVocabulary learnVocabulary)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, learnVocabulary);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns learnvocabulary book in the file or an empty learnvocabulary book
     */
    public static XmlSerializableLearnVocabulary loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableLearnVocabulary.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
