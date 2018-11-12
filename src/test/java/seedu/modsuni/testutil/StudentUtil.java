package seedu.modsuni.testutil;

import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_ENROLLMENT_DATE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_MAJOR;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_MINOR;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERDATA;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.List;

import seedu.modsuni.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.modsuni.logic.commands.LoginCommand;
import seedu.modsuni.logic.commands.RegisterCommand;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.Student;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    /**
     * Returns a login command string for the respective {@code student}.
     */
    public static String getLoginCommand(User user, String password,
                                         String path) {
        return LoginCommand.COMMAND_WORD + " "
            + PREFIX_USERNAME + user.getUsername().toString() + " "
            + PREFIX_PASSWORD + password + " "
            + PREFIX_USERDATA + path;
    }


    /**
     * Returns a register command string for adding the {@code student}.
     */
    public static String getRegisterCommand(Student student) {
        return RegisterCommand.COMMAND_WORD + " "
            + PREFIX_USERNAME + student.getUsername().getUsername() + " "
            + PREFIX_PASSWORD + VALID_PASSWORD + " "
            + getStudentDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code student}'s
     * details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + student.getName().fullName + " ");
        sb.append(PREFIX_STUDENT_ENROLLMENT_DATE + student.getEnrollmentDate().enrollmentDate + " ");
        student.getMajor().stream().forEach(
            s -> sb.append(PREFIX_STUDENT_MAJOR + s + " ")
        );
        student.getMinor().stream().forEach(
            s -> sb.append(PREFIX_STUDENT_MINOR + s + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code
     * EditStudentDescriptor}'s details.
     */

    /**
     * Returns the part of command string for the given {@code
     * EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(
            name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getEnrollmentDate().ifPresent(
            date -> sb.append(PREFIX_STUDENT_ENROLLMENT_DATE).append(date.enrollmentDate).append(" "));
        if (descriptor.getMajors().isPresent()) {
            List<String> majors = descriptor.getMajors().get();
            if (majors.isEmpty()) {
                sb.append(PREFIX_STUDENT_MAJOR);
            } else {
                majors.forEach(major -> sb.append(PREFIX_STUDENT_MAJOR).append(major).append(
                    " "));
            }
        }
        if (descriptor.getMinors().isPresent()) {
            List<String> minors = descriptor.getMinors().get();
            if (minors.isEmpty()) {
                sb.append(PREFIX_STUDENT_MINOR);
            } else {
                minors.forEach(minor -> sb.append(PREFIX_STUDENT_MINOR).append(minor).append(
                    " "));
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
