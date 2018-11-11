package seedu.restaurant.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NUM;

import java.util.HashMap;
import java.util.Map;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.menu.AddRequiredIngredientsCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.ingredient.IngredientParserUtil;
import seedu.restaurant.logic.parser.util.ArgumentIndexAndPairMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;
import seedu.restaurant.logic.parser.util.Prefix;
import seedu.restaurant.model.ingredient.IngredientName;

//@@author yican95
/**
 * Parses input arguments and creates a new AddRequiredIngredientsCommand object
 */
public class AddRequiredIngredientsCommandParser implements Parser<AddRequiredIngredientsCommand> {
    public static final String MESSAGE_INTEGER_CONSTRAINTS =
            "Number of ingredients should only contain positive numbers";
    public static final String INTEGER_VALIDATION_REGEX = "\\d+";
    public static final String MESSAGE_DUPLICATE_INGREDIENT_NAME = "Duplicated ingredients entered";

    /**
     * Parses the given {@code String} of arguments in the context of the RecipeItemCommand
     * and returns an AddRequiredIngredientsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRequiredIngredientsCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentIndexAndPairMultimap argMultimap =
                ArgumentTokenizer.tokenizeToIndexAndPair(args, PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_NUM);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(0).get(new Prefix("")));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRequiredIngredientsCommand.MESSAGE_USAGE), pe);
        }

        int count = 1;
        Map<IngredientName, Integer> requiredIngredients = new HashMap<>();
        while (argMultimap.contains(count)) {
            Map<Prefix, String> map = argMultimap.getValue(count);

            if (!map.containsKey(PREFIX_INGREDIENT_NAME)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddRequiredIngredientsCommand.MESSAGE_USAGE));
            }
            IngredientName name = IngredientParserUtil.parseIngredientName(map.get(PREFIX_INGREDIENT_NAME));
            if (requiredIngredients.containsKey(name)) {
                throw new ParseException(MESSAGE_DUPLICATE_INGREDIENT_NAME);
            }

            if (!map.containsKey(PREFIX_INGREDIENT_NUM)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddRequiredIngredientsCommand.MESSAGE_USAGE));
            }
            String trimmedInteger = map.get(PREFIX_INGREDIENT_NUM);
            if (!isValidNumberOfIngredients(trimmedInteger)) {
                throw new ParseException(MESSAGE_INTEGER_CONSTRAINTS);
            }
            Integer num = Integer.parseInt(trimmedInteger);

            requiredIngredients.put(name, num);
            count++;
        }

        return new AddRequiredIngredientsCommand(index, requiredIngredients);
    }

    /**
     * Returns true if a given string is a valid number of ingredient.
     */
    private static boolean isValidNumberOfIngredients(String test) {
        if (!test.matches(INTEGER_VALIDATION_REGEX)) { // TODO: This should be in ingredient class
            return false;
        }
        return Integer.parseInt(test) > 0;
    }
}
