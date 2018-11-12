package seedu.superta.storage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.superta.commons.exceptions.DataConversionException;
import seedu.superta.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given client data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableSuperTaClient addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns the client data in the stream or empty client data.
     */
    public static XmlSerializableSuperTaClient loadDataFromStream(InputStream stream) throws DataConversionException {
        try {
            return XmlUtil.getDataFromStream(stream, XmlSerializableSuperTaClient.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns client data in the file or an empty client data.
     */
    public static XmlSerializableSuperTaClient loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableSuperTaClient.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
