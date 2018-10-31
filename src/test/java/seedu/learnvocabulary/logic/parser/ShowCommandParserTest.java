package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_ENTER_WORD;
import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.learnvocabulary.logic.commands.FindCommand;
import seedu.learnvocabulary.logic.commands.ShowCommand;
import seedu.learnvocabulary.model.word.NameContainsKeywordsPredicate;

public class ShowCommandParserTest {

    private ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_ENTER_WORD,
                ShowCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsShowCommand() {
        // no leading and trailing whitespaces
        ShowCommand expectedShowCommand =
                new ShowCommand(new NameContainsKeywordsPredicate(Arrays.asList("fire", "fly")));
        assertParseSuccess(parser, "fire fly", expectedShowCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n fire \n \t fly  \t", expectedShowCommand);
    }

}
