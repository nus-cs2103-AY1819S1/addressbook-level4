package seedu.jxmusic.logic;

import static org.junit.Assert.assertEquals;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.jxmusic.logic.commands.CommandResult;
//import seedu.jxmusic.logic.commands.PlaylistDelCommand;
import seedu.jxmusic.logic.commands.PlaylistListCommand;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.ModelManager;
import seedu.jxmusic.model.UserPrefs;


public class LogicManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model);

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String playlistDelCommand = "playlist delete 9";
        assertCommandException(playlistDelCommand, MESSAGE_INVALID_PLAYLIST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = PlaylistListCommand.COMMAND_PHRASE;
        assertCommandSuccess(listCommand, PlaylistListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void getFilteredPlaylistList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPlaylistList().remove(0);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s jxmusic book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }
}
