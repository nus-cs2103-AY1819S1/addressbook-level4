package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import seedu.address.logic.commands.FilterByTimeCommand;

import org.junit.Test;

public class FilterByTimeCommandParserTest {
    private FilterByEducationCommandParser parser = new FilterByEducationCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "  ", String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterByTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FilterByTimeCommand expectedFindCommand =
                new FilterByTimeCommand("Mon 1300 1400");
        assertParseSuccess(parser, "Mon 1300 1400", expectedFindCommand);


    }
}
