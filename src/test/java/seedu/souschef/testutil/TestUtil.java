package seedu.souschef.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.souschef.commons.core.index.Index;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;

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
     * Returns the middle index of the recipe in the {@code recipeModel}'s recipe list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredList().size() / 2);
    }

    /**
     * Returns the last index of the recipe in the {@code recipeModel}'s recipe list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredList().size());
    }

    /**
     * Returns the recipe in the {@code recipeModel}'s recipe list at {@code index}.
     */
    public static Recipe getRecipe(Model<Recipe> model, Index index) {
        return model.getFilteredList().get(index.getZeroBased());
    }
}
