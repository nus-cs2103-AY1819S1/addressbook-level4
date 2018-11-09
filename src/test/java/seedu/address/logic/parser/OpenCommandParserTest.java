package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_IMAGE;

import org.junit.Test;

import seedu.address.logic.commands.OpenCommand;

public class OpenCommandParserTest {

    private OpenCommandParser parser = new OpenCommandParser();

    @Test
    public void parse_validArgs_returnsOpenCommand() {
        assertParseSuccess(parser, "1", new OpenCommand(INDEX_FIRST_IMAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
    }
}
