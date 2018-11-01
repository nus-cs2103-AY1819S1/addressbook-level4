package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;

/**
 * Tests that the permitted filters for ListCommand work.
 */
public class ListCommandParserTest {

    private String filterPrefix = " " + ListCommandParser.PREFIX_FILTER;

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand());

        assertParseSuccess(parser, filterPrefix + ListCommandParser.DUE_TODAY_OPTION,
                new ListCommand(ListCommand.ListFilter.DUE_TODAY));
        assertParseSuccess(parser, filterPrefix + ListCommandParser.DUE_END_OF_WEEK_OPTION,
                new ListCommand(ListCommand.ListFilter.DUE_END_OF_WEEK));
        assertParseSuccess(parser, filterPrefix + ListCommandParser.DUE_END_OF_MONTH_OPTION,
                new ListCommand(ListCommand.ListFilter.DUE_END_OF_MONTH));
        assertParseSuccess(parser, filterPrefix + ListCommandParser.NOT_BLOCKED_OPTION,
                new ListCommand(ListCommand.ListFilter.NOT_BLOCKED));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, filterPrefix + "sunImplodes", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }
}
