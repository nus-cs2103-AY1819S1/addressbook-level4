package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.restaurant.logic.parser.util.ParserUtil.arePrefixesPresent;

import seedu.restaurant.logic.commands.ingredient.AddIngredientCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.IngredientPrice;
import seedu.restaurant.model.ingredient.IngredientUnit;
import seedu.restaurant.model.ingredient.MinimumUnit;
import seedu.restaurant.model.ingredient.NumUnits;

/**
 * Parses input arguments and creates a new AddIngredientCommand object
 */
public class AddIngredientCommandParser implements Parser<AddIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddIngredientCommand and returns an
     * AddIngredientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddIngredientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_UNIT,
                        PREFIX_INGREDIENT_PRICE, PREFIX_INGREDIENT_MINIMUM);

        if (!arePrefixesPresent(argMultimap, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_UNIT, PREFIX_INGREDIENT_PRICE,
                PREFIX_INGREDIENT_MINIMUM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE));
        }

        IngredientName name = IngredientParserUtil
                .parseIngredientName(argMultimap.getValue(PREFIX_INGREDIENT_NAME).get());
        IngredientUnit unit = IngredientParserUtil
                .parseIngredientUnit(argMultimap.getValue(PREFIX_INGREDIENT_UNIT).get());
        IngredientPrice price = IngredientParserUtil
                .parseIngredientPrice(argMultimap.getValue(PREFIX_INGREDIENT_PRICE).get());
        MinimumUnit minimum = IngredientParserUtil
                .parseMinimumUnit(argMultimap.getValue(PREFIX_INGREDIENT_MINIMUM).get());

        Ingredient ingredient = new Ingredient(name, unit, price, minimum, new NumUnits(0));

        return new AddIngredientCommand(ingredient);
    }
}
