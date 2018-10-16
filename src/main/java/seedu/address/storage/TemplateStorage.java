package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

import seedu.address.model.template.Template;

/**
 * Represents a storage for {@link seedu.address.model.template.Template}.
 */
public class TemplateStorage {

    private String filePath;

    public TemplateStorage() {
    }

    public TemplateStorage(String filePath) {
        this.filePath = filePath;
    }

    //@Override
    public String getTemplateFilePath() {
        return filePath;
    }

    //@Override
    public Optional<Template> readTemplateFromFile() throws FileNotFoundException {
        return readTemplateFromFile(filePath);
    }

    /**
     * Similar to {@link #readTemplateFromFile()}
     *
     * @param templateFilePath location of the data. Cannot be null.
     * @throws FileNotFoundException if the file is not found.
     */
    public Optional<Template> readTemplateFromFile(String templateFilePath)
        throws FileNotFoundException {
        File file = new File(templateFilePath);
        Scanner s = new Scanner(file);
        Template t = new Template();

        while (s.hasNextLine()) {
            String curr = s.nextLine();
            t.addSection(curr);
        }
        return Optional.of(t);
    }
}


