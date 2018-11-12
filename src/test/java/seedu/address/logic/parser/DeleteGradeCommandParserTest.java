package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGradeCommand;

public class DeleteGradeCommandParserTest {
    private DeleteGradesCommandParser parser = new DeleteGradesCommandParser();

    @Test
    public void parseValidIndexValidName() {
        String userInput = "1 Y1819S1_Mid";
        DeleteGradeCommand expectedCommand = new DeleteGradeCommand(INDEX_FIRST_PERSON, "Y1819S1_Mid");
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseInvalidIndexValidName() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE);

        String userInput = "-1 test1";
        assertParseFailure(parser, userInput, expectedMessage);

        userInput = "0 test1";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
