package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.task.Address;
import seedu.address.model.task.Email;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCommand.EditTaskDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditCommand.EditTaskDescriptor();
    }

    public EditPersonDescriptorBuilder(EditCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditPersonDescriptorBuilder(Task task) {
        descriptor = new EditCommand.EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setPhone(task.getPhone());
        descriptor.setEmail(task.getEmail());
        descriptor.setAddress(task.getAddress());
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditTaskDescriptor build() {
        return descriptor;
    }
}
