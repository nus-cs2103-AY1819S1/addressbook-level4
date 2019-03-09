package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jxmusic.testutil.TypicalIndexes.INDEX_FIRST_PLAYLIST;

import org.junit.Test;

import seedu.jxmusic.logic.commands.PlaylistDelCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the PlaylistDelCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the PlaylistDelCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class PlaylistDelCommandParserTest {

    private PlaylistDelCommandParser parser = new PlaylistDelCommandParser();

    @Test
    public void parse_validArgs_returnsPlaylistDelCommand() {
        assertParseSuccess(parser, "1", new PlaylistDelCommand(INDEX_FIRST_PLAYLIST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PlaylistDelCommand.MESSAGE_USAGE));
    }
}
