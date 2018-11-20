package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddProfilePhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Weight;
import seedu.address.model.attendance.AttendanceMark;
import seedu.address.model.attendance.Session;
import seedu.address.model.attendance.SessionDate;
import seedu.address.model.common.Mark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePhoto;
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
     * Checks if (@code String args) is a valid image filename or directory leading to a image file
     */

    public static String parseImageFilename(String args) throws IllegalValueException {
        requireNonNull(args);
        String file = args.trim();

        if (file.isEmpty() || !ProfilePhoto.isValidPath(file)) {
            System.out.println(ProfilePhoto.isValidPath(file));
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddProfilePhotoCommand.MESSAGE_USAGE));
        }

        return file;
    }

    /**
     * Parses a {@code String assignmentName} into an {@code AssignmentName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code assignmentName} is invalid.
     */
    public static AssignmentName parseAssignmentName(String assignmentName) throws ParseException {
        requireNonNull(assignmentName);
        String trimmedName = assignmentName.trim();
        if (!AssignmentName.isValid(trimmedName)) {
            throw new ParseException(AssignmentName.MESSAGE_CONSTRAINTS);
        }
        return new AssignmentName(trimmedName);
    }

    /**
     * Parses a {@code String weight} into an {@code Weight}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws ParseException {
        requireNonNull(weight);
        String trimmedWeight = weight.trim();
        if (!Weight.isValid(trimmedWeight)) {
            throw new ParseException(Weight.MESSAGE_CONSTRAINTS);
        }
        return new Weight(trimmedWeight);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Deadline parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Deadline.isValid(date)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDate);
    }

    /**
     * Parses a {@code String mark} into an {@code Mark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code mark} is invalid.
     */
    public static Mark parseMark(String mark) throws ParseException {
        requireNonNull(mark);
        String trimmedMark = mark.trim();
        if (!Mark.isValid(trimmedMark)) {
            throw new ParseException(Mark.MESSAGE_CONSTRAINTS);
        }
        return new Mark(trimmedMark);
    }

    /**
     * Parses a {@code String mark} into an {@code AttendanceMark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code mark} is invalid.
     */
    public static AttendanceMark parseAttendanceMark (String mark) throws ParseException {
        requireNonNull(mark);
        String trimmedMark = mark.trim();
        if (!AttendanceMark.isValid(trimmedMark)) {
            throw new ParseException(AttendanceMark.MESSAGE_CONSTRAINTS);
        }
        return new AttendanceMark(trimmedMark);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static SessionDate parseSessionDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Deadline.isValid(date)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new SessionDate(trimmedDate);
    }

    /**
     * Parses a {@code String lessonName} into an {@code LessonName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lessonName} is invalid.
     */
    public static Session parseSession(String lessonName) throws ParseException {
        requireNonNull(lessonName);
        String trimmedName = lessonName.trim();
        if (!AssignmentName.isValid(trimmedName)) {
            throw new ParseException(AssignmentName.MESSAGE_CONSTRAINTS);
        }
        return new Session(trimmedName);
    }

    /**
     * Returns a {@code Matcher} after matching the given args with the given regex.
     * @throws ParseException if args don't match the regex.
     */
    public static Matcher parseWithMatcher(String regex, String args) throws ParseException {
        Pattern formatter = Pattern.compile(regex);
        Matcher matcher = formatter.matcher(args);

        // has to be " %d " followed by anything
        boolean isMatching = matcher.matches();
        if (!isMatching) {
            throw new ParseException("");
        }
        return matcher;
    }
}
