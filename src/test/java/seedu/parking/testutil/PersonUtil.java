package seedu.parking.testutil;

//import static seedu.parking.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.parking.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static seedu.parking.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.parking.logic.parser.CliSyntax.PREFIX_PHONE;
//import static seedu.parking.logic.parser.CliSyntax.PREFIX_TAG;
//
//import java.util.Set;
//
//import seedu.parking.logic.commands.AddCommand;
//import seedu.parking.logic.commands.EditCommand.EditCarparkDescriptor;
//import seedu.parking.model.carpark.Person;
//import seedu.parking.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

//    /**
//     * Returns an add command string for adding the {@code carpark}.
//     */
//    public static String getAddCommand(Person carpark) {
//        return AddCommand.COMMAND_WORD + " " + getPersonDetails(carpark);
//    }
//
//    /**
//     * Returns the part of command string for the given {@code carpark}'s details.
//     */
//    public static String getPersonDetails(Person carpark) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(PREFIX_NAME + carpark.getName().fullName + " ");
//        sb.append(PREFIX_PHONE + carpark.getPhone().value + " ");
//        sb.append(PREFIX_EMAIL + carpark.getEmail().value + " ");
//        sb.append(PREFIX_ADDRESS + carpark.getAddress().value + " ");
//        carpark.getTags().stream().forEach(
//            s -> sb.append(PREFIX_TAG + s.tagName + " ")
//        );
//        return sb.toString();
//    }
//
//    /**
//     * Returns the part of command string for the given {@code EditCarparkDescriptor}'s details.
//     */
//    public static String getEditPersonDescriptorDetails(EditCarparkDescriptor descriptor) {
//        StringBuilder sb = new StringBuilder();
//        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
//        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
//        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
//        descriptor.getAddress().ifPresent(parking -> sb.append(PREFIX_ADDRESS).append(parking.value).append(" "));
//        if (descriptor.getTags().isPresent()) {
//            Set<Tag> tags = descriptor.getTags().get();
//            if (tags.isEmpty()) {
//                sb.append(PREFIX_TAG);
//            } else {
//                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
//            }
//        }
//        return sb.toString();
//    }
}
