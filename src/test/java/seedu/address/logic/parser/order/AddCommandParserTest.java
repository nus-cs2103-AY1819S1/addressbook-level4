package seedu.address.logic.parser.order;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FOOD_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.FOOD_DESC_RICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_RICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalOrders.BOB;

import org.junit.Test;

import seedu.address.logic.commands.order.AddCommand;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.order.Order;
import seedu.address.testutil.OrderBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Order expectedOrder = new OrderBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + FOOD_DESC_RICE, new AddCommand(expectedOrder));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + FOOD_DESC_RICE, new AddCommand(expectedOrder));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + FOOD_DESC_RICE, new AddCommand(expectedOrder));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + DATE_DESC_BOB + FOOD_DESC_RICE, new AddCommand(expectedOrder));

        // multiple date - last date accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DATE_DESC_AMY + DATE_DESC_BOB + FOOD_DESC_RICE, new AddCommand(expectedOrder));

        // multiple food - all accepted
        Order expectedOrderMultipleFood = new OrderBuilder(BOB).withFood(VALID_FOOD_BURGER, VALID_FOOD_RICE)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB
                + FOOD_DESC_BURGER + FOOD_DESC_RICE, new AddCommand(expectedOrderMultipleFood));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB
                        + FOOD_DESC_BURGER,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB
                        + FOOD_DESC_BURGER,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ADDRESS_BOB + DATE_DESC_BOB
                        + FOOD_DESC_BURGER,
                expectedMessage);

        // missing food prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB
                        + VALID_FOOD_BURGER,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + VALID_DATE_BOB
                        + FOOD_DESC_BURGER,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_ADDRESS_BOB + VALID_DATE_BOB
                        + VALID_FOOD_BURGER,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB
                + FOOD_DESC_BURGER + FOOD_DESC_RICE, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB + DATE_DESC_BOB
                + FOOD_DESC_BURGER + FOOD_DESC_RICE, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_ADDRESS_DESC + DATE_DESC_BOB
                + FOOD_DESC_BURGER + FOOD_DESC_RICE, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_ADDRESS_DESC + DATE_DESC_BOB
                        + FOOD_DESC_BURGER,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + DATE_DESC_BOB
                        + ADDRESS_DESC_BOB + FOOD_DESC_RICE + FOOD_DESC_BURGER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
