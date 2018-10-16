package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.personcommands.FindByPhoneCommand;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindByPhoneCommand object
 */
public class FindByPhoneCommandParserTest {

    private FindByPhoneCommandParser parser = new FindByPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        FindByPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindByPhoneCommand() {
        // no leading and trailing whitespaces
        FindByPhoneCommand expectedFindByPhoneCommand =
                new FindByPhoneCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "98761111")));
        assertParseSuccess(parser, "98765432 98761111", expectedFindByPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 98765432  \t 98761111 \n \t", expectedFindByPhoneCommand);
    }
}
