package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.filereader.FilePath;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

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

    /*public static Set parseSet(String set) throws ParseException {
        requireNonNull(set);
        String trimmedSet = set.trim();
        if (!Set.isValidSet(trimmedSet)) {
            throw new ParseException(Tag.MESSAGE_SET_CONSTRAINTS);
        }
        return new Set(trimmedSet);
    }*/

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
     * Parses {@code String filePath} into a {@code file}.
     */
    public static FilePath parseFilePath(String filePath) throws ParseException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();
        if (!FilePath.isValidPath(trimmedFilePath)) {
            throw new ParseException(FilePath.MESSAGE_FILEPATH_CONSTRAINTS);
        }
        return new FilePath(trimmedFilePath);
    }

    /**
     * Parses a {@code String eventName} into a {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventName} is invalid.
     */
    public static EventName parseEventName(String eventName) throws ParseException {
        requireNonNull(eventName);
        String trimmedName = eventName.trim();
        if (!EventName.isValidName(trimmedName)) {
            throw new ParseException(EventName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new EventName(trimmedName);
    }

    /**
     * Parses a {@code String eventDescription} into a {@code EventDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventDescription} is invalid.
     */
    public static EventDescription parseEventDescription(String eventDescription) throws ParseException {
        requireNonNull(eventDescription);
        String trimmedDescription = eventDescription.trim();
        if (!EventDescription.isValidDescription(trimmedDescription)) {
            throw new ParseException(EventDescription.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new EventDescription(trimmedDescription);
    }

    /**
     * Parses a {@code String eventDate} into a {@code EventDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventDate} is invalid.
     */
    public static EventDate parseEventDate(String eventDate) throws ParseException {
        requireNonNull(eventDate);
        String trimmedDate = eventDate.trim();
        if (!EventDate.isValidDate(trimmedDate)) {
            throw new ParseException(EventDate.MESSAGE_DATE_CONSTRAINTS);
        }
        return new EventDate(trimmedDate);
    }

    /**
     * Parses a {@code String eventTime} into a {@code EventTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventTime} is invalid.
     */
    public static EventTime parseEventTime(String eventTime) throws ParseException {
        requireNonNull(eventTime);
        String trimmedTime = eventTime.trim();
        if (!EventTime.isValidTime(trimmedTime)) {
            throw new ParseException(EventTime.MESSAGE_TIME_CONSTRAINTS);
        }
        return new EventTime(trimmedTime);
    }

    /**
     * Parses a {@code String eventAddress} into an {@code EventAddress}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code eventAddress} is invalid.
     */
    public static EventAddress parseEventAddress(String eventAddress) throws ParseException {
        requireNonNull(eventAddress);
        String trimmedAddress = eventAddress.trim();
        if (!EventAddress.isValidAddress(trimmedAddress)) {
            throw new ParseException(EventAddress.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new EventAddress(trimmedAddress);
    }

    /**
     * Parses a {@code Collection<String> indices} into a {@code Set<Index>}.
     */
    public static Set<Index> parseIndices(Collection<String> indices) throws ParseException {
        requireNonNull(indices);
        final Set<Index> indexSet = new HashSet<>();
        for (String index : indices) {
            indexSet.add(parseIndex(index));
        }
        return indexSet;
    }

    /**
     * Parses a {@code String faculty} into an {@code Faculty}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code faculty} is invalid.
     */
    public static Faculty parseFaculty(String faculty) throws ParseException {
        requireNonNull(faculty);
        String trimmedFaculty = faculty.trim();
        if (!Faculty.isValidFaculty(faculty)) {
            throw new ParseException(Faculty.MESSAGE_FACULTY_CONSTRAINTS);
        }
        return new Faculty(trimmedFaculty);
    }
}
