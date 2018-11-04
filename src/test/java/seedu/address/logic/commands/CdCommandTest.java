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

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Contains integration tests (interaction with the Model) for {@code CdCommand}.
 */
public class CdCommandTest extends DefaultCommandTest {
    private Model model = getDefaultModel();
    private Model expectedModel = getDefaultModel();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Path currPath = Paths.get(".");
        Path nextPath = Paths.get("Desktop");
        Path backPath = Paths.get("..");

        CdCommand currDirectory = new CdCommand(currPath);
        CdCommand nextDirectory = new CdCommand(nextPath);
        CdCommand prevDirectory = new CdCommand(backPath);

        String homeDirectory = System.getProperty("user.home");
        String currDir = homeDirectory + "/" + currDirectory.getPath().toString();
        String nextDir = homeDirectory + "/" + nextDirectory.getPath().toString();
        String prevDir = homeDirectory + "/" + prevDirectory.getPath().toString();

        // same directory -> returns true
        assertTrue(Paths.get(currDir).normalize().equals(model.getCurrDirectory()));

        // change to Desktop
        model.updateCurrDirectory(model.getCurrDirectory().resolve("Desktop").normalize());
        assertTrue(Paths.get(nextDir).normalize().equals(model.getCurrDirectory()));

        // change to previous directory
        model.updateCurrDirectory(model.getCurrDirectory().resolve("..").normalize());
        assertTrue(Paths.get(currDir).normalize().equals(model.getCurrDirectory()));

        // change to previous directory
        model.updateCurrDirectory(model.getCurrDirectory().resolve("..").normalize());
        assertTrue(Paths.get(prevDir).normalize().equals(model.getCurrDirectory()));

        // different paths -> returns false
        assertFalse(Paths.get(currDir).normalize().equals(model.getCurrDirectory()));
        assertFalse(Paths.get(nextDir).normalize().equals(model.getCurrDirectory()));

        // different object -> returns false
        assertFalse(currDirectory.equals(nextDirectory));
    }

    @Test
    public void execute_notDirectory() {
        String expectedMessage = CdCommand.MESSAGE_FAILURE;
        Path directory = Paths.get("testFolder");
        CdCommand command = new CdCommand(directory);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Paths.get(System.getProperty("user.home")), model.getCurrDirectory());
    }

}
