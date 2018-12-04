package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.thanepark.logic.commands.HistoryCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class HistoryCommandParserTest {

    private HistoryCommandParser parser = new HistoryCommandParser();

    @Test
    public void parse_validArgs_returnsHistoryCommand() {
        assertParseSuccess(parser, "", new HistoryCommand());
        final String[] args = {"more"};
        assertParseSuccess(parser, "more", new HistoryCommand(args));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            HistoryCommand.MESSAGE_USAGE));
    }
}
