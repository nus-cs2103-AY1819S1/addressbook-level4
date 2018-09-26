package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.exceptions.NoUserSelectedException;
import seedu.address.model.expense.Person;

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
     * Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model) {
        try {
            return Index.fromOneBased(model.getFilteredPersonList().size() / 2);
        } catch (NoUserSelectedException e) {
            throw new IllegalArgumentException("Model has no user selected.");
        }
    }

    /**
     * Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        try {
            return Index.fromOneBased(model.getFilteredPersonList().size());
        } catch (NoUserSelectedException e) {
            throw new IllegalArgumentException("Model has no user selected.");
        }
    }

    /**
     * Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        try {
            return model.getFilteredPersonList().get(index.getZeroBased());
        } catch (NoUserSelectedException e) {
            throw new IllegalArgumentException("Model has no user selected.");
        }
    }
}
