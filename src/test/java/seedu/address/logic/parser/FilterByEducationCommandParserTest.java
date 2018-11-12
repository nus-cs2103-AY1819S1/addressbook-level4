package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterByEducationCommand;

public class FilterByEducationCommandParserTest {
    private FilterByEducationCommandParser parser = new FilterByEducationCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, " ", String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterByEducationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FilterByEducationCommand expectedFindCommand =
                new FilterByEducationCommand("Secondary");
        assertParseSuccess(parser, "Secondary", expectedFindCommand);


    }
}
