package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.UserData;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlUsersStorage implements UsersStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUsersStorage.class);

    private Path filePath;

    public XmlUsersStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUsersFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserData> readUserData() throws DataConversionException, IOException {
        return readUserData(filePath);
    }

    /**
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<UserData> readUserData(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Users file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableUsers xmlUsers = XmlFileStorage.loadUsersFromSaveFile(filePath);
        try {
            return Optional.of(xmlUsers.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserData(UserData userData) throws IOException {
        saveUserData(userData, filePath);
    }

    /**
     * @param filePath location of the data. Cannot be null
     */
    public void saveUserData(UserData userData, Path filePath) throws IOException {
        requireNonNull(userData);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveUsersToFile(filePath, new XmlSerializableUsers(userData));
    }
}
