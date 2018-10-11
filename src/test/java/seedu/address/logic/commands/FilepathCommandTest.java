package seedu.address.logic.commands;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TestUtil;
import seedu.address.ui.testutil.EventsCollectorRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) for {@code FilepathCommand}.
 */
public class FilepathCommandTest {

    private static final Path NEW_PATH = TestUtil.getFilePathInSandboxFolder("Data.xml");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_filepath_success() {

        FilepathCommand filepathCommand = new FilepathCommand(NEW_PATH);
        assertCommandSuccess(filepathCommand, model, commandHistory, FilepathCommand.MESSAGE_CHANGEPATH_SUCCESS, expectedModel);
        deleteFileIfExists(NEW_PATH);
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
