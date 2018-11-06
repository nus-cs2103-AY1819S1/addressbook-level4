package seedu.restaurant.testutil.reservation;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.restaurant.logic.commands.reservation.EditReservationCommand.EditReservationDescriptor;
import seedu.restaurant.model.reservation.Date;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.Time;
import seedu.restaurant.model.tag.Tag;

//@@author m4dkip
/**
 * A utility class to help with building EditReservationDescriptor objects.
 */
public class EditReservationDescriptorBuilder {

    private EditReservationDescriptor descriptor;

    public EditReservationDescriptorBuilder() {
        descriptor = new EditReservationDescriptor();
    }

    public EditReservationDescriptorBuilder(EditReservationDescriptor descriptor) {
        this.descriptor = new EditReservationDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReservationDescriptor} with fields containing {@code reservation}'s details
     */
    public EditReservationDescriptorBuilder(Reservation reservation) {
        descriptor = new EditReservationDescriptor();
        descriptor.setName(reservation.getName());
        descriptor.setPax(reservation.getPax());
        descriptor.setDate(reservation.getDate());
        descriptor.setTime(reservation.getTime());
        descriptor.setTags(reservation.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Pax} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withPax(String pax) {
        descriptor.setPax(new Pax(pax));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withTime(String time) {
        descriptor.setTime(new Time(time));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditReservationDescriptor}
     * that we are building.
     */
    public EditReservationDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditReservationDescriptor build() {
        return descriptor;
    }
}
