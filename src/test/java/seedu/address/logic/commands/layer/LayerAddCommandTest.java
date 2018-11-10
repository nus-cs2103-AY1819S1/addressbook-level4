// @@author j-lum
package seedu.address.logic.commands.layer;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModelGenerator.getModelWithPopulatedCanvasAndImgDirectory;
import static seedu.address.testutil.TypicalIndexes.INDEX_EIGHT_IMAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_ELEVEN_IMAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_IMAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_IMAGE;
import static seedu.address.testutil.UndoRedoCommandTestUtil.assertCommandFailure;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.GuiUnitTest;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code LayerAddCommand}.
 */

public class LayerAddCommandTest extends GuiUnitTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = getModelWithPopulatedCanvasAndImgDirectory();
    private Model expectedModel = getModelWithPopulatedCanvasAndImgDirectory();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidIndex_failure() {
        String invalidIndex = "invalid";
        assertExecutionFailure(invalidIndex, LayerAddCommand.OUTPUT_FAILURE);
    }


    @Test
    public void execute_validIndexImageList_success() {
        assertExecutionSuccess(INDEX_FIRST_IMAGE);
        assertExecutionSuccess(INDEX_SECOND_IMAGE);
    }

    @Test
    public void execute_indexExceedsTotalImageList_failure() {
        assertEquals(model.getDirectoryImageList().size(), 7);
        assertExecutionFailure(INDEX_EIGHT_IMAGE, LayerAddCommand.OUTPUT_FAILURE);
        String outOfBoundsIndex = Integer.toString(INDEX_EIGHT_IMAGE.getOneBased());
        assertExecutionFailure(outOfBoundsIndex, LayerAddCommand.OUTPUT_FAILURE);
    }

    @Test
    public void execute_indexExceedsBatchSize_failure() {
        model.updateCurrDirectory(model.getCurrDirectory().resolve("testimgs10"));
        assertEquals(model.getTotalImagesInDir(), 14);
        String outOfBoundsIndex = Integer.toString(INDEX_ELEVEN_IMAGE.getOneBased());
        assertExecutionFailure(outOfBoundsIndex, LayerAddCommand.OUTPUT_FAILURE);

        assertExecutionFailure(INDEX_ELEVEN_IMAGE, LayerAddCommand.OUTPUT_FAILURE);
    }

    /**
     * Executes a {@code LayerAddCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        LayerAddCommand openCommand = new LayerAddCommand((Integer.toString(index.getOneBased())));
        String expectedMessage = LayerAddCommand.OUTPUT_SUCCESS;

        assertCommandSuccess(openCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code LayerAddCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     * @param index - A string with a valid index
     */
    private void assertExecutionSuccess(String index) {
        LayerAddCommand openCommand = new LayerAddCommand(index);
        String expectedMessage = LayerAddCommand.OUTPUT_SUCCESS;

        assertCommandSuccess(openCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    /**
     * Executes a {@code OpenCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        LayerAddCommand openCommand = new LayerAddCommand((Integer.toString(index.getOneBased())));
        assertCommandFailure(openCommand, model, commandHistory, expectedMessage);
    }

    /**
     * Executes a {@code LayerAddCommand} with the given {@code index},
     * and checks that the correct error message is thrown.
     * @param index - A string with a valid index
     */
    private void assertExecutionFailure(String index, String expectedMessage) {
        LayerAddCommand openCommand = new LayerAddCommand(index);
        assertCommandFailure(openCommand, model, commandHistory, expectedMessage);
    }
}
