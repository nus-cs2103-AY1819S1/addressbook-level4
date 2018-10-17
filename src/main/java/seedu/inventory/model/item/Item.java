package seedu.inventory.model.item;

import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.inventory.model.tag.Tag;

/**
 * Represents an Item in the inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Identity fields
    private final Name name;
    private final Sku sku;

    // Data fields
    private final Image image;
    private final Price price;
    private final Quantity quantity;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Item(Name name, Price price, Quantity quantity, Sku sku, Image image, Set<Tag> tags) {
        requireAllNonNull(name, price, quantity, sku, image, tags);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.image = image;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Sku getSku() {
        return sku;
    }

    public Image getImage() {
        return image;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both items have the same SKU.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
                && otherItem.getSku().equals(getSku());
    }

    /**
     * Returns true if both items have the same SKU and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return otherItem.getName().equals(getName())
                && otherItem.getPrice().equals(getPrice())
                && otherItem.getQuantity().equals(getQuantity())
                && otherItem.getSku().equals(getSku())
                && otherItem.getImage().equals(getImage())
                && otherItem.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price, quantity, sku, image, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Sku: ")
                .append(getSku())
                .append(" Image: ")
                .append(getImage())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
