package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.EventName;
import seedu.address.model.event.RepeatType;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final Parser NATTY_PARSER = new Parser();
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_FAILED_DATE_TIME_PARSE = "Natural language date time parsing failed";
    public static final String MESSAGE_FAILED_REPEAT_TYPE_PARSE = "Repeat type is not valid";

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
     * Parses a {@code String eventName} into a {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventName} is invalid.
     */
    public static EventName parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        String trimmedEventName = eventName.trim();
        if (!EventName.isValidEventName(eventName)) {
            throw new ParseException(EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        return new EventName(trimmedEventName);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String venue} into a {@code Venue}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Venue parseVenue(String venue) {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        return new Venue(trimmedVenue);
    }

    /**
     * Parses a {@code String eventName} into a {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventName} is invalid.
     */
    public static DateTime parseDateTime(String unformattedDateTime) throws ParseException {
        requireNonNull(unformattedDateTime);
        List<DateGroup> groups = NATTY_PARSER.parse(unformattedDateTime.trim());
        if (groups.size() != 1) {
            throw new ParseException(String.format(MESSAGE_FAILED_DATE_TIME_PARSE, unformattedDateTime));
        }
        List<Date> dates = groups.get(0).getDates();
        if (dates.size() != 1) {
            throw new ParseException(String.format(MESSAGE_FAILED_DATE_TIME_PARSE, unformattedDateTime));
        }
        LocalDateTime formattedDateTime = dates.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return new DateTime(formattedDateTime);
    }

    /**
     * Parses a {@code String repeatType} into a {@code RepeatType}.
     *
     * @throws ParseException if the given {@code repeatType} is invalid.
     */
    public static RepeatType parseRepeatType(String repeatType) throws ParseException {
        requireNonNull(repeatType);
        String trimmedRepeatType = repeatType.trim();
        if (trimmedRepeatType.isEmpty() || trimmedRepeatType.equalsIgnoreCase(RepeatType.NONE.name())) {
            return RepeatType.NONE;
        } else if (trimmedRepeatType.equalsIgnoreCase(RepeatType.DAILY.name())) {
            return RepeatType.DAILY;
        } else if (trimmedRepeatType.equalsIgnoreCase(RepeatType.WEEKLY.name())) {
            return RepeatType.WEEKLY;
        } else if (trimmedRepeatType.equalsIgnoreCase(RepeatType.MONTHLY.name())) {
            return RepeatType.MONTHLY;
        } else if (trimmedRepeatType.equalsIgnoreCase(RepeatType.YEARLY.name())) {
            return RepeatType.YEARLY;
        } else {
            throw new ParseException(String.format(MESSAGE_FAILED_REPEAT_TYPE_PARSE, repeatType));
        }
    }
}
