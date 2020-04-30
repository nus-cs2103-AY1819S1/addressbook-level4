package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.restaurant.logic.commands.menu.DeleteItemByNameCommand;
import seedu.restaurant.model.menu.Name;

//@@author yican95
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteItemByNameCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteItemByNameCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteItemByNameCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteItemByNameCommand.MESSAGE_USAGE);

    private DeleteItemByNameCommandParser parser = new DeleteItemByNameCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidName_failure() {
        // non alphanumeric
        assertParseFailure(parser, "-9", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validName_returnsDeleteCommand() {
        assertParseSuccess(parser, "Apple Juice", new DeleteItemByNameCommand(new Name("Apple Juice")));
    }
}
