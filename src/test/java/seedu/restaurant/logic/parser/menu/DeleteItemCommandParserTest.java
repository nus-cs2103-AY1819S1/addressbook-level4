package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.restaurant.logic.commands.menu.DeleteItemByIndexCommand;
import seedu.restaurant.logic.commands.menu.DeleteItemByNameCommand;
import seedu.restaurant.logic.commands.menu.DeleteItemCommand;
import seedu.restaurant.model.menu.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteItemCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteItemCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteItemCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemCommand.MESSAGE_USAGE);

    private DeleteItemCommandParser parser = new DeleteItemCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, " ei/7", MESSAGE_INVALID_FORMAT);

        // no index and no ending index specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero starting index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // smaller ending index
        assertParseFailure(parser, "3 ei/2", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validName_returnsDeleteCommand() {
        assertParseSuccess(parser, "Apple Juice", new DeleteItemByNameCommand(new Name("Apple Juice")));
    }

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        // 1 item
        assertParseSuccess(parser, "1", new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_FIRST));

        // multiple items
        assertParseSuccess(parser, "1 ei/3", new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_THIRD));
    }
}
