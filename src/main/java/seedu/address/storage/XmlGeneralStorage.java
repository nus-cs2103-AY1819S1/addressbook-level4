package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAppContent;


/**
 * This class is for the general extension of all XmlStorage subtypes that may extend the
 * functionality to generalize the function calls
 */
public abstract class XmlGeneralStorage implements GenericStorage {

    private Path filePath;

    public XmlGeneralStorage(){

    }

    public XmlGeneralStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAppContent> read() throws DataConversionException, IOException {
        return read(filePath);
    }
    @Override
    public Optional<ReadOnlyAppContent> read(Path filePath) throws DataConversionException, FileNotFoundException {
        return Optional.empty();
    }

    @Override
    public void save(ReadOnlyAppContent appContent) throws IOException {
        save(appContent, filePath);
    }
    @Override
    public abstract void save(ReadOnlyAppContent appContent, Path filePath) throws IOException;

}
