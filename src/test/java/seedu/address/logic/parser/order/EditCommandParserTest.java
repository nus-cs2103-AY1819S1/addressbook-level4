package seedu.address.logic.parser.order;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.FOOD_DESC_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.FOOD_DESC_RICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOOD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_RICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.order.EditCommand;
import seedu.address.logic.commands.order.EditCommand.EditOrderDescriptor;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.order.Food;
import seedu.address.model.order.OrderDate;
import seedu.address.testutil.EditOrderDescriptorBuilder;

public class EditCommandParserTest {
    private static final String FOOD_EMPTY = " " + PREFIX_FOOD;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, OrderDate.MESSAGE_DATE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_FOOD_DESC, Food.MESSAGE_FOOD_CONSTRAINTS); // invalid food

        // invalid phone followed by valid date
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + DATE_DESC_AMY, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // empty food provided
        assertParseFailure(parser, "1" + FOOD_EMPTY, Food.MESSAGE_FOOD_CONSTRAINTS);

        // parsing empty food with valid food
        assertParseFailure(parser, "1" + FOOD_EMPTY + FOOD_DESC_RICE, Food.MESSAGE_FOOD_CONSTRAINTS);
        assertParseFailure(parser, "1" + FOOD_DESC_RICE + FOOD_EMPTY, Food.MESSAGE_FOOD_CONSTRAINTS);
        assertParseFailure(parser, "1" + FOOD_DESC_BURGER + FOOD_EMPTY + FOOD_DESC_RICE, Food.MESSAGE_FOOD_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DATE_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + FOOD_DESC_RICE
                + DATE_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + FOOD_DESC_BURGER;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withDate(VALID_DATE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withFood(VALID_FOOD_RICE, VALID_FOOD_BURGER).build();

        EditCommand expectedCommnand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommnand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + DATE_DESC_AMY;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withDate(VALID_DATE_AMY).build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD;

        // name
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_AMY;
        descriptor = new EditOrderDescriptorBuilder().withDate(VALID_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // food
        userInput = targetIndex.getOneBased() + FOOD_DESC_BURGER;
        descriptor = new EditOrderDescriptorBuilder().withFood(VALID_FOOD_BURGER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + DATE_DESC_AMY
                + FOOD_DESC_BURGER + PHONE_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_BOB + FOOD_DESC_RICE;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withDate(VALID_DATE_BOB).withAddress(VALID_ADDRESS_BOB).withFood(VALID_FOOD_BURGER, VALID_FOOD_RICE)
                .build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Index targetIndex = INDEX_FIRST;

        // one type
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + DATE_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditOrderDescriptorBuilder().withDate(VALID_DATE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
