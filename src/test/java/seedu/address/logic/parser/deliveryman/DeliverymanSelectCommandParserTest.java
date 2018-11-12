package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.deliveryman.DeliverymanSelectCommand;

public class DeliverymanSelectCommandParserTest {
    private DeliverymanSelectCommandParser parser = new DeliverymanSelectCommandParser();

    @Test
    public void parse_validArgs_returnsDeliverymanSelectCommand() {
        assertParseSuccess(parser, "1", new DeliverymanSelectCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_thrwosParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeliverymanSelectCommand.MESSAGE_USAGE));
    }
}
