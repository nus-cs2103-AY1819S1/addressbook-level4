package seedu.restaurant.model.menu;

import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.tag.Tag;

//@@author yican95
/**
 * Represents an item in the menu. Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Identity fields
    private final Name name;
    private final Price price;
    private final Recipe recipe;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Map<IngredientName, Integer> requiredIngredients = new HashMap<>();


    /**
     * Every field must be present and not null.
     */
    public Item(Name name, Price price, Recipe recipe, Set<Tag> tags,
            Map<IngredientName, Integer> requiredIngredients) {
        requireAllNonNull(name, price, tags);
        this.name = name;
        this.price = price;
        this.recipe = recipe;
        this.tags.addAll(tags);
        this.requiredIngredients.putAll(requiredIngredients);
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public double getPercent() {
        return price.getPercent();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable required ingredients map, which throws {@code UnsupportedOperationException} if modification
     * is attempted.
     */
    public Map<IngredientName, Integer> getRequiredIngredients() {
        return Collections.unmodifiableMap(requiredIngredients);
    }

    /**
     * Returns true if both items of the same name have at least one other identity field that is the same. This defines
     * a weaker notion of equality between two items.
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null && otherItem.getName().equals(getName());
    }

    /**
     * Returns true if both items have the same identity and data fields. This defines a stronger notion of equality
     * between two items.
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
                && otherItem.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Name: ");
        builder.append(getName())
                .append(" Price: ")
                .append(getPrice())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
