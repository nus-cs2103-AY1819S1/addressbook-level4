package seedu.souschef.logic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
import seedu.souschef.commons.core.index.Index;
import seedu.souschef.commons.util.CollectionUtil;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.tag.Tag;

/**
 * Stores the details to edit the recipe with. Each non-empty field value will replace the
 * corresponding field value of the recipe.
 */
public class EditRecipeDescriptor {
    private Name name;
    private Difficulty difficulty;
    private CookTime cookTime;
    private Set<Tag> tags;
    private Pair<Index, Instruction> instruction;

    public EditRecipeDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditRecipeDescriptor(EditRecipeDescriptor toCopy) {
        setName(toCopy.name);
        setDifficulty(toCopy.difficulty);
        setCooktime(toCopy.cookTime);
        setTags(toCopy.tags);
    }

    /**
     * Edit command should either change general attribute(s) of a recipe or one of its instruction.
     */
    public boolean isFieldEditedSpecific() {
        if (instruction == null) {
            return CollectionUtil.isAnyNonNull(name, difficulty, cookTime, tags);
        } else {
            // Editing instruction, all other attribute should not be in this edit command
            return !CollectionUtil.isAnyNonNull(name, difficulty, tags);
        }
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Optional<Difficulty> getDifficulty() {
        return Optional.ofNullable(difficulty);
    }

    public void setCooktime(CookTime cooktime) {
        this.cookTime = cooktime;
    }

    public Optional<CookTime> getCooktime() {
        return Optional.ofNullable(cookTime);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    public void setInstruction(Index index, Instruction instruction) {
        this.instruction = (instruction != null) ? new Pair<>(index, instruction) : null;
    }

    public Optional<Pair<Index, Instruction>> getInstruction() {
        return (instruction != null) ? Optional.of(instruction) : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRecipeDescriptor)) {
            return false;
        }

        // state check
        EditRecipeDescriptor e = (EditRecipeDescriptor) other;

        return getName().equals(e.getName())
                && getDifficulty().equals(e.getDifficulty())
                && getCooktime().equals(e.getCooktime())
                && getTags().equals(e.getTags())
                && getInstruction().equals(e.getInstruction());
    }
}
