package seedu.souschef.model.favourite;

import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.souschef.model.UniqueType;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;

import seedu.souschef.model.tag.Tag;

/**
 * Represents a Recipe in the application content.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Favourites extends UniqueType {

    // Identity fields
    private final Name name;
    private final CookTime cookTime;
    private final Difficulty difficulty;

    // Data fields
    private final ArrayList<Instruction> instructions = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Favourites(Name name, Difficulty difficulty, CookTime cooktime, Set<Tag> tags) {
        requireAllNonNull(name, difficulty, cooktime, tags);
        this.name = name;
        this.cookTime = cooktime;
        this.difficulty = difficulty;
        this.tags.addAll(tags);

        //this.instructions.addAll(instructions);

        this.instructions.add(new Instruction("Instruction placeholder 123.", new HashSet<>()));
    }

    public Name getName() {
        return name;
    }

    public CookTime getCookTime() {
        return cookTime;
    }

    public Difficulty getDifficulty() {
        return difficulty;
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
    private boolean isSame(Favourites otherRecipe) {
        if (otherRecipe == this) {
            return true;
        }

        return otherRecipe != null
                && otherRecipe.getName().equals(getName())
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
        if (uniqueType instanceof Favourites) {
            return isSame((Favourites) uniqueType);
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

        if (!(other instanceof Favourites)) {
            return false;
        }

        Favourites otherRecipe = (Favourites) other;
        return otherRecipe.getName().equals(getName())
                && otherRecipe.getCookTime().equals(getCookTime())
                && otherRecipe.getDifficulty().equals(getDifficulty())
                && otherRecipe.getTags().equals(getTags())
                && otherRecipe.getInstructions().equals(getInstructions());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, cookTime, difficulty, tags, instructions);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" CookTime: ")
                .append(getCookTime())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Instructions: ");
        getInstructions().forEach(builder::append);
        return builder.toString();
    }

}
