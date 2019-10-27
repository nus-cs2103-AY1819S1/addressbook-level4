package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.InvalidTemplateFileException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.Assert;

public class LoadTemplateCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final String DUMMY_FILEPATH = "dummy";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullFilepath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new LoadTemplateCommand(null));
    }

    @Test
    public void execute_validFile_success() throws Exception {
        Model model = new ModelManager();
        LoadTemplateCommandStubAlwaysValid loadTemplateCommand =
                new LoadTemplateCommandStubAlwaysValid(Paths.get(DUMMY_FILEPATH));

        CommandResult commandResult = loadTemplateCommand.execute(model, commandHistory);
        assertEquals(String.format(LoadTemplateCommand.MESSAGE_SUCCESS, DUMMY_FILEPATH), commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidFilepath_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        LoadTemplateCommandStubAlwaysInvalidFilepath loadTemplateCommand =
                new LoadTemplateCommandStubAlwaysInvalidFilepath(Paths.get(DUMMY_FILEPATH));

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(LoadTemplateCommand.MESSAGE_FILE_NOT_FOUND, DUMMY_FILEPATH));
        loadTemplateCommand.execute(model, commandHistory);;
    }

    @Test
    public void execute_invalidFileFormat_throwsCommandException() throws Exception {
        Model model = new ModelManager();
        LoadTemplateCommandStubAlwaysInvalidFormat loadTemplateCommand =
                new LoadTemplateCommandStubAlwaysInvalidFormat(Paths.get(DUMMY_FILEPATH));

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(LoadTemplateCommand.MESSAGE_INVALID_FILE_FORMAT, DUMMY_FILEPATH));
        loadTemplateCommand.execute(model, commandHistory);;
    }

    @Test
    public void equals() {
        Path facebookTemplatePath = Paths.get("facebook.txt");
        Path googleTemplatePath = Paths.get("google.txt");
        LoadTemplateCommand loadFacebookTemplate = new LoadTemplateCommand(facebookTemplatePath);
        LoadTemplateCommand loadGoogleTemplate = new LoadTemplateCommand(googleTemplatePath);

        // same object -> returns true
        assertTrue(loadFacebookTemplate.equals(loadFacebookTemplate));

        // same values -> returns true
        LoadTemplateCommand loadFacebookTemplateCopy = new LoadTemplateCommand(facebookTemplatePath);
        assertTrue(loadFacebookTemplate.equals(loadFacebookTemplateCopy));

        // different types -> returns false
        assertFalse(loadFacebookTemplate.equals(1));

        // null -> returns false
        assertFalse(loadFacebookTemplate.equals(null));

        // different path -> returns false
        assertFalse(loadFacebookTemplate.equals(loadGoogleTemplate));
    }

    private class LoadTemplateCommandStubAlwaysValid extends LoadTemplateCommand {

        LoadTemplateCommandStubAlwaysValid(Path filePath) {
            super(filePath);
            setSuccessful(true);
        }
    }

    private class LoadTemplateCommandStubAlwaysInvalidFilepath extends LoadTemplateCommand {

        LoadTemplateCommandStubAlwaysInvalidFilepath(Path filePath) {
            super(filePath);
            setSuccessful(false);
            setException(new IOException());
        }
    }

    private class LoadTemplateCommandStubAlwaysInvalidFormat extends LoadTemplateCommand {

        LoadTemplateCommandStubAlwaysInvalidFormat(Path filePath) {
            super(filePath);
            setSuccessful(false);
            setException(new InvalidTemplateFileException("Specified file has invalid format."));
        }
    }
}
