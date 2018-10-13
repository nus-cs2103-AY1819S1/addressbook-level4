package seedu.address.model.diet;

//@@author yuntongzhang

import java.util.Objects;

/**
 * Represents dietary requirement for a person.
 */
public class Diet {
    private final String detail;
    private final DietType type;

    /**
     * Constructor for a Diet Object.
     * All fields must be non-null.
     *
     * @param detail The detail of this Diet requirement.
     * @param type The type of this Diet requirement.
     */
    public Diet(String detail, DietType type) {
        Objects.requireNonNull(detail);
        Objects.requireNonNull(type);
        this.detail = detail;
        this.type = type;
    }

    /**
     * Get the detail if this Diet requirement.
     * @return The detail of this Diet requirement.
     */
    public String getDetail() {
        return this.detail;
    }

    /**
     * Get the type of this Diet requirement.
     * @return The type of this Diet requirement.
     */
    public DietType getType() {
        return this.type;
    }

    /**
     * Check whether this diet requirement is allergy.
     * @return True if this diet requirement is an allergy.
     */
    public boolean isAllergy() {
        return this.type == DietType.ALLERGY;
    }

    /**
     * Check whether this diet requirement is cultural requirement.
     * @return True if this diet requirement is a cultural requirement.
     */
    public boolean isCulturalRequirement() {
        return this.type == DietType.CULTURAL;
    }

    /**
     * Check whether this diet requirement is physical difficulty.
     * @return True if this diet requirement is a physical difficulty.
     */
    public boolean isPhysicalDifficulty() {
        return this.type == DietType.PHYSICAL;
    }


    /**
     * Check whether this diet is equal to the given object.
     * @param other The other object to compare with
     * @return Whether this diet is equal to the given object.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Diet)) {
            return false;
        }

        Diet otherDiet = (Diet) other;
        return otherDiet.getDetail().equals(getDetail())
                && (otherDiet.getType() == getType());
    }

    /**
     * Convert the diet to String for representation.
     * @return The string representation for this diet.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        builder.append(" ")
                .append(getDetail())
                .append(" ");

        return builder.toString();
    }
}
