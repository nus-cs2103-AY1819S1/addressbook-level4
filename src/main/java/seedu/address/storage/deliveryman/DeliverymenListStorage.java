package seedu.address.storage.deliveryman;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.deliveryman.DeliverymenList;

/**
 * Represents the Storage interface for DeliverymenList
 */
public interface DeliverymenListStorage {
    Path getDeliverymenListFilePath();

    /**
     * Returns a list of deliveryman as a {@link DeliverymenList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<DeliverymenList> readDeliverymenList() throws DataConversionException, IOException;

    /**
     * @see #getDeliverymanFilePath()
     */
    Optional<DeliverymenList> readDeliverymenList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link DeliverymenList} to the storage.
     * @param deliverymanList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDeliverymenList(DeliverymenList deliverymanList) throws IOException;

    /**
     * @see #saveDeliverymenList(DeliverymenList)
     */
    void saveDeliverymenList(DeliverymenList deliverymanList, Path filePath) throws IOException;
}
