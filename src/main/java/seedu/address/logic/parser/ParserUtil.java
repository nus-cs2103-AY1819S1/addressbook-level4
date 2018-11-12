package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.SimpleParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Frequency;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_FILENAME = "Invalid filename! File name can only contain alphanumeric"
            + " characters, full stop and the underscore [_] characters";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws SimpleParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws SimpleParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new SimpleParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws SimpleParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws SimpleParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new SimpleParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws SimpleParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws SimpleParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new SimpleParseException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    /**
     * Parses a {@code String frequency} into a {@code Frequency}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws SimpleParseException if the given {@code frequency} is invalid.
     */
    public static Frequency parseFrequency(String frequency) throws SimpleParseException {
        requireNonNull(frequency);
        String trimmedFrequency = frequency.trim();
        if (!Frequency.isValidFrequency(trimmedFrequency)) {
            throw new SimpleParseException(Frequency.MESSAGE_FREQUENCY_CONSTRAINTS);
        }
        return new Frequency(trimmedFrequency);
    }

    /**
     * Parses a {@code String deadline} into a {@code Deadline}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws SimpleParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws SimpleParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        try {
            return new Deadline(trimmedDeadline);
        } catch (IllegalArgumentException e) {
            throw new SimpleParseException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS, e);
        }
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws SimpleParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws SimpleParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new SimpleParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws SimpleParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
