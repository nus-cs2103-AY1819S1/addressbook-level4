package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ChangeTimeCommand;

public class ChangeTimeCommandParserTest {
    private ChangeTimeCommandParser parser = new ChangeTimeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, " ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ChangeTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        ChangeTimeCommand expectedFindCommand =
                new ChangeTimeCommand("Alice 0 Bob 0");
        assertParseSuccess(parser, "Alice 0 Bob 0", expectedFindCommand);

    }

}
