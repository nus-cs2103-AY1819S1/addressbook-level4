package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.expense.ExpenseContainsKeywordsPredicate;
import seedu.address.model.expense.Name;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ArgumentMultimap keywordsMap = ArgumentTokenizer.tokenize(" n/Alice Bob", PREFIX_NAME);
        FindCommand expectedFindCommand =
                new FindCommand(new ExpenseContainsKeywordsPredicate(keywordsMap));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseFailure(parser, " n/ \n Alice \n \t Bob  \t",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_NAME_CONSTRAINTS));

        // no prefix
        assertParseFailure(parser, " Alice Bob", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
