package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.awareness.Awareness;

/**
 * A class used to access Awareness data stored as XML on the disk.
 * This class itself does not control the logic used in de-serializing XML into in-memory objects.
 */
public class XmlAwarenessStorage implements AwarenessStorage {

    private final Path filePath;

    public XmlAwarenessStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads awareness data from the specified XML file.
     *
     * @return an Optional wrapping the Awareness object created by reading the XML file.
     * @throws DataConversionException if there was a problem with parsing the XML data, or the XML contains invalid
     *                                 values.
     * @throws FileNotFoundException if the file was not found.
     */
    public Optional<Awareness> readAwarenessData() throws DataConversionException, FileNotFoundException {

        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        XmlSerializableAwareness xmlAwareness = XmlSerializableAwareness.loadDataFromSaveFile(filePath);

        try {
            return Optional.of(xmlAwareness.toModelType());
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }
}
