package seedu.souschef.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.core.Messages.MESSAGE_ADD_INGREDIENT_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_INGREDIENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.core.Messages;
import seedu.souschef.commons.core.index.Index;
import seedu.souschef.commons.util.StringUtil;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.healthplan.Age;
import seedu.souschef.model.healthplan.CurrentHeight;
import seedu.souschef.model.healthplan.CurrentWeight;
import seedu.souschef.model.healthplan.Duration;
import seedu.souschef.model.healthplan.HealthPlanName;
import seedu.souschef.model.healthplan.Scheme;
import seedu.souschef.model.healthplan.TargetWeight;
import seedu.souschef.model.ingredient.IngredientAmount;
import seedu.souschef.model.ingredient.IngredientName;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.ingredient.IngredientServingUnit;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final Logger logger = LogsCenter.getLogger(ParserUtil.class);
    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Difficulty parseDifficulty(String difficulty) throws ParseException {
        requireNonNull(difficulty);
        String trimmedDiff = difficulty.trim();
        if (!Difficulty.isValidDifficulty(trimmedDiff)) {
            throw new ParseException(Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);
        }
        return new Difficulty(trimmedDiff);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static CookTime parseCooktime(String cooktime) throws ParseException {
        requireNonNull(cooktime);
        String trimmedCooktime = cooktime.trim();
        if (!CookTime.isValidCookTime(trimmedCooktime)) {
            throw new ParseException(CookTime.MESSAGE_COOKTIME_CONSTRAINTS);
        }
        return new CookTime(trimmedCooktime);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String instruction} into an Instruction value.
     * Leading and trailing whitespaces will be trimmed.
     * Unrelated PREFIXs will be removed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static String parseInstructionText(String instruction) throws ParseException {
        requireNonNull(instruction);

        String trimmedInstruction = instruction.trim();

        //Remove ingredients prefix
        trimmedInstruction = trimmedInstruction.replaceAll(PREFIX_INGREDIENT.getPrefix(), "");

        if (!Instruction.isValidInstruction(trimmedInstruction)) {
            throw new ParseException(Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS);
        }
        return trimmedInstruction;
    }

    /**
     * Parses a {@code String ingredients} into a set of ingredient portions.
     * PREFIX will be removed.
     * Each ingredient string will be parsed into ingredient name, amount and unit.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Set<IngredientPortion> parseIngredients(String instruction) throws ParseException {
        requireNonNull(instruction);
        Set<IngredientPortion> ingredients = new HashSet<>();

        String trimmedInstruction = instruction.trim();
        List<String> rawIngredients = new ArrayList<>(Arrays.asList(trimmedInstruction.split("#")));
        // Ingredient only comes after the prefix.
        rawIngredients.remove(0);

        //Contains no hash tag
        if (rawIngredients.size() < 1) {
            return ingredients;
        }
        Iterator<String> it = rawIngredients.iterator();
        while (it.hasNext()) {
            String rawIngredient = it.next();
            IngredientPortion ip = parseIngredient(rawIngredient.trim().replace("#", ""));
            ingredients.add(ip);
        }
        return ingredients;
    }

    /**
     * Parses a {@code String ingredient} into a ingredient portion.
     * Ingredient string will be parsed into ingredient name, amount and unit.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    private static IngredientPortion parseIngredient(String rawIngredient) throws ParseException {
        Pattern pattern = Pattern
                .compile(IngredientPortion.INGREDIENTPORTION_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(rawIngredient);
        if (!matcher.find()) {
            throw new ParseException(IngredientPortion.MESSAGE_INGREDIENTPORTION_CONSTRAINTS);
        }
        if (!IngredientServingUnit.isValid(matcher.group("unit"))) {
            throw new ParseException(IngredientServingUnit.MESSAGE_UNIT_CONSTRAINTS
                    + " Use following values: " + IngredientServingUnit.allUnits());
        }
        IngredientName name = new IngredientName(matcher.group("name").trim());
        double amount;
        try {
            amount = Double.parseDouble(matcher.group("amt"));
        } catch (NumberFormatException ne) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_ADD_INGREDIENT_USAGE));
        }
        IngredientAmount amt = new IngredientAmount(amount);
        IngredientServingUnit unit = new IngredientServingUnit(matcher.group("unit"));
        return new IngredientPortion(name, unit, amt);
    }

    /**
     * parse plan names for commands
     * parse plan name
     */
    public static HealthPlanName parseHpName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!HealthPlanName.isValidName(trimmedName)) {
            throw new ParseException(HealthPlanName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new HealthPlanName(trimmedName);
    }
    /**
     * parse target weight for commands
     * parse tweight
     */
    public static TargetWeight parseTWeight(String weight) throws ParseException {
        requireNonNull(weight);
        String trimmedWeight = weight.trim();
        if (!TargetWeight.isValidWeight(trimmedWeight)) {
            throw new ParseException(CurrentWeight.MESSAGE_WEIGHT_CONSTRAINTS);
        }

        if (trimmedWeight.indexOf(".") == -1) {
            trimmedWeight = trimmedWeight + ".0";
        }

        return new TargetWeight(trimmedWeight);
    }

    /**
     * parse current weight for commands
     * parse cweight
     */
    public static CurrentWeight parseCWeight(String weight) throws ParseException {
        requireNonNull(weight);
        String trimmedWeight = weight.trim();
        if (!CurrentWeight.isValidWeight(trimmedWeight)) {
            throw new ParseException(CurrentWeight.MESSAGE_WEIGHT_CONSTRAINTS);
        }

        if (trimmedWeight.indexOf(".") == -1) {
            trimmedWeight = trimmedWeight + ".0";
        }

        return new CurrentWeight(trimmedWeight);
    }

    /**
     * parse current height for commands
     * parse cheight
     */
    public static CurrentHeight parseCHeight(String height) throws ParseException {
        requireNonNull(height);
        String trimmedHeight = height.trim();
        if (!CurrentHeight.isValidHeight(trimmedHeight)) {
            throw new ParseException(CurrentHeight.MESSAGE_HEIGHT_CONSTRAINTS);
        }
        return new CurrentHeight(trimmedHeight);
    }


    /**
     * parse age for commands
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();

        if (!Age.isValidAge((trimmedAge))) {
            throw new ParseException(Age.MESSAGE_AGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }

    /**
     * parse duration for commands
     */
    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        String trimmedDuration = duration.trim();

        if (!Duration.isValidDuration((trimmedDuration))) {
            throw new ParseException(Duration.MESSAGE_DURATION_CONSTRAINTS);
        }
        return new Duration(trimmedDuration);
    }

    /**
     * parse scheme
     */
    public static Scheme parseScheme(String scheme) throws ParseException {
        requireNonNull(scheme);
        String trimmedScheme = scheme.trim();
        logger.info(trimmedScheme);

        if (!"LOSS".equals(trimmedScheme) && !"GAIN".equals(trimmedScheme) && !"MAINTAIN".equals(trimmedScheme)) {
            throw new ParseException("invalid scheme");

        }
        return Scheme.valueOf(trimmedScheme);
    }


    public static String parsePlanIndex(String index) throws ParseException {
        String regex ="^[0-9]+$";
        requireNonNull(index);
        String trimmedIndex = index.trim();



        if (trimmedIndex.length() == 0) {
            throw new ParseException(Messages.MESSAGE_INVALID_PLAN_INDEX);
        }

        if (!trimmedIndex.matches(regex)) {
            throw new ParseException(Messages.MESSAGE_INVALID_PLAN_INDEX);
        }
        return trimmedIndex;
    }

    public static String parseDayIndex(String index) throws ParseException {
        String regex ="^[0-9]+$";
        requireNonNull(index);
        String trimmedIndex = index.trim();


        if (trimmedIndex.length() == 0) {
            throw new ParseException(Messages.MESSAGE_INVALID_DAY_INDEX);

        }

        if (!trimmedIndex.matches(regex)) {
            throw new ParseException(Messages.MESSAGE_INVALID_DAY_INDEX);
        }
        return trimmedIndex;
    }

}
