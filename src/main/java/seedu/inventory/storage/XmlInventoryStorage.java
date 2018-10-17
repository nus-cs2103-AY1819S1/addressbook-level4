package seedu.inventory.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.model.ReadOnlyInventory;

/**
 * A class to access Inventory data stored as an xml file on the hard disk.
 */
public class XmlInventoryStorage implements InventoryStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlInventoryStorage.class);

    private Path filePath;

    public XmlInventoryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInventoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInventory> readInventory() throws DataConversionException, IOException {
        return readInventory(filePath);
    }

    /**
     * Similar to {@link #readInventory()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyInventory> readInventory(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Inventory file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableInventory xmlInventory = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlInventory.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInventory(ReadOnlyInventory inventory) throws IOException {
        saveInventory(inventory, filePath);
    }

    /**
     * Similar to {@link #saveInventory(ReadOnlyInventory)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveInventory(ReadOnlyInventory inventory, Path filePath) throws IOException {
        requireNonNull(inventory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableInventory(inventory));
    }

}
