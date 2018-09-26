package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditWishDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Address;
import seedu.address.model.wish.Email;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Phone;
import seedu.address.model.wish.Wish;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditWishDescriptorBuilder {

    private EditWishDescriptor descriptor;

    public EditWishDescriptorBuilder() {
        descriptor = new EditWishDescriptor();
    }

    public EditWishDescriptorBuilder(EditWishDescriptor descriptor) {
        this.descriptor = new EditWishDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code wish}'s details
     */
    public EditWishDescriptorBuilder(Wish wish) {
        descriptor = new EditWishDescriptor();
        descriptor.setName(wish.getName());
        descriptor.setPhone(wish.getPhone());
        descriptor.setEmail(wish.getEmail());
        descriptor.setAddress(wish.getAddress());
        descriptor.setTags(wish.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditWishDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditWishDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditWishDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditWishDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditWishDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditWishDescriptor build() {
        return descriptor;
    }
}
