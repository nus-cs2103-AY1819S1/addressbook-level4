package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ViewPermissionCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 *
 * @see DeleteCommandParserTest
 */
public class ViewPermissionCommandParserTest {

    private ViewPermissionCommandParser parser = new ViewPermissionCommandParser();

    @Test
    public void parse_validArgs_returnsViewPermissionCommand() {
        assertParseSuccess(parser, "1", new ViewPermissionCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewPermissionCommand.MESSAGE_USAGE));
    }
}
