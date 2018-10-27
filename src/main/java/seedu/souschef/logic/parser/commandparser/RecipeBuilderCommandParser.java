package seedu.souschef.logic.parser.commandparser;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_ADD_RECIPE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_COOKTIME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.souschef.logic.commands.BuildRecipeInstructionCommand;
import seedu.souschef.logic.commands.CreateRecipeBuildCommand;
import seedu.souschef.logic.parser.ArgumentMultimap;
import seedu.souschef.logic.parser.ArgumentTokenizer;
import seedu.souschef.logic.parser.ParserUtil;
import seedu.souschef.logic.parser.Prefix;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.recipe.RecipeBuilder;
import seedu.souschef.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RecipeBuilderCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateRecipeBuildCommand parseRecipe(Model<Recipe> model, String args) throws ParseException {
        requireNonNull(model);
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DIFFICULTY, PREFIX_COOKTIME, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DIFFICULTY, PREFIX_COOKTIME)
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_RECIPE_USAGE));
        }

        RecipeBuilder recipeBuilder = new RecipeBuilder(name, difficulty, cookTime, tagList);
        return new CreateRecipeBuildCommand(recipeBuilder);
    }

    public BuildRecipeInstructionCommand parseInstruction(String args) throws ParseException {
        requireNonNull(args);
//        CookTime cookTime;
//        Set<IngredientPortion> ingredients = new HashSet<>();
//        ArrayList<String> words = Arrays.asList(args.split("\\s"));
//
//        // Parse cooktime, if available
//        List<String> cookTimes = words.stream()
//                .filter(word -> word.matches("^c\\\\(\\p{Alnum})+")).collect(Collectors.toList());
//        if (cookTimes.size() > 1) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_RECIPE_USAGE));
//        } else {
//            cookTime = ParserUtil.parseCooktime(cookTimes.get(0));
//        }
//
//        // Parse ingredient(s), if available
//        for (int i = 0; i < words.size(); i++) {
//            if (!words.get(i).matches("^#(\\p{Alnum})+")) {
//                break;
//            }
//
//            if (i + 2 < words.size()) {
//                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_RECIPE_USAGE));
//            }
//            String name = words.get(i).replace("#", "");
//            String amt = words.get(i+1);
//            String unit = words.get(i+2);
//        }

        return new BuildRecipeInstructionCommand(new Instruction("builder instruction test123."));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
