//package seedu.parking.testutil;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import seedu.parking.logic.commands.EditCommand;
//import seedu.parking.logic.commands.EditCommand.EditCarparkDescriptor;
//import seedu.parking.model.carpark.Address;
//import seedu.parking.model.tag.Tag;
//
///**
// * A utility class to help with building EditCarparkDescriptor objects.
// */
//public class EditPersonDescriptorBuilder {
//
//    private EditCarparkDescriptor descriptor;
//
//    public EditPersonDescriptorBuilder() {
//        descriptor = new EditCommand.EditCarparkDescriptor();
//    }
//
//    public EditPersonDescriptorBuilder(EditCarparkDescriptor descriptor) {
//        this.descriptor = new EditCommand.EditCarparkDescriptor(descriptor);
//    }
//
//    /**
//     * Returns an {@code EditCarparkDescriptor} with fields containing {@code carpark}'s details
//     */
//    public EditPersonDescriptorBuilder(Person carpark) {
//        descriptor = new EditCommand.EditCarparkDescriptor();
//        descriptor.setName(carpark.getName());
//        descriptor.setPhone(carpark.getPhone());
//        descriptor.setEmail(carpark.getEmail());
//        descriptor.setAddress(carpark.getAddress());
//        descriptor.setTags(carpark.getTags());
//    }
//
//    /**
//     * Sets the {@code Name} of the {@code EditCarparkDescriptor} that we are building.
//     */
//    public EditPersonDescriptorBuilder withName(String name) {
//        descriptor.setName(new Name(name));
//        return this;
//    }
//
//    /**
//     * Sets the {@code Phone} of the {@code EditCarparkDescriptor} that we are building.
//     */
//    public EditPersonDescriptorBuilder withPhone(String phone) {
//        descriptor.setPhone(new Phone(phone));
//        return this;
//    }
//
//    /**
//     * Sets the {@code Email} of the {@code EditCarparkDescriptor} that we are building.
//     */
//    public EditPersonDescriptorBuilder withEmail(String email) {
//        descriptor.setEmail(new Email(email));
//        return this;
//    }
//
//    /**
//     * Sets the {@code Address} of the {@code EditCarparkDescriptor} that we are building.
//     */
//    public EditPersonDescriptorBuilder withAddress(String parking) {
//        descriptor.setAddress(new Address(parking));
//        return this;
//    }
//
//    /**
//     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCarparkDescriptor}
//     * that we are building.
//     */
//    public EditPersonDescriptorBuilder withTags(String... tags) {
//        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
//        descriptor.setTags(tagSet);
//        return this;
//    }
//
//    public EditCarparkDescriptor build() {
//        return descriptor;
//    }
//}
