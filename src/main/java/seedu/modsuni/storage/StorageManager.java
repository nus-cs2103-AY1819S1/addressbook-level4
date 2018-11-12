package seedu.modsuni.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.crypto.NoSuchPaddingException;

import com.google.common.eventbus.Subscribe;

import seedu.modsuni.commons.core.ComponentManager;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.model.CredentialStoreChangedEvent;
import seedu.modsuni.commons.events.model.ModuleListChangedEvent;
import seedu.modsuni.commons.events.model.SaveUserChangedEvent;
import seedu.modsuni.commons.events.storage.DataSavingExceptionEvent;
import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;
import seedu.modsuni.commons.util.DataSecurityUtil;
import seedu.modsuni.model.ReadOnlyModuleList;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.ReadOnlyCredentialStore;
import seedu.modsuni.model.user.User;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static final DataSecurityUtil DataSecurityUtil = new DataSecurityUtil();
    private UserPrefsStorage userPrefsStorage;
    private ModuleListStorage moduleListStorage;
    private CredentialStoreStorage credentialStoreStorage;
    private UserStorage userStorage;

    public StorageManager(ModuleListStorage moduleListStorage,
                          UserPrefsStorage userPrefsStorage,
                          CredentialStoreStorage credentialStoreStorage,
                          UserStorage userStorage) {
        super();
        this.moduleListStorage = moduleListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.credentialStoreStorage = credentialStoreStorage;
        this.userStorage = userStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ Module methods ==============================

    @Override
    public Path getModuleFilePath() {
        return moduleListStorage.getModuleFilePath();
    }

    @Override
    public Optional<ReadOnlyModuleList> readModuleList() throws DataConversionException, IOException {
        return readModuleList(moduleListStorage.getModuleFilePath());
    }

    @Override
    public Optional<ReadOnlyModuleList> readModuleList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return moduleListStorage.readModuleList(filePath);
    }

    @Override
    public void saveModuleList(ReadOnlyModuleList moduleList) throws IOException {
        saveModuleList(moduleList, moduleListStorage.getModuleFilePath());
    }

    @Override
    public void saveModuleList(ReadOnlyModuleList moduleList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        moduleListStorage.saveModuleList(moduleList, filePath);
    }

    @Override
    @Subscribe
    public void handleModuleListChangedEvent(ModuleListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "ModuleList data changed, saving to file"));
        try {
            saveModuleList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ CredentialStore methods =========================

    @Override
    public Path getCredentialStoreFilePath() {
        return credentialStoreStorage.getCredentialStoreFilePath();
    }

    @Override
    public Optional<ReadOnlyCredentialStore> readCredentialStore() throws DataConversionException, IOException {
        return credentialStoreStorage.readCredentialStore();
    }

    @Override
    public Optional<ReadOnlyCredentialStore> readCredentialStore(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return credentialStoreStorage.readCredentialStore();
    }

    @Override
    public void saveCredentialStore(ReadOnlyCredentialStore credentialStore) throws IOException {
        credentialStoreStorage.saveCredentialStore(credentialStore,
            credentialStoreStorage.getCredentialStoreFilePath());
    }

    @Override
    public void saveCredentialStore(ReadOnlyCredentialStore credentialStore,
                                    Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        credentialStoreStorage.saveCredentialStore(credentialStore, filePath);
    }

    @Override
    @Subscribe
    public void handleCredentialStoreChangedEvent(CredentialStoreChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Credential "
            + "Store changed. Saving to file"));
        try {
            saveCredentialStore(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ Save/Read User methods ==============================

    @Override
    public Path getUserSavedFilePath() {
        return userStorage.getUserSavedFilePath();
    }

    @Override
    public Optional<User> readUser(Path filePath, String password)
            throws DataConversionException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidPasswordException, CorruptedFileException,
            NoSuchPaddingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userStorage.readUser(filePath, password);
    }

    @Override
    public void saveUser(User user, Path filePath, String password) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userStorage.saveUser(user, filePath, password);
    }

    @Override
    @Subscribe
    public void handleSaveUserChangedEvent(SaveUserChangedEvent cuce) {
        logger.fine("Attempting to write " + cuce.user.getName() + " to data file: " + cuce.filePath);
        try {
            userStorage.saveUser(cuce.user, cuce.filePath, cuce.password.getValue());
        } catch (IOException e) {
            logger.warning("Unable to save");
            e.printStackTrace();
        }
    }

}
