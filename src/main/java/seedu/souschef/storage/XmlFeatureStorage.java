package seedu.souschef.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.model.ReadOnlyAppContent;


/**
 * This class is for the general extension of all XmlStorage subtypes that may extend the
 * functionality to generalize the function calls
 */
public abstract class XmlFeatureStorage implements FeatureStorage {

    protected Path filePath;

    protected XmlFeatureStorage(){

    }

    protected XmlFeatureStorage (Path filePath) {
        this();
        this.filePath = filePath;

    }


    public Path getFeatureFilePath() {
        return filePath;
    }


    @Override
    public abstract Optional<ReadOnlyAppContent> readFeature(Path filePath)
            throws DataConversionException,
            FileNotFoundException;

    @Override
    public void saveFeature(ReadOnlyAppContent appContent) throws IOException {
        saveFeature(appContent, filePath);
    }
    @Override
    public abstract void saveFeature(ReadOnlyAppContent appContent, Path filePath) throws IOException;

}
