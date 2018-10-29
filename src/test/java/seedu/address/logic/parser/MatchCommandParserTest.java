package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchCommand;

public class MatchCommandParserTest {
    private MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void test_parseSuccess() {
        MatchCommand expectedCommand =
                new MatchCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        assertParseSuccess(parser, "1 2", expectedCommand);
        assertParseSuccess(parser, "   1 2    ", expectedCommand);
        assertParseSuccess(parser, "   1 2  3  ", expectedCommand);
        assertParseSuccess(parser, "1 2 3 4 5   ", expectedCommand);
    }

    @Test
    public void test_parseFailure() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MatchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "sd sa", String.format(ParserUtil.MESSAGE_INVALID_INDEX,
                MatchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "sd", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MatchCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "    sd    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MatchCommand.MESSAGE_USAGE));
    }
}
