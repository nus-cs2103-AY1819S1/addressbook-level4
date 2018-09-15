package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCCommand;
import seedu.address.model.card.QuestionContainsKeywordsPredicate;

public class FindCCommandParserTest {

    private FindCCommandParser parser = new FindCCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCCommand expectedFindCommand =
                new FindCCommand(new QuestionContainsKeywordsPredicate(Arrays.asList("Why", "earth")));
        assertParseSuccess(parser, "Why earth", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Why \n \t earth  \t", expectedFindCommand);
    }
}
