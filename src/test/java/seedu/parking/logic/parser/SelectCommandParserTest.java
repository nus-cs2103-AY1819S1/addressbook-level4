package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.parking.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.parking.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.parking.testutil.TypicalIndexes.INDEX_FIRST_CARPARK;

import org.junit.Test;

import seedu.parking.logic.commands.SelectCommand;

/**
 * Check for valid arguments
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_CARPARK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
