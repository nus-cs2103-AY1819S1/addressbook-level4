package seedu.inventory.logic.parser.purchaseorder;

import static seedu.inventory.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_REQUIRED_DATE_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_SKU_DESC;
import static seedu.inventory.logic.commands.CommandTestUtil.INVALID_SUPPLIER_DESC;

import static seedu.inventory.logic.commands.CommandTestUtil.NAME_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.NAME_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.inventory.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.inventory.logic.commands.CommandTestUtil.QUANTITY_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.QUANTITY_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.REQUIRED_DATE_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.REQUIRED_DATE_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.SKU_DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.SUPPLIER_DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.SUPPLIER_DESC_SONY;

import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_REQUIRED_DATE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SUPPLIER_SONY;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.inventory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.SONYPO;

import org.junit.Test;

import seedu.inventory.logic.commands.purchaseorder.GeneratePurchaseOrderCommand;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;
import seedu.inventory.testutil.purchaseorder.PurchaseOrderBuilder;

public class GeneratePurchaseOrderParserTest {
    private GeneratePurchaseOrderCommandParser parser = new GeneratePurchaseOrderCommandParser();

    /**
     * TODO: Fix Bug
     */
    //@Test
    public void parse_allFieldsPresent_success() {
        PurchaseOrder expectedPurchaseOrder = new PurchaseOrderBuilder(SONYPO).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                        + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY,
                new GeneratePurchaseOrderCommand(expectedPurchaseOrder));

        // multiple SKUs - last SKU accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SKU_DESC_SONY + SKU_DESC_OPPO + NAME_DESC_SONY
                + QUANTITY_DESC_SONY + REQUIRED_DATE_DESC_SONY
                + SUPPLIER_DESC_SONY, new GeneratePurchaseOrderCommand(expectedPurchaseOrder));

        // multiple names - last name accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SKU_DESC_SONY + NAME_DESC_SONY + NAME_DESC_OPPO
                + QUANTITY_DESC_SONY + REQUIRED_DATE_DESC_SONY
                + SUPPLIER_DESC_SONY, new GeneratePurchaseOrderCommand(expectedPurchaseOrder));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + QUANTITY_DESC_OPPO + REQUIRED_DATE_DESC_SONY
                + SUPPLIER_DESC_SONY, new GeneratePurchaseOrderCommand(expectedPurchaseOrder));


        // multiple required date - last date accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + REQUIRED_DATE_DESC_OPPO
                + SUPPLIER_DESC_SONY, new GeneratePurchaseOrderCommand(expectedPurchaseOrder));

        // multiple supplier - last supplier accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY
                + SUPPLIER_DESC_OPPO, new GeneratePurchaseOrderCommand(expectedPurchaseOrder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GeneratePurchaseOrderCommand.MESSAGE_USAGE);

        // missing sku prefix
        assertParseFailure(parser, VALID_SKU_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, SKU_DESC_SONY + VALID_NAME_SONY + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY, expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, SKU_DESC_SONY + NAME_DESC_SONY + VALID_QUANTITY_SONY
                + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY, expectedMessage);

        // missing required date prefix
        assertParseFailure(parser, SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + VALID_REQUIRED_DATE_SONY + SUPPLIER_DESC_SONY, expectedMessage);

        // missing supplier prefix
        assertParseFailure(parser, SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + VALID_SUPPLIER_SONY, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_SKU_SONY + VALID_NAME_SONY + VALID_QUANTITY_SONY
                + VALID_REQUIRED_DATE_SONY + VALID_SUPPLIER_SONY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid SKU
        assertParseFailure(parser, INVALID_SKU_DESC + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY, Sku.MESSAGE_SKU_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, SKU_DESC_SONY + INVALID_NAME_DESC + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, SKU_DESC_SONY + NAME_DESC_SONY + INVALID_QUANTITY_DESC
                + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);


        // invalid required date
        assertParseFailure(parser, SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + INVALID_REQUIRED_DATE_DESC + SUPPLIER_DESC_SONY, RequiredDate.MESSAGE_DATE_CONSTRAINTS);

        // invalid supplier
        assertParseFailure(parser, SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + INVALID_SUPPLIER_DESC, Supplier.MESSAGE_SUPPLIER_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_SKU_DESC + INVALID_NAME_DESC + QUANTITY_DESC_SONY
                + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY, Sku.MESSAGE_SKU_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + SKU_DESC_SONY + NAME_DESC_SONY + QUANTITY_DESC_SONY
                        + REQUIRED_DATE_DESC_SONY + SUPPLIER_DESC_SONY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GeneratePurchaseOrderCommand.MESSAGE_USAGE));
    }


}
