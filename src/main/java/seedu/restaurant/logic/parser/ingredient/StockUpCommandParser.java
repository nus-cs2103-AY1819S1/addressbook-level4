package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NUM;
import static seedu.restaurant.model.ingredient.IngredientName.MESSAGE_NAME_CONSTRAINTS;
import static seedu.restaurant.model.ingredient.NumUnits.MESSAGE_NUMUNITS_CONSTRAINTS;

import java.util.HashMap;
import java.util.Map;

import seedu.restaurant.commons.util.StringUtil;
import seedu.restaurant.logic.commands.ingredient.StockUpCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentPairMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.model.ingredient.IngredientName;

/**
 * Parses input arguments and creates a new StockUpCommand object
 */
public class StockUpCommandParser implements Parser<StockUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StockUpCommand and returns a StockUpCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StockUpCommand parse(String args) throws ParseException {

        if (args.trim().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StockUpCommand.MESSAGE_USAGE));
        }

        ArgumentPairMultimap argMultimap =
                ArgumentTokenizer.tokenizeToPair(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_NUM);

        Map<IngredientName, Integer> transformedHashMap = transformArgMultimap(argMultimap);

        return new StockUpCommand(transformedHashMap);
    }

    private Map<IngredientName, Integer> transformArgMultimap(ArgumentPairMultimap argMultimap) throws ParseException {
        Map<IngredientName, Integer> newHashMap = new HashMap<>();
        for (HashMap.Entry<String, String> argPair : argMultimap.getArgMultimap().entrySet()) {
            String ingredientName = argPair.getKey();
            if (!IngredientName.isValidName(ingredientName)) {
                throw new ParseException(MESSAGE_NAME_CONSTRAINTS);
            }
            IngredientName validName = new IngredientName(ingredientName);

            String numUnits = argPair.getValue();
            if (!StringUtil.isUnsignedInteger(numUnits)) {
                throw new ParseException(MESSAGE_NUMUNITS_CONSTRAINTS);
            }
            Integer validNumUnits = Integer.parseInt(numUnits);

            newHashMap.put(validName, validNumUnits);
        }

        return newHashMap;
    }
}
