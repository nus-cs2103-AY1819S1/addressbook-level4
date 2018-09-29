package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class SuggestCommandTest {
    private final AddressBookParser parser = new AddressBookParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SuggestCommand(null);
    }

    @Test
    public void execute_suggestCommand_Success() throws Exception {
        assertSuggestCommandSuccessBehavior("a",
                SuggestCommand.MESSAGE_SUCCESS_HEADER + "add");
        assertSuggestCommandSuccessBehavior("c",
                SuggestCommand.MESSAGE_SUCCESS_HEADER + "clear");
        assertSuggestCommandSuccessBehavior("d",
                SuggestCommand.MESSAGE_SUCCESS_HEADER + "delete");
        assertSuggestCommandSuccessBehavior("e",
                SuggestCommand.MESSAGE_SUCCESS_HEADER + "edit, exit");
        assertSuggestCommandSuccessBehavior("f",
                SuggestCommand.MESSAGE_SUCCESS_HEADER + "find");
        assertSuggestCommandSuccessBehavior("l",
                SuggestCommand.MESSAGE_SUCCESS_HEADER + "list");
        assertSuggestCommandSuccessBehavior("r",
                SuggestCommand.MESSAGE_SUCCESS_HEADER + "redo");
        assertSuggestCommandSuccessBehavior("u",
                SuggestCommand.MESSAGE_SUCCESS_HEADER + "undo");
    }

    @Test
    public void execute_suggestCommand_Failure() throws Exception {
        assertSuggestCommandFailureBehavior("b");
        assertSuggestCommandFailureBehavior("g");
        assertSuggestCommandFailureBehavior("h");
        assertSuggestCommandFailureBehavior("i");
        assertSuggestCommandFailureBehavior("j");
        assertSuggestCommandFailureBehavior("k");
        assertSuggestCommandFailureBehavior("m");
        assertSuggestCommandFailureBehavior("n");
        assertSuggestCommandFailureBehavior("o");
        assertSuggestCommandFailureBehavior("p");
        assertSuggestCommandFailureBehavior("q");
        assertSuggestCommandFailureBehavior("s");
        assertSuggestCommandFailureBehavior("t");
        assertSuggestCommandFailureBehavior("v");
        assertSuggestCommandFailureBehavior("w");
        assertSuggestCommandFailureBehavior("x");
        assertSuggestCommandFailureBehavior("y");
        assertSuggestCommandFailureBehavior("z");
    }

    /**
     * Verifies behavior of successful suggest.
     * @param userInput
     * @param expectedMessage
     * @throws Exception
     */
    private void assertSuggestCommandSuccessBehavior(String userInput, String expectedMessage) throws Exception {
        Model model = new ModelManager();
        CommandHistory history = new CommandHistory();
        Command command = parser.parseCommand(userInput);
        assertTrue(command instanceof SuggestCommand);
        SuggestCommand suggestCommand = (SuggestCommand) command;
        assertEquals(suggestCommand.execute(model, history).feedbackToUser, expectedMessage);
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
