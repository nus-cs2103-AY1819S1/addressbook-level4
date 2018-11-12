package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindDepartmentCommand;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

public class FindDepartmentCommandParserTest {

    private FindDepartmentCommandParser parser = new FindDepartmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDepartmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindDepartmentCommand expectedFindCommand =
                new FindDepartmentCommand(new DepartmentContainsKeywordsPredicate(Arrays.asList("Accounting", "Tech")));
        assertParseSuccess(parser, "Accounting Tech", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Accounting \n \t Tech  \t", expectedFindCommand);
    }

}
