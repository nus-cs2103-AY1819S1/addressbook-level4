package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, " p/1",
            new SelectCommand(INDEX_FIRST_PERSON, SelectCommand.SelectCommandType.PERSON));
    }

    @Test
    public void parse_validArgs_returnsSelectGroupCommand() {
        assertParseSuccess(parser, " g/1",
            new SelectCommand(INDEX_FIRST_GROUP, SelectCommand.SelectCommandType.GROUP));
    }

    @Test
    public void parse_validArgs_returnsSelectMeetingCommand() {
        assertParseSuccess(parser, " m/1",
            new SelectCommand(INDEX_FIRST_MEETING, SelectCommand.SelectCommandType.MEETING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
