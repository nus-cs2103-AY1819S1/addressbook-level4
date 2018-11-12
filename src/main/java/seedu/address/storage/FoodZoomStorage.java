package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.deliveryman.DeliverymenList;

/**
 * Represents storage for FoodZoom
 */
public interface FoodZoomStorage {
    Path getFoodZoomFilePath();

    Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException;

    Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException, IOException;

    Optional<DeliverymenList> readDeliverymenList() throws DataConversionException, IOException;

    Optional<DeliverymenList> readDeliverymenList(Path filePath) throws DataConversionException, IOException;

    void saveFoodZoom(ReadOnlyOrderBook orderBook, DeliverymenList deliverymenList) throws IOException;

    void saveFoodZoom(ReadOnlyOrderBook orderBook, DeliverymenList deliverymenList, Path filePath) throws IOException;
}
