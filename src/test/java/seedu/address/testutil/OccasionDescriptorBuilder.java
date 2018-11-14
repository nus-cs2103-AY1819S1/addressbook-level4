package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditOccasionDescriptor objects.
 */
public class OccasionDescriptorBuilder {

    private OccasionDescriptor descriptor;

    public OccasionDescriptorBuilder() {
        descriptor = new OccasionDescriptor();
    }

    public OccasionDescriptorBuilder(OccasionDescriptor descriptor) {
        this.descriptor = new OccasionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOccasionDescriptor} with fields containing {@code Occasion}'s details
     */
    public OccasionDescriptorBuilder(Occasion occasion) {
        descriptor = new OccasionDescriptor();
        descriptor.setOccasionName(occasion.getOccasionName());
        descriptor.setOccasionDate(occasion.getOccasionDate());
        descriptor.setOccasionLocation(occasion.getOccasionLocation());
        descriptor.setTags(occasion.getTags());
    }

    /**
     * Sets the {@code OccasionName} of the {@code EditOccasionDescriptor} that we are building.
     */
    public OccasionDescriptorBuilder withOccasionName(String name) {
        descriptor.setOccasionName(new OccasionName(name));
        return this;
    }

    /**
     * Sets the {@code OccasionDate} of the {@code EditOccasionDescriptor} that we are building.
     */
    public OccasionDescriptorBuilder withOccasionDate(String phone) {
        descriptor.setOccasionDate(new OccasionDate(phone));
        return this;
    }

    /**
     * Sets the {@code OccasionLocation} of the {@code EditOccasionDescriptor} that we are building.
     */
    public OccasionDescriptorBuilder withOccasionLocation(String email) {
        descriptor.setOccasionLocation(new OccasionLocation(email));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditOccasionDescriptor}
     * that we are building.
     */
    public OccasionDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public OccasionDescriptor build() {
        return descriptor;
    }
}
