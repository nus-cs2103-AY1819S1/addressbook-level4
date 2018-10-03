package seedu.address.model.occasion;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.TypeUtil;
import seedu.address.model.inanimate.Inanimate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents an Occasion within the address book.
 * @author KongZijin
 */
public class Occasion extends Inanimate {

    // Identity fields
    private final OccasionName occasionName;
    private final OccasionDate occasionDate;
    private final Person organiser;

    // Date fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Occasion(OccasionName occasionName, OccasionDate occasionDate,
        Person organiser, Set<Tag> tags, TypeUtil type) {
        requireAllNonNull(occasionName, occasionDate, organiser, tags, type);
        this.occasionName = occasionName;
        this.occasionDate = occasionDate;
        this.organiser = organiser;
        this.tags.addAll(tags);
        this.type = type;
    }

    public OccasionName getOccasionName() {
        return occasionName;
    }

    public OccasionDate getOccasionDate() {
        return occasionDate;
    }

    public Person getOrganiser() {
        return organiser;
    }

    /**
     * Get the set of Tags.
     *
     * @return An immutable tag set, which throws {@code
     * UnsupportedOperationException} if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Check the equality of two occasions. This defines a stronger notion of
     * equality between two occasions.
     *
     * @return Return true if both occasion have the same identity and data
     * fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Occasion)) {
            return false;
        }

        Occasion otherOccasion = (Occasion) other;
        return otherOccasion.getOccasionName().equals(this.getOccasionName())
            && otherOccasion.getOccasionDate().equals(this.getOccasionDate())
            && otherOccasion.getOrganiser().equals(this.getOrganiser())
            && otherOccasion.getTags().equals(this.getTags());
    }

    @Override
    public int hashCode() {
        // Use this method for custom fields hashing instead of implementing one.
        return Objects.hash(occasionName, occasionDate, organiser, tags);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getOccasionName())
            .append(" ")
            .append(getOccasionDate())
            .append(" Organiser: ")
            .append(getOrganiser().getName()) // use the person's name to represent this Peron
            // object.
            .append(" Tags: ");
        getTags().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
