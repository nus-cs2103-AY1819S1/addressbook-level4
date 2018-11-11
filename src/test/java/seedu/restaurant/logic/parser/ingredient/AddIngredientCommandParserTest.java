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
import static seedu.restaurant.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_MINIMUM_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PRICE_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_UNIT_BROCCOLI;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.BROCCOLI;

import org.junit.Test;

import seedu.restaurant.logic.commands.ingredient.AddIngredientCommand;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.IngredientPrice;
import seedu.restaurant.model.ingredient.IngredientUnit;
import seedu.restaurant.model.ingredient.MinimumUnit;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;

//@@author rebstan97
public class AddIngredientCommandParserTest {
    private AddIngredientCommandParser parser = new AddIngredientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Ingredient expectedIngredient = new IngredientBuilder(BROCCOLI).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_BROCCOLI
                + INGREDIENT_UNIT_DESC_BROCCOLI + INGREDIENT_PRICE_DESC_BROCCOLI
                + INGREDIENT_MINIMUM_DESC_BROCCOLI, new AddIngredientCommand(expectedIngredient));

        // multiple names - last name accepted
        assertParseSuccess(parser, INGREDIENT_NAME_DESC_APPLE + INGREDIENT_NAME_DESC_BROCCOLI
                + INGREDIENT_UNIT_DESC_BROCCOLI + INGREDIENT_PRICE_DESC_BROCCOLI
                + INGREDIENT_MINIMUM_DESC_BROCCOLI, new AddIngredientCommand(expectedIngredient));

        // multiple units - last unit accepted
        assertParseSuccess(parser, INGREDIENT_NAME_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_APPLE
                + INGREDIENT_UNIT_DESC_BROCCOLI + INGREDIENT_PRICE_DESC_BROCCOLI
                + INGREDIENT_MINIMUM_DESC_BROCCOLI, new AddIngredientCommand(expectedIngredient));

        // multiple prices - last price accepted
        assertParseSuccess(parser, INGREDIENT_NAME_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI
                + INGREDIENT_PRICE_DESC_APPLE + INGREDIENT_PRICE_DESC_BROCCOLI
                + INGREDIENT_MINIMUM_DESC_BROCCOLI, new AddIngredientCommand(expectedIngredient));

        // multiple minimums - last minimum accepted
        assertParseSuccess(parser, INGREDIENT_NAME_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI
                + INGREDIENT_PRICE_DESC_BROCCOLI + INGREDIENT_MINIMUM_DESC_APPLE
                + INGREDIENT_MINIMUM_DESC_BROCCOLI, new AddIngredientCommand(expectedIngredient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI
                        + INGREDIENT_PRICE_DESC_BROCCOLI + INGREDIENT_MINIMUM_DESC_BROCCOLI, expectedMessage);

        // missing unit prefix
        assertParseFailure(parser, INGREDIENT_NAME_DESC_BROCCOLI + VALID_UNIT_BROCCOLI
                        + INGREDIENT_PRICE_DESC_BROCCOLI + INGREDIENT_MINIMUM_DESC_BROCCOLI, expectedMessage);

        // missing price prefix
        assertParseFailure(parser, INGREDIENT_NAME_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI
                        + VALID_PRICE_BROCCOLI + INGREDIENT_MINIMUM_DESC_BROCCOLI, expectedMessage);

        // missing minimum prefix
        assertParseFailure(parser, INGREDIENT_NAME_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI
                        + INGREDIENT_PRICE_DESC_BROCCOLI + VALID_MINIMUM_BROCCOLI, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BROCCOLI + VALID_UNIT_BROCCOLI + VALID_PRICE_BROCCOLI
                        + VALID_MINIMUM_BROCCOLI, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_INGREDIENT_NAME_DESC + INGREDIENT_UNIT_DESC_BROCCOLI
                + INGREDIENT_PRICE_DESC_BROCCOLI + INGREDIENT_MINIMUM_DESC_BROCCOLI,
                IngredientName.MESSAGE_NAME_CONSTRAINTS);

        // invalid unit
        assertParseFailure(parser, INGREDIENT_NAME_DESC_BROCCOLI + INVALID_INGREDIENT_UNIT_DESC
                        + INGREDIENT_PRICE_DESC_BROCCOLI + INGREDIENT_MINIMUM_DESC_BROCCOLI,
                IngredientUnit.MESSAGE_UNIT_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, INGREDIENT_NAME_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI
                        + INVALID_INGREDIENT_PRICE_DESC + INGREDIENT_MINIMUM_DESC_BROCCOLI,
                IngredientPrice.MESSAGE_PRICE_CONSTRAINTS);

        // invalid minimum
        assertParseFailure(parser, INGREDIENT_NAME_DESC_BROCCOLI + INGREDIENT_UNIT_DESC_BROCCOLI
                        + INGREDIENT_PRICE_DESC_BROCCOLI + INVALID_INGREDIENT_MINIMUM_DESC,
                MinimumUnit.MESSAGE_MINIMUM_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_INGREDIENT_NAME_DESC + INGREDIENT_UNIT_DESC_BROCCOLI
                        + INGREDIENT_PRICE_DESC_BROCCOLI + INVALID_INGREDIENT_MINIMUM_DESC,
                IngredientName.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INGREDIENT_NAME_DESC_BROCCOLI
                        + INGREDIENT_UNIT_DESC_BROCCOLI + INGREDIENT_PRICE_DESC_BROCCOLI
                        + INGREDIENT_MINIMUM_DESC_BROCCOLI,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE));
    }
}
