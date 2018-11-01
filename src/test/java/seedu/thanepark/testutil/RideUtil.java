package seedu.thanepark.testutil;

import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_WAITING_TIME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;

import java.util.Set;

import seedu.thanepark.logic.commands.AddCommand;
import seedu.thanepark.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.tag.Tag;

/**
 * A utility class for Ride.
 */
public class RideUtil {

    /**
     * Returns an add command string for adding the {@code ride}.
     */
    public static String getAddCommand(Ride ride) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(ride);
    }

    /**
     * Returns the part of command string for the given {@code ride}'s details.
     */
    public static String getPersonDetails(Ride ride) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + ride.getName().fullName + " ");
        sb.append(PREFIX_MAINTENANCE + String.valueOf(ride.getDaysSinceMaintenance().getValue()) + " ");
        sb.append(PREFIX_WAITING_TIME + String.valueOf(ride.getWaitingTime().getValue()) + " ");
        sb.append(PREFIX_ZONE + ride.getZone().value + " ");
        ride.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code UpdateRideDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(UpdateRideDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getMaintenance().ifPresent(
            phone -> sb.append(PREFIX_MAINTENANCE).append(phone.getValue()).append(" "));
        descriptor.getWaitTime().ifPresent(
            email-> sb.append(PREFIX_WAITING_TIME).append(email.getValue()).append(" "));
        descriptor.getZone().ifPresent(address -> sb.append(PREFIX_ZONE).append(address.value).append(" "));
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
