package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.restaurant.logic.commands.menu.FilterMenuCommand;
import seedu.restaurant.model.menu.TagContainsKeywordsPredicate;

//@@author yican95
public class FilterMenuCommandParserTest {

    private FilterMenuCommandParser parser = new FilterMenuCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterMenuCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindItemCommand() {
        // no leading and trailing whitespaces
        FilterMenuCommand expectedFindItemCommand =
                new FilterMenuCommand(new TagContainsKeywordsPredicate(Arrays.asList("Apple", "Burger")));
        assertParseSuccess(parser, "Apple Burger", expectedFindItemCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Apple \n \t Burger  \t", expectedFindItemCommand);
    }

}
