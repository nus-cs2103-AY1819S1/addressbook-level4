package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_ITEM_PERCENT_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.ITEM_PERCENT_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_PERCENT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.menu.DiscountItemCommand;
import seedu.restaurant.model.menu.Price;

//@@author yican95
public class DiscountItemCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DiscountItemCommand.MESSAGE_USAGE);

    private DiscountItemCommandParser parser = new DiscountItemCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index|ALL specified
        assertParseFailure(parser, ITEM_PERCENT_DESC, MESSAGE_INVALID_FORMAT);

        // no percent specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index|ALL and no percent specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ITEM_PERCENT_DESC, MESSAGE_INVALID_FORMAT);

        // zero starting index
        assertParseFailure(parser, "0" + ITEM_PERCENT_DESC, MESSAGE_INVALID_FORMAT);

        // smaller ending index
        assertParseFailure(parser, "3 ei/2" + ITEM_PERCENT_DESC, MESSAGE_INVALID_FORMAT);

        // invalid ending index
        assertParseFailure(parser, "3 ei/some string" + ITEM_PERCENT_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPercent_failure() {
        // invalid percent
        assertParseFailure(parser, "1" + INVALID_ITEM_PERCENT_DESC,
                Price.MESSAGE_PERCENT_CONSTRAINTS);
    }

    @Test
    public void parse_validIndexFollowedByInvalidPercent_failure() {
        // only starting index specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_ITEM_PERCENT_DESC;
        assertParseFailure(parser, userInput, Price.MESSAGE_PERCENT_CONSTRAINTS);

        // both index specified
        Index endingIndex = INDEX_SECOND;
        userInput = targetIndex.getOneBased() + " ei/" + endingIndex.getOneBased() + INVALID_ITEM_PERCENT_DESC;
        assertParseFailure(parser, userInput, Price.MESSAGE_PERCENT_CONSTRAINTS);
    }

    @Test
    public void parse_validAllFollowedByInvalidPercent_failure() {
        String userInput = "ALL" + INVALID_ITEM_PERCENT_DESC;
        assertParseFailure(parser, userInput, Price.MESSAGE_PERCENT_CONSTRAINTS);
    }

    @Test
    public void parse_validIndexFollowedByValidPercent_success() {
        // Only starting index specified
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + ITEM_PERCENT_DESC;

        DiscountItemCommand expectedCommand = new DiscountItemCommand(targetIndex, targetIndex,
                Double.parseDouble(VALID_ITEM_PERCENT), false);

        assertParseSuccess(parser, userInput, expectedCommand);

        // both index specified
        Index endingIndex = INDEX_THIRD;
        userInput = targetIndex.getOneBased() + " ei/" + endingIndex.getOneBased() + ITEM_PERCENT_DESC;

        expectedCommand = new DiscountItemCommand(targetIndex, endingIndex,
                Double.parseDouble(VALID_ITEM_PERCENT), false);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validAllFollowedByValidPercent_success() {
        String userInput = "ALL" + ITEM_PERCENT_DESC;

        DiscountItemCommand expectedCommand = new DiscountItemCommand(INDEX_FIRST, INDEX_FIRST,
                Double.parseDouble(VALID_ITEM_PERCENT), true);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
