package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.superta.logic.commands.MarkAttendanceCommand;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.StudentId;

// @@author triger15
public class MarkAttendanceCommandParserTest {
    private MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();
    private final String tgId = "01a";
    private final Session session = new Session("Lab 1");
    private Set<StudentId> idSet;

    @Before
    public void setUp() {
        idSet = new HashSet<>();
        idSet.add(new StudentId(VALID_STUDENT_ID_AMY));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = "    " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tgId + " "
                + PREFIX_SESSION_NAME + session.getSessionName() + " "
                + PREFIX_GENERAL_STUDENT_ID + VALID_STUDENT_ID_AMY;
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(tgId, session, idSet);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE);
        // tutorial group id missing
        assertParseFailure(parser, " n/" + session.getSessionName() + " st/" + VALID_STUDENT_ID_AMY,
                expectedMessage);
        // session missing
        assertParseFailure(parser, " tg/" + tgId + " st/" + VALID_STUDENT_ID_AMY, expectedMessage);
        // student ids missing
        assertParseFailure(parser, " n/" + session.getSessionName() + " tg/" + tgId, expectedMessage);
        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);
    }
}
