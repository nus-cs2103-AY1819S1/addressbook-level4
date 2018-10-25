package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManagerToDo;
import seedu.address.model.ModelToDo;
import seedu.address.model.UserPrefs;

public class LogicToDoManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelToDo modelToDo = new ModelManagerToDo();

    private LogicToDo logicToDo = new LogicToDoManager(modelToDo);

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteToDoCommand = "delete_todo 9";
        assertCommandException(deleteToDoCommand, MESSAGE_INVALID_TODOLIST_EVENTS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() {
        String deleteToDoCommand = DeleteToDoCommand.COMMAND_WORD;
        assertCommandSuccess(deleteToDoCommand, AddToDoCommand.MESSAGE_SUCCESS, modelToDo);
    }

    @Test
    public void getFilteredToDoListEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logicToDo.getFilteredToDoListEventList().remove(0);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     *
     * @see #assertCommandBehavior(Class, String, String, ModelToDo)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, ModelToDo expectedModel) {
        System.out.printf("inputCommand = %s\n",inputCommand);
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, ModelToDo)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, ModelToDo)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, ModelToDo)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        ModelToDo expectedModel = new ModelManagerToDo(modelToDo.getToDoList(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     * - the internal model manager data are same as those in the {@code expectedModel} <br>
     * - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                       String expectedMessage, ModelToDo expectedModel) {

        try {
            CommandResult result = logicToDo.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, modelToDo);
    }

}
