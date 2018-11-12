package tutorhelper.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tutorhelper.commons.core.index.Index;
import tutorhelper.commons.util.StringUtil;
import tutorhelper.logic.parser.exceptions.ParseException;
import tutorhelper.model.student.Address;
import tutorhelper.model.student.Email;
import tutorhelper.model.student.Name;
import tutorhelper.model.student.Payment;
import tutorhelper.model.student.Phone;
import tutorhelper.model.subject.Subject;
import tutorhelper.model.subject.SubjectType;
import tutorhelper.model.subject.Syllabus;
import tutorhelper.model.tag.Tag;
import tutorhelper.model.tuitiontiming.TuitionTiming;

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
     * Parses a {@code String subject} into a {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject;
        boolean result;

        try {
            trimmedSubject = subject.trim();
            result = SubjectType.isValidSubjectName(trimmedSubject);
        } catch (IllegalArgumentException err) {
            throw new ParseException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }

        if (!result) {
            throw new ParseException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        return Subject.makeSubject(trimmedSubject);
    }

    /**
     * Parses {@code Collection<String> subjects} into a {@code Set<Subject>}.
     */
    public static Set<Subject> parseSubjects(String subjects) throws ParseException {
        requireNonNull(subjects);
        final Set<Subject> subjectSet = new HashSet<>();
        String trimmedSubjects = subjects.trim();
        String[] separatedSubjects = trimmedSubjects.split(",");
        for (String subjectName : separatedSubjects) {
            subjectSet.add(parseSubject(subjectName));
        }
        return subjectSet;
    }

    /**
     * Parses a {@code String tuitionTiming} into a {@code TuitionTiming}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tuitionTiming} is invalid.
     */
    public static TuitionTiming parseTuitionTiming(String tuitionTiming) throws ParseException {
        requireNonNull(tuitionTiming);
        String trimmedTuitionTiming = tuitionTiming.trim();
        if (!TuitionTiming.isValidTiming(trimmedTuitionTiming)) {
            throw new ParseException(TuitionTiming.MESSAGE_TUITION_TIMING_CONSTRAINTS);
        }
        return new TuitionTiming(trimmedTuitionTiming);
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

        for (int i = 0; i < trimmedAmount.length(); i++) {
            if (!Character.isDigit(trimmedAmount.charAt(i))) {
                throw new ParseException(Payment.MESSAGE_PAYMENT_AMOUNT_CONSTRAINTS);
            }
        }

        if ((!StringUtil.isNonZeroUnsignedInteger(trimmedAmount))
                || !Payment.isValidAmount(Integer.parseInt(trimmedAmount))) {
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
        for (int i = 0; i < trimmedMonth.length(); i++) {
            if (!Character.isDigit(trimmedMonth.charAt(i))) {
                throw new ParseException(Payment.MESSAGE_PAYMENT_MONTH_CONSTRAINTS);
            }
        }

        if ((!StringUtil.isNonZeroUnsignedInteger(trimmedMonth)
                || !Payment.isValidMonth(Integer.parseInt(trimmedMonth)))) {
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

        for (int i = 0; i < trimmedYear.length(); i++) {
            if (!Character.isDigit(trimmedYear.charAt(i))) {
                throw new ParseException(Payment.MESSAGE_PAYMENT_YEAR_CONSTRAINTS);
            }
        }

        if ((!StringUtil.isNonZeroUnsignedInteger(trimmedYear)
                || !Payment.isValidYear(Integer.parseInt(trimmedYear)))) {
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
    public static Set<Tag> parseTags(String tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        if (tags.length() == 0) { // We allow no tag input
            return tagSet;
        }
        String trimmedTags = tags.trim();
        String[] separatedTags = trimmedTags.split(",");
        for (String tag : separatedTags) {
            tagSet.add(ParserUtil.parseTag(tag));
        }
        return tagSet;
    }

    /**
     * Parses {@code oneBasedIndexes} into a list of {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndexes(String oneBasedIndexes) throws ParseException {
        String trimmedIndexes = oneBasedIndexes.trim();
        String[] separatedIndexes = trimmedIndexes.split("\\s");
        List<Index> listIndexes = new ArrayList<>();

        for (String indexCandidate : separatedIndexes) {
            listIndexes.add(ParserUtil.parseIndex(indexCandidate));
        }

        return listIndexes;
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
        return new Syllabus(trimmedSyllabus, false);
    }

    /**
     * Parses {@code Collection<String> syllabuses} into a {@code List<Syllabus>}.
     */
    public static List<Syllabus> parseSyllabuses(String syllabuses) throws ParseException {
        requireNonNull(syllabuses);
        final List<Syllabus> syllabusList = new ArrayList<>();
        String trimmedSyllabuses = syllabuses.trim();
        String[] separatedSyllabuses = trimmedSyllabuses.split(",");

        for (String syllabus: separatedSyllabuses) {
            syllabusList.add(ParserUtil.parseSyllabus(syllabus));
        }
        return syllabusList;
    }

}
