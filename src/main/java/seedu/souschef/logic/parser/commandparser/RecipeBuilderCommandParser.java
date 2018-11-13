package seedu.souschef.logic.parser.commandparser;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_ADD_RECIPE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_CONT_RECIPE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_DUPLICATE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.commons.core.Messages.MESSAGE_NO_RECIPE_INSTRUCTION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_COOKTIME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;

import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.AddCommand;
import seedu.souschef.logic.commands.BuildRecipeInstructionCommand;
import seedu.souschef.logic.commands.CreateRecipeBuildCommand;
import seedu.souschef.logic.parser.ArgumentMultimap;
import seedu.souschef.logic.parser.ArgumentTokenizer;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.logic.parser.ParserUtil;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.recipe.RecipeBuilder;
import seedu.souschef.model.recipe.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RecipeBuilderCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the CreateRecipeBuildCommand
     * and returns an CreateRecipeBuildCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateRecipeBuildCommand parseRecipe(Model<Recipe> model, String args) throws ParseException {
        requireNonNull(model);
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DIFFICULTY, PREFIX_COOKTIME, PREFIX_TAG);

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_DIFFICULTY, PREFIX_COOKTIME)
                || argMultimap.hasNestedPrefix()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_ADD_RECIPE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Difficulty difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
        CookTime cookTime = ParserUtil.parseCooktime(argMultimap.getValue(PREFIX_COOKTIME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Recipe toAdd = new Recipe(name, difficulty, cookTime, new ArrayList<>(), tagList);
        if (model.has(toAdd)) {
            throw new ParseException(String.format(MESSAGE_DUPLICATE, Context.RECIPE.command));
        }

        RecipeBuilder recipeBuilder = new RecipeBuilder(name, difficulty, cookTime, tagList);
        return new CreateRecipeBuildCommand(recipeBuilder);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the BuildRecipeInstructionCommand
     * and returns an BuildRecipeInstructionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BuildRecipeInstructionCommand parseInstruction(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INSTRUCTION, PREFIX_COOKTIME);

        if (!argMultimap.arePrefixesPresent(PREFIX_INSTRUCTION)
                || argMultimap.hasNestedPrefix()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_CONT_RECIPE_USAGE));
        }

        CookTime cookTime = ParserUtil.parseCooktime(argMultimap.getValue(PREFIX_COOKTIME)
                .orElse(CookTime.ZERO_COOKTIME));
        String unparsedtext = argMultimap.getValue(PREFIX_INSTRUCTION).orElse("");
        String instruction = ParserUtil.parseInstructionText(unparsedtext);
        Set<IngredientPortion> ingredients = ParserUtil.parseIngredients(unparsedtext);

        return new BuildRecipeInstructionCommand(new Instruction(instruction, cookTime, ingredients));
    }

    /**
     * Parses the given {@code History} to create Recipe so as to check if instructions are included
     * and returns an AddCommand object for execution.
     * @throws ParseException if there is no instruction in the recipe
     */
    public AddCommand parseCompleteRecipe(Model recipeModel, History history) throws ParseException {
        Recipe recipe = history.buildRecipe();
        if (recipe.getInstructions().size() < 1) {
            throw new ParseException(MESSAGE_NO_RECIPE_INSTRUCTION);
        }
        history.clearRecipe();
        return new AddCommand<>(recipeModel, recipe);
    }

}
