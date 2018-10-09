package seedu.souschef.storage.healthplan;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.commons.util.FileUtil;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.storage.XmlFileStorage;
import seedu.souschef.storage.XmlGeneralStorage;
import seedu.souschef.storage.XmlSerializableGeneric;

/**
 * A class to access AppContent data stored as an xml file on the hard disk.
 */
public class XmlHealthPlanStorage extends XmlGeneralStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlHealthPlanStorage.class);

    private Path filePath;

    public XmlHealthPlanStorage(Path filePath) {

        super(filePath);
        this.filePath = super.getAppContentFilePath();
    }

    public Path getAppContentFilePath() {
        return filePath;
    }


    /**
     * Similar to {@link #readAppContent()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyAppContent> readAppContent(Path filePath) throws DataConversionException,
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
     * Similar to {@link #saveAppContent(ReadOnlyAppContent)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveAppContent(ReadOnlyAppContent appContent, Path filePath) throws IOException {
        requireNonNull(appContent);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableHealthPlan(appContent));
    }







}
