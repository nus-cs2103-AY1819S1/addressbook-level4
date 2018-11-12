package seedu.parking.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.parking.commons.core.Messages.MESSAGE_UNCERTAIN_CLEAR_OR_CALCULATE_COMMAND;
import static seedu.parking.commons.core.Messages.MESSAGE_UNCERTAIN_FIND_OR_FILTER_COMMAND;
import static seedu.parking.commons.core.Messages.MESSAGE_UNCERTAIN_HELP_OR_HISTORY_COMMAND;
import static seedu.parking.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.parking.logic.parser.CarparkFinderParser.containsFromFirstLetter;
import static seedu.parking.testutil.TypicalIndexes.INDEX_FIRST_CARPARK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.parking.logic.commands.CalculateCommand;
import seedu.parking.logic.commands.ClearCommand;
import seedu.parking.logic.commands.ExitCommand;
import seedu.parking.logic.commands.FilterCommand;
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
    public void parseCommand_ambiguous_commands() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNCERTAIN_CLEAR_OR_CALCULATE_COMMAND);
        parser.parseCommand("c");
        thrown.expectMessage(MESSAGE_UNCERTAIN_HELP_OR_HISTORY_COMMAND);
        parser.parseCommand("h");
        thrown.expectMessage(MESSAGE_UNCERTAIN_FIND_OR_FILTER_COMMAND);
        parser.parseCommand("f");
        parser.parseCommand("fi");
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("clears");
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.substring(0, 1)) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.substring(0, 1) + " 3") instanceof ExitCommand);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("exits");
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD.substring(0, 2)) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD.substring(0, 2) + " 3") instanceof HelpCommand);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("clears");
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD.substring(0, 2)) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD.substring(0, 2) + " 3") instanceof HistoryCommand);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("histories");
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD.substring(0, 1)) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD.substring(0, 1) + " 3") instanceof ListCommand);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("lists");
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD.substring(0, 1)) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD.substring(0, 1) + " 3") instanceof RedoCommand);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("redos");
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD.substring(0, 1)) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD.substring(0, 1) + " 3") instanceof UndoCommand);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("undos");
    }

    @Test
    public void parseCommand_query() throws Exception {
        assertTrue(parser.parseCommand(QueryCommand.COMMAND_WORD) instanceof QueryCommand);
        assertTrue(parser.parseCommand(QueryCommand.COMMAND_WORD + " 3") instanceof QueryCommand);
        assertTrue(parser.parseCommand(QueryCommand.COMMAND_WORD.substring(0, 1)) instanceof QueryCommand);
        assertTrue(parser.parseCommand(QueryCommand.COMMAND_WORD.substring(0, 1) + " 3") instanceof QueryCommand);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("queries");
    }

    @Test
    public void parseCommand_notify() throws Exception {
        NotifyCommand command = (NotifyCommand) parser.parseCommand(
            NotifyCommand.COMMAND_WORD + " " + 60);
        assertEquals(new NotifyCommand(60), command);
        command = (NotifyCommand) parser.parseCommand(
            NotifyCommand.COMMAND_WORD.substring(0, 1) + " " + 60);
        assertEquals(new NotifyCommand(60), command);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("notifies " + 60);
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

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("finds punggol");
    }

    @Test
    public void parseCommand_findEmpty() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " ");
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
            SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_CARPARK.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_CARPARK), command);
        command = (SelectCommand) parser.parseCommand(
            SelectCommand.COMMAND_WORD.substring(0, 1) + " " + INDEX_FIRST_CARPARK.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_CARPARK), command);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("selects " + INDEX_FIRST_CARPARK.getOneBased());
    }

    @Test
    public void parseCommand_filter() throws Exception {
        List<String> flagList = new ArrayList<>();
        flagList.add("a/");
        FilterCommand command = (FilterCommand) parser.parseCommand(FilterCommand.COMMAND_WORD + " a/");
        assertEquals(new FilterCommand(flagList, null, null, null), command);
        command = (FilterCommand) parser.parseCommand(FilterCommand.COMMAND_WORD.substring(0, 3) + " a/");
        assertEquals(new FilterCommand(flagList, null, null, null), command);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("filters a/");
    }

    @Test
    public void parseCommand_calculate() throws Exception {
        String[] flags = "TJ39 SUN 3.30AM 6.30PM".trim().split("\\s+");
        CalculateCommand command = (CalculateCommand) parser.parseCommand(
            CalculateCommand.COMMAND_WORD + " TJ39 SUN 3.30AM 6.30PM");
        assertEquals(new CalculateCommand(flags), command);
        command = (CalculateCommand) parser.parseCommand(
            CalculateCommand.COMMAND_WORD.substring(0, 2) + " TJ39 SUN 3.30AM 6.30PM");
        assertEquals(new CalculateCommand(flags), command);

        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("calculates TJ39 SUN 3.30AM 6.30PM");
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

    @Test
    public void parseCommand_containsFromFirstLetter() throws Exception {
        assertTrue(containsFromFirstLetter(FilterCommand.COMMAND_WORD, "filt"));
        assertFalse(containsFromFirstLetter(FilterCommand.COMMAND_WORD, "ilt"));
    }
}
