package seedu.restaurant.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.restaurant.commons.events.model.RestaurantBookChangedEvent;
import seedu.restaurant.commons.events.storage.DataSavingExceptionEvent;
import seedu.restaurant.commons.exceptions.DataConversionException;
import seedu.restaurant.model.ReadOnlyRestaurantBook;
import seedu.restaurant.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RestaurantBookStorage, UserPrefsStorage {

    // ================ UserPrefs methods ==============================

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    // ================ RestaurantBook methods ==============================

    @Override
    Path getRestaurantBookFilePath();

    @Override
    Optional<ReadOnlyRestaurantBook> readRestaurantBook() throws DataConversionException, IOException;

    @Override
    void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook) throws IOException;

    /**
     * Saves the current version of the Restaurant Book to the hard disk. Creates the data file if it is missing. Raises
     * {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRestaurantBookChangedEvent(RestaurantBookChangedEvent rbce);
}
