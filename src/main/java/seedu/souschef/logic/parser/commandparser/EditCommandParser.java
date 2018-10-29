package seedu.souschef.logic.parser.commandparser;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_EDIT_HEALTHPLAN_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_EDIT_INGREDIENT_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_EDIT_RECIPE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import seedu.souschef.model.ingredient.IngredientServingUnit;
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

        if (argMultimap.getValue(PREFIX_STEP).isPresent()) {
            
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRecipeDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            editRecipeDescriptor.setDifficulty(ParserUtil.parseDifficulty(
                    argMultimap.getValue(PREFIX_DIFFICULTY).get()));
        }
        if (argMultimap.getValue(PREFIX_COOKTIME).isPresent()) {
            editRecipeDescriptor.setCooktime(ParserUtil.parseCooktime(argMultimap.getValue(PREFIX_COOKTIME).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editRecipeDescriptor::setTags);

        if (!editRecipeDescriptor.isAnyFieldEdited()) {
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
        String[] tokens = args.trim().split("\\s+");
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

        IngredientName name = toEdit.getName();
        IngredientAmount amount = toEdit.getAmount();
        IngredientServingUnit unit = toEdit.getUnit();
        IngredientDate date = toEdit.getDate();

        for (int i = 1; i < tokens.length; i += 2) {
            if (tokens[i].equals("name")) {
                name = new IngredientName(tokens[i + 1]);
                if (!name.isValid()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_EDIT_INGREDIENT_USAGE));
                }
            } else if (tokens[i].equals("amount")) {
                try {
                    amount = new IngredientAmount(tokens[i + 1]);
                } catch (NumberFormatException e) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_EDIT_INGREDIENT_USAGE));
                }
            } else if (tokens[i].equals("unit")) {
                unit = new IngredientServingUnit(tokens[i + 1]);
                if (!unit.isValid()) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_EDIT_INGREDIENT_USAGE));
                }
            } else if (tokens[i].equals("date")) {
                try {
                    date = new IngredientDate(tokens[i + 1]);
                } catch (java.text.ParseException pe) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MESSAGE_EDIT_INGREDIENT_USAGE));
                }
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_EDIT_INGREDIENT_USAGE));
            }
        }

        Ingredient edited = new Ingredient(name, amount, unit, date);

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
    private static Recipe createEditedRecipe(Recipe toEdit, EditRecipeDescriptor editRecipeDescriptor) {
        assert toEdit != null;

        Name updatedName = editRecipeDescriptor.getName().orElse(toEdit.getName());
        Difficulty updatedPhone = editRecipeDescriptor.getDifficulty().orElse(toEdit.getDifficulty());
        CookTime updatedEmail = editRecipeDescriptor.getCooktime().orElse(toEdit.getCookTime());
        Set<Tag> updatedTags = editRecipeDescriptor.getTags().orElse(toEdit.getTags());
        List<Instruction> instructions = new ArrayList<>(toEdit.getInstructions());

        Pair<Integer, Instruction> updatedInstruction = editRecipeDescriptor.getInstruction().orElse(null);
        if (updatedInstruction != null) {
            if (updatedInstruction.getKey() > 0 && updatedInstruction.getKey() <= instructions.size()) {
                instructions.set(updatedInstruction.getKey() - 1, updatedInstruction.getValue());
            } else if (updatedInstruction.getKey() == instructions.size() + 1) {
                instructions.add(updatedInstruction.getValue());
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

        return new HealthPlan(updatedHealthPlanName, updatedTargetWeight, updatedCurrentWeight,
                updatedCurrentHeight, updatedAge, updatedDuration, updatedScheme);

    }

}
