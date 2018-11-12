package seedu.modsuni.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.commons.util.XmlUtil;

/**
 * Stores data in an XML file
 */
public class XmlFileStorage {

    /**
     * Saves the given modulelist data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableModuleList moduleList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, moduleList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Saves the given credential store data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableCredentialStore credentialStore)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, credentialStore);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Saves the given user data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableUser user)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, user);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns module list in the file
     */
    public static XmlSerializableModuleList loadModuleListFromSaveFile(Path file) throws
            DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableModuleList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns CredentialStore in the file or an empty CredentialStore
     */
    public static XmlSerializableCredentialStore loadCredentialStoreDataFromSaveFile(Path file)
        throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCredentialStore.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns User in the file or an empty User
     */
    public static XmlSerializableUser loadUserDataFromSaveFile(Path file)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableUser.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }


}
