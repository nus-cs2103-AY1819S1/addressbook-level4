package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.model.template.Template;

/**
 * Represents a storage for {@link seedu.address.model.template.Template}.
 */
public interface TemplateStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTemplateFilePath();

    /**
     * Loads template from file.
     *
     * @throws IOException if the file is not found.
     * @throws InvalidTemplateFileException if the file is of invalid format.
     */
    Template loadTemplate() throws IOException, InvalidTemplateFileException;

    /**
     * Similar to {@link #loadTemplate()}
     *
     * @param filePath location of the data. Cannot be null.
     * @throws IOException if the file is not found.
     * @throws InvalidTemplateFileException if the file is of invalid format.
     */
    Template loadTemplate(Path filePath)
        throws IOException, InvalidTemplateFileException;
}
