package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.EditContactDescriptor;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditContactDescriptor objects.
 */
public class EditContactDescriptorBuilder {

    private UpdateCommand.EditContactDescriptor descriptor;

    public EditContactDescriptorBuilder() {
        descriptor = new UpdateCommand.EditContactDescriptor();
    }

    public EditContactDescriptorBuilder(EditContactDescriptor descriptor) {
        this.descriptor = new EditContactDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditContactDescriptor} with fields containing {@code client}'s details
     */
    public EditContactDescriptorBuilder(Contact contact) {
        descriptor = new EditContactDescriptor();
        descriptor.setName(contact.getName());
        descriptor.setPhone(contact.getPhone());
        descriptor.setEmail(contact.getEmail());
        descriptor.setAddress(contact.getAddress());
        descriptor.setTags(contact.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditContactDescriptor} that we are building.
     */
    public EditContactDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditContactDescriptor}
     * that we are building.
     */
    public EditContactDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public UpdateCommand.EditContactDescriptor build() {
        return descriptor;
    }
}
