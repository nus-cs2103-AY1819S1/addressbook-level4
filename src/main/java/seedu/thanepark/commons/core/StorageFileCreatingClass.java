package seedu.thanepark.commons.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Abstract class that all storage file creating classes should implement (templates and information do not need this).
 * Creates files in test directory by with testing behavior by default.
 */
public abstract class StorageFileCreatingClass {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");
    private static boolean isTestMode = true;

    /**
     * Disables test mode of all file creating subclasses
     */
    public static void disableTestMode() {
        isTestMode = false;
    }

    /**
     * Returns the file Path depending on the test state
     */
    public static Path getFilePath(String fileName) throws IOException {
        if (!isTestMode) {
            return Paths.get(fileName);
        }
        Files.createDirectories(SANDBOX_FOLDER);
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the filePathString depending on the test state
     */
    public static String getFilePathString(String fileName) throws IOException {
        return getFilePath(fileName).toString();
    }

    /**
     * Returns whether the application is in testing state
     */
    protected boolean isInTestMode() {
        return isTestMode;
    }

    /**
     * Creates a file depending on the test state
     */
    protected static boolean createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (isTestMode || !file.exists()) {
            file.delete();
            file.createNewFile();
        }
        if (isTestMode) {
            file.deleteOnExit();
        }
        return file.canRead() && file.canWrite();
    }
}
