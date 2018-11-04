package ssp.scheduleplanner.model.category;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.tag.UniqueTagList;

/**
 * A category has a name and contains a list of tags.
 */
public class Category {
    private String name;
    private UniqueTagList tags;

    public Category(String name) {
        this.name = name;
        this.tags = new UniqueTagList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && name.equals(((Category) other).getName())
                && tags.equals(((Category) other).getTags())); // state check
    }

    /**
     * Check if two categories are the same
     * @param other
     * @return true if name and tag list of two categories are the same.
     */
    public boolean isSameCategory(Category other) {
        if (this == other) {
            return true;
        } else {
            if (other != null) {
                return this.equals(other);
            }
        }
        return false;
    }

    /**
     * Adds a tag into this category if it does not contain this tag yet.
     * @param tag
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public String getName() {
        return name;
    }

    public ObservableList<Tag> getTags() {
        return tags.asUnmodifiableObservableList();
    }

    /**
     * Returns true if a tag with the same name as {@code tag} exists in the tag list of this category.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }
}
