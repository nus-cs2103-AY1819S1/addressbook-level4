package seedu.thanepark.commons.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Class that handles FilePath to Url conversion.
 */
public class FilePathToUrl {
    private static final String MESSAGE_FILE_ERROR = "%1$s cannot be accessed!";

    private final String filePath;
    private final boolean isGeneratedAtRuntime;

    /**
     * Constructs a FilePathToUrl using a filePath String.
     */
    public FilePathToUrl (String filePath, boolean isGeneratedAtRuntime) {
        this.filePath = filePath;
        this.isGeneratedAtRuntime = isGeneratedAtRuntime;
    }

    /**
     * Returns filePath String.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Returns the String representation of the file's Url.
     */
    public String filePathToUrlString() throws IOException {
        return filePathToUrl().toString();
    }

    /**
     * Returns the URL representation of the file's Url.
     */
    public URL filePathToUrl() throws IOException {
        if (!isGeneratedAtRuntime) {
            return getClass().getResource(filePath);
        }
        String formattedFilePath = filePath;
        File file = new File(formattedFilePath);
        if (!file.exists() || !file.canRead()) {
            throw new IOException(String.format(MESSAGE_FILE_ERROR, formattedFilePath));
        }
        return file.toURI().toURL();
    }
}
