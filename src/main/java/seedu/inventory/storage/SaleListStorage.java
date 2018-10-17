package seedu.inventory.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.inventory.commons.exceptions.DataConversionException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.SaleList;

/**
 * Represents a storage for {@link SaleList}.
 */
public interface SaleListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSaleListFilePath();

    /**
     * Returns sale list data as a {@link ReadOnlySaleList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySaleList> readSaleList(ReadOnlyInventory inventory) throws DataConversionException, IOException;

    /**
     * @see #getSaleListFilePath()
     */
    Optional<ReadOnlySaleList> readSaleList(Path filePath, ReadOnlyInventory inventory)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySaleList} to the storage.
     * @param saleList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSaleList(ReadOnlySaleList saleList) throws IOException;

    /**
     * @see #saveSaleList(ReadOnlySaleList) (ReadOnlySaleList)
     */
    void saveSaleList(ReadOnlySaleList saleList, Path filePath) throws IOException;

}
