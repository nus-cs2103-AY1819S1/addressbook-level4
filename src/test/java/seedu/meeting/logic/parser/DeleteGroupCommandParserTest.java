package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.meeting.testutil.TypicalGroups.GROUP_0;

import org.junit.Test;

import seedu.meeting.logic.commands.DeleteGroupCommand;


public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "group", new DeleteGroupCommand(GROUP_0));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "@",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
    }
}
