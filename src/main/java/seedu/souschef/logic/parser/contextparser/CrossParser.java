package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.CrossFindCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.ListCommand;
import seedu.souschef.logic.commands.SelectCommand;
import seedu.souschef.logic.parser.commandparser.CrossFindCommandParser;
import seedu.souschef.logic.parser.commandparser.SelectCommandParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;

/**
 * Parses user input.
 */
public class CrossParser {
    public static final String COMMAND_WORD = "cross";
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param crossRecipeModel
     * @param ingredientModel
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command<CrossRecipe> parseCommand(Model<CrossRecipe> crossRecipeModel, Model<Ingredient> ingredientModel,
                                             String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case CrossFindCommand.COMMAND_WORD:

            return new CrossFindCommandParser().parse(crossRecipeModel, ingredientModel, arguments);
        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parseIndex(crossRecipeModel, arguments);
        case ListCommand.COMMAND_WORD:
            crossRecipeModel.updateFilteredList(Model.PREDICATE_SHOW_ALL_CROSSRECIPES);
            List<CrossRecipe> crossRecipeList = crossRecipeModel.getFilteredList();
            for (CrossRecipe crossRecipe : crossRecipeList) {
                Recipe recipe = crossRecipe.getRecipe();
                crossRecipeModel.update(crossRecipe, new CrossRecipe(recipe, recipe.getIngredients()));
            }
            return new ListCommand<CrossRecipe>(crossRecipeModel);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
