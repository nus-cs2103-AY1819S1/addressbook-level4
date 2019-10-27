package seedu.address.storage.entry;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyEntryBook;
import seedu.address.storage.XmlFileStorage;

/**
 * A class to access entrybook data stored as an xml file on the hard disk.
 */
public class XmlEntryBookStorage implements EntryBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEntryBookStorage.class);

    private Path filePath;

    public XmlEntryBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEntryBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEntryBook> readEntryBook() throws DataConversionException, IOException {
        return readEntryBook(filePath);
    }

    /**
     * Similar to {@link #readEntryBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEntryBook> readEntryBook(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Entrybook file " + filePath + " not found");
            return Optional.empty();
        }

        //try {
        //    XmlSerializableEntryBook xmlEntryBook = XmlUtil.getDataFromFile(filePath, XmlSerializableEntryBook.class);
        //
        //    return Optional.of(xmlEntryBook.toModelType());
        //} catch (JAXBException jaxbe) {
        //    logger.info("There is a problem in the XML formatting in " + filePath + ": " + jaxbe.getMessage());
        //    throw new DataConversionException(jaxbe);
        //} catch (IllegalValueException ive) {
        //    logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
        //    throw new DataConversionException(ive);
        //}

        XmlSerializableEntryBook xmlEntryBook = XmlFileStorage.loadEntryBookDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlEntryBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }


    @Override
    public void saveEntryBook(ReadOnlyEntryBook entryBook) throws IOException {
        saveEntryBook(entryBook, filePath);
    }

    /**
     * Similar to {@link #saveEntryBook(ReadOnlyEntryBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveEntryBook(ReadOnlyEntryBook entryBook, Path filePath) throws IOException {
        requireNonNull(entryBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableEntryBook(entryBook));
    }

}
