package ssp.scheduleplanner.logic.parser;

import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static ssp.scheduleplanner.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import ssp.scheduleplanner.logic.commands.SelectCommand;
import ssp.scheduleplanner.commons.core.Messages;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
