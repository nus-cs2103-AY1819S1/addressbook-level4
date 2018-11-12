package tutorhelper.testutil;

import static tutorhelper.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_DAY_AND_TIME;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import tutorhelper.logic.commands.AddCommand;
import tutorhelper.logic.commands.EditCommand;
import tutorhelper.model.student.Student;
import tutorhelper.model.subject.Subject;
import tutorhelper.model.tag.Tag;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return AddCommand.COMMAND_WORD + " " + getStudentDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + student.getName().fullName + " ");
        sb.append(PREFIX_PHONE + student.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + student.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + student.getAddress().value + " ");
        student.getSubjects().stream().forEach(
            s -> sb.append(PREFIX_SUBJECT + s.getSubjectName() + " ")
        );
        sb.append(PREFIX_DAY_AND_TIME + student.getTuitionTiming().toString() + " ");
        sb.append(PREFIX_TAG);
        student.getTags().stream().forEach(
            s -> sb.append(s.tagName + ", ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditCommand.EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getSubjects().isPresent()) {
            Set<Subject> subjects = descriptor.getSubjects().get();
            if (subjects.isEmpty()) {
                sb.append(PREFIX_SUBJECT);
            } else {
                subjects.forEach(s -> sb.append(PREFIX_SUBJECT).append(s.getSubjectName()).append(" "));
            }
        }
        descriptor.getTuitionTiming().ifPresent(tuitionTiming -> sb.append(PREFIX_DAY_AND_TIME)
                .append(tuitionTiming.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
