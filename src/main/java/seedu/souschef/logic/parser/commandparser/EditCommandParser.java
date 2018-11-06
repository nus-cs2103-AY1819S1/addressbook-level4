package seedu.souschef.logic.parser.commandparser;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_EDIT_HEALTHPLAN_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_EDIT_INGREDIENT_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_EDIT_RECIPE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_INSTRUCTION_INDEX;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CHEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_COOKTIME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CWEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_HPNAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_SCHEME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TWEIGHT;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
import seedu.souschef.commons.core.index.Index;
import seedu.souschef.logic.EditHealthPlanDescriptor;
import seedu.souschef.logic.EditRecipeDescriptor;
import seedu.souschef.logic.commands.EditCommand;
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
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.ingredient.IngredientServingUnit;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements CommandParser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand<Recipe> parseRecipe(Model model, String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DIFFICULTY, PREFIX_COOKTIME,
                        PREFIX_TAG, PREFIX_STEP, PREFIX_INSTRUCTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EDIT_RECIPE_USAGE), pe);
        }

        EditRecipeDescriptor editRecipeDescriptor = new EditRecipeDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecipeDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            editRecipeDescriptor.setDifficulty(ParserUtil.parseDifficulty(
                    argMultimap.getValue(PREFIX_DIFFICULTY).get()));
        }

        CookTime cookTime = new CookTime("PT0M");
        if (argMultimap.getValue(PREFIX_COOKTIME).isPresent()) {
            cookTime = ParserUtil.parseCooktime(argMultimap.getValue(PREFIX_COOKTIME).get());
            editRecipeDescriptor.setCooktime(cookTime);
        }

        if (argMultimap.getValue(PREFIX_INSTRUCTION).isPresent() && argMultimap.getValue(PREFIX_STEP).isPresent()) {
            Index step = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STEP).get());
            String instructionText = ParserUtil
                    .parseInstructionText(argMultimap.getValue(PREFIX_INSTRUCTION).get());
            Set<IngredientPortion> ingredients = ParserUtil
                            .parseIngredients(argMultimap.getValue(PREFIX_INSTRUCTION).get());
            Instruction instruction = new Instruction(instructionText, cookTime, ingredients);
            editRecipeDescriptor.setInstruction(step, instruction);
            editRecipeDescriptor.setCooktime(null);
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editRecipeDescriptor::setTags);

        if (argMultimap.getValue(PREFIX_STEP).isPresent() && !argMultimap.getValue(PREFIX_INSTRUCTION).isPresent()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_INSTRUCTION_EDITED);
        }
        if (!argMultimap.getValue(PREFIX_STEP).isPresent() && argMultimap.getValue(PREFIX_INSTRUCTION).isPresent()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_INSTRUCTION_EDITED);
        }
        if (!editRecipeDescriptor.isFieldEditedSpecific()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        requireNonNull(model);
        List<Recipe> lastShownList = model.getFilteredList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EDIT_RECIPE_USAGE));
        }

        Recipe toEdit = lastShownList.get(index.getZeroBased());
        Recipe edited = createEditedRecipe(toEdit, editRecipeDescriptor);

        if (!toEdit.isSame(edited) && model.has(edited)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EDIT_RECIPE_USAGE));
        }

        return new EditCommand<>(model, toEdit, edited);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditCommand<Ingredient> parseIngredient(Model model, String args) throws ParseException {
        requireNonNull(model);
        requireNonNull(args);

        List<Ingredient> lastShownList = model.getFilteredList();
        String[] tokens = args.toLowerCase().trim().split("\\s+");
        int index;
        try {
            index = Integer.parseInt(tokens[0]) - 1;
            if (index >= lastShownList.size() || tokens.length % 2 == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EDIT_INGREDIENT_USAGE));
            }
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EDIT_INGREDIENT_USAGE));
        }

        Ingredient toEdit = lastShownList.get(index);

        String name;
        Double amount;
        String servingUnit;
        Date date;

        IngredientName ingredientName = toEdit.getName();
        IngredientAmount ingredientAmount = toEdit.getAmount();
        IngredientServingUnit ingredientServingUnit = toEdit.getUnit();
        IngredientDate ingredientDate = toEdit.getDate();

        for (int i = 1; i < tokens.length; i += 2) {
            if (tokens[i].equals("name")) {
                name = tokens[i + 1].replaceAll("_", " ");
                if (!IngredientName.isValid(name)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_EDIT_INGREDIENT_USAGE));
                }
                ingredientName = new IngredientName(name);
            } else if (tokens[i].equals("amount")) {
                try {
                    amount = Double.parseDouble(tokens[i + 1]);
                } catch (NumberFormatException e) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_EDIT_INGREDIENT_USAGE));
                }
                ingredientAmount = new IngredientAmount(amount);
            } else if (tokens[i].equals("unit")) {
                servingUnit = tokens[i + 1];
                if (!IngredientServingUnit.isValid(servingUnit)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_EDIT_INGREDIENT_USAGE));
                }
                ingredientServingUnit = new IngredientServingUnit(servingUnit);
            } else if (tokens[i].equals("date")) {
                SimpleDateFormat sdf = new SimpleDateFormat();
                sdf.applyPattern("MM-dd-yyyy");
                sdf.setLenient(false);
                try {
                    date = sdf.parse(tokens[i + 1]);
                } catch (java.text.ParseException pe) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_EDIT_INGREDIENT_USAGE));
                }
                ingredientDate = new IngredientDate(date);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EDIT_INGREDIENT_USAGE));
            }
        }

        Ingredient edited = new Ingredient(ingredientName, ingredientAmount, ingredientServingUnit, ingredientDate);

        return new EditCommand<>(model, toEdit, edited);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand<HealthPlan> parseHealthPlan(Model model, String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HPNAME, PREFIX_TWEIGHT, PREFIX_CWEIGHT,
                        PREFIX_CHEIGHT, PREFIX_AGE, PREFIX_DURATION, PREFIX_SCHEME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_EDIT_HEALTHPLAN_USAGE), pe);
        }

        EditHealthPlanDescriptor editHealthPlanDescriptor = new EditHealthPlanDescriptor();
        if (argMultimap.getValue(PREFIX_HPNAME).isPresent()) {
            editHealthPlanDescriptor.setHealthPlanName(
                    ParserUtil.parseHpName(argMultimap.getValue(PREFIX_HPNAME).get()));
        }
        if (argMultimap.getValue(PREFIX_TWEIGHT).isPresent()) {
            editHealthPlanDescriptor.setTargetWeight(
                    ParserUtil.parseTWeight(argMultimap.getValue(PREFIX_TWEIGHT).get()));
        }
        if (argMultimap.getValue(PREFIX_CWEIGHT).isPresent()) {
            editHealthPlanDescriptor.setCurrentWeight(
                    ParserUtil.parseCWeight(argMultimap.getValue(PREFIX_CWEIGHT).get()));
        }
        if (argMultimap.getValue(PREFIX_CHEIGHT).isPresent()) {
            editHealthPlanDescriptor.setCurrentHeight(
                    ParserUtil.parseCHeight(argMultimap.getValue(PREFIX_CHEIGHT).get()));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            editHealthPlanDescriptor.setAge(ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editHealthPlanDescriptor.setDuration(ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
        }
        if (argMultimap.getValue(PREFIX_SCHEME).isPresent()) {
            editHealthPlanDescriptor.setScheme(ParserUtil.parseScheme(argMultimap.getValue(PREFIX_SCHEME).get()));
        }

        if (!editHealthPlanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        requireNonNull(model);
        List<HealthPlan> lastShownList = model.getFilteredList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_EDIT_HEALTHPLAN_USAGE));
        }

        HealthPlan toEdit = lastShownList.get(index.getZeroBased());
        HealthPlan edited = createEditedHealthPlan(toEdit, editHealthPlanDescriptor);

        if (!toEdit.isSame(edited) && model.has(edited)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_EDIT_HEALTHPLAN_USAGE));
        }

        return new EditCommand<>(model, toEdit, edited);
    }




    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Creates and returns a {@code Recipe} with the details of {@code toEdit}
     * edited with {@code editRecipeDescriptor}.
     */
    private static Recipe createEditedRecipe(Recipe toEdit, EditRecipeDescriptor editRecipeDescriptor)
        throws ParseException {
        assert toEdit != null;

        Name updatedName = editRecipeDescriptor.getName().orElse(toEdit.getName());
        Difficulty updatedPhone = editRecipeDescriptor.getDifficulty().orElse(toEdit.getDifficulty());
        CookTime updatedEmail = editRecipeDescriptor.getCooktime().orElse(toEdit.getCookTime());
        Set<Tag> updatedTags = editRecipeDescriptor.getTags().orElse(toEdit.getTags());
        List<Instruction> instructions = new ArrayList<>(toEdit.getInstructions());

        Pair<Index, Instruction> updatedInstruction = editRecipeDescriptor.getInstruction().orElse(null);
        if (updatedInstruction != null) {
            if (updatedInstruction.getKey().getOneBased() > 0
                    && updatedInstruction.getKey().getOneBased() <= instructions.size()) {
                instructions.set(updatedInstruction.getKey().getZeroBased(), updatedInstruction.getValue());
            } else if (updatedInstruction.getKey().getOneBased() == instructions.size() + 1) {
                instructions.add(updatedInstruction.getValue());
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_INSTRUCTION_INDEX));
            }
        }

        return new Recipe(updatedName, updatedPhone, updatedEmail, instructions, updatedTags);
    }

    /**
     * private function to handle edit logic for the healthplans
     */
    private static HealthPlan createEditedHealthPlan(HealthPlan toEdit,
                                                     EditHealthPlanDescriptor editHealthPlanDescriptor) {
        assert toEdit != null;

        HealthPlanName updatedHealthPlanName = editHealthPlanDescriptor.getHealthPlanName()
                .orElse(toEdit.getHealthPlanName());

        Age updatedAge = editHealthPlanDescriptor.getAge().orElse(toEdit.getAge());

        CurrentHeight updatedCurrentHeight = editHealthPlanDescriptor.getCurrentHeight()
                .orElse(toEdit.getCurrentHeight());
        CurrentWeight updatedCurrentWeight = editHealthPlanDescriptor.getCurrentWeight()
                .orElse(toEdit.getCurrentWeight());
        TargetWeight updatedTargetWeight = editHealthPlanDescriptor.getTargetWeight()
                .orElse(toEdit.getTargetWeight());
        Duration updatedDuration = editHealthPlanDescriptor.getDuration().orElse(toEdit.getDuration());

        Scheme updatedScheme = editHealthPlanDescriptor.getScheme().orElse(toEdit.getScheme());

        //list of meals shouldnt be edited at edit pane
        ArrayList<Day> mealsToSet = toEdit.getMealPlans();

        return new HealthPlan(updatedHealthPlanName, updatedTargetWeight, updatedCurrentWeight,
                updatedCurrentHeight, updatedAge, updatedDuration, updatedScheme, mealsToSet);

    }

}
