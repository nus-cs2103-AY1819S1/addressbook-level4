package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.learnvocabulary.logic.commands.FindCommand;
import seedu.learnvocabulary.model.word.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("fire", "fly")));
        assertParseSuccess(parser, "fire fly", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n fire \n \t fly  \t", expectedFindCommand);
    }

}
