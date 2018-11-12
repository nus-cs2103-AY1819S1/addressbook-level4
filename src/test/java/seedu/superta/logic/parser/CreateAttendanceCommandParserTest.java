package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.CreateAttendanceCommand;
import seedu.superta.model.attendance.Session;

// @@author triger15
public class CreateAttendanceCommandParserTest {
    private CreateAttendanceCommandParser parser = new CreateAttendanceCommandParser();
    private final String tgId = "01a";
    private final Session session = new Session("Lab 1");

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = "    " + PREFIX_GENERAL_TUTORIAL_GROUP_ID
                + tgId + " " + PREFIX_SESSION_NAME + session.getSessionName();
        CreateAttendanceCommand expectedCommand = new CreateAttendanceCommand(tgId, session);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAttendanceCommand.MESSAGE_USAGE);
        // tutorial group id missing
        assertParseFailure(parser, " n/" + session.getSessionName(), expectedMessage);
        // session missing
        assertParseFailure(parser, " tg/" + tgId, expectedMessage);
        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);
    }
}
