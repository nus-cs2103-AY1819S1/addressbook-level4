package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;

import org.junit.Test;

import seedu.address.logic.parser.SelectEntryCommandParser;

/**
 * test for SelectEntryCommandParser.
 */
public class SelectEntryCommandParserTest {

    private SelectEntryCommandParser parser = new SelectEntryCommandParser();

    @Test
    public void parse_validArgs_returnsSelectEntryCommand() {
        assertParseSuccess(parser, "1", new SelectEntryCommand(INDEX_FIRST_ENTRY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectEntryCommand.MESSAGE_USAGE));
    }
}
