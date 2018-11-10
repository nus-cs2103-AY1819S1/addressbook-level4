// @@author benedictcss
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModelGenerator.getModelWithTestImgDirectory;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code PrevCommand}.
 */
public class PrevCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = getModelWithTestImgDirectory();
    private Model expectedModel = getModelWithTestImgDirectory();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executePrevBatchFailure() {
        assertEquals(model.getTotalImagesInDir(), 7);

        // Directory image list does not change if prev fails
        assertExecutionFailure(Messages.MESSAGE_NO_MORE_PREV_IMAGES);
        assertEquals(model.getDirectoryImageList().size(), 7);
        assertEquals(model.numOfRemainingImagesInDir(), 7);
    }

    @Test
    public void executePrevBatchSuccess() {
        model.updateCurrDirectory(model.getCurrDirectory().resolve("testimgs10"));
        assertEquals(model.getTotalImagesInDir(), 14);

        // Directory image list does not change if prev fails
        assertExecutionFailure(Messages.MESSAGE_NO_MORE_PREV_IMAGES);
        assertEquals(model.getDirectoryImageList().size(), 10);
        assertEquals(model.numOfRemainingImagesInDir(), 14);

        model.updateImageListNextBatch();
        assertEquals(model.numOfRemainingImagesInDir(), 4);

        assertExecutionSuccess();
        assertEquals(model.getDirectoryImageList().size(), 10);
        assertEquals(model.numOfRemainingImagesInDir(), 14);
    }

    /**
     * Executes a {@code PrevCommand} and checks that {@code CommandResult}
     * is raised with the {@code expectedMessage}.
     */
    private void assertExecutionSuccess() {
        PrevCommand prevCommand = new PrevCommand();

        String expectedMessage = String.format(Messages.MESSAGE_TOTAL_IMAGES_IN_DIR, 14)
                + (String.format(Messages.MESSAGE_CURRENT_BATCH_IN_IMAGE_LIST, 1, 10)
                + (String.format(Messages.MESSAGE_CURRENT_IMAGES_IN_BATCH, 10)));

        assertCommandSuccess(prevCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code PrevCommand} and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String expectedMessage) {
        PrevCommand prevCommand = new PrevCommand();
        assertCommandFailure(prevCommand, model, commandHistory, expectedMessage);
    }
}
