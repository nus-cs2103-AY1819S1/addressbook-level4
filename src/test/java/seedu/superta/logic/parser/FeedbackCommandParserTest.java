package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_FEEDBACK;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.FeedbackCommand;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.StudentId;

// @@author triger15
public class FeedbackCommandParserTest {
    private FeedbackCommandParser parser = new FeedbackCommandParser();
    private final String nonEmptyFeedback = "Some feedback.";

    @Test
    public void parse_allFieldsPresent_success() {
        // have feedback
        StudentId amy = new StudentId(VALID_STUDENT_ID_AMY);
        String userInput = "    " + PREFIX_STUDENT_ID + VALID_STUDENT_ID_AMY + " " + PREFIX_FEEDBACK + nonEmptyFeedback;
        FeedbackCommand expectedCommand = new FeedbackCommand(amy, new Feedback(nonEmptyFeedback));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FeedbackCommand.MESSAGE_USAGE);

        // student id + prefix missing
        assertParseFailure(parser, " f/" + nonEmptyFeedback, expectedMessage);
        // feedback + prefix missing
        assertParseFailure(parser, " id/" + VALID_STUDENT_ID_AMY, expectedMessage);
        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);

        // student id prefix present, actual student id missing
        assertParseFailure(parser, " id/ f/" + nonEmptyFeedback, expectedMessage);
        // feedback prefix present, actual feedback missing
        assertParseFailure(parser, " id/" + VALID_STUDENT_ID_AMY + " f/", expectedMessage);
        // student id and feedback prefix present, actual student id and feedback missing
        assertParseFailure(parser, " id/ f/", expectedMessage);
    }
}
