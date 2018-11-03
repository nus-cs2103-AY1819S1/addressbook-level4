package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveStatus;
import seedu.address.model.permission.Permission;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Username;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String salary} into a {@code Salary}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code salary} is invalid.
     */
    public static Salary parseSalary(String salary) throws ParseException {
        requireNonNull(salary);
        String trimmedSalary = salary.trim();
        if (!Salary.isValidSalary(trimmedSalary)) {
            throw new ParseException(Salary.SALARY_CONSTRAINTS);
        }
        return new Salary(trimmedSalary);
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
     * Parses a {@code String username} into a {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     * Does not verify if the username is unqiue in the system.
     *
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedName = username.trim();
        if (!Username.isValidUsername(username) || username.equals("Admin")) {
            throw new ParseException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new Username(username);
    }

    /**
     * Parses a {@code String projectName} into an {@code ProjectName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code projectName} is invalid.
     */
    public static ProjectName parseProjectName(String projectName) throws ParseException {
        requireNonNull(projectName);
        String trimmedProjectName = projectName.trim();
        if (!ProjectName.isValidName(trimmedProjectName)) {
            throw new ParseException(ProjectName.MESSAGE_PROJECT_NAME_CONSTRAINTS);
        }
        return new ProjectName(trimmedProjectName);
    }

    /**
     * Parses a {@code String description} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String project} into a {@code Project}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code project} is invalid.
     */
    public static Project parseProject(String project) throws ParseException {
        requireNonNull(project);
        String trimmedProject = project.trim();
        if (!Project.isValidProjectName(trimmedProject)) {
            throw new ParseException(Project.MESSAGE_PROJECT_CONSTRAINTS);
        }
        return new Project(trimmedProject);
    }

    /**
     * Parses {@code Collection<String> projects} into a {@code Set<Project>}.
     */
    public static Set<Project> parseProjects(Collection<String> projects) throws ParseException {
        requireNonNull(projects);
        final Set<Project> projectSet = new HashSet<>();
        for (String tagName : projects) {
            projectSet.add(parseProject(tagName));
        }
        return projectSet;
    }

    /**
     * Parses a {@code String leaveStatus} into an {@code LeaveStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code leaveStatus} is invalid.
     */
    public static LeaveStatus parseLeaveStatus(String leaveStatus) throws ParseException {
        requireNonNull(leaveStatus);
        String trimmedLeaveStatus = leaveStatus.trim();
        if (!LeaveStatus.isValidStatus(trimmedLeaveStatus)) {
            throw new ParseException(LeaveStatus.MESSAGE_STATUS_CONSTRAINTS);
        }
        return new LeaveStatus(trimmedLeaveStatus);
    }

    /**
     * Parses a {@code String date} into an {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!DateUtil.isValidDateFormat(trimmedDate)) {
            throw new ParseException(DateUtil.MESSAGE_DATE_CONSTRANTS);
        }
        return DateUtil.convertToDate(trimmedDate);
    }

    /**
     * Parses {@code Collection<String> dates} into a {@code List<LocalDate>}.
     */
    public static List<LocalDate> parseDates(Collection<String> dates) throws ParseException {
        requireNonNull(dates);
        final List<LocalDate> datelist = new ArrayList<>();
        for (String date : dates) {
            datelist.add(parseDate(date));
        }
        return datelist;
    }

    /**
     * Parses a {@code String permission} into a {@code Permission}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Permission} is invalid.
     */
    public static Permission parsePermission(String permission) throws ParseException {
        requireNonNull(permission);
        String trimmedPermission = permission.trim();
        if (!Permission.isValidPermission(trimmedPermission)) {
            throw new ParseException(Permission.MESSAGE_INVALID_PERMISSION);
        }
        return Permission.valueOf(permission);
    }

    /**
     * Parses {@code Collection<String> permissions} into a {@code Set<Permission>}.
     */
    public static Set<Permission> parsePermissions(Collection<String> permissions) throws ParseException {
        requireNonNull(permissions);
        final Set<Permission> permissionSet = new HashSet<>();
        for (String permissionName : permissions) {
            permissionSet.add(parsePermission(permissionName));
        }
        return permissionSet;
    }
}
