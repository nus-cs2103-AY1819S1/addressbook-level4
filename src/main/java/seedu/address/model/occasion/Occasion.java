package seedu.address.model.occasion;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import seedu.address.model.person.UniquePersonList;
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
    private final String location;
    private final UniquePersonList attendanceList;

    private final TagMap tags = new TagMap();

    /**
     * Every field must be present and not null.
     */
    public Occasion(OccasionName occasionName, OccasionDate occasionDate, String location,
                    TagMap tags, UniquePersonList attendanceList) {
        requireAllNonNull(occasionName, occasionDate, tags);
        this.occasionName = occasionName;
        this.occasionDate = occasionDate;
        this.location = location;
        this.attendanceList = attendanceList;
        this.tags.addAll(tags);
    }

    public Occasion(OccasionName occasionName, OccasionDate occasionDate,
                    TagMap tags) {
        this(occasionName, occasionDate, null, tags, null);
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

    public String getLocation() {
        return location;
    }

    /**
     * Get the set of Tags.
     *
     * @return An immutable tag set, which throws {@code
     * UnsupportedOperationException} if modification is attempted.
     */
    // TODO change the implementation of all the places that use this method.
    public Map<TagKey, TagValue> getTags() {
        return Collections.unmodifiableMap(tags.getTagMap());
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
        getTags().forEach((key, value) -> stringBuilder.append(key + ": " + value + " "));
        return stringBuilder.toString();
    }
}
