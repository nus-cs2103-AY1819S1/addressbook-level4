package seedu.souschef.logic.parser.commandparser;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_ADD_FAVOURITE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_ADD_HEALTHPLAN_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_ADD_INGREDIENT_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_DUPLICATE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CHEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CWEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_HPNAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TWEIGHT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import seedu.souschef.logic.commands.AddCommand;
import seedu.souschef.logic.parser.ArgumentMultimap;
import seedu.souschef.logic.parser.ArgumentTokenizer;
import seedu.souschef.logic.parser.ParserUtil;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.healthplan.Age;
import seedu.souschef.model.healthplan.CurrentHeight;
import seedu.souschef.model.healthplan.CurrentWeight;
import seedu.souschef.model.healthplan.Duration;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.healthplan.HealthPlanName;
import seedu.souschef.model.healthplan.Scheme;
import seedu.souschef.model.healthplan.TargetWeight;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.ingredient.IngredientAmount;
import seedu.souschef.model.ingredient.IngredientDate;
import seedu.souschef.model.ingredient.IngredientName;
import seedu.souschef.model.ingredient.IngredientServingUnit;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.Recipe;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {
    private static final String MESSAGE_INGREDIENT_PROMPT_EDIT = "Ingredient with the same name and date already "
            + "exist! Please use edit function instead.";
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand<Ingredient> parseIngredient(Model model, String args) throws ParseException {
        requireNonNull(model);
        requireNonNull(args);

        String[] tokens = args.toLowerCase().trim().split("\\s+");
        if (tokens.length != 4) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_INGREDIENT_USAGE));
        }

        String name = tokens[0].replaceAll("_", " ");
        String unit = tokens[2];
        if (!(IngredientName.isValid(name) && IngredientServingUnit.isValid(unit))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_INGREDIENT_USAGE));
        }
        IngredientName ingredientName = new IngredientName(name);
        IngredientServingUnit ingredientServingUnit = new IngredientServingUnit(unit);

        double amount;
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM-dd-yyyy");
        sdf.setLenient(false);
        try {
            amount = Double.parseDouble(tokens[1]);
            date = sdf.parse(tokens[3]);
        } catch (NumberFormatException ne) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_INGREDIENT_USAGE));
        } catch (java.text.ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_INGREDIENT_USAGE));
        }

        IngredientAmount ingredientAmount = new IngredientAmount(amount);
        IngredientDate ingredientDate = new IngredientDate(date);

        Ingredient toAdd = new Ingredient(ingredientName, ingredientAmount,
                ingredientServingUnit, ingredientDate).convertToCommonUnit();

        if (model.has(toAdd)) {
            throw new ParseException(MESSAGE_INGREDIENT_PROMPT_EDIT);
        }

        return new AddCommand<>(model, toAdd);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand<HealthPlan> parseHealthPlan(Model model, String args) throws ParseException {
        requireNonNull(model);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HPNAME, PREFIX_TWEIGHT, PREFIX_CWEIGHT,
                        PREFIX_CHEIGHT, PREFIX_AGE, PREFIX_DURATION);

        if (!argMultimap.arePrefixesPresent(PREFIX_HPNAME, PREFIX_AGE, PREFIX_TWEIGHT, PREFIX_CWEIGHT, PREFIX_CHEIGHT,
                PREFIX_AGE, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_HEALTHPLAN_USAGE));
        }
        HealthPlanName healthPlanName = ParserUtil.parseHpName(argMultimap.getValue(PREFIX_HPNAME).get());
        TargetWeight targetWeight = ParserUtil.parseTWeight(argMultimap.getValue(PREFIX_TWEIGHT).get());
        CurrentWeight currentWeight = ParserUtil.parseCWeight(argMultimap.getValue(PREFIX_CWEIGHT).get());
        CurrentHeight currentHeight = ParserUtil.parseCHeight(argMultimap.getValue(PREFIX_CHEIGHT).get());
        Age age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());

        //determine the scheme base on current and target weight
        double difference = Double.parseDouble(targetWeight.value) - Double.parseDouble(currentWeight.value);
        Scheme scheme;
        //gain
        if (difference > 0) {
            scheme = Scheme.GAIN;
        } else if (difference == 0) {
            scheme = Scheme.MAINTAIN;
        } else {
            scheme = Scheme.LOSS;
        }

        //generating a new plan, no list (empty by default)
        HealthPlan toAdd = new HealthPlan(healthPlanName, targetWeight,
                currentWeight, currentHeight, age, duration, scheme, new ArrayList<Day>());
        if (model.has(toAdd)) {
            throw new ParseException(String.format(MESSAGE_DUPLICATE, "plan name"));
        }

        return new AddCommand<>(model, toAdd);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddFavouriteCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddCommand<Recipe> parseFav(Model fav, Model<Recipe> recipes, String args) throws ParseException {

        if (!args.trim().matches("^[1-9]\\d*$")) {
            throw new ParseException(MESSAGE_ADD_FAVOURITE_USAGE);
        }

        int index = Integer.parseInt(args.trim()) - 1;
        Recipe recipe = recipes.getFilteredList().get(index);



        int size = recipes.getFilteredList().size();
        if (index < 0 || index >= size) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_FAVOURITE_USAGE));
        }

        //detect if the recipe already exists in the list
        if (fav.getAppContent().getObservableFavouritesList().contains(recipe)) {
            throw new ParseException(String.format(MESSAGE_DUPLICATE, "favourite"));
        }

        return new AddCommand<>(fav, recipe);
    }

}
