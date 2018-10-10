package seedu.souschef.model.recipe;

import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.souschef.model.UniqueType;
import seedu.souschef.model.tag.Tag;

/**
 * Represents a Recipe in the application content.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recipe extends UniqueType {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final CookTime cookTime;
    private final Difficulty difficulty;

    // Data fields
    private final Address address;
    private final ArrayList<Instruction> instructions = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Recipe(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);

        //this.cookTime = cookTime;
        //this.difficulty = difficulty;
        //this.instructions.addAll(instructions);

        this.cookTime = new CookTime("PT20H");
        this.difficulty = new Difficulty(3);
        this.instructions.add(new Instruction("Instruction placeholder 123."));
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public CookTime getCookTime() {
        return cookTime;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Address getAddress() {
        return address;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both recipes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two recipes.
     */
    private boolean isSame(Recipe otherRecipe) {
        if (otherRecipe == this) {
            return true;
        }

        return otherRecipe != null
                && otherRecipe.getName().equals(getName())
                && (otherRecipe.getPhone().equals(getPhone()) || otherRecipe.getEmail().equals(getEmail()))
                && otherRecipe.getCookTime().equals(getCookTime())
                && otherRecipe.getDifficulty().equals(getDifficulty());
    }

    /**
     * Returns true if other is a instance of Recipe and they are of the same name have at least one other identity
     * field that is the same.
     * This defines a weaker notion of equality between two recipes.
     */
    @Override
    public boolean isSame(UniqueType uniqueType) {
        if (uniqueType instanceof Recipe) {
            return isSame((Recipe) uniqueType);
        } else {
            return false;
        }
    }

    /**
     * Returns true if both recipes have the same identity and data fields.
     * This defines a stronger notion of equality between two recipes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Recipe)) {
            return false;
        }

        Recipe otherRecipe = (Recipe) other;
        return otherRecipe.getName().equals(getName())
                && otherRecipe.getPhone().equals(getPhone())
                && otherRecipe.getEmail().equals(getEmail())
                && otherRecipe.getCookTime().equals(getCookTime())
                && otherRecipe.getDifficulty().equals(getDifficulty())
                && otherRecipe.getAddress().equals(getAddress())
                && otherRecipe.getTags().equals(getTags())
                && otherRecipe.getInstructions().equals(getInstructions());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, cookTime, difficulty, address, tags, instructions);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" CookTime: ")
                .append(getCookTime())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Instructions: ");
        getInstructions().forEach(builder::append);
        return builder.toString();
    }

}
