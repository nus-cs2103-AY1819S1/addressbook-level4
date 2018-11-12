package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.ViewAssignmentCommand;
import seedu.superta.model.assignment.Title;

public class ViewAssignmentCommandParserTest {

    private ViewAssignmentCommandParser parser = new ViewAssignmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "      ",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        String invalidCommandResult = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAssignmentCommand
            .MESSAGE_USAGE);
        // wrong prefix
        assertParseFailure(
            parser, PREFIX_TUTORIAL_GROUP_ID + "04a " + PREFIX_GENERAL_ASSIGNMENT_TITLE + "lab1",
                invalidCommandResult);

        // not enough arguments
        assertParseFailure(
            parser, PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a " + PREFIX_GENERAL_ASSIGNMENT_TITLE + "lab1",
                invalidCommandResult);

        // invalid title: special characters
        assertParseFailure(parser, PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a " + PREFIX_GENERAL_ASSIGNMENT_TITLE
            + "*lab1", invalidCommandResult);

        // too many arguments
        assertParseFailure(parser, PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a " + PREFIX_GENERAL_ASSIGNMENT_TITLE
            + "lab1 " + PREFIX_TUTORIAL_GROUP_ID + "04a", invalidCommandResult);
    }

    @Test
    public void parse_validArgs_returnsCommand() {
        String tutorialGroupId = "test";
        Title assignmentTitle = new Title("hello world");
        // no leading and trailing whitespaces
        ViewAssignmentCommand expectedCommand = new ViewAssignmentCommand(
            tutorialGroupId, assignmentTitle
        );
        String userInput = " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tutorialGroupId + " "
            + PREFIX_GENERAL_ASSIGNMENT_TITLE + assignmentTitle.assignmentTitle;
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
