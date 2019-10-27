package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.model.template.Template;

/**
 * Represents a storage for {@link seedu.address.model.template.Template}.
 */
public class TxtTemplateStorage implements TemplateStorage {

    private static final Logger logger = LogsCenter.getLogger(TxtTemplateStorage.class);

    private Path filePath;

    public TxtTemplateStorage() {
    }

    public TxtTemplateStorage(Path filePath) {
        this.filePath = filePath;
    }

    //@Override
    public Path getTemplateFilePath() {
        return filePath;
    }

    //@Override
    public Template loadTemplate() throws IOException, InvalidTemplateFileException {
        return loadTemplate(filePath);
    }

    /**
     * Similar to {@link #loadTemplate()}
     *
     * @param filePath location of the data. Cannot be null.
     * @throws IOException if the file is not found.
     */
    public Template loadTemplate(Path filePath)
        throws IOException, InvalidTemplateFileException {

        requireNonNull(filePath);

        File file = filePath.toFile();
        Scanner s = new Scanner(file);
        Template template = new Template(filePath.toString());

        while (s.hasNextLine()) {
            String curr = s.nextLine();
            template.addSection(curr);
        }
        return template;
    }
}


