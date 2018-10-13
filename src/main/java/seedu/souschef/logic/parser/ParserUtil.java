package seedu.souschef.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.souschef.commons.core.LogsCenter;

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

import seedu.souschef.model.recipe.Address;
import seedu.souschef.model.recipe.Email;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Phone;
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
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
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








}
