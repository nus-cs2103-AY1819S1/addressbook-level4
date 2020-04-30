package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.ingredient.SelectIngredientCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ParserUtil;

//@@author rebstan97
/**
 * Parses input arguments and creates a new SelectIngredientCommand object
 */
public class SelectIngredientCommandParser implements Parser<SelectIngredientCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectIngredientCommand
     * and returns an SelectIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectIngredientCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectIngredientCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectIngredientCommand.MESSAGE_USAGE), pe);
        }
    }
}
