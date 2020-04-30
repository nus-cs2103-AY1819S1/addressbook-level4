package seedu.restaurant.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.restaurant.commons.exceptions.DataConversionException;
import seedu.restaurant.model.ReadOnlyRestaurantBook;
import seedu.restaurant.model.RestaurantBook;

/**
 * Represents a storage for {@link RestaurantBook}.
 */
public interface RestaurantBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRestaurantBookFilePath();

    /**
     * Returns RestaurantBook data as a {@link ReadOnlyRestaurantBook}. Returns {@code Optional.empty()} if storage file
     * is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRestaurantBook> readRestaurantBook() throws DataConversionException, IOException;

    /**
     * @see #getRestaurantBookFilePath()
     */
    Optional<ReadOnlyRestaurantBook> readRestaurantBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRestaurantBook} to the storage.
     *
     * @param restaurantBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook) throws IOException;

    /**
     * @see #saveRestaurantBook(ReadOnlyRestaurantBook)
     */
    void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook, Path filePath) throws IOException;

    /**
     * Backup the given {@link ReadOnlyRestaurantBook} to a temporary local backup file.
     *
     * @param restaurantBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupRestaurantBook(ReadOnlyRestaurantBook restaurantBook) throws IOException;
}
