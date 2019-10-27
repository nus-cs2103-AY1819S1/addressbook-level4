package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.MarkdownProcessingException;
import seedu.address.storage.util.MarkdownConverter;

/**
 * Converts a Java object instance to Markdown and vice versa
 */
public class MdUtil {

    private static final Logger logger = LogsCenter.getLogger(MdUtil.class);

    static <T> void serializeObjectToMdFile(Path mdFile, T objectToSerialize) throws IOException {
        FileUtil.writeToFile(mdFile, toMdString(objectToSerialize));
    }

    /**
     * Saves the Md object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     * @param mdFile cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static <T> void saveMdFile(T mdFile, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(mdFile);

        serializeObjectToMdFile(filePath, mdFile);
    }

    /**
     * Converts a given instance of a class into its Markdown formatted string representation
     * @param instance The T object to be converted into the Markdown string
     * @param <T> The generic type to create an instance of
     * @return Markdown data representation of the given class instance, in string
     */
    public static <T> String toMdString(T instance) throws MarkdownProcessingException {
        return MarkdownConverter.toMarkdown(instance);
    }
}
