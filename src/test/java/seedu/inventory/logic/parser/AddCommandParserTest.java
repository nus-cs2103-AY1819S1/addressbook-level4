package seedu.inventory.logic.parser;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.inventory.logic.commands.CommandTestUtil.IMAGE_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.IMAGE_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_IMAGE_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_SKU_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.NAME_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.NAME_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.inventory.logic.commands.CommandTestUtil.PRICE_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.PRICE_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.QUANTITY_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.QUANTITY_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.TAG_DESC_GADGET;
import static seedu.inventory.logic.commands.CommandTestUtil.TAG_DESC_SMARTPHONE;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PRICE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_GADGET;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.inventory.testutil.TypicalItems.OPPO;
import static seedu.inventory.testutil.TypicalItems.SONY;

import org.junit.Test;

import seedu.inventory.logic.commands.AddCommand;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.tag.Tag;
import seedu.inventory.testutil.ItemBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Item expectedItem = new ItemBuilder(SONY).withTags(VALID_TAG_GADGET).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_SONY + PRICE_DESC_SONY
                + QUANTITY_DESC_SONY + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET,
                new AddCommand(expectedItem));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_OPPO + NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY
                + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET, new AddCommand(expectedItem));

        // multiple prices - last price accepted
        assertParseSuccess(parser, NAME_DESC_SONY + PRICE_DESC_OPPO + PRICE_DESC_SONY + QUANTITY_DESC_SONY
                + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET, new AddCommand(expectedItem));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_OPPO
                + QUANTITY_DESC_SONY + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET,
                new AddCommand(expectedItem));

        // multiple SKUs - last SKU accepted
        assertParseSuccess(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_OPPO
                + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_GADGET, new AddCommand(expectedItem));

        // multiple images - last inventory accepted
        assertParseSuccess(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + IMAGE_DESC_OPPO + IMAGE_DESC_SONY + TAG_DESC_GADGET, new AddCommand(expectedItem));

        // multiple tags - all accepted
        Item expectedItemMultipleTags = new ItemBuilder(SONY).withTags(VALID_TAG_GADGET, VALID_TAG_SMARTPHONE)
                .build();
        assertParseSuccess(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + IMAGE_DESC_SONY + TAG_DESC_SMARTPHONE + TAG_DESC_GADGET, new AddCommand(expectedItemMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Item expectedItem = new ItemBuilder(OPPO).withTags().build();
        assertParseSuccess(parser, NAME_DESC_OPPO + PRICE_DESC_OPPO + QUANTITY_DESC_OPPO + SKU_DESC_OPPO
                + IMAGE_DESC_OPPO, new AddCommand(expectedItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + IMAGE_DESC_SONY, expectedMessage);

        // missing price prefix
        assertParseFailure(parser, NAME_DESC_SONY + VALID_PRICE_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + IMAGE_DESC_SONY, expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, NAME_DESC_SONY + PRICE_DESC_SONY + VALID_QUANTITY_SONY + SKU_DESC_SONY
                + IMAGE_DESC_SONY, expectedMessage);

        // missing SKU prefix
        assertParseFailure(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + VALID_SKU_SONY
                + IMAGE_DESC_SONY, expectedMessage);

        // missing image prefix
        assertParseFailure(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + VALID_IMAGE_SONY, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_SONY + VALID_PRICE_SONY + VALID_QUANTITY_SONY + VALID_SKU_SONY
                + VALID_IMAGE_SONY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + IMAGE_DESC_SONY + TAG_DESC_SMARTPHONE + TAG_DESC_GADGET, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, NAME_DESC_SONY + INVALID_PRICE_DESC + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + IMAGE_DESC_SONY + TAG_DESC_SMARTPHONE + TAG_DESC_GADGET, Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, NAME_DESC_SONY + PRICE_DESC_SONY + INVALID_QUANTITY_DESC + SKU_DESC_SONY
                + IMAGE_DESC_SONY + TAG_DESC_SMARTPHONE + TAG_DESC_GADGET, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        // invalid SKU
        assertParseFailure(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + INVALID_SKU_DESC
                + IMAGE_DESC_SONY + TAG_DESC_SMARTPHONE + TAG_DESC_GADGET, Sku.MESSAGE_SKU_CONSTRAINTS);

        // invalid image
        assertParseFailure(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + INVALID_IMAGE_DESC + TAG_DESC_SMARTPHONE + TAG_DESC_GADGET, Image.MESSAGE_IMAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + IMAGE_DESC_SONY + INVALID_TAG_DESC + VALID_TAG_GADGET, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PRICE_DESC_SONY + QUANTITY_DESC_SONY + SKU_DESC_SONY
                + INVALID_IMAGE_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_SONY + PRICE_DESC_SONY + QUANTITY_DESC_SONY
                + SKU_DESC_SONY + IMAGE_DESC_SONY + TAG_DESC_SMARTPHONE + TAG_DESC_GADGET,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
