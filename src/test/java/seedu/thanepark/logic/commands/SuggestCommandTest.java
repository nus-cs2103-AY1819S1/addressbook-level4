package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.thanepark.logic.commands.SuggestCommand.MESSAGE_SUGGEST_COMMAND_SUCCESS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.logic.parser.ThaneParkParser;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;

/**
 * Contains unit tests for {@code SuggestCommand}
 */
public class SuggestCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ThaneParkParser parser = new ThaneParkParser();

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SuggestCommand(null);
    }

    @Test
    public void execute_suggestCommand_success() throws Exception {
        assertSuggestCommandSuccessBehavior("a", "add");
        assertSuggestCommandSuccessBehavior("c", "clear");
        assertSuggestCommandSuccessBehavior("d", "delete");
        assertSuggestCommandSuccessBehavior("e", "exit");
        assertSuggestCommandSuccessBehavior("f", "find", "filter");
        assertSuggestCommandSuccessBehavior("h", "help", "history");
        assertSuggestCommandSuccessBehavior("r", "redo");
        assertSuggestCommandSuccessBehavior("u", "update", "undo");
        assertSuggestCommandSuccessBehavior("v", "viewall", "viewstatus", "view");

        assertSuggestCommandSuccessBehavior("a j\\km bu/ni i?@w 359h", "add");
    }


    @Test
    public void execute_suggestCommand_failure() throws Exception {
        assertSuggestCommandFailureBehavior("b");
    }

    @Test
    public void execute_suggestCommand2_failure() throws Exception {
        assertSuggestCommandFailureBehavior("g");
    }

    /**
     * Verifies behavior of successful suggest.
     * @param userInput
     * @param commandWords
     * @throws Exception
     */
    private void assertSuggestCommandSuccessBehavior(String userInput, String... commandWords) throws Exception {
        Model model = new ModelManager();
        CommandHistory history = new CommandHistory();
        String expectedMessage = String.format(MESSAGE_SUGGEST_COMMAND_SUCCESS,
                SuggestCommand.combineCommandWords(commandWords));
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof SuggestCommand);
        SuggestCommand suggestCommand = (SuggestCommand) command;
        assertEquals(expectedMessage, suggestCommand.execute(model, history).feedbackToUser);
    }

    /**
     * Verifies behavior of failed suggest
     * @param userInput
     * @throws Exception
     */
    private void assertSuggestCommandFailureBehavior(String userInput) throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand(userInput);
    }

}
