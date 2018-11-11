package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.logic.parser.util.ParserUtil.arePrefixesPresent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import seedu.restaurant.logic.commands.menu.AddItemCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.Price;
import seedu.restaurant.model.menu.Recipe;
import seedu.restaurant.model.tag.Tag;

//@@author yican95
/**
 * Parses input arguments and creates a new AddItemCommand object
 */
public class AddItemCommandParser implements Parser<AddItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddItemCommand
     * and returns an AddItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddItemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
        }

        Name name = ItemParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Price price = ItemParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Recipe recipe = new Recipe(""); //TODO: Can consider adding recipe here.
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Map<IngredientName, Integer> requiredIngredients = new HashMap<>();

        Item item = new Item(name, price, recipe, tagList, requiredIngredients);

        return new AddItemCommand(item);
    }
}
