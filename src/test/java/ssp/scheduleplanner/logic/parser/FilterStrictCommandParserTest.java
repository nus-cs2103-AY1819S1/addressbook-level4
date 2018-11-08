package ssp.scheduleplanner.logic.parser;

import static ssp.scheduleplanner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import ssp.scheduleplanner.logic.commands.FilterStrictCommand;
import ssp.scheduleplanner.model.task.TagsContainsAllKeywordsPredicate;

public class FilterStrictCommandParserTest {

    private FilterStrictCommandParser parser = new FilterStrictCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterStrictCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterStrictCommand() {
        // no leading and trailing whitespaces
        FilterStrictCommand expectedFilterStrictCommand =
                new FilterStrictCommand(new TagsContainsAllKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFilterStrictCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFilterStrictCommand);
    }

}
