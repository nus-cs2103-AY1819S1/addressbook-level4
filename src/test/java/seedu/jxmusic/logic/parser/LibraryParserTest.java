package seedu.jxmusic.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.jxmusic.logic.commands.ClearCommand;
import seedu.jxmusic.logic.commands.ExitCommand;
import seedu.jxmusic.logic.commands.HelpCommand;
import seedu.jxmusic.logic.commands.PlaylistDelCommand;
import seedu.jxmusic.logic.commands.PlaylistListCommand;
import seedu.jxmusic.logic.commands.PlaylistNewCommand;
import seedu.jxmusic.logic.commands.SelectCommand;
import seedu.jxmusic.logic.commands.TrackListCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;
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
        PlaylistNewCommand command = (PlaylistNewCommand) parser.parseCommand(
                PlaylistUtil.getPlaylistNewCommand(playlist));
        assertEquals(new PlaylistNewCommand(playlist), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_PHRASE) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_PHRASE + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        PlaylistDelCommand command = (PlaylistDelCommand) parser.parseCommand(
                PlaylistDelCommand.COMMAND_PHRASE + " " + INDEX_FIRST_PLAYLIST.getOneBased());
        assertEquals(new PlaylistDelCommand(INDEX_FIRST_PLAYLIST), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    // @Test
    // public void parseCommand_find() throws Exception {
    //     List<String> keywords = Arrays.asList("foo", "bar", "baz");
    // (Collectors.joining(" ")));
    //     PlaylistSearchCommand command = (PlaylistSearchCommand) parser.parseCommand(
    //             PlaylistSearchCommand.COMMAND_PHRASE + " " + keywords.stream().collect(Collectors.joining(" ")));
    //     assertEquals(new PlaylistSearchCommand(new NameContainsKeywordsPredicate(keywords)), command);
    // }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_playlistList() throws Exception {
        assertTrue(parser.parseCommand(PlaylistListCommand.COMMAND_PHRASE) instanceof PlaylistListCommand);
        assertTrue(parser.parseCommand(PlaylistListCommand.COMMAND_PHRASE + " 3") instanceof PlaylistListCommand);
    }

    @Test
    public void parseCommand_trackList() throws Exception {
        assertTrue(parser.parseCommand(TrackListCommand.COMMAND_PHRASE) instanceof TrackListCommand);
        assertTrue(parser.parseCommand(TrackListCommand.COMMAND_PHRASE + " 3") instanceof TrackListCommand);
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
