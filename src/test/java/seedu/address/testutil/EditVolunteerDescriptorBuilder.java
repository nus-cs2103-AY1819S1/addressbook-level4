package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditVolunteerDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Address;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class to help with building EditVolunteerDescriptor objects.
 */
public class EditVolunteerDescriptorBuilder {

    private EditVolunteerDescriptor descriptor;

    public EditVolunteerDescriptorBuilder() {
        descriptor = new EditVolunteerDescriptor();
    }

    public EditVolunteerDescriptorBuilder(EditVolunteerDescriptor descriptor) {
        this.descriptor = new EditVolunteerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditVolunteerDescriptor} with fields containing {@code volunteer}'s details
     */
    public EditVolunteerDescriptorBuilder(Volunteer volunteer) {
        descriptor = new EditVolunteerDescriptor();

        descriptor.setVolunteerId(volunteer.getVolunteerId());
        descriptor.setName(volunteer.getName());
        descriptor.setGender(volunteer.getGender());
        descriptor.setBirthday(volunteer.getBirthday());
        descriptor.setPhone(volunteer.getPhone());
        descriptor.setEmail(volunteer.getEmail());
        descriptor.setAddress(volunteer.getAddress());
        descriptor.setTags(volunteer.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditVolunteerDescriptor} that we are building.
     */
    public EditVolunteerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code EditVolunteerDescriptor} that we are building.
     */
    public EditVolunteerDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code EditVolunteerDescriptor} that we are building.
     */
    public EditVolunteerDescriptorBuilder withBirthday(String birthday) {
        descriptor.setBirthday(new Birthday(birthday));
        return this;
    }
    /**
     * Sets the {@code Phone} of the {@code EditVolunteerDescriptor} that we are building.
     */
    public EditVolunteerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditVolunteerDescriptor} that we are building.
     */
    public EditVolunteerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPVolunteerDescriptor} that we are building.
     */
    public EditVolunteerDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditVolunteerDescriptor}
     * that we are building.
     */
    public EditVolunteerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditVolunteerDescriptor build() {
        return descriptor;
    }
}
