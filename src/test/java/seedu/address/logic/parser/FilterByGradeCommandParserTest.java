package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterByGradeCommand;

public class FilterByGradeCommandParserTest {
    private FilterByGradeCommandParser parser = new FilterByGradeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, " ", String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterByGradeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FilterByGradeCommand expectedFindCommand =
                new FilterByGradeCommand("3 100");
        assertParseSuccess(parser, "3 100", expectedFindCommand);


    }
}
