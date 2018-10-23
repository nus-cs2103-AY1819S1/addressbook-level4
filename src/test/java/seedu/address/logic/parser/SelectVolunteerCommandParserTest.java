package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SelectVolunteerCommand;

/**
 * Test scope: similar to {@code DeleteVolunteerCommandParserTest}.
 * @see DeleteVolunteerCommandParserTest
 */
public class SelectVolunteerCommandParserTest {

    private SelectVolunteerCommandParser parser = new SelectVolunteerCommandParser();

    @Test
    public void parse_validArgs_returnsSelectVolunteerCommand() {
        assertParseSuccess(parser, "1", new SelectVolunteerCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectVolunteerCommand.MESSAGE_USAGE));
    }
}
