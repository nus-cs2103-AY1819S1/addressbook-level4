package seedu.address.storage.healthplan;

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
import seedu.address.model.ReadOnlyAppContent;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlGeneralStorage;
import seedu.address.storage.XmlSerializableGeneric;

/**
 * A class to access AppContent data stored as an xml file on the hard disk.
 */
public class XmlHealthPlanStorage extends XmlGeneralStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlHealthPlanStorage.class);

    private Path filePath;

    public XmlHealthPlanStorage(Path filePath) {

        super(filePath);
        this.filePath = super.getFilePath();
    }

    public Path getFilePath() {
        return filePath;
    }


    /**
     * Similar to {@link #read()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyAppContent> read(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("AppContent file " + filePath + " not found");
            return Optional.empty();
        }
        XmlSerializableGeneric xmlhealthplanBook = new XmlSerializableHealthPlan();
        xmlhealthplanBook = XmlFileStorage.loadDataFromSaveFile(filePath, "recipe");
        try {
            return Optional.of(xmlhealthplanBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }


    /**
     * Similar to {@link #save(ReadOnlyAppContent)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void save(ReadOnlyAppContent appContent, Path filePath) throws IOException {
        requireNonNull(appContent);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableHealthPlan(appContent));
    }







}
