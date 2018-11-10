package seedu.thanepark.logic.parser;

import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.thanepark.logic.commands.HelpCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validArgs_returnsHistoryCommand() {
        assertParseSuccess(parser, "", new HelpCommand(true, ""));
        final String args2 = "more";
        assertParseSuccess(parser, args2, new HelpCommand(false, ""));
        final String args3 = "add";
        assertParseSuccess(parser, args3, new HelpCommand(false, args3));
        final String args4 = args3 + " 1234";
        assertParseSuccess(parser, args4, new HelpCommand(false, args3));
    }
}
