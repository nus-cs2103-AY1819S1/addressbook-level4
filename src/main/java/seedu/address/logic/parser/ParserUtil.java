package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
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
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code modulecode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ModuleCode.isValidCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_MODULECODE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses a {@code String moduleTitle} into a {@code ModuleTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleTitle} is invalid.
     */
    public static ModuleTitle parseModuleTitle(String moduleTitle) throws ParseException {
        requireNonNull(moduleTitle);
        String trimmedModuleTitle = moduleTitle.trim();
        if (!ModuleTitle.isValidTitle(trimmedModuleTitle)) {
            throw new ParseException(ModuleTitle.MESSAGE_MODULETITLE_CONSTRAINTS);
        }
        return new ModuleTitle(trimmedModuleTitle);
    }

    /**
     * Parses a {@code String academicYear} into an {@code AcademicYear}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code academicYear} is invalid.
     */
    public static AcademicYear parseAcademicYear(String academicYear) throws ParseException {
        requireNonNull(academicYear);
        String trimmedAcademicYear = academicYear.trim();
        if (!AcademicYear.isValidYear(trimmedAcademicYear)) {
            throw new ParseException(AcademicYear.MESSAGE_ACADEMICYEAR_CONSTRAINTS);
        }
        return new AcademicYear(trimmedAcademicYear);
    }

    /**
     * Parses a {@code String semester} into a {@code Semester}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code semester} is invalid.
     */
    public static Semester parseSemester(String semester) throws ParseException {
        requireNonNull(semester);
        String trimmedSemester = semester.trim();
        if (!Semester.isValidSemester(trimmedSemester)) {
            throw new ParseException(Semester.MESSAGE_SEMESTER_CONSTRAINTS);
        }
        return new Semester(trimmedSemester);
    }

    /**
     * Parses an {@code String occasionName} into an {@code OccasionName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code occasionName} is invalid.
     */
    public static OccasionName parseOccasionName(String occasionName) throws ParseException {
        requireNonNull(occasionName);
        String trimmedOccasionName = occasionName.trim();
        if (!OccasionName.isValidName(trimmedOccasionName)) {
            throw new ParseException(OccasionName.MESSAGE_OCCASIONNAME_CONSTRAINTS);
        }
        return new OccasionName(trimmedOccasionName);
    }

    /**
     * Parses an {@code String occasionDate} into an {@code OccasionDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code occasionDate} is invalid.
     */
    public static OccasionDate parseOccasionDate(String occasionDate) throws ParseException {
        requireNonNull(occasionDate);
        String trimmedOccasionDate = occasionDate.trim();
        if (!OccasionDate.isValidDate(trimmedOccasionDate)) {
            throw new ParseException(OccasionDate.MESSAGE_OCCASIONDATE_CONSTRAINTS);
        }
        return new OccasionDate(trimmedOccasionDate);
    }

    /**
     * Parses an {@code String occasionLocation} into an {@code OccasionLocation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code occasionLocation} is invalid.
     */
    public static OccasionLocation parseOccasionLocation(String occasionLocation) throws ParseException {
        requireNonNull(occasionLocation);
        String trimmedOccasionLocation = occasionLocation.trim();
        if (!OccasionLocation.isValidLocation(trimmedOccasionLocation)) {
            throw new ParseException(OccasionLocation.MESSAGE_OCCASIONLOCATION_CONSTRAINTS);
        }
        return new OccasionLocation(trimmedOccasionLocation);
    }

    /**
     * Parses a {@code filePath} into a {@code Path}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param filePath
     * @return The trimmedFilePath.
     */
    public static Path parseFilePath(String filePath) {
        Path trimmedFilePath = Paths.get(filePath.trim());
        return trimmedFilePath;
    }
}
