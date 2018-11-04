package seedu.address.testutil;

import static seedu.address.model.google.PhotosLibraryClientFactory.dataStore;
import static seedu.address.model.google.PhotosLibraryClientFactory.testFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Blocks google login process
     * @throws IOException
     */
    public static void blockGoogleLogin() {
        if (!dataStore.exists()) {
            dataStore.mkdirs();
        }
        try {
            testFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Unblocks google login process
     * @throws IOException
     */
    public static void unblockGoogleLogin() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
