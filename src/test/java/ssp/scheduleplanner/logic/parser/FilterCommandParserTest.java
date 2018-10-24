package ssp.scheduleplanner.logic.parser;

import static ssp.scheduleplanner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static ssp.scheduleplanner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import ssp.scheduleplanner.logic.commands.FilterCommand;
import ssp.scheduleplanner.model.task.TagsContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new TagsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFilterCommand);
    }

}
