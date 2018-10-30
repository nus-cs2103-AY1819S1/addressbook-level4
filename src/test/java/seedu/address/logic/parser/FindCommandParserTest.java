package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.wish.WishContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "apple",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-e",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, "p/22.30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        /* isExactMatch set to false */

        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new WishContainsKeywordsPredicate(Arrays.asList("Water", "Balloon"),
                        Arrays.asList("family", "important"), Arrays.asList("Visa", "must do"), false));
        assertParseSuccess(parser, " n/Water n/Balloon t/family t/important r/Visa r/must do", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/Water \n n/Balloon          "
                + "t/family \n t/important   \n r/Visa      r/must do", expectedFindCommand);

        // No tags keywords
        expectedFindCommand =
                new FindCommand(new WishContainsKeywordsPredicate(Arrays.asList("Water", "Balloon"),
                        Arrays.asList(), Arrays.asList("Visa", "must do"), false));
        assertParseSuccess(parser, " n/Water n/Balloon r/Visa r/must do", expectedFindCommand);

        // No remark keywords
        expectedFindCommand =
                new FindCommand(new WishContainsKeywordsPredicate(Arrays.asList("Water", "Balloon"),
                        Arrays.asList("family", "important"), Arrays.asList(), false));
        assertParseSuccess(parser, " n/Water n/Balloon t/family t/important", expectedFindCommand);

        // No name keywords
        expectedFindCommand =
                new FindCommand(new WishContainsKeywordsPredicate(Arrays.asList(),
                        Arrays.asList("family", "important"), Arrays.asList("Visa", "must do"), false));
        assertParseSuccess(parser, " t/family t/important r/Visa r/must do", expectedFindCommand);

        // invalid preamble
        expectedFindCommand =
                new FindCommand(new WishContainsKeywordsPredicate(Arrays.asList("Water", "Balloon"),
                        Arrays.asList("family", "important"), Arrays.asList("Visa", "must do"), false));
        assertParseSuccess(parser, " dsfas;dkfjsd n/Water n/Balloon t/family t/important r/Visa r/must do",
                expectedFindCommand);

        // valid preamble
        expectedFindCommand =
                new FindCommand(new WishContainsKeywordsPredicate(Arrays.asList("Water", "Balloon"),
                        Arrays.asList("family", "important"), Arrays.asList("Visa", "must do"), true));
        assertParseSuccess(parser, " -e n/Water n/Balloon t/family t/important r/Visa r/must do", expectedFindCommand);

        // jumbled up order of prefixes
        expectedFindCommand =
                new FindCommand(new WishContainsKeywordsPredicate(Arrays.asList("Water", "Balloon"),
                        Arrays.asList("family", "important"), Arrays.asList("Visa", "must do"), false));
        assertParseSuccess(parser, " n/Water r/Visa t/family n/Balloon  t/important r/must do", expectedFindCommand);

    }

}
