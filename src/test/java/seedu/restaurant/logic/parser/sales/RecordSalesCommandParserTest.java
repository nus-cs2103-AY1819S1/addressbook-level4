package seedu.restaurant.logic.parser.sales;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_DATE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_ITEM_NAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_PRICE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_QUANTITY_SOLD;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_DATE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_ITEM_NAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_PRICE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_QUANTITY_SOLD;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.sales.RecordBuilder.DEFAULT_DATE;
import static seedu.restaurant.testutil.sales.RecordBuilder.DEFAULT_ITEM_NAME;
import static seedu.restaurant.testutil.sales.RecordBuilder.DEFAULT_PRICE;
import static seedu.restaurant.testutil.sales.RecordBuilder.DEFAULT_QUANTITY_SOLD;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_ONE;

import org.junit.Test;

import seedu.restaurant.logic.commands.sales.RecordSalesCommand;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.testutil.sales.RecordBuilder;

//@@author HyperionNKJ
public class RecordSalesCommandParserTest {
    private RecordSalesCommandParser parser = new RecordSalesCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        SalesRecord expectedRecord = new RecordBuilder(RECORD_ONE).build();
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PREFIX_WITH_VALID_DATE
                + PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_PRICE,
                new RecordSalesCommand(expectedRecord));
        // whitespace only
        assertParseSuccess(parser, PREFIX_WITH_VALID_DATE
                        + PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_PRICE,
                new RecordSalesCommand(expectedRecord));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordSalesCommand.MESSAGE_USAGE);
        // missing date
        assertParseFailure(parser, PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_VALID_QUANTITY_SOLD
                + PREFIX_WITH_VALID_PRICE, expectedMessage);
        // missing item name
        assertParseFailure(parser, PREFIX_WITH_VALID_DATE
                + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_PRICE, expectedMessage);
        // missing quantity sold
        assertParseFailure(parser, PREFIX_WITH_VALID_DATE
                + PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_VALID_PRICE, expectedMessage);
        // missing price
        assertParseFailure(parser, PREFIX_WITH_VALID_DATE
                + PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_VALID_QUANTITY_SOLD, expectedMessage);
        // empty input
        assertParseFailure(parser, "", expectedMessage);
        // missing 1 prefix
        assertParseFailure(parser, PREFIX_WITH_VALID_DATE
                + PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_VALID_QUANTITY_SOLD + DEFAULT_PRICE, expectedMessage);
        // all prefixes missing
        assertParseFailure(parser, " " + DEFAULT_DATE + " " + DEFAULT_ITEM_NAME + " " + DEFAULT_QUANTITY_SOLD
                + " " + DEFAULT_PRICE, expectedMessage);
    }
    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser,
                PREFIX_WITH_INVALID_DATE + PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_VALID_QUANTITY_SOLD
                        + PREFIX_WITH_VALID_PRICE, Date.MESSAGE_DATE_CONSTRAINTS);
        // invalid item name
        assertParseFailure(parser,
                PREFIX_WITH_VALID_DATE + PREFIX_WITH_INVALID_ITEM_NAME + PREFIX_WITH_VALID_QUANTITY_SOLD
                        + PREFIX_WITH_VALID_PRICE, ItemName.MESSAGE_NAME_CONSTRAINTS);
        // invalid quantity sold
        assertParseFailure(parser,
                PREFIX_WITH_VALID_DATE + PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_INVALID_QUANTITY_SOLD
                        + PREFIX_WITH_VALID_PRICE, QuantitySold.MESSAGE_QUANTITY_CONSTRAINTS);
        // invalid price
        assertParseFailure(parser,
                PREFIX_WITH_VALID_DATE + PREFIX_WITH_VALID_ITEM_NAME + PREFIX_WITH_VALID_QUANTITY_SOLD
                        + PREFIX_WITH_INVALID_PRICE, Price.MESSAGE_PRICE_CONSTRAINTS);
        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                PREFIX_WITH_VALID_DATE + PREFIX_WITH_INVALID_ITEM_NAME + PREFIX_WITH_INVALID_QUANTITY_SOLD
                        + PREFIX_WITH_VALID_PRICE,
                ItemName.MESSAGE_NAME_CONSTRAINTS);
        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + PREFIX_WITH_VALID_DATE + PREFIX_WITH_VALID_ITEM_NAME
                        + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_PRICE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RecordSalesCommand.MESSAGE_USAGE));
    }
}
