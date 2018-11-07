package seedu.parking.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.parking.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.parking.testutil.TypicalIndexes.INDEX_FIRST_CARPARK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.parking.logic.commands.ClearCommand;
import seedu.parking.logic.commands.ExitCommand;
import seedu.parking.logic.commands.FindCommand;
import seedu.parking.logic.commands.HelpCommand;
import seedu.parking.logic.commands.HistoryCommand;
import seedu.parking.logic.commands.ListCommand;
import seedu.parking.logic.commands.NotifyCommand;
import seedu.parking.logic.commands.QueryCommand;
import seedu.parking.logic.commands.RedoCommand;
import seedu.parking.logic.commands.SelectCommand;
import seedu.parking.logic.commands.UndoCommand;
import seedu.parking.logic.parser.exceptions.ParseException;
import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;

public class CarparkFinderParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CarparkFinderParser parser = new CarparkFinderParser();

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.substring(0, 1)) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.substring(0, 1) + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new CarparkContainsKeywordsPredicate(keywords)), command);
        command = (FindCommand) parser.parseCommand(
            FindCommand.COMMAND_WORD.substring(0, 3) + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new CarparkContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD.substring(0, 2)) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD.substring(0, 2) + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD.substring(0, 1)) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD.substring(0, 1) + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_CARPARK.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_CARPARK), command);
        command = (SelectCommand) parser.parseCommand(
            SelectCommand.COMMAND_WORD.substring(0, 1) + " " + INDEX_FIRST_CARPARK.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_CARPARK), command);
    }

    @Test
    public void parseCommand_query() throws Exception {
        assertTrue(parser.parseCommand(QueryCommand.COMMAND_WORD) instanceof QueryCommand);
        assertTrue(parser.parseCommand(QueryCommand.COMMAND_WORD + " 3") instanceof QueryCommand);
        assertTrue(parser.parseCommand(QueryCommand.COMMAND_WORD.substring(0, 1)) instanceof QueryCommand);
        assertTrue(parser.parseCommand(QueryCommand.COMMAND_WORD.substring(0, 1) + " 3") instanceof QueryCommand);
    }

    @Test
    public void parseCommand_notify() throws Exception {
        NotifyCommand command = (NotifyCommand) parser.parseCommand(
            NotifyCommand.COMMAND_WORD + " " + 60);
        assertEquals(new NotifyCommand(60), command);
        command = (NotifyCommand) parser.parseCommand(
            NotifyCommand.COMMAND_WORD.substring(0, 1) + " " + 60);
        assertEquals(new NotifyCommand(60), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
