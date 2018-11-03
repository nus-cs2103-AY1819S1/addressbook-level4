package seedu.meeting.logic.commands;

import static seedu.meeting.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;
import seedu.meeting.model.ModelManager;
import seedu.meeting.model.UserPrefs;
import seedu.meeting.testutil.TestUtil;
import seedu.meeting.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportCommand}.
 */
public class ExportCommandTest {

    private static final Path EXPORT_PATH = TestUtil.getFilePathInSandboxFolder("exportData.xml");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_export_success() {

        ExportCommand exportCommand = new ExportCommand(EXPORT_PATH);
        assertCommandSuccess(exportCommand, model, commandHistory, ExportCommand.MESSAGE_EXPORT_SUCCESS, expectedModel);
        deleteFileIfExists(EXPORT_PATH);
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @After
    /**
     * Cleanup of file created in event of test case fails
     */
    public void cleanUp() {
        deleteFileIfExists(EXPORT_PATH);
    }
}
