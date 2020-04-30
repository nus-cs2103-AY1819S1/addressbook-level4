package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.ingredient.DeleteIngredientByIndexCommand;
import seedu.restaurant.logic.commands.ingredient.DeleteIngredientByNameCommand;
import seedu.restaurant.logic.commands.ingredient.DeleteIngredientCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.ingredient.IngredientName;

/**
 * Parses input arguments and creates a new DeleteIngredientCommand object
 */
public class DeleteIngredientCommandParser implements Parser<DeleteIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteIngredientCommand
     * and returns an DeleteIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteIngredientCommand parse(String args) throws ParseException {

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteIngredientCommand.MESSAGE_USAGE));
        }

        Object indexOrName = IngredientParserUtil.parseIndexOrIngredientName(args);
        DeleteIngredientCommand deleteCommand = null;
        if (indexOrName instanceof Index) {
            deleteCommand = new DeleteIngredientByIndexCommand((Index) indexOrName);
        }
        if (indexOrName instanceof IngredientName) {
            deleteCommand = new DeleteIngredientByNameCommand((IngredientName) indexOrName);
        }

        return deleteCommand;
    }

}
