package ssp.scheduleplanner.model.category;

import static java.util.Objects.requireNonNull;
import static ssp.scheduleplanner.commons.util.AppUtil.checkArgument;

import javafx.collections.ObservableList;

import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.tag.UniqueTagList;

/**
 * A category has a name and contains a list of tags.
 */
public class Category {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Category name should only contain alphanumeric characters and spaces, and it should not be blank \n"
                   + "Example: Steam discount list, Projects";
    public static final String CATEGORY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private String name;
    private UniqueTagList tags;

    public Category(String name) {
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        this.name = name;
        this.tags = new UniqueTagList();
    }

    public Category(String name, UniqueTagList tags) {
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        this.name = name;
        this.tags = tags;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(CATEGORY_VALIDATION_REGEX);
    }


    /**
     * Change name of this category.
     * @param name
     */
    public void editName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && name.equals(((Category) other).getName())
                && (tags.equals(((Category) other).getUniqueTagList())
                || getTags().equals(((Category) other).getTags()))); // state check
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

    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }

    /**
     * Returns true if a tag with the same name as {@code tag} exists in the tag list of this category.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }
}
