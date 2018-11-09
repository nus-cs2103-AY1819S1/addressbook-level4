// @@author benedictcss
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModelGenerator.getModelWithTestImgDirectory;
import static seedu.address.testutil.TypicalIndexes.INDEX_EIGHT_IMAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_ELEVEN_IMAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_IMAGE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_IMAGE;

import java.nio.file.Path;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code OpenCommand}.
 */
public class OpenCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = getModelWithTestImgDirectory();
    private Model expectedModel = getModelWithTestImgDirectory();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexImageList_success() {

        assertExecutionSuccess(INDEX_FIRST_IMAGE);
        assertExecutionSuccess(INDEX_SECOND_IMAGE);
    }

    @Test
    public void execute_indexExceedsTotalImageList_failure() {
        assertEquals(model.getDirectoryImageList().size(), 7);
        assertExecutionFailure(INDEX_EIGHT_IMAGE, Messages.MESSAGE_INDEX_END_OF_IMAGE_LIST);
    }

    @Test
    public void execute_indexExceedsBatchSize_failure() {
        model.updateCurrDirectory(model.getCurrDirectory().resolve("testimgs10"));
        assertEquals(model.getTotalImagesInDir(), 14);

        assertExecutionFailure(INDEX_ELEVEN_IMAGE, Messages.MESSAGE_INDEX_EXCEED_MAX_BATCH_SIZE);
    }

    @Test
    public void equals() {
        OpenCommand openFirstCommand = new OpenCommand(INDEX_FIRST_IMAGE);
        OpenCommand openSecondCommand = new OpenCommand(INDEX_SECOND_IMAGE);

        // same object -> returns true
        assertTrue(openFirstCommand.equals(openFirstCommand));

        // same values -> returns true
        OpenCommand selectFirstCommandCopy = new OpenCommand(INDEX_FIRST_IMAGE);
        assertTrue(openFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(openFirstCommand.equals(1));

        // null -> returns false
        assertFalse(openFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(openFirstCommand.equals(openSecondCommand));
    }

    /**
     * Executes a {@code OpenCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        Path expectedImagePath = expectedModel.getDirectoryImageList().get(index.getZeroBased());
        OpenCommand openCommand = new OpenCommand(index);
        String expectedMessage = String.format(OpenCommand.MESSAGE_OPEN_IMAGE_SUCCESS, index.getOneBased())
                + " of " + Math.min(OpenCommand.BATCH_SIZE, model.getDirectoryImageList().size()) + "\n"
                + "Image opened: " + expectedImagePath.getFileName().toString();

        assertCommandSuccess(openCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code OpenCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        OpenCommand openCommand = new OpenCommand(index);
        assertCommandFailure(openCommand, model, commandHistory, expectedMessage);
    }
}
