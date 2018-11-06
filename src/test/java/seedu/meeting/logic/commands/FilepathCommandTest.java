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
 * Contains integration tests (interaction with the Model) for {@code FilepathCommand}.
 */
public class FilepathCommandTest {

    private static final Path NEW_PATH = TestUtil.getFilePathInSandboxFolder("Data.xml");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager(getTypicalMeetingBook(), userPrefs);
    private Model expectedModel = new ModelManager(getTypicalMeetingBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_filepath_success() {
        FilepathCommand filepathCommand = new FilepathCommand(NEW_PATH);
        assertCommandSuccess(filepathCommand, model, commandHistory,
                String.format(FilepathCommand.MESSAGE_CHANGEPATH_SUCCESS, NEW_PATH), expectedModel);
        deleteFileIfExists(NEW_PATH);
    }

    @Test
    public void execute_showFilepath_success() {
        FilepathCommand filepathCommand = new FilepathCommand(null);
        assertCommandSuccess(filepathCommand, model, commandHistory,
                String.format(FilepathCommand.MESSAGE_SHOWPATH_SUCCESS, userPrefs.getMeetingBookFilePath()),
                expectedModel);
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
        deleteFileIfExists(NEW_PATH);
    }
}
