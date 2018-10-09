package seedu.address.logic.parser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Payment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.syllabusbook.Syllabus;
import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;

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
     * Parses a {@code String amount} into an integer.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code integer} is invalid.
     */
    public static int parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Payment.isValidAmount(Integer.parseInt(trimmedAmount))) {
            throw new ParseException(Payment.MESSAGE_PAYMENT_AMOUNT_CONSTRAINTS);
        }
        return Integer.parseInt(trimmedAmount);
    }

    /**
     * Parses a {@code String month} into an integer.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code integer} is invalid.
     */
    public static int parseMonth(String month) throws ParseException {
        requireNonNull(month);
        String trimmedMonth = month.trim();
        if (!Payment.isValidMonth(Integer.parseInt(trimmedMonth))) {
            throw new ParseException(Payment.MESSAGE_PAYMENT_MONTH_CONSTRAINTS);
        }
        return Integer.parseInt(trimmedMonth);
    }

    /**
     * Parses a {@code String year} into an integer.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code integer} is invalid.
     */
    public static int parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Payment.isValidYear(Integer.parseInt(trimmedYear))) {
            throw new ParseException(Payment.MESSAGE_PAYMENT_YEAR_CONSTRAINTS);
        }
        return Integer.parseInt(trimmedYear);
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
     * Parses a {@code String syllabus} into a {@code Syllabus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code syllabus} input is invalid.
     */
    public static Syllabus parseSyllabus(String syllabus) throws ParseException {
        requireNonNull(syllabus);
        String trimmedSyllabus = syllabus.trim();
        if (!Syllabus.isValidSyllabus(trimmedSyllabus)) {
            throw new ParseException(Syllabus.MESSAGE_SYLLABUS_CONSTRAINTS);
        }
        return new Syllabus(trimmedSyllabus);
    }

}
