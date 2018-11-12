package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FilterByTimeCommandParserTest {
    private FilterByTimeCommandParser parser = new FilterByTimeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "  ", String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterByTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        FilterByTimeCommand expectedFindCommand =
                new FilterByTimeCommand("mon 1300 1400");
        assertParseSuccess(parser, "mon 1300 1400", expectedFindCommand);


    }
}
