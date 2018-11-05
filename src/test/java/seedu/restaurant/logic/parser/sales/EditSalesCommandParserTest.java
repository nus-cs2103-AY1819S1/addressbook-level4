package seedu.restaurant.logic.parser.sales;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_DATE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_ITEM_NAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_PRICE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_INVALID_QUANTITY_SOLD;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_DATE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_DATE_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_ITEM_NAME;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_ITEM_NAME_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_PRICE;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_PRICE_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_QUANTITY_SOLD;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREFIX_WITH_VALID_QUANTITY_SOLD_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_NAME_RECORD_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PRICE_RECORD_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PRICE_RECORD_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_RECORD_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_QUANTITY_SOLD_RECORD_TWO;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.sales.EditSalesCommand;
import seedu.restaurant.logic.commands.sales.EditSalesCommand.EditRecordDescriptor;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.testutil.sales.EditRecordDescriptorBuilder;

public class EditSalesCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSalesCommand.MESSAGE_USAGE);

    private EditSalesCommandParser parser = new EditSalesCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ITEM_NAME_RECORD_ONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditSalesCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-2" + PREFIX_WITH_VALID_ITEM_NAME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_WITH_VALID_ITEM_NAME, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + PREFIX_WITH_INVALID_ITEM_NAME, ItemName.MESSAGE_NAME_CONSTRAINTS);
        // invalid date
        assertParseFailure(parser, "1" + PREFIX_WITH_INVALID_DATE, Date.MESSAGE_DATE_CONSTRAINTS);
        // invalid quantity sold
        assertParseFailure(parser, "1" + PREFIX_WITH_INVALID_QUANTITY_SOLD,
                QuantitySold.MESSAGE_QUANTITY_CONSTRAINTS);
        // invalid price
        assertParseFailure(parser, "1" + PREFIX_WITH_INVALID_PRICE, Price.MESSAGE_PRICE_CONSTRAINTS);

        // invalid date followed by valid quantity sold
        assertParseFailure(parser, "1" + PREFIX_WITH_INVALID_DATE + PREFIX_WITH_VALID_QUANTITY_SOLD,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PREFIX_WITH_VALID_DATE + PREFIX_WITH_INVALID_DATE,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + PREFIX_WITH_INVALID_DATE + PREFIX_WITH_INVALID_ITEM_NAME
                        + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_PRICE, Date.MESSAGE_DATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PREFIX_WITH_VALID_DATE + PREFIX_WITH_VALID_ITEM_NAME
                + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_PRICE;

        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder()
                .withDate(VALID_DATE_RECORD_ONE)
                .withName(VALID_ITEM_NAME_RECORD_ONE)
                .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_ONE)
                .withPrice(VALID_PRICE_RECORD_ONE)
                .build();
        EditSalesCommand expectedCommand = new EditSalesCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PREFIX_WITH_VALID_DATE + PREFIX_WITH_VALID_QUANTITY_SOLD;

        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withDate(VALID_DATE_RECORD_ONE)
                .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_ONE).build();
        EditSalesCommand expectedCommand = new EditSalesCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // date
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + PREFIX_WITH_VALID_DATE;
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withDate(VALID_DATE_RECORD_ONE).build();
        EditSalesCommand expectedCommand = new EditSalesCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // name
        userInput = targetIndex.getOneBased() + PREFIX_WITH_VALID_ITEM_NAME;
        descriptor = new EditRecordDescriptorBuilder().withName(VALID_ITEM_NAME_RECORD_ONE).build();
        expectedCommand = new EditSalesCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity sold
        userInput = targetIndex.getOneBased() + PREFIX_WITH_VALID_QUANTITY_SOLD;
        descriptor = new EditRecordDescriptorBuilder().withQuantitySold(VALID_QUANTITY_SOLD_RECORD_ONE).build();
        expectedCommand = new EditSalesCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + PREFIX_WITH_VALID_PRICE;
        descriptor = new EditRecordDescriptorBuilder().withPrice(VALID_PRICE_RECORD_ONE).build();
        expectedCommand = new EditSalesCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput =
                targetIndex.getOneBased()
                        + PREFIX_WITH_VALID_DATE + PREFIX_WITH_VALID_ITEM_NAME
                        + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_PRICE
                        + PREFIX_WITH_VALID_DATE + PREFIX_WITH_VALID_ITEM_NAME
                        + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_PRICE
                        + PREFIX_WITH_VALID_DATE_TWO + PREFIX_WITH_VALID_ITEM_NAME_TWO
                        + PREFIX_WITH_VALID_QUANTITY_SOLD_TWO + PREFIX_WITH_VALID_PRICE_TWO;

        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withDate(VALID_DATE_RECORD_TWO)
                .withName(VALID_ITEM_NAME_RECORD_TWO).withQuantitySold(VALID_QUANTITY_SOLD_RECORD_TWO)
                .withPrice(VALID_PRICE_RECORD_TWO).build();
        EditSalesCommand expectedCommand = new EditSalesCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PREFIX_WITH_INVALID_DATE + PREFIX_WITH_VALID_DATE_TWO;
        EditRecordDescriptor descriptor = new EditRecordDescriptorBuilder().withDate(VALID_DATE_RECORD_TWO).build();
        EditSalesCommand expectedCommand = new EditSalesCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + PREFIX_WITH_VALID_DATE + PREFIX_WITH_INVALID_ITEM_NAME
                + PREFIX_WITH_VALID_QUANTITY_SOLD + PREFIX_WITH_VALID_ITEM_NAME;
        descriptor =
                new EditRecordDescriptorBuilder().withDate(VALID_DATE_RECORD_ONE).withName(VALID_ITEM_NAME_RECORD_ONE)
                .withQuantitySold(VALID_QUANTITY_SOLD_RECORD_ONE).build();
        expectedCommand = new EditSalesCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
