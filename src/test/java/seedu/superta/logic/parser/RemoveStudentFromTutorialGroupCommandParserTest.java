package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.RemoveStudentFromTutorialGroupCommand;
import seedu.superta.model.student.StudentId;

public class RemoveStudentFromTutorialGroupCommandParserTest {
    private RemoveStudentFromTutorialGroupCommandParser parser = new RemoveStudentFromTutorialGroupCommandParser();

    @Test
    public void parse_validArg_success() {
        String userInput = " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "01a " + PREFIX_GENERAL_STUDENT_ID + "A0123456T";
        RemoveStudentFromTutorialGroupCommand expectedCommand = new RemoveStudentFromTutorialGroupCommand(
                "01a",
                new StudentId("A0123456T"));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveStudentFromTutorialGroupCommand.MESSAGE_USAGE);

        //wrong student ID prefix
        String userInput = " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "01a " + PREFIX_STUDENT_ID + "A0123456T";
        assertParseFailure(parser, userInput, expectedMessage);

        //invalid tutorial group prefix
        userInput = " " + PREFIX_TUTORIAL_GROUP_ID + "01a " + PREFIX_GENERAL_STUDENT_ID + "A0123456T";
        assertParseFailure(parser, userInput, expectedMessage);

        //both invalid prefixes
        userInput = " " + PREFIX_TUTORIAL_GROUP_ID + "01a " + PREFIX_STUDENT_ID + "A0123456T";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveStudentFromTutorialGroupCommand.MESSAGE_USAGE);
        String userInput = " ";

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
