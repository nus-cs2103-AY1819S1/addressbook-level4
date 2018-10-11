package seedu.address.storage.deliveryman;

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
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.storage.XmlFileStorage;

/**
 * Represents the Storage methods for storage of XML deliverymen list.
 */
public class XmlDeliverymenListStorage implements DeliverymenListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlDeliverymenListStorage.class);

    private Path filePath;

    public XmlDeliverymenListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDeliverymenListFilePath() {
        return filePath;
    }

    @Override
    public Optional<DeliverymenList> readDeliverymenList() throws DataConversionException, IOException {
        return readDeliverymenList(filePath);
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
            logger.info("DeliverymenList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableDeliverymenList xmlDeliverymenList = XmlFileStorage.loadDeliverymenDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlDeliverymenList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDeliverymenList(DeliverymenList addressBook) throws IOException {
        saveDeliverymenList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveDeliverymenList(DeliverymenList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveDeliverymenList(DeliverymenList addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDeliverymenDataToFile(filePath, new XmlSerializableDeliverymenList(addressBook));
    }
}
