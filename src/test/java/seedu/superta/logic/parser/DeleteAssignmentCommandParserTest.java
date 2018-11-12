package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.DeleteAssignmentCommand;

public class DeleteAssignmentCommandParserTest {

    private DeleteAssignmentCommandParser parser = new DeleteAssignmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,
                "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        //wrong tutorial prefix
        assertParseFailure(parser,
                " " + PREFIX_TUTORIAL_GROUP_ID + "T01 " + PREFIX_GENERAL_ASSIGNMENT_TITLE + "Lab 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE));

        //wrong assignment prefix
        assertParseFailure(parser,
                " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "T01 " + PREFIX_ASSIGNMENT_TITLE + "Lab 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE));

        //wrong tutorial and assignment prefix
        assertParseFailure(parser,
                " " + PREFIX_TUTORIAL_GROUP_ID + "T01 " + PREFIX_ASSIGNMENT_TITLE + "Lab 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnDeleteAssignmentCommand() {
        String assignment = "Lab 1";
        String tg = "T01";
        DeleteAssignmentCommand expected = new DeleteAssignmentCommand(assignment, tg);

        String arguments = " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tg + " "
                + PREFIX_GENERAL_ASSIGNMENT_TITLE + assignment;
        assertParseSuccess(parser , arguments, expected);

        arguments = " " + PREFIX_GENERAL_ASSIGNMENT_TITLE + assignment + " "
                + PREFIX_GENERAL_TUTORIAL_GROUP_ID + tg;
        assertParseSuccess(parser , arguments, expected);
    }
}
