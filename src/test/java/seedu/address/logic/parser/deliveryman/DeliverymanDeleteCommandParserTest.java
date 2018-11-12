package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.logic.commands.deliveryman.DeliverymanDeleteCommand;

public class DeliverymanDeleteCommandParserTest {
    private DeliverymanDeleteCommandParser parser = new DeliverymanDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeliverymanDeleteCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliverymanDeleteCommand.MESSAGE_USAGE));
    }
}
