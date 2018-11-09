package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jxmusic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jxmusic.logic.parser.ParserUtil.MESSAGE_INVALID_TIME_FORMAT;

import org.junit.Test;

import javafx.util.Duration;
import seedu.jxmusic.logic.commands.SeekCommand;

public class SeekCommandParserTest {
    private SeekCommandParser parser = new SeekCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SeekCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SeekCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnTrue() {
        assertParseSuccess(parser, " d/2", new SeekCommand(new Duration(2000)));
        assertParseSuccess(parser, " d/1 2", new SeekCommand(new Duration(62000)));
        assertParseSuccess(parser, " d/1 2 3", new SeekCommand(new Duration(3723000)));
    }
    @Test
    public void parse_invalidValue_throwsParseException() {
        //invalid number of parameters
        assertParseFailure(parser, " d/1 2 3 4", MESSAGE_INVALID_TIME_FORMAT);
        //invalid format of parameters
        assertParseFailure(parser, " d/1.2 3 4", MESSAGE_INVALID_TIME_FORMAT);
        assertParseFailure(parser, " d/-1 3 4", MESSAGE_INVALID_TIME_FORMAT);
        assertParseFailure(parser, " d/a", MESSAGE_INVALID_TIME_FORMAT);

    }
}
