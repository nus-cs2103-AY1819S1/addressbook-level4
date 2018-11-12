package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.deliveryman.DeliverymenList;

/**
 * A class to access FoodZoom data stored as an XML file.
 */
public class XmlFoodZoomStorage implements FoodZoomStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlFoodZoomStorage.class);

    private Path foodZoomFilePath;
    private Optional<DeliverymenList> deliverymenList;
    private Optional<OrderBook> orderBook;

    public XmlFoodZoomStorage(Path foodZoomFilePath) {
        this.foodZoomFilePath = foodZoomFilePath;
    }


    public Path getFoodZoomFilePath() {
        return foodZoomFilePath;
    }

    public void saveFoodZoom(ReadOnlyOrderBook orderBook, DeliverymenList deliverymenList) throws IOException {
        saveFoodZoom(orderBook, deliverymenList, foodZoomFilePath);
    }

    /**
     * Saves data to the XML file in the hard disk.
     */
    public void saveFoodZoom(ReadOnlyOrderBook orderBook, DeliverymenList deliverymenList, Path filePath)
            throws IOException {
        requireNonNull(deliverymenList);
        requireNonNull(orderBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveFoodZoomDataToFile(filePath, new XmlFoodZoom(orderBook, deliverymenList));
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException {
        return readOrderBook(foodZoomFilePath);
    }

    /**
     * Similar to {@link #readOrderBook()} ()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException,
        FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("FoodZoom file " + filePath + " not found");
            return Optional.empty();
        }

        XmlFoodZoom xmlFoodZoom = XmlFileStorage.loadFoodZoomDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlFoodZoom.getOrderBook());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public Optional<DeliverymenList> readDeliverymenList() throws DataConversionException, IOException {
        return readDeliverymenList(foodZoomFilePath);
    }

    /**
     * Similar to {@link #readDeliverymenList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<DeliverymenList> readDeliverymenList(Path filePath) throws DataConversionException,
        FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("FoodZoom file " + filePath + " not found");
            return Optional.empty();
        }

        XmlFoodZoom xmlFoodZoom = XmlFileStorage.loadFoodZoomDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlFoodZoom.getDeliverymenList());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
}
