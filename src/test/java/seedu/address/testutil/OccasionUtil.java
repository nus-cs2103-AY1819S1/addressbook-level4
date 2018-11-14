package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Occasion.
 */
public class OccasionUtil {

    /**
     * Returns the part of command string for the given {@code occasion}'s details.
     */
    public static String getOccasionDetails(Occasion occasion) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_OCCASIONNAME + occasion.getOccasionName().fullOccasionName + " ");
        sb.append(PREFIX_OCCASIONDATE + occasion.getOccasionDate().fullOccasionDate + " ");
        sb.append(PREFIX_OCCASIONLOCATION + "" + occasion.getOccasionLocation().fullOccasionLocation + " ");
        occasion.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditOccasionDescriptor}'s details.
     */
    public static String getEditOccasionDescriptorDetails(OccasionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getOccasionName().ifPresent(name -> sb.append(PREFIX_OCCASIONNAME)
                .append(name.fullOccasionName).append(" "));
        descriptor.getOccasionDate().ifPresent(occasionDate -> sb.append(PREFIX_OCCASIONDATE)
                .append(occasionDate.fullOccasionDate)
                .append(" "));
        descriptor.getOccasionLocation().ifPresent(academicYear -> sb.append(PREFIX_OCCASIONLOCATION)
                .append(academicYear.fullOccasionLocation).append(" "));
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
