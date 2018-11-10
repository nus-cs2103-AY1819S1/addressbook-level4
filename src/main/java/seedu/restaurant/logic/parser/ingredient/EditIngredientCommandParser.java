package seedu.restaurant.logic.parser.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.commons.util.StringUtil.isNonZeroUnsignedInteger;
import static seedu.restaurant.logic.parser.ingredient.IngredientParserUtil.MESSAGE_NOT_INDEX_OR_NAME;
import static seedu.restaurant.logic.parser.ingredient.IngredientParserUtil.parseIngredientName;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_MINIMUM;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_ORIGINAL_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_PRICE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.restaurant.logic.parser.util.ParserUtil.parseIndex;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.ingredient.EditIngredientByIndexCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientByNameCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand;
import seedu.restaurant.logic.commands.ingredient.EditIngredientCommand.EditIngredientDescriptor;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditIngredientCommandParser implements Parser<EditIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditIngredientCommand and returns an
     * EditIngredientCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_ORIGINAL_NAME, PREFIX_INGREDIENT_NAME,
                        PREFIX_INGREDIENT_UNIT, PREFIX_INGREDIENT_PRICE, PREFIX_INGREDIENT_MINIMUM);

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditIngredientCommand.MESSAGE_USAGE));
        }

        EditIngredientDescriptor editIngredientDescriptor = new EditIngredientDescriptor();
        setNameDescriptor(argMultimap, editIngredientDescriptor);
        setUnitDescriptor(argMultimap, editIngredientDescriptor);
        setPriceDescriptor(argMultimap, editIngredientDescriptor);
        setMinDescriptor(argMultimap, editIngredientDescriptor);

        if (!editIngredientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditIngredientCommand.MESSAGE_NOT_EDITED);
        }

        EditIngredientCommand editCommand = null;

        String trimmedIndex = argMultimap.getPreamble().trim();
        if (!trimmedIndex.trim().isEmpty() && isNonZeroUnsignedInteger(trimmedIndex)) {
            Index index = parseIndex(trimmedIndex);
            editCommand = new EditIngredientByIndexCommand(index, editIngredientDescriptor);
        } else {
            String originalName = argMultimap.getValue(PREFIX_INGREDIENT_ORIGINAL_NAME).orElse("");
            if (originalName.isEmpty()) {
                throw new ParseException(MESSAGE_NOT_INDEX_OR_NAME + "\n" +
                        EditIngredientCommand.MESSAGE_USAGE);
            }
            IngredientName name = parseIngredientName(originalName.trim());
            editCommand = new EditIngredientByNameCommand(name, editIngredientDescriptor);

        }
//
//        if (indexOrName instanceof Index) {
//            index = (Index) indexOrName;
//            editCommand = new EditIngredientByIndexCommand(index, editIngredientDescriptor);
//        }
//        if (indexOrName instanceof IngredientName) {
//            name = (IngredientName) indexOrName;
//            editCommand = new EditIngredientByNameCommand(name, editIngredientDescriptor);
//        }

        return editCommand;
    }

    private void setMinDescriptor(ArgumentMultimap argMultimap, EditIngredientDescriptor editIngredientDescriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_INGREDIENT_MINIMUM).isPresent()) {
            editIngredientDescriptor.setMinimum(
                    IngredientParserUtil.parseMinimumUnit(argMultimap.getValue(PREFIX_INGREDIENT_MINIMUM).get()));
        }
    }

    private void setPriceDescriptor(ArgumentMultimap argMultimap, EditIngredientDescriptor editIngredientDescriptor)
            throws ParseException {
        setUnitDescriptor(argMultimap, editIngredientDescriptor);
        if (argMultimap.getValue(PREFIX_INGREDIENT_PRICE).isPresent()) {
            editIngredientDescriptor.setPrice(
                    IngredientParserUtil.parseIngredientPrice(argMultimap.getValue(PREFIX_INGREDIENT_PRICE).get()));
        }
    }

    private void setUnitDescriptor(ArgumentMultimap argMultimap, EditIngredientDescriptor editIngredientDescriptor)
            throws ParseException {
        setNameDescriptor(argMultimap, editIngredientDescriptor);
        if (argMultimap.getValue(PREFIX_INGREDIENT_UNIT).isPresent()) {
            editIngredientDescriptor.setUnit(
                    IngredientParserUtil.parseIngredientUnit(argMultimap.getValue(PREFIX_INGREDIENT_UNIT).get()));
        }
    }

    private void setNameDescriptor(ArgumentMultimap argMultimap, EditIngredientDescriptor editIngredientDescriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_INGREDIENT_NAME).isPresent()) {
            editIngredientDescriptor.setName(
                    IngredientParserUtil.parseIngredientName(argMultimap.getValue(PREFIX_INGREDIENT_NAME).get()));
        }
    }
}
