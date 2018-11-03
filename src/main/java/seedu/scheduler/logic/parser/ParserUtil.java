package seedu.scheduler.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.LIST_OF_ALL_FLAG;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.commons.util.StringUtil;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final Parser NATTY_PARSER = new Parser();
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_FAILED_DATE_TIME_PARSE = "Natural language date time parsing failed";
    public static final String MESSAGE_FAILED_DURATION_PARSE = "Reminder Time is not valid. Please enter xxHxxMxxS";
    public static final String MESSAGE_FAILED_REPEAT_TYPE_PARSE = "Repeat type is not valid";
    public static final String MESSAGE_FAILED_FLAG_PARSE = "Input flag is not valid";
    public static final String EMPTY_STRING = "";

    /**
     * Gets the integer value of {@code argsString} and parses it into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String argsString) throws ParseException {
        String trimmedIndex = argsString.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code List<Flag> flags} into a {@code Set<Flag>}.
     */
    public static Set<Flag> parseFlags(List<Flag> flags) throws ParseException {
        requireAllNonNull(flags);
        final Set<Flag> flagSet = new HashSet<>();
        for (Flag flag : flags) {
            if (!LIST_OF_ALL_FLAG.contains(flag)) {
                throw new ParseException(MESSAGE_FAILED_FLAG_PARSE);
            }
            flagSet.add(flag);
        }
        return flagSet;
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
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
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

    /**
     *
     * @param reminderDuration a string representing a duration
     * @return a duration object
     * @throws ParseException
     */
    public static Duration parseReminderDuration(String reminderDuration) throws ParseException {
        requireNonNull(reminderDuration);
        String parseDuration = reminderDuration.trim();
        parseDuration.replace(" ", "");
        parseDuration = "PT".concat(parseDuration.replace("d", "D"));
        parseDuration = parseDuration.replace("h", "H");
        parseDuration = parseDuration.replace("min", "m").toUpperCase();
        parseDuration = parseDuration.replace("sec", "s").toUpperCase();
        try {
            Duration duration = Duration.parse(parseDuration);
            return duration;
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_FAILED_DURATION_PARSE));
        }


    }

    /**
     * Parses {@code Collection<String> reminderTimes} into a {@code Set<DateTime>}.
     */
    public static ReminderDurationList parseReminderDurations(Collection<String> reminderDurations)
            throws ParseException {
        requireNonNull(reminderDurations);
        ReminderDurationList reminderDurationList = new ReminderDurationList();
        for (String reminderDuration : reminderDurations) {
            if (reminderDuration.equals(EMPTY_STRING)) {
                return reminderDurationList;
            }
            reminderDurationList.add(parseReminderDuration(reminderDuration));
        }
        return reminderDurationList;
    }

}
