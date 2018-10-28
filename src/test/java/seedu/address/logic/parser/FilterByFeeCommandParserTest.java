package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterByFeeCommand;

public class FilterByFeeCommandParserTest {

    private FilterByFeeCommandParser parser = new FilterByFeeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, " ", String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterByFeeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FilterByFeeCommand expectedFindCommand =
                new FilterByFeeCommand("20");
        assertParseSuccess(parser, "20", expectedFindCommand);

    }

}
