package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import org.junit.Test;

import seedu.address.logic.commands.SelectRestaurantCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectRestaurantCommandParserTest {

    private SelectRestaurantCommandParser parser = new SelectRestaurantCommandParser();

    @Test
    public void parse_validArgs_returnsSelectRestaurantCommand() {
        assertParseSuccess(parser, "1", new SelectRestaurantCommand(INDEX_FIRST_RESTAURANT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectRestaurantCommand.MESSAGE_USAGE));
    }
}
