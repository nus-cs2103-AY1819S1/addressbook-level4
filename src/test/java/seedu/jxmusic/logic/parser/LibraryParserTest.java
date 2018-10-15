package seedu.jxmusic.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.jxmusic.logic.commands.PlaylistNewCommand;
import seedu.jxmusic.logic.commands.ClearCommand;
import seedu.jxmusic.logic.commands.DeleteCommand;
import seedu.jxmusic.logic.commands.ExitCommand;
import seedu.jxmusic.logic.commands.FindCommand;
import seedu.jxmusic.logic.commands.HelpCommand;
import seedu.jxmusic.logic.commands.ListCommand;
import seedu.jxmusic.logic.commands.SelectCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
import seedu.jxmusic.model.NameContainsKeywordsPredicate;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.testutil.PlaylistBuilder;
import seedu.jxmusic.testutil.PlaylistUtil;

public class LibraryParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LibraryParser parser = new LibraryParser();

    @Test
    public void parseCommand_add() throws Exception {
        Playlist playlist = new PlaylistBuilder().build();
        PlaylistNewCommand command = (PlaylistNewCommand) parser.parseCommand(PlaylistUtil.getPlaylistNewCommand(playlist));
        assertEquals(new PlaylistNewCommand(playlist), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_PHRASE) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_PHRASE + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PLAYLIST.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PLAYLIST), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PLAYLIST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PLAYLIST), command);
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
