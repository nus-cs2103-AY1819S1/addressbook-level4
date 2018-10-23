package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditVolunteerDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class for Volunteer.
 */
public class VolunteerUtil {

    /**
     * Returns an add command string for adding the {@code volunteer}.
     */
    public static String getAddCommand(Volunteer volunteer) {
        return AddCommand.COMMAND_WORD + " " + getVolunteerDetails(volunteer);
    }
    /**
     * Returns the part of command string for the given {@code volunteer}'s details.
     */
    public static String getVolunteerDetails(Volunteer volunteer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + volunteer.getName().fullName + " ");
        sb.append(PREFIX_GENDER + volunteer.getGender().value + " ");
        sb.append(PREFIX_BIRTHDAY + volunteer.getBirthday().value + " ");
        sb.append(PREFIX_PHONE + volunteer.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + volunteer.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + volunteer.getAddress().value + " ");
        volunteer.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditVolunteerDescriptor}'s details.
     */
    public static String getEditVolunteerDescriptorDetails(EditVolunteerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getBirthday().ifPresent(birthday -> sb.append(PREFIX_BIRTHDAY)
                .append(birthday.value).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS)
                .append(address.value).append(" "));
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
