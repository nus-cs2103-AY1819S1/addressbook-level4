package seedu.address.model.occasion;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.TypeUtil;
import seedu.address.model.inanimate.Inanimate;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Represents an Occasion within the address book.
 * @author KongZijin
 */
public class Occasion extends Inanimate {

    // Identity fields
    private final OccasionName occasionName;
    private final OccasionDate occasionDate;
    private final String location;
    private final UniquePersonList attendanceList;

    // Date fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Occasion(OccasionName occasionName, OccasionDate occasionDate, String location,
                    Set<Tag> tags, TypeUtil type) {
        requireAllNonNull(occasionName, occasionDate, tags, type);
        this.occasionName = occasionName;
        this.occasionDate = occasionDate;
        this.location = location;
        this.attendanceList = new UniquePersonList();
        this.tags.addAll(tags);
        this.type = type;
    }

    public Occasion(OccasionName occasionName, OccasionDate occasionDate,
                    Set<Tag> tags, TypeUtil type) {
        this(occasionName, occasionDate, null, tags, type);
    }

    public OccasionName getOccasionName() {
        return occasionName;
    }

    public OccasionDate getOccasionDate() {
        return occasionDate;
    }

    public UniquePersonList getAttendanceList() {
        return attendanceList;
    }

    public String getLocation() {
        return location;
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
                && otherOccasion.getAttendanceList().equals(this.getAttendanceList())
                && otherOccasion.getTags().equals(this.getTags());
    }

    @Override
    public int hashCode() {
        // Use this method for custom fields hashing instead of implementing one.
        return Objects.hash(occasionName, occasionDate, attendanceList, tags);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getOccasionName())
                .append(" ")
                .append(getOccasionDate())
                .append(" Attendance List: ")
                .append(getAttendanceList()) // use the person's name to represent this Peron
                // object.
                .append(" Tags: ");
        getTags().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
