package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NEW_MAX_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.CreateAssignmentCommand;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Title;

public class CreateAssignmentCommandParserTest {
    private CreateAssignmentCommandParser parser = new CreateAssignmentCommandParser();

    @Test
    public void parse_validArg_success() {
        String userInput = " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "01a "
                + PREFIX_ASSIGNMENT_TITLE + "Lab1 "
                + PREFIX_ASSIGNMENT_MAX_MARKS + "100";
        CreateAssignmentCommand expectedCommand = new CreateAssignmentCommand(
                "01a",
                new Assignment(
                        new Title("Lab1"),
                        100d));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAssignmentCommand.MESSAGE_USAGE);

        //invalid tutorial group prefix
        String userInput = " " + PREFIX_TUTORIAL_GROUP_ID + "01a "
                + PREFIX_ASSIGNMENT_TITLE + "Lab1 "
                + PREFIX_ASSIGNMENT_MAX_MARKS + "100";
        assertParseFailure(parser, userInput, expectedMessage);

        //invalid assignment title prefix
        userInput = " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "01a "
                + PREFIX_GENERAL_ASSIGNMENT_TITLE + "Lab1 "
                + PREFIX_ASSIGNMENT_MAX_MARKS + "100";
        assertParseFailure(parser, userInput, expectedMessage);

        //invalid assignment max marks prefix
        userInput = " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "01a "
                + PREFIX_ASSIGNMENT_TITLE + "Lab1 "
                + PREFIX_ASSIGNMENT_NEW_MAX_MARKS + "100";
        assertParseFailure(parser, userInput, expectedMessage);

        //invalid prefixes
        userInput = " " + PREFIX_TUTORIAL_GROUP_ID + "01a "
                + PREFIX_GENERAL_ASSIGNMENT_TITLE + "Lab1 "
                + PREFIX_ASSIGNMENT_NEW_MAX_MARKS + "100";
        assertParseFailure(parser, userInput, expectedMessage);

    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateAssignmentCommand.MESSAGE_USAGE);
        String userInput = " ";

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
