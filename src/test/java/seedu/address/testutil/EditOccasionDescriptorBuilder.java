package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditOccasionCommand.EditOccasionDescriptor;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditOccasionDescriptor objects.
 */
public class EditOccasionDescriptorBuilder {

    private EditOccasionDescriptor descriptor;

    public EditOccasionDescriptorBuilder() {
        descriptor = new EditOccasionDescriptor();
    }

    public EditOccasionDescriptorBuilder(EditOccasionDescriptor descriptor) {
        this.descriptor = new EditOccasionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOccasionDescriptor} with fields containing {@code Occasion}'s details
     */
    public EditOccasionDescriptorBuilder(Occasion occasion) {
        descriptor = new EditOccasionDescriptor();
        descriptor.setOccasionName(occasion.getOccasionName());
        descriptor.setOccasionDate(occasion.getOccasionDate());
        descriptor.setOccasionLocation(occasion.getOccasionLocation());
        descriptor.setTags(occasion.getTags());
    }

    /**
     * Sets the {@code OccasionName} of the {@code EditOccasionDescriptor} that we are building.
     */
    public EditOccasionDescriptorBuilder withOccasionName(String name) {
        descriptor.setOccasionName(new OccasionName(name));
        return this;
    }

    /**
     * Sets the {@code OccasionDate} of the {@code EditOccasionDescriptor} that we are building.
     */
    public EditOccasionDescriptorBuilder withOccasionDate(String phone) {
        descriptor.setOccasionDate(new OccasionDate(phone));
        return this;
    }

    /**
     * Sets the {@code OccasionLocation} of the {@code EditOccasionDescriptor} that we are building.
     */
    public EditOccasionDescriptorBuilder withOccasionLocation(String email) {
        descriptor.setOccasionLocation(new OccasionLocation(email));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditOccasionDescriptor}
     * that we are building.
     */
    public EditOccasionDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditOccasionDescriptor build() {
        return descriptor;
    }
}
