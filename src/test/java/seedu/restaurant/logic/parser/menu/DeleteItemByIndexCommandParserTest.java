package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.restaurant.logic.commands.menu.DeleteItemByIndexCommand;

//@@author yican95
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteItemByIndexCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteItemByIndexCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteItemByIndexCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemByIndexCommand.MESSAGE_USAGE);

    private DeleteItemByIndexCommandParser parser = new DeleteItemByIndexCommandParser();

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

        // invalid ending index
        assertParseFailure(parser, "3 ei/some string", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_returnsDeleteCommand() {
        // 1 item
        assertParseSuccess(parser, "1", new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_FIRST));

        // multiple items
        assertParseSuccess(parser, "1 ei/3", new DeleteItemByIndexCommand(INDEX_FIRST, INDEX_THIRD));
    }
}
