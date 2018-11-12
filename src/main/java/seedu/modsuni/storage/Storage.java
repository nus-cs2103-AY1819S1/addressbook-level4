package seedu.modsuni.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.modsuni.commons.events.model.CredentialStoreChangedEvent;
import seedu.modsuni.commons.events.model.ModuleListChangedEvent;
import seedu.modsuni.commons.events.model.SaveUserChangedEvent;
import seedu.modsuni.commons.events.storage.DataSavingExceptionEvent;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ModuleListStorage,
    UserPrefsStorage, CredentialStoreStorage, UserStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    /**
     * Saves the current version of the Module List to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleModuleListChangedEvent(ModuleListChangedEvent abce);

    /**
     * Saves the current version of the Credential Store to the hard disks.
     * Creates the data file it it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleCredentialStoreChangedEvent(CredentialStoreChangedEvent csce);


    /**
     * Export the current user to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleSaveUserChangedEvent(SaveUserChangedEvent suce);
}
