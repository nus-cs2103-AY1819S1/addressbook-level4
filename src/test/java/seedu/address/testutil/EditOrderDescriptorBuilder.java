package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.order.EditCommand.EditOrderDescriptor;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.order.Food;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;

/**
 * A utility class to help with building EditOrderDescriptor objects.
 */
public class EditOrderDescriptorBuilder {
    private EditOrderDescriptor descriptor;

    public EditOrderDescriptorBuilder() {
        descriptor = new EditOrderDescriptor();
    }

    public EditOrderDescriptorBuilder(EditOrderDescriptor descriptor) {
        this.descriptor = new EditOrderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOrderDescriptor} with fields containing {@code order}'s details
     */
    public EditOrderDescriptorBuilder(Order order) {
        descriptor = new EditOrderDescriptor();
        descriptor.setName(order.getName());
        descriptor.setPhone(order.getPhone());
        descriptor.setAddress(order.getAddress());
        descriptor.setDate(order.getDate());
        descriptor.setFood(order.getFood());
    }

    /**
     * Sets the {@code Name} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditOrderDescriptor} that we are building.
     */
    public EditOrderDescriptorBuilder withDate(String date) {
        descriptor.setDate(new OrderDate(date));
        return this;
    }

    /**
     * Parses the {@code food} into a {@code Set<Food>} and set it to the {@code EditOrderDescriptor}
     * that we are building.
     */
    public EditOrderDescriptorBuilder withFood(String... food) {
        Set<Food> foodSet = Stream.of(food).map(Food::new).collect(Collectors.toSet());
        descriptor.setFood(foodSet);
        return this;
    }

    public EditOrderDescriptor build() {
        return descriptor;
    }
}
