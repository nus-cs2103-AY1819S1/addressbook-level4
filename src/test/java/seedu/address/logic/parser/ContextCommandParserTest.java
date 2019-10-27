package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ContextCommand;

public class ContextCommandParserTest {

    private ContextCommandParser parser = new ContextCommandParser();

    @Test
    public void parse_expressionPresent_success() {

        String singleWordExpression = "orb";
        assertParseSuccess(parser, singleWordExpression, new ContextCommand(singleWordExpression));

        String multiWordExpression = "nussu exco member";
        assertParseSuccess(parser, multiWordExpression, new ContextCommand(multiWordExpression));

        String multiWordWithPaddedWhiteSpace = "    ug research opps prog    ";
        assertParseSuccess(parser, multiWordWithPaddedWhiteSpace,
                new ContextCommand(multiWordWithPaddedWhiteSpace.trim()));
    }

    @Test
    public void parse_emptyExpression_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ContextCommand.MESSAGE_USAGE);

        String emptyString = "";
        assertParseFailure(parser, emptyString, expectedMessage);

        String onlyWhitespace = "    ";
        assertParseFailure(parser, onlyWhitespace, expectedMessage);

    }

}
