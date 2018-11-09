package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.thanepark.logic.commands.MaintainCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MaintainCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MaintainCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MaintainCommandParserTest {

    private MaintainCommandParser parser = new MaintainCommandParser();

    @Test
    public void parse_validArgs_returnsShutDownCommand() {
        assertParseSuccess(parser, "1", new MaintainCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MaintainCommand.MESSAGE_USAGE));
    }
}
