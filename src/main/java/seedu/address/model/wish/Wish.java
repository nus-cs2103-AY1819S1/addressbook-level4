package seedu.address.model.wish;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Wish {

    // Identity fields
    private final Name name;
    private final Price price;
    private final SavedAmount savedAmount;
    private final Email email;

    // Data fields
    private final Url url;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Wish(Name name, Price price, Email email, Url url, SavedAmount savedAmount, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, price, email, url, tags);
        this.name = name;
        this.price = price;
        this.email = email;
        this.url = url;
        this.tags.addAll(tags);
        this.remark = remark;
        this.savedAmount = savedAmount;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Email getEmail() {
        return email;
    }

    public SavedAmount getSavedAmount() {
        return savedAmount;
    }

    public Url getUrl() {
        return url;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameWish(Wish otherWish) {
        if (otherWish == this) {
            return true;
        }

        return otherWish != null
                && otherWish.getName().equals(getName())
                && (otherWish.getPrice().equals(getPrice()) || otherWish.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Wish)) {
            return false;
        }

        Wish otherWish = (Wish) other;
        return otherWish.getName().equals(getName())
                && otherWish.getPrice().equals(getPrice())
                && otherWish.getEmail().equals(getEmail())
                && otherWish.getUrl().equals(getUrl())
                && otherWish.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, price, email, url, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPrice())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getUrl())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
