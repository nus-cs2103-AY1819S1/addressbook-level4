package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddVolunteerCommand;
import seedu.address.logic.commands.EditVolunteerCommand.EditVolunteerDescriptor;
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
        return AddVolunteerCommand.COMMAND_WORD + " " + getVolunteerDetails(volunteer);
    }
    /**
     * Returns the part of command string for the given {@code volunteer}'s details.
     */
    public static String getVolunteerDetails(Volunteer volunteer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_VOLUNTEER_NAME + volunteer.getName().fullName + " ");
        sb.append(PREFIX_VOLUNTEER_GENDER + volunteer.getGender().value + " ");
        sb.append(PREFIX_VOLUNTEER_BIRTHDAY + volunteer.getBirthday().value + " ");
        sb.append(PREFIX_VOLUNTEER_PHONE + volunteer.getPhone().value + " ");
        sb.append(PREFIX_VOLUNTEER_EMAIL + volunteer.getEmail().value + " ");
        sb.append(PREFIX_VOLUNTEER_ADDRESS + volunteer.getAddress().value + " ");
        volunteer.getTags().stream().forEach(
        s -> sb.append(PREFIX_VOLUNTEER_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditVolunteerDescriptor}'s details.
     */
    public static String getEditVolunteerDescriptorDetails(EditVolunteerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_VOLUNTEER_NAME).append(name.fullName).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_VOLUNTEER_GENDER).append(gender.value).append(" "));
        descriptor.getBirthday().ifPresent(birthday -> sb.append(PREFIX_VOLUNTEER_BIRTHDAY)
                .append(birthday.value).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_VOLUNTEER_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_VOLUNTEER_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_VOLUNTEER_ADDRESS)
                .append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_VOLUNTEER_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_VOLUNTEER_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
