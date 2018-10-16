package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Awareness;

/**
 * A class used to access Awareness data stored as XML on the disk.
 * This class itself does not control the logic used in de-serializing XML into in-memory objects.
 */
public class XmlAwarenessStorage implements AwarenessStorage {

    private final Path filePath;

    public XmlAwarenessStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Awareness readAwarenessData() throws DataConversionException, FileNotFoundException {
        return XmlSerializableAwareness.loadDataFromSaveFile(filePath).toModelType();
    }
}
