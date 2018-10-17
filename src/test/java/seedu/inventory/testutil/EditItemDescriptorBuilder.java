package seedu.inventory.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.inventory.logic.commands.EditCommand;
import seedu.inventory.logic.commands.EditCommand.EditItemDescriptor;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.tag.Tag;

/**
 * A utility class to help with building EditItemDescriptor objects.
 */
public class EditItemDescriptorBuilder {

    private EditCommand.EditItemDescriptor descriptor;

    public EditItemDescriptorBuilder() {
        descriptor = new EditItemDescriptor();
    }

    public EditItemDescriptorBuilder(EditItemDescriptor descriptor) {
        this.descriptor = new EditItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditItemDescriptor} with fields containing {@code item}'s details
     */
    public EditItemDescriptorBuilder(Item item) {
        descriptor = new EditItemDescriptor();
        descriptor.setName(item.getName());
        descriptor.setPrice(item.getPrice());
        descriptor.setQuantity(item.getQuantity());
        descriptor.setSku(item.getSku());
        descriptor.setImage(item.getImage());
        descriptor.setTags(item.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code price} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Sets the {@code Sku} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withSku(String sku) {
        descriptor.setSku(new Sku(sku));
        return this;
    }

    /**
     * Sets the {@code Image} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withImage(String image) {
        descriptor.setImage(new Image(image));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditItemDescriptor}
     * that we are building.
     */
    public EditItemDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditItemDescriptor build() {
        return descriptor;
    }
}
