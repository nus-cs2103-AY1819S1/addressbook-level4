package seedu.address.storage.route;

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
import seedu.address.model.route.ReadOnlyRouteList;
import seedu.address.storage.XmlFileStorage;

/**
 * A class to access RouteList data stored as an xml file on the hard disk.
 */
public class XmlRouteListStorage implements RouteListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRouteListStorage.class);

    private Path filePath;

    public XmlRouteListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRouteListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRouteList> readRouteList() throws DataConversionException, IOException {
        return readRouteList(filePath);
    }

    /**
     * Similar to {@link #readRouteList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRouteList> readRouteList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("RouteList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableRouteList xmlRouteList = XmlFileStorage.loadRouteDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlRouteList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRouteList(ReadOnlyRouteList addressBook) throws IOException {
        saveRouteList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveRouteList(ReadOnlyRouteList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRouteList(ReadOnlyRouteList routeList, Path filePath) throws IOException {
        requireNonNull(routeList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveRouteDataToFile(filePath, new XmlSerializableRouteList(routeList));
    }

}
