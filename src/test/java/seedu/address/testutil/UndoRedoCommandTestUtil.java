package seedu.address.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PreviewImage;

//@@author ihwk1996

/**
 * Contains helper methods for testing undo and redo commands.
 */
public class UndoRedoCommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     * - model's previewImage's new index and size equal to the expected index and size.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, int expectedIndex, int expectedSize) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertTrue(previewImageHasCorrectState(actualModel.getCurrentPreviewImage(), expectedIndex, expectedSize));
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the previewImage in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     * - model's previewImage's index and size does not change.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        int originalIndex = actualModel.getCurrentPreviewImage().getCurrentIndex();
        int originalSize = actualModel.getCurrentPreviewImage().getCurrentSize();

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertTrue(previewImageHasCorrectState(actualModel.getCurrentPreviewImage(), originalIndex, originalSize));
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Check if a previewImage is at the expected index and size
     */
    private static boolean previewImageHasCorrectState(PreviewImage previewImage, int expectedIndex, int expectedSize) {
        int actualIndex = previewImage.getCurrentIndex();
        int actualSize = previewImage.getCurrentSize();
        if (actualIndex != expectedIndex) {
            return false;
        }
        if (actualSize != expectedSize) {
            return false;
        }
        return true;
    }

    /**
     * Clears cache in storage folder.
     */
    public static void clearCache() {
        File cache = new File("cache");
        File[] list = cache.listFiles();
        if (list != null) {
            for (File file: list) {
                file.delete();
            }
        }
    }
}
