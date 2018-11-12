package seedu.restaurant.logic.parser.account;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.restaurant.logic.commands.account.FindAccountCommand;
import seedu.restaurant.model.account.UsernameContainsKeywordPredicate;

//@@author AZhiKai
public class FindAccountCommandParserTest {

    private FindAccountCommandParser parser = new FindAccountCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindAccountCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindAccountCommand() {
        // no leading and trailing whitespaces
        FindAccountCommand expectedFindAccountCommand =
                new FindAccountCommand(new UsernameContainsKeywordPredicate("Demo"));
        assertParseSuccess(parser, "Demo", expectedFindAccountCommand);

        // multiple whitespaces
        assertParseSuccess(parser, " \n Demo \n \t", expectedFindAccountCommand);
    }
}
