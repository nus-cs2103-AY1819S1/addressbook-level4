package seedu.restaurant.logic.parser.menu;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_RECIPE;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.menu.RecipeItemCommand;
import seedu.restaurant.logic.parser.Parser;
import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.util.ArgumentMultimap;
import seedu.restaurant.logic.parser.util.ArgumentTokenizer;
import seedu.restaurant.logic.parser.util.ParserUtil;
import seedu.restaurant.model.menu.Recipe;

//@@author yican95
/**
 * Parses input arguments and creates a new RecipeItemCommand object
 */
public class RecipeItemCommandParser implements Parser<RecipeItemCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RecipeItemCommand
     * and returns an RecipeItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecipeItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECIPE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RecipeItemCommand.MESSAGE_USAGE), pe);
        }

        String recipe = argMultimap.getValue(PREFIX_RECIPE).orElse("");
        return new RecipeItemCommand(index, new Recipe(recipe));
    }
}
