package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddServiceCommand;
import seedu.address.logic.commands.UpdateCommand.EditContactDescriptor;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Service;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Client.
 */
public class PersonUtil {

    /**
     * Returns an add client command string for adding the {@code client}.
     */
    public static String getAddClientCommand(Contact contact) {
        return AddCommand.COMMAND_WORD_CLIENT + " " + getPersonDetails(contact);
    }

    /**
     * Returns an add vendor command string for adding the {@code vendor}.
     */
    public static String getAddVendorCommand(Contact contact) {
        return AddCommand.COMMAND_WORD_VENDOR + " " + getPersonDetails(contact);
    }

    /**
     * Returns the part of command string for the given {@code client}'s details.
     */
    public static String getPersonDetails(Contact contact) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + contact.getName().fullName + " ");
        sb.append(PREFIX_PHONE + contact.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + contact.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + contact.getAddress().value + " ");
        contact.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditContactDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditContactDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
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

    /**
     * Creates a client addservice input.
     * @param contact Contact to add service to.
     * @param service Service to be added.
     * @return New string containing client addservice input.
     */
    public static String getClientAddServiceCommand(Contact contact, Service service, Index index) {
        return "client#" + index.getOneBased() + " " + AddServiceCommand.COMMAND_WORD + " s/" + service.getName()
                + " c/" + service.getCost();
    }

    /**
     * Creates a vendor addservice input.
     * @param contact Contact to add service to.
     * @param service Service to be added.
     * @return New string containing vendor addservice input.
     */
    public static String getServiceProviderAddServiceCommand(Contact contact, Service service, Index index) {
        return "vendor#" + index.getOneBased() + " " + AddServiceCommand.COMMAND_WORD + " s/"
                + service.getName() + " c/" + service.getCost();
    }
}
