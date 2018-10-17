package seedu.inventory.storage;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.inventory.commons.core.LogsCenter;
import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;

/**
 * A class to access Sale List data stored as an xml file on the hard disk.
 */
public class XmlSaleListStorage implements SaleListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlSaleListStorage.class);

    private Path filePath = Paths.get("data" , "sale.xml");

    public Path getSaleListFilePath() {
        return filePath;
    }

    public Optional<ReadOnlySaleList> readSaleList(ReadOnlyInventory inventory) throws DataConversionException,
            IOException {
        return readSaleList(filePath, inventory);
    }

    /**
     * Similar to {@link #readSaleList(ReadOnlyInventory inventory)}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySaleList> readSaleList(Path filePath, ReadOnlyInventory inventory) throws
            DataConversionException, FileNotFoundException {
        requireAllNonNull(filePath, inventory);

        if (!Files.exists(filePath)) {
            logger.info("Sale List file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableSaleList xmlSaleList = XmlFileStorage.loadSaleListFromSaveFile(filePath);

        try {
            return Optional.of(xmlSaleList.toModelType(inventory));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    public void saveSaleList(ReadOnlySaleList saleList) throws IOException {
        saveSaleList(saleList, filePath);
    }

    /**
     * Similar to {@link #saveSaleList(ReadOnlySaleList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveSaleList(ReadOnlySaleList saleList, Path filePath) throws IOException {
        requireNonNull(saleList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);

        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableSaleList(saleList));
    }

}
