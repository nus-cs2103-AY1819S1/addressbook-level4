package seedu.parking.logic.parser;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.parking.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.parking.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.parking.testutil.TypicalIndexes.INDEX_FIRST_CARPARK;

import org.junit.Test;

import seedu.parking.logic.commands.NotifyCommand;
import seedu.parking.logic.commands.SelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class NotifyCommandParserTest {

    private NotifyCommandParser parser = new NotifyCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "60", new NotifyCommand(60));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotifyCommand.MESSAGE_USAGE));
    }
}
