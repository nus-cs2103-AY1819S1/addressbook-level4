package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.jxmusic.logic.commands.PlaylistSearchCommand;
import seedu.jxmusic.model.NameContainsKeywordsPredicate;

public class PlaylistSearchCommandParserTest {

    private PlaylistSearchCommandParser parser = new PlaylistSearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PlaylistSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        PlaylistSearchCommand expectedPlaylistSearchCommand =
                new PlaylistSearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("Anime", "songs")));
        assertParseSuccess(parser, "Anime songs", expectedPlaylistSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Anime \n \t songs  \t", expectedPlaylistSearchCommand);
    }

}
