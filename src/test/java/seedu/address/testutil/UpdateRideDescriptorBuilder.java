package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.Maintenance;
import seedu.address.model.ride.Name;
import seedu.address.model.ride.Ride;
import seedu.address.model.ride.WaitTime;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building UpdateRideDescriptor objects.
 */
public class UpdateRideDescriptorBuilder {

    private UpdateRideDescriptor descriptor;

    public UpdateRideDescriptorBuilder() {
        descriptor = new UpdateRideDescriptor();
    }

    public UpdateRideDescriptorBuilder(UpdateRideDescriptor descriptor) {
        this.descriptor = new UpdateRideDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateRideDescriptor} with fields containing {@code ride}'s details
     */
    public UpdateRideDescriptorBuilder(Ride ride) {
        descriptor = new UpdateRideDescriptor();
        descriptor.setName(ride.getName());
        descriptor.setMaintenance(ride.getDaysSinceMaintenance());
        descriptor.setWaitTime(ride.getWaitingTime());
        descriptor.setAddress(ride.getAddress());
        descriptor.setTags(ride.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code UpdateRideDescriptor} that we are building.
     */
    public UpdateRideDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Maintenance} of the {@code UpdateRideDescriptor} that we are building.
     */
    public UpdateRideDescriptorBuilder withMaintenance(String daysSinceMaintenanceString) {
        descriptor.setMaintenance(new Maintenance(daysSinceMaintenanceString));
        return this;
    }

    /**
     * Sets the {@code WaitTime} of the {@code UpdateRideDescriptor} that we are building.
     */
    public UpdateRideDescriptorBuilder withWaitTime(String email) {
        descriptor.setWaitTime(new WaitTime(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code UpdateRideDescriptor} that we are building.
     */
    public UpdateRideDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code UpdateRideDescriptor}
     * that we are building.
     */
    public UpdateRideDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public UpdateRideDescriptor build() {
        return descriptor;
    }
}
