package ssp.scheduleplanner.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import ssp.scheduleplanner.commons.core.index.Index;
import ssp.scheduleplanner.commons.util.StringUtil;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;
import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Date;
import ssp.scheduleplanner.model.task.Interval;
import ssp.scheduleplanner.model.task.Name;
import ssp.scheduleplanner.model.task.Priority;
import ssp.scheduleplanner.model.task.Repeat;
import ssp.scheduleplanner.model.task.Venue;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String repeat} into a {@code Repeat}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code repeat} is invalid.
     */
    public static Repeat parseRepeat(String repeat) throws ParseException {
        requireNonNull(repeat);
        String trimmedRepeat = repeat.trim();
        if (!Repeat.isValidRepeat(trimmedRepeat)) {
            throw new ParseException(Repeat.MESSAGE_REPEAT_CONSTRAINTS);
        }
        return new Repeat(trimmedRepeat);
    }

    /**
     * Parses a {@code String interval} into a {@code interval}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code repeat} is invalid.
     */
    public static Interval parseInterval(String interval) throws ParseException {
        requireNonNull(interval);
        String trimmedInterval = interval.trim();
        if (!Interval.isValidInterval(trimmedInterval)) {
            throw new ParseException(Interval.MESSAGE_INTERVAL_CONSTRAINTS);
        }
        return new Interval(trimmedInterval);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String address} into an {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Venue parseVenue(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Venue.isValidAddress(trimmedAddress)) {
            throw new ParseException(Venue.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Venue(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Priority parsePriority(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Priority.isValidPriority(trimmedEmail)) {
            throw new ParseException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        return new Priority(trimmedEmail);
    }

    /**
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static String parseCategoryName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Category.isValidName(trimmedName)) {
            throw new ParseException(Category.MESSAGE_NAME_CONSTRAINTS);
        }
        return trimmedName;
    }

    /**
     * Parses {@code Collection<String> categories} into a String array of size two {@code names}.
     * names[0] contains original name of selected category,
     * names[1] contains new name of this category.
     */
    public static String[] parseCategoryNames(Collection<String> categories) throws ParseException {
        requireNonNull(categories);
        String[] names = new String[2];
        int i = 0;
        for (String categoryName: categories) {
            names[i] = parseCategoryName(categoryName);
            i++;
        }
        return names;
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
}
