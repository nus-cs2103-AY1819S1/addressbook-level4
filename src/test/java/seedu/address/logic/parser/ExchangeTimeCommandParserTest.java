package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ExchangeTimeCommand;

public class ExchangeTimeCommandParserTest {
    private ExchangeTimeCommandParser parser = new ExchangeTimeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, " ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ExchangeTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        ExchangeTimeCommand expectedFindCommand =
                new ExchangeTimeCommand("Alice 0 Bob 0");
        assertParseSuccess(parser, "Alice 0 Bob 0", expectedFindCommand);

    }

}
