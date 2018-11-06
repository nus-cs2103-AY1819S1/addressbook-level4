package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.commands.CommandTestUtil.INGREDIENT_MINIMUM_DESC_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.INGREDIENT_MINIMUM_DESC_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.INGREDIENT_PRICE_DESC_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.INGREDIENT_PRICE_DESC_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_INGREDIENT_MINIMUM_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_INGREDIENT_NAME_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_INGREDIENT_PRICE_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_INGREDIENT_UNIT_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_MINIMUM_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_MINIMUM_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PRICE_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PRICE_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_UNIT_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_UNIT_BROCCOLI;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.logic.parser.ingredient.IngredientParserUtil.MESSAGE_NOT_INDEX_OR_NAME;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.ingredient.EditIngredientByIndexCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientByNameCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand.EditIngredientDescriptor;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.IngredientPrice;
import seedu.restaurant.model.ingredient.IngredientUnit;
import seedu.restaurant.model.ingredient.MinimumUnit;
import seedu.restaurant.testutil.ingredient.EditIngredientDescriptorBuilder;

public class EditIngredientCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIngredientByIndexCommand.MESSAGE_USAGE);

    private EditIngredientCommandParser parser = new EditIngredientCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no preamble or field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // preamble is not index or name
        assertParseFailure(parser, "1+" + INGREDIENT_UNIT_DESC_BROCCOLI, MESSAGE_NOT_INDEX_OR_NAME);
        assertParseFailure(parser, "apple+" + INGREDIENT_UNIT_DESC_BROCCOLI, MESSAGE_NOT_INDEX_OR_NAME);

        // no preamble but field specified
        assertParseFailure(parser, INGREDIENT_NAME_DESC_APPLE, MESSAGE_INVALID_FORMAT);

        // index specified but no field specified
        assertParseFailure(parser, "1", EditIngredientByIndexCommand.MESSAGE_NOT_EDITED);

        // name specified but no field specified
        assertParseFailure(parser, VALID_NAME_APPLE, EditIngredientByIndexCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_indexWithInvalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_INGREDIENT_NAME_DESC,
                IngredientName.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_INGREDIENT_UNIT_DESC,
                IngredientUnit.MESSAGE_UNIT_CONSTRAINTS); // invalid unit
        assertParseFailure(parser, "1" + INVALID_INGREDIENT_PRICE_DESC,
                IngredientPrice.MESSAGE_PRICE_CONSTRAINTS); // invalid price
        assertParseFailure(parser, "1" + INVALID_INGREDIENT_MINIMUM_DESC,
                MinimumUnit.MESSAGE_MINIMUM_CONSTRAINTS); // invalid minimum

        // invalid unit followed by valid price
        assertParseFailure(parser, "1" + INVALID_INGREDIENT_UNIT_DESC + INGREDIENT_PRICE_DESC_APPLE,
                IngredientUnit.MESSAGE_UNIT_CONSTRAINTS);

        // valid value followed by invalid value. The test case for invalid value followed by valid value
        // is tested at {@code parse_index_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + INGREDIENT_UNIT_DESC_BROCCOLI + INVALID_INGREDIENT_UNIT_DESC,
                IngredientUnit.MESSAGE_UNIT_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_INGREDIENT_NAME_DESC + INVALID_INGREDIENT_UNIT_DESC,
                IngredientName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_nameWithInvalidValue_failure() {
        assertParseFailure(parser, VALID_NAME_BROCCOLI + INVALID_INGREDIENT_NAME_DESC,
                IngredientName.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_NAME_BROCCOLI + INVALID_INGREDIENT_UNIT_DESC,
                IngredientUnit.MESSAGE_UNIT_CONSTRAINTS); // invalid unit
        assertParseFailure(parser, VALID_NAME_BROCCOLI + INVALID_INGREDIENT_PRICE_DESC,
                IngredientPrice.MESSAGE_PRICE_CONSTRAINTS); // invalid price
        assertParseFailure(parser, VALID_NAME_BROCCOLI + INVALID_INGREDIENT_MINIMUM_DESC,
                MinimumUnit.MESSAGE_MINIMUM_CONSTRAINTS); // invalid minimum

        // invalid unit followed by valid price
        assertParseFailure(parser, VALID_NAME_BROCCOLI + INVALID_INGREDIENT_UNIT_DESC
                + INGREDIENT_PRICE_DESC_APPLE, IngredientUnit.MESSAGE_UNIT_CONSTRAINTS);

        // valid value followed by invalid value. The test case for invalid value followed by valid value
        // is tested at {@code parse_name_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, VALID_NAME_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI
                + INVALID_INGREDIENT_UNIT_DESC, IngredientUnit.MESSAGE_UNIT_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_NAME_BROCCOLI + INVALID_INGREDIENT_NAME_DESC
                + INVALID_INGREDIENT_UNIT_DESC, IngredientName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_indexWithAllFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + INGREDIENT_UNIT_DESC_BROCCOLI + INGREDIENT_PRICE_DESC_APPLE
                + INGREDIENT_MINIMUM_DESC_BROCCOLI + INGREDIENT_NAME_DESC_BROCCOLI;

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BROCCOLI)
                .withUnit(VALID_UNIT_BROCCOLI).withPrice(VALID_PRICE_APPLE)
                .withMinimum(VALID_MINIMUM_BROCCOLI).build();
        EditIngredientCommand expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameWithAllFieldsSpecified_success() {
        IngredientName targetName = new IngredientName(VALID_NAME_APPLE);
        String userInput = VALID_NAME_APPLE + " " + INGREDIENT_UNIT_DESC_BROCCOLI + INGREDIENT_PRICE_DESC_APPLE
                + INGREDIENT_MINIMUM_DESC_BROCCOLI + INGREDIENT_NAME_DESC_BROCCOLI;

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withName(VALID_NAME_BROCCOLI)
                .withUnit(VALID_UNIT_BROCCOLI).withPrice(VALID_PRICE_APPLE)
                .withMinimum(VALID_MINIMUM_BROCCOLI).build();
        EditIngredientCommand expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexWithSomeFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INGREDIENT_UNIT_DESC_APPLE + INGREDIENT_PRICE_DESC_BROCCOLI;

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_APPLE)
                .withPrice(VALID_PRICE_BROCCOLI).build();
        EditIngredientCommand expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameWithSomeFieldsSpecified_success() {
        IngredientName targetName = new IngredientName(VALID_NAME_APPLE);
        String userInput = VALID_NAME_APPLE + " " + INGREDIENT_UNIT_DESC_APPLE + INGREDIENT_PRICE_DESC_BROCCOLI;

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_APPLE)
                .withPrice(VALID_PRICE_BROCCOLI).build();
        EditIngredientCommand expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexWithOneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + INGREDIENT_NAME_DESC_APPLE;
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder()
                .withName(VALID_NAME_APPLE).build();
        EditIngredientCommand expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // unit
        userInput = targetIndex.getOneBased() + INGREDIENT_UNIT_DESC_APPLE;
        descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_APPLE).build();
        expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = targetIndex.getOneBased() + INGREDIENT_PRICE_DESC_APPLE;
        descriptor = new EditIngredientDescriptorBuilder().withPrice(VALID_PRICE_APPLE).build();
        expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // minimum
        userInput = targetIndex.getOneBased() + INGREDIENT_MINIMUM_DESC_APPLE;
        descriptor = new EditIngredientDescriptorBuilder().withMinimum(VALID_MINIMUM_APPLE).build();
        expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameWithOneFieldSpecified_success() {
        // name
        IngredientName targetName = new IngredientName(VALID_NAME_BROCCOLI);
        String userInput = VALID_NAME_BROCCOLI + " " + INGREDIENT_NAME_DESC_APPLE;
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder()
                .withName(VALID_NAME_APPLE).build();
        EditIngredientCommand expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // unit
        userInput = VALID_NAME_BROCCOLI + " " + INGREDIENT_UNIT_DESC_APPLE;
        descriptor = new EditIngredientDescriptorBuilder()
                .withUnit(VALID_UNIT_APPLE).build();
        expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // price
        userInput = VALID_NAME_BROCCOLI + " " + INGREDIENT_PRICE_DESC_APPLE;
        descriptor = new EditIngredientDescriptorBuilder()
                .withPrice(VALID_PRICE_APPLE).build();
        expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // minimum
        userInput = VALID_NAME_BROCCOLI + " " + INGREDIENT_MINIMUM_DESC_APPLE;
        descriptor = new EditIngredientDescriptorBuilder()
                .withMinimum(VALID_MINIMUM_APPLE).build();
        expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexWithMultipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INGREDIENT_UNIT_DESC_APPLE + INGREDIENT_MINIMUM_DESC_APPLE
                + INGREDIENT_PRICE_DESC_APPLE + INGREDIENT_UNIT_DESC_APPLE + INGREDIENT_MINIMUM_DESC_APPLE
                + INGREDIENT_PRICE_DESC_APPLE + INGREDIENT_UNIT_DESC_BROCCOLI + INGREDIENT_MINIMUM_DESC_BROCCOLI
                + INGREDIENT_PRICE_DESC_BROCCOLI;

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_BROCCOLI)
                .withPrice(VALID_PRICE_BROCCOLI).withMinimum(VALID_MINIMUM_BROCCOLI)
                .build();
        EditIngredientCommand expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_nameWithMultipleRepeatedFields_acceptsLast() {
        IngredientName targetName = new IngredientName(VALID_NAME_APPLE);
        String userInput = VALID_NAME_APPLE + " " + INGREDIENT_UNIT_DESC_APPLE + INGREDIENT_MINIMUM_DESC_APPLE
                + INGREDIENT_PRICE_DESC_APPLE + INGREDIENT_UNIT_DESC_APPLE + INGREDIENT_MINIMUM_DESC_APPLE
                + INGREDIENT_PRICE_DESC_APPLE + INGREDIENT_UNIT_DESC_BROCCOLI + INGREDIENT_MINIMUM_DESC_BROCCOLI
                + INGREDIENT_PRICE_DESC_BROCCOLI;

        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_BROCCOLI)
                .withPrice(VALID_PRICE_BROCCOLI).withMinimum(VALID_MINIMUM_BROCCOLI)
                .build();
        EditIngredientCommand expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexWithInvalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_INGREDIENT_UNIT_DESC + INGREDIENT_UNIT_DESC_BROCCOLI;
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_BROCCOLI)
                .build();
        EditIngredientByIndexCommand expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INGREDIENT_PRICE_DESC_BROCCOLI + INVALID_INGREDIENT_UNIT_DESC
                + INGREDIENT_MINIMUM_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI;
        descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_BROCCOLI).withPrice(VALID_PRICE_BROCCOLI)
                .withMinimum(VALID_MINIMUM_BROCCOLI).build();
        expectedCommand = new EditIngredientByIndexCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_nameWithInvalidValueFollowedByValidValue_success() {
        // no other valid values specified
        IngredientName targetName = new IngredientName(VALID_NAME_APPLE);
        String userInput = VALID_NAME_APPLE + INVALID_INGREDIENT_UNIT_DESC + INGREDIENT_UNIT_DESC_BROCCOLI;
        EditIngredientDescriptor descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_BROCCOLI)
                .build();
        EditIngredientCommand expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = VALID_NAME_APPLE + INGREDIENT_PRICE_DESC_BROCCOLI + INVALID_INGREDIENT_UNIT_DESC
                + INGREDIENT_MINIMUM_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI;
        descriptor = new EditIngredientDescriptorBuilder().withUnit(VALID_UNIT_BROCCOLI).withPrice(VALID_PRICE_BROCCOLI)
                .withMinimum(VALID_MINIMUM_BROCCOLI).build();
        expectedCommand = new EditIngredientByNameCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
