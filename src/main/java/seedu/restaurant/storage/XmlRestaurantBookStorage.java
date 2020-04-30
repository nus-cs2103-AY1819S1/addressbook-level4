package seedu.restaurant.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.exceptions.DataConversionException;
import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.commons.util.FileUtil;
import seedu.restaurant.model.ReadOnlyRestaurantBook;

/**
 * A class to access RestaurantBook data stored as an xml file on the hard disk.
 */
public class XmlRestaurantBookStorage implements RestaurantBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRestaurantBookStorage.class);

    private Path filePath;
    private Path backupFilePath;

    public XmlRestaurantBookStorage(Path filePath) {
        this.filePath = filePath;
        this.backupFilePath = Paths.get(filePath.toString() + ".backup");
    }

    public Path getRestaurantBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRestaurantBook> readRestaurantBook() throws DataConversionException, IOException {
        return readRestaurantBook(filePath);
    }

    /**
     * Similar to {@link #readRestaurantBook()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRestaurantBook> readRestaurantBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("RestaurantBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableRestaurantBook xmlRestaurantBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlRestaurantBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook) throws IOException {
        saveRestaurantBook(restaurantBook, filePath);
    }

    /**
     * Similar to {@link #saveRestaurantBook(ReadOnlyRestaurantBook)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook, Path filePath) throws IOException {
        requireNonNull(restaurantBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableRestaurantBook(restaurantBook));
    }

    /**
     * Similar to {@link #saveRestaurantBook(ReadOnlyRestaurantBook, Path)}
     */
    @Override
    public void backupRestaurantBook(ReadOnlyRestaurantBook restaurantBook) throws IOException {
        saveRestaurantBook(restaurantBook, backupFilePath);
    }
}
