package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindEmailCommand;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

public class FindEmailCommandParserTest {

    private FindEmailCommandParser parser = new FindEmailCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindEmailCommand expectedFindCommand =
                new FindEmailCommand(new EmailContainsKeywordsPredicate(Arrays.asList("daniel@example.com")));
        assertParseSuccess(parser, "daniel@example.com", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n daniel@example.com \n \t   \t", expectedFindCommand);
    }
}
