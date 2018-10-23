package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterByEducationCommand;

import org.junit.Test;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class FilterByGradeCommandParserTest {
    private FilterByGradeCommandParser parser = new FilterByGradeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByEducationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FilterByEducationCommand expectedFindCommand =
                new FilterByEducationCommand("Sec");
        assertParseSuccess(parser, "Sec", expectedFindCommand);


    }
}
