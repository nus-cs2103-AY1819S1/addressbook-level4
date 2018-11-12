package seedu.lostandfound.model.article;

import static seedu.lostandfound.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.lostandfound.model.image.Image;
import seedu.lostandfound.model.tag.Tag;

/**
 * Represents a Article in the article list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Article {

    // Identity fields
    private final Name name;
    private final Description description;

    // Data fields
    private final Name finder;
    private final Phone phone;
    private final Email email;
    private final Name owner;

    // Data fields
    private final Image image;
    private final Set<Tag> tags = new HashSet<>();

    // Others
    private final boolean isResolved;

    /**
     * Every field must be present and not null.
     */
    public Article(Name name, Phone phone, Email email, Description description,
                   Image image, Name finder, Name owner, boolean isResolved, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, description, image, finder, owner, isResolved, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.image = image;
        this.finder = finder;
        this.owner = owner;
        this.isResolved = isResolved;
        this.tags.addAll(tags);
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

    public Description getDescription() {
        return description;
    }

    public Image getImage() {
        return image != null ? image : Image.getDefault();
    }

    public boolean getIsResolved() {
        return isResolved;
    }

    public Name getFinder() {
        return finder;
    }

    public Name getOwner() {
        return owner;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public String getStringTags() {
        return getTags().toString().replace("[", "").replace("]", "")
                .replace(",", "");
    }

    /**
     * Returns true if both articles of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two articles.
     */
    public boolean isSameArticle(Article otherArticle) {
        if (otherArticle == this) {
            return true;
        }

        return otherArticle != null
                && otherArticle.getName().equals(getName())
                && otherArticle.getDescription().equals(getDescription());
    }

    /**
     * Returns true if both articles have the same identity and data fields.
     * This defines a stronger notion of equality between two articles.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Article)) {
            return false;
        }

        Article otherArticle = (Article) other;
        return otherArticle.getName().equals(getName())
                && otherArticle.getPhone().equals(getPhone())
                && otherArticle.getEmail().equals(getEmail())
                && otherArticle.getDescription().equals(getDescription())
                && otherArticle.getFinder().equals(getFinder())
                && otherArticle.getOwner().equals(getOwner())
                && otherArticle.getImage().equals(getImage())
                && otherArticle.getIsResolved() == getIsResolved()
                && otherArticle.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, description, image, finder, owner, isResolved, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Image: ")
                .append(getFinder())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Finder: ")
                .append(getImage())
                .append(" Owner: ")
                .append(getOwner())
                .append(" isResolved: ")
                .append(getIsResolved())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
