package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindOccasionCommand;
import seedu.address.model.occasion.OccasionDateContainsKeywordsPredicate;
import seedu.address.model.occasion.OccasionLocationContainsKeywordsPredicate;
import seedu.address.model.occasion.OccasionNameContainsKeywordsPredicate;

public class FindOccasionCommandParserTest {
    private FindOccasionCommandParser parser = new FindOccasionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOccasionCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validArgs_returnsFindOccasionCommand() {
        // no leading and trailing whitespaces
        FindOccasionCommand expectedFindOccasionCommand =
                new FindOccasionCommand(
                        new OccasionDateContainsKeywordsPredicate(Arrays.asList("2018-01-01", "2018-02-01")));
        assertParseSuccess(parser, " od/2018-01-01 2018-02-01", expectedFindOccasionCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " od/ \n 2018-01-01 \n \t 2018-02-01  \t", expectedFindOccasionCommand);

        expectedFindOccasionCommand = new FindOccasionCommand(
                new OccasionNameContainsKeywordsPredicate(Arrays.asList("Christmas", "Hiking")));
        assertParseSuccess(parser, " on/Christmas Hiking", expectedFindOccasionCommand);
        assertParseSuccess(parser, " on/ \n Christmas \n \t Hiking  \t", expectedFindOccasionCommand);

        expectedFindOccasionCommand = new FindOccasionCommand(
                new OccasionLocationContainsKeywordsPredicate(Arrays.asList("UTown", "Mongolia")));
        assertParseSuccess(parser, " loc/UTown Mongolia", expectedFindOccasionCommand);
        assertParseSuccess(parser, " loc/ \n UTown \n \t Mongolia  \t", expectedFindOccasionCommand);

    }
}
