package seedu.address.model.ingredient;

/**
 * Represents an unique ingredient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class UniqueIngredient {
    /**
     * Every field must be present and not null.
     */
    // Identity fields
    private String name;

    public UniqueIngredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns true if both unique ingredients have the same name.
     * This defines a stronger notion of equality between two unique ingredients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueIngredient)) {
            return false;
        }

        UniqueIngredient otherUniqueIngredient = (UniqueIngredient) other;
        return otherUniqueIngredient.getName().equals(getName());
    }
}
