package seedu.learnvocabulary.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.word.Word;

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
     * Returns the middle index of the word in the {@code model}'s word list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredWordList().size() / 2);
    }

    /**
     * Returns the last index of the word in the {@code model}'s word list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredWordList().size());
    }

    /**
     * Returns the word in the {@code model}'s word list at {@code index}.
     */
    public static Word getWord(Model model, Index index) {
        return model.getFilteredWordList().get(index.getZeroBased());
    }
}
