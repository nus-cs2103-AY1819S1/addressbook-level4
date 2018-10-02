package seedu.address.model.diet;

//@@author yuntongzhang
/**
 * Represents a Dietary requirement for a Person.
 * Guarantees: field values are validated and immutable.
 */
public class Diet {
    private final Allegy allergy;
    private final CulturalRequriement cuturalRequirement;
    private final PhysicalDifficulty physicalDifficulty;

    // Every field must be present
    public Diet(Allegy allergy, CulturalRequriement cuturalRequirement, PhysicalDifficulty physicalDifficulty) {
        this.allergy = allergy;
        this.cuturalRequirement = cuturalRequirement;
        this.physicalDifficulty = physicalDifficulty;
    }

    public Allergy getAllergy() {
        return allergy;
    }

    public CulturalRequirement getCulturalRequirement() {
        return culturalRequirement;
    }

    public PhysicalDifficulty getPhysicalDifficulty() {
        return physicalDifficulty;
    }

    /**
     * Returns true if the two diet requirements have the same allergy requirement,
     * cultural requirement and physical difficulty requirement.
     *
     * @param other The other Object to be compared with
     * @return Whether this Diet is the same as the other Object
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
        return otherDiet.getAllergy().equals(getAllergy())
               && otherDiet.getCulturalRequirement().equals(getCulturalRequirement())
               && otherDiet.getPhysicalDifficulty().equals(getPhysicalDifficulty());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.appen(" Allegies: ")
                .append(getAllergy())
                .append(" Cultural Requirements: ")
                .append(getCulturalRequirement())
                .append(" Physical Difficulties: ")
                .append(getPhysicalDifficulty());
        return builder.toString();
    }
}
