// @@author benedictcss
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModelGenerator.getModelWithTestImgDirectory;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.testutil.EventsCollectorRule;

import java.util.logging.Logger;


/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class NextCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = getModelWithTestImgDirectory();
    private Model expectedModel = getModelWithTestImgDirectory();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nextBatch_failure() {
        assertEquals(model.getDirectoryImageList().size(), 7);

        // Directory image list does not change if next fails
        assertExecutionFailure(Messages.MESSAGE_NO_MORE_IMAGES);
        assertEquals(model.getDirectoryImageList().size(), 7);
    }

    @Test
    public void execute_nextBatch_success() {
        model.updateCurrDirectory(model.getCurrDirectory().resolve("testimgs10"));
        assertEquals(model.getDirectoryImageList().size(), 14);

        assertExecutionSuccess();
        assertEquals(model.getDirectoryImageList().size(), 4);

        // Directory image list does not change if next fails
        assertExecutionFailure(Messages.MESSAGE_NO_MORE_IMAGES);
        assertEquals(model.getDirectoryImageList().size(), 4);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code UpdateFilmReelEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess() {
        NextCommand nextCommand = new NextCommand();

        String expectedMessage = String.format(NextCommand.MESSAGE_NEXT_SUCCESS, 4) + "\n"
                + (String.format(Messages.MESSAGE_REMAINING_IMAGES_IN_DIR, 4)
                + (String.format(Messages.MESSAGE_CURRENT_IMAGES_IN_BATCH, 4)));

        assertCommandSuccess(nextCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String expectedMessage) {
        NextCommand nextCommand = new NextCommand();
        assertCommandFailure(nextCommand, model, commandHistory, expectedMessage);
    }
}
