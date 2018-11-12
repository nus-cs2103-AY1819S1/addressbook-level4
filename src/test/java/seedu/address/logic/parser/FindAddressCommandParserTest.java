//@@author LZYAndy
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.FindAddressCommand;

public class FindAddressCommandParserTest {

    private FindAddressCommandParser parser = new FindAddressCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindAddressCommand.MESSAGE_USAGE));
    }

}
//@@author LZYAndy
