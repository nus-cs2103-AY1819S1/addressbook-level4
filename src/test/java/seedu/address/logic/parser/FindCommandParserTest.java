package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.model.person.util.PersonNameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefixUsage_defaultToAllPrefixBehaviour() {
        // no leading and trailing whitespaces
        FindPersonCommand expectedFindPersonCommand =
                new FindPersonCommand(new PersonNameContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"), Collections.emptyList(), Collections.emptyList()));
        assertParseSuccess(parser, "Alice Bob", expectedFindPersonCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindPersonCommand);
    }

    @Test
    public void parse_prefixUsage() {
        // no leading and trailing whitespaces
        FindPersonCommand expectedFindPersonCommand =
                new FindPersonCommand(new PersonNameContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"),
                        Arrays.asList("Charlie", "David"),
                        Arrays.asList("Earl", "Grey")));
        assertParseSuccess(parser, " a/Alice Bob s/Charlie David n/Earl Grey", expectedFindPersonCommand);
    }

    /**
     * Parses {@code userInput} into a {@code PersonNameContainsKeywordsPredicate}.
     */
    private PersonNameContainsKeywordsPredicate preparePredicate(String userInputForAllPrefix, String userInputForSomePrefix,
                                                                 String userInputForNonePrefix) {
        return new PersonNameContainsKeywordsPredicate(Arrays.asList(userInputForAllPrefix.split("\\s+")),
                Arrays.asList(userInputForSomePrefix.split("\\s+")),
                Arrays.asList(userInputForNonePrefix.split("\\s+")));
    }
}
