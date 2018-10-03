package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArgs_returnsListAll() {
        assertParseSuccess(parser, "   ", new ListCommand(ListCommand.ListType.SHOW_ALL));
    }

    @Test
    public void parse_validArgs_returnsCorrectList() {
        ListCommand completedListCommand = new ListCommand(ListCommand.ListType.SHOW_COMPLETED);
        ListCommand uncompletedListCommand = new ListCommand(ListCommand.ListType.SHOW_UNCOMPLETED);

        assertParseSuccess(parser, "-c", completedListCommand);
        assertParseSuccess(parser, "-u", uncompletedListCommand);
    }
}
