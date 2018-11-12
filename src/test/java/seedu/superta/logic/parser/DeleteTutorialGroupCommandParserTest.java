package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_NAME;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.DeleteTutorialGroupCommand;

public class DeleteTutorialGroupCommandParserTest {

    private DeleteTutorialGroupCommandParser parser = new DeleteTutorialGroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTutorialGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        String userInput = " " + PREFIX_TUTORIAL_GROUP_NAME + "CS1010-C01";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTutorialGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnDeleteTutorialGroupCommand () {
        String tgToDelete = "group01";
        DeleteTutorialGroupCommand expected = new DeleteTutorialGroupCommand(tgToDelete);

        String userInput = " " + PREFIX_TUTORIAL_GROUP_ID + "group01";
        assertParseSuccess(parser, userInput, expected);
    }
}
