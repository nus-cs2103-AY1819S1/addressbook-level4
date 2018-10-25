package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.commands.HelpToDoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todolist.ToDoListEvent;
import seedu.address.testutil.ToDoListEventBuilder;
import seedu.address.testutil.ToDoListEventUtil;

public class ToDoListParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ToDoListParser parser = new ToDoListParser();

    @Test
    public void parseCommand_addToDo() throws Exception {
        ToDoListEvent toDoListEvent = new ToDoListEventBuilder().build();
        AddToDoCommand commandToDo = (AddToDoCommand) parser
                .toDoParseCommand(ToDoListEventUtil.getAddToDoCommand(toDoListEvent));
        System.out.printf("commandToDo = %s\n",commandToDo);
        assertEquals(new AddToDoCommand(toDoListEvent), commandToDo);
    }

    @Test
    public void parseCommand_deleteToDo() throws Exception {
        System.out.printf("%s\n",DeleteToDoCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        DeleteToDoCommand commandToDo = (DeleteToDoCommand) parser.toDoParseCommand(
                DeleteToDoCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteToDoCommand(INDEX_FIRST_PERSON), commandToDo);
    }

    @Test
    public void parseCommand_helpToDo() throws Exception {
        assertTrue(parser.toDoParseCommand(HelpToDoCommand.COMMAND_WORD) instanceof HelpToDoCommand);
        assertTrue(parser.toDoParseCommand(HelpToDoCommand.COMMAND_WORD + " 3") instanceof HelpToDoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpToDoCommand.MESSAGE_USAGE));
        parser.toDoParseCommand("");
    }

    @Test
    public void parseCommand_unknownToDoCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.toDoParseCommand("unknownCommand");
    }

}
