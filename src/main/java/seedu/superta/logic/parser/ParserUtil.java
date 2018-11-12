package seedu.superta.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.superta.commons.core.index.Index;
import seedu.superta.commons.util.StringUtil;
import seedu.superta.logic.parser.exceptions.ParseException;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.Email;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Name;
import seedu.superta.model.student.Phone;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tag.Tag;

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
     * Parses a {@code String studentId} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedStudentId = studentId.trim().toUpperCase();
        if (!StudentId.isValidStudentId(trimmedStudentId)) {
            throw new ParseException(StudentId.MESSAGE_STUDENT_ID_CONSTRAINTS);
        }
        return new StudentId(trimmedStudentId);
    }

    public static String parseTutorialGroupName(String tgName) {
        return tgName.trim();
    }

    public static String parseTutorialGroupId(String tgId) {
        return tgId.trim();
    }

    /**
     * Parses a {@code Title title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String assignmentTitle) throws ParseException {
        requireNonNull(assignmentTitle);
        String trimmedTitle = assignmentTitle.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String maxMarks} into a {@code Double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code maxMarks} is invalid.
     */
    public static Double parseMaxMarks(String maxMarks) throws ParseException {
        requireNonNull(maxMarks);
        Double trimmedMaxMarks = Double.parseDouble(maxMarks.trim());
        if (trimmedMaxMarks <= 0) {
            throw new ParseException(Assignment.MESSAGE_MAXMARKS_CONSTRAINTS);
        }
        return Double.parseDouble(maxMarks.trim());
    }

    /**
     * Parses a {@code String marks} into a {@code Double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code maxMarks} is invalid.
     */
    public static Double parseGrade(String grade) throws ParseException {
        requireNonNull(grade);
        Double trimmedGrade = Double.parseDouble(grade.trim());
        if (trimmedGrade < 0) {
            throw new ParseException(Assignment.MESSAGE_GRADE_CONSTRAINTS);
        }
        return Double.parseDouble(grade.trim());
    }

    /**
     * Parses a {@code String feedback} into a {@code Feedback}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code feedback} is invalid.
     */
    public static Feedback parseFeedback(String feedback) {
        requireNonNull(feedback);
        return new Feedback(feedback.trim());
    }

    /**
     * Parses a {@code String sessionName} into a {@code Session}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code sessionName} is invalid.
     */
    public static Session parseSession(String sessionName) {
        requireNonNull(sessionName);
        return new Session(sessionName.trim());
    }

    public static String parseString(String str) {
        return str.trim();
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
     * Parses {@code Collection<String> studentIds} into a {@code Set<StudentId>}.
     */
    public static Set<StudentId> parseStudentIds(Collection<String> studentIds) throws ParseException {
        requireNonNull(studentIds);
        final Set<StudentId> studentIdSet = new HashSet<>();
        for (String stId : studentIds) {
            studentIdSet.add(parseStudentId(stId));
        }
        return studentIdSet;
    }

    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
