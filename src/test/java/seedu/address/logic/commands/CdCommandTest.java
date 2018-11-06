//@@author benedictcss
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ModelGenerator.getDefaultModel;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Contains integration tests (interaction with the Model) for {@code CdCommand}.
 */
public class CdCommandTest {

    private Model model = getDefaultModel();
    private Model expectedModel = getDefaultModel();
    private CommandHistory commandHistory = new CommandHistory();

    private Path currPath = Paths.get(".");
    private Path nextPath = Paths.get("Desktop");
    private Path backPath = Paths.get("..");

    private String os = System.getProperty("os.name").toLowerCase();

    @Test
    public void equals() {
        CdCommand currDirectory = new CdCommand(currPath);
        CdCommand nextDirectory = new CdCommand(nextPath);
        CdCommand prevDirectory = new CdCommand(backPath);

        String homeDirectory = System.getProperty("user.home");
        String currDir = homeDirectory + "/" + currDirectory.getPath().toString();
        String nextDir = homeDirectory + "/" + nextDirectory.getPath().toString();
        String prevDir = homeDirectory + "/" + prevDirectory.getPath().toString();

        // same directory -> returns true
        assertTrue(Paths.get(currDir).normalize().equals(model.getCurrDirectory()));
        assert_cdToDirectory_success(currDirectory);

        // change to Desktop
        expectedModel.updateCurrDirectory(expectedModel.getCurrDirectory().resolve("Desktop").normalize());
        //assert_cdToDirectory_success(nextDirectory);
        //assertTrue(Paths.get(nextDir).normalize().equals(model.getCurrDirectory()));

        // change to previous directory
        expectedModel.updateCurrDirectory(expectedModel.getCurrDirectory().resolve("..").normalize());
        //assert_cdToDirectory_success(prevDirectory);
        //assertTrue(Paths.get(currDir).normalize().equals(model.getCurrDirectory()));

        // change to previous directory
        expectedModel.updateCurrDirectory(expectedModel.getCurrDirectory().resolve("..").normalize());
        //assert_cdToDirectory_success(prevDirectory);
        //assertTrue(Paths.get(prevDir).normalize().equals(model.getCurrDirectory()));

        // different paths -> returns false
        //assertFalse(Paths.get(currDir).normalize().equals(model.getCurrDirectory()));
        //assertFalse(Paths.get(nextDir).normalize().equals(model.getCurrDirectory()));

        // different object -> returns false
        //assertFalse(currDirectory.equals(nextDirectory));
    }

    @Test
    public void execute_cdToDirectory_failure() {
        // access a non existent directory in current directory
        String expectedMessage = CdCommand.MESSAGE_FAILURE;
        Path directory = Paths.get("testFolder");
        CdCommand command = new CdCommand(directory);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Paths.get(System.getProperty("user.home")), model.getCurrDirectory());

        // access a non existent directory from drive


        if (os.contains("win")) {
            // cd commands to change to non existent directory in drive on windows
            Path absWinPath = Paths.get("C://12345");
            CdCommand winCommand = new CdCommand(absWinPath);

            assertCommandSuccess(winCommand, model, commandHistory, expectedMessage, expectedModel);
            assertEquals(Paths.get(System.getProperty("user.home")), model.getCurrDirectory());
        }

        if (os.contains("mac")) {
            // cd commands to change to non existent directory in drive on mac
            Path absMacPath = Paths.get("/Volume/12345");
            CdCommand winCommand = new CdCommand(absMacPath);

            assertCommandSuccess(winCommand, model, commandHistory, expectedMessage, expectedModel);
            assertEquals(Paths.get(System.getProperty("user.home")), model.getCurrDirectory());
        }
    }

    @Test
    public void execute_cdToDirectory_success() {
        if (os.contains("win")) {
            // cd commands to change drive on windows
            Path absWinPath = Paths.get("C://Users");
            expectedModel.updateCurrDirectory(absWinPath);

            CdCommand command = new CdCommand(absWinPath);
            assert_cdToDirectory_success(command);
        }

        if (os.contains("mac")) {
            // cd commands to change drive on mac
            Path absMacPath = Paths.get("/Volume");
            expectedModel.updateCurrDirectory(absMacPath);

            CdCommand command = new CdCommand(absMacPath);
            assert_cdToDirectory_success(command);
        }
    }

    /**
     * Asserts cd command success with the given {@code command}
     */
    public void assert_cdToDirectory_success(CdCommand command) {
        // directory exists
        String expectedMessage = expectedModel.getCurrDirectory().toString() + "\n"
                + String.format(Messages.MESSAGE_TOTAL_IMAGES_IN_DIR, expectedModel.getTotalImagesInDir())
                + String.format(Messages.MESSAGE_CURRENT_IMAGES_IN_BATCH,
                Math.min(expectedModel.getDirectoryImageList().size(), SelectCommand.BATCH_SIZE));

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

}
