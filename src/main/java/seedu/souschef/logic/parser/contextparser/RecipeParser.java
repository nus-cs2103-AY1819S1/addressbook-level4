package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.commons.core.Messages.MESSAGE_NO_RECIPE_CONSTRUCTED;
import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.AddCommand;
import seedu.souschef.logic.commands.BuildRecipeInstructionCommand;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.CreateRecipeBuildCommand;
import seedu.souschef.logic.commands.DeleteCommand;
import seedu.souschef.logic.commands.EditCommand;
import seedu.souschef.logic.commands.FindCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.ListCommand;
import seedu.souschef.logic.commands.SelectCommand;
import seedu.souschef.logic.commands.SurpriseCommand;
import seedu.souschef.logic.parser.commandparser.DeleteCommandParser;
import seedu.souschef.logic.parser.commandparser.EditCommandParser;
import seedu.souschef.logic.parser.commandparser.FindCommandParser;
import seedu.souschef.logic.parser.commandparser.RecipeBuilderCommandParser;
import seedu.souschef.logic.parser.commandparser.SelectCommandParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;

/**
 * Parses user input.
 */
public class RecipeParser {
    public static final String COMMAND_WORD = "recipe";
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input from recipe within context into command for execution.
     *
     * @param recipeModel model data to be edited
     * @param userInput full user input string
     * @param history historical data from user input
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command<Recipe> parseCommand(Model recipeModel,
                                        String userInput, History history) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case CreateRecipeBuildCommand.COMMAND_WORD:
            return new RecipeBuilderCommandParser().parseRecipe(recipeModel, arguments);

        case BuildRecipeInstructionCommand.COMMAND_WORD:
            validateBuildingRecipe(history);
            return new RecipeBuilderCommandParser().parseInstruction(arguments);

        case AddCommand.COMMAND_WORD_END:
            validateBuildingRecipe(history);
            return new RecipeBuilderCommandParser().parseCompleteRecipe(recipeModel, history);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parseRecipe(recipeModel, arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parseRecipe(recipeModel, arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parseRecipe(recipeModel, arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand(recipeModel);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parseIndex(recipeModel, arguments);

        case SurpriseCommand.COMMAND_WORD:
            return new SurpriseCommand(recipeModel);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Check if recipe history contains incomplete recipe.
     * @throws ParseException if history does not contains incomplete recipe.
     */
    private void validateBuildingRecipe(History history) throws ParseException {
        if (!history.isBuildingRecipe()) {
            throw new ParseException(MESSAGE_NO_RECIPE_CONSTRUCTED);
        }
    }
}
