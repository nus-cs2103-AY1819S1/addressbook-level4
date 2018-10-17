package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.inventory.logic.commands.CreateSaleCommand;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;

public class CreateSaleCommandParserTest {
    private CreateSaleCommandParser parser = new CreateSaleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " s/apple q/1",
                new CreateSaleCommand(new Sku("apple"), new Quantity("1")));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSaleCommand.MESSAGE_USAGE);

        // missing sku prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " q/1", expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " s/apple", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, PREAMBLE_WHITESPACE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid sku
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " s/@PPLE q/1", Sku.MESSAGE_SKU_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " s/apple q/ABC", Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, PREAMBLE_WHITESPACE + " s/@PPLE q/ABC", Sku.MESSAGE_SKU_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " s/apple q/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSaleCommand.MESSAGE_USAGE));
    }
}
