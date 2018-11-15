package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventName;
import seedu.address.model.interest.Interest;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_EMPTY_STRING = "String argument provided should not be empty.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * Parses a {@code String password} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
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
     * Parses a {@code String eventName} into an {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventName} is invalid.
     */
    public static EventName parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        String trimmedEventName = eventName.trim();
        if (!Address.isValidAddress(trimmedEventName)) {
            throw new ParseException(EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        return new EventName(trimmedEventName);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            LocalDate newDate = LocalDate.parse(trimmedDate, DateTimeFormatter.ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT));
            return newDate;
        } catch (DateTimeParseException e) {
            throw new ParseException(Messages.MESSAGE_WRONG_DATE_FORMAT);
        }
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        try {
            LocalTime newTime = LocalTime.parse(trimmedTime, DateTimeFormatter.ofPattern("HH:mm"));
            return newTime;
        } catch (DateTimeParseException e) {
            throw new ParseException(Messages.MESSAGE_WRONG_TIME_FORMAT);
        }
    }

    /**
     * Parses a {@code String interest} into a {@code Interest}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code interest} is invalid.
     */
    public static Interest parseInterest(String interest) throws ParseException {
        requireNonNull(interest);
        String trimmedInterest = interest.trim();
        if (!Interest.isValidInterestName(trimmedInterest)) {
            throw new ParseException(Interest.MESSAGE_INTEREST_CONSTRAINTS);
        }
        return new Interest(trimmedInterest);
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
     * Parses a {@code String friend} into a {@code Friend}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code friend} is invalid.
     */
    public static Friend parseFriend(String friend) {
        requireNonNull(friend);
        String trimmedFriend = friend.trim();
        return new Friend(trimmedFriend);
    }

    /**
     * Parses a {@code String} into a {@code Schedule}.
     * @param schedule
     *
     * @throws ParseException
     */
    public static Schedule parseSchedule(String schedule) throws ParseException {
        requireNonNull(schedule);
        String trimmedSchedule = schedule.trim();
        if (!Schedule.isValidSchedule(trimmedSchedule)) {
            throw new ParseException(Schedule.MESSAGE_SCHEDULE_CONSTRAINTS);
        }
        return new Schedule(schedule);

    }

    /**
     * Parses {@code Collection<String> interests} into a {@code Set<Interest>}.
     */
    public static Set<Interest> parseInterests(Collection<String> interests) throws ParseException {
        requireNonNull(interests);
        final Set<Interest> interestSet = new HashSet<>();
        for (String interestName : interests) {
            interestSet.add(parseInterest(interestName));
        }
        return interestSet;
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
     * Parses a {@code String stringArgument} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String parseGenericString(String stringArgument) throws ParseException {
        requireNonNull(stringArgument);
        String string = stringArgument.trim();
        if (string.equals("")) {
            throw new ParseException(MESSAGE_EMPTY_STRING);
        }
        return string;
    }
}
