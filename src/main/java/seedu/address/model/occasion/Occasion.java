package seedu.address.model.occasion;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import seedu.address.commons.util.TypeUtil;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagKey;
import seedu.address.model.tag.TagMap;
import seedu.address.model.tag.TagValue;

/**
 * Represents an Occasion within the address book.
 * @author KongZijin
 */
public class Occasion {

    // Identity fields
    private final OccasionName occasionName;
    private final OccasionDate occasionDate;
    private final OccasionLocation location;
    private final UniquePersonList attendanceList;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Occasion(OccasionName occasionName, OccasionDate occasionDate, OccasionLocation location,
                    Set<Tag> tags, TypeUtil type) {
        requireAllNonNull(occasionName, occasionDate, tags, type);
        this.occasionName = occasionName;
        this.occasionDate = occasionDate;
        this.location = location;
        this.attendanceList = new UniquePersonList(new ArrayList<>());
        this.tags.addAll(tags);
    }

    public Occasion(OccasionName occasionName, OccasionDate occasionDate,
                    Set<Tag> tags) {
        this(occasionName, occasionDate, null, tags, TypeUtil.OCCASION);
    }

    public OccasionName getOccasionName() {
        return occasionName;
    }

    public OccasionDate getOccasionDate() {
        return occasionDate;
    }

    public UniquePersonList getAttendanceList() {
        return attendanceList == null ? new UniquePersonList(new ArrayList<>()) : attendanceList;
    }

    public OccasionLocation getLocation() {
        return location;
    }

    /**
     * Get the set of Tags.
     *
     * @return An immutable tag set, which throws {@code
     * UnsupportedOperationException} if modification is attempted.
     */
    // TODO change the implementation of all the places that use this method.
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
