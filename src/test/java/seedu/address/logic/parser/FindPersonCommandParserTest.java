package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonModuleCodeContainsKeywordsPredicate;
import seedu.address.model.person.PersonOccasionNameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

public class FindPersonCommandParserTest {
    private FindPersonCommandParser parser = new FindPersonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsFindPersonCommand() {
        // no leading and trailing whitespaces
        FindPersonCommand expectedFindPersonCommand =
                new FindPersonCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindPersonCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindPersonCommand);

        expectedFindPersonCommand = new FindPersonCommand(
                new PhoneContainsKeywordsPredicate(Arrays.asList("95352563", "9482224")));
        assertParseSuccess(parser, " p/95352563 9482224", expectedFindPersonCommand);
        assertParseSuccess(parser, " p/ \n 95352563 \n \t 9482224  \t", expectedFindPersonCommand);

        expectedFindPersonCommand = new FindPersonCommand(
                new EmailContainsKeywordsPredicate(Arrays.asList("heinz@example.com", "werner@example.com")));
        assertParseSuccess(parser, " e/heinz@example.com werner@example.com", expectedFindPersonCommand);
        assertParseSuccess(parser, " e/ \n heinz@example.com \n \t werner@example.com  \t", expectedFindPersonCommand);

        expectedFindPersonCommand = new FindPersonCommand(
                new AddressContainsKeywordsPredicate(Arrays.asList("wall", "michegan")));
        assertParseSuccess(parser, " a/wall michegan", expectedFindPersonCommand);
        assertParseSuccess(parser, " a/ \n wall \n \t michegan  \t", expectedFindPersonCommand);

        expectedFindPersonCommand = new FindPersonCommand(
                new PersonModuleCodeContainsKeywordsPredicate(Arrays.asList("cs2103", "cs2103t")));
        assertParseSuccess(parser, " mc/cs2103 cs2103t", expectedFindPersonCommand);
        assertParseSuccess(parser, " mc/ \n cs2103 \n \t cs2103t  \t", expectedFindPersonCommand);

        expectedFindPersonCommand = new FindPersonCommand(
                new PersonOccasionNameContainsKeywordsPredicate(Arrays.asList("project", "meeting")));
        assertParseSuccess(parser, " on/project meeting", expectedFindPersonCommand);
        assertParseSuccess(parser, " on/ \n project \n \t meeting  \t", expectedFindPersonCommand);
    }
}
