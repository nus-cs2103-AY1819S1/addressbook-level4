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
        assertParseSuccess(parser, filterPrefix + "today", new ListCommand(ListCommand.ListFilter.DUE_TODAY));
        assertParseSuccess(parser, filterPrefix + "week", new ListCommand(ListCommand.ListFilter.DUE_END_OF_WEEK));
        assertParseSuccess(parser, filterPrefix + "month", new ListCommand(ListCommand.ListFilter.DUE_END_OF_MONTH));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, filterPrefix + "sunImplodes", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }
}
