package seedu.souschef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.souschef.commons.events.model.AppContentChangedEvent;
import seedu.souschef.commons.events.storage.DataSavingExceptionEvent;
import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FeatureStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getFeatureFilePath();

    @Override
    Optional<ReadOnlyAppContent> readFeature() throws DataConversionException, IOException;
    
    @Override
    void saveFeature(ReadOnlyAppContent appContent) throws IOException;

    Optional<ReadOnlyAppContent> readAll() throws DataConversionException, IOException;

    void setMainFeatureStorage(FeatureStorage featureStorage);

    Storage include(FeatureStorage feature);

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAppContentChangedEvent(AppContentChangedEvent abce);
}
