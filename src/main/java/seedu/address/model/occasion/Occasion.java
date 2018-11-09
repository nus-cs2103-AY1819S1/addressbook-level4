package seedu.address.model.occasion;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import seedu.address.commons.util.TypeUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

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
                    Set<Tag> tags, TypeUtil type, UniquePersonList personList) {
        requireAllNonNull(occasionName, occasionDate, tags, type);
        this.occasionName = occasionName;
        this.occasionDate = occasionDate;
        this.location = location;
        this.attendanceList = personList;
        this.tags.addAll(tags);
    }

    public Occasion(OccasionName occasionName, OccasionDate occasionDate, OccasionLocation location,
                    Set<Tag> tags, TypeUtil type, List<Person> attendanceList) {
        requireAllNonNull(occasionName, occasionDate, tags, type, attendanceList);
        this.occasionName = occasionName;
        this.occasionDate = occasionDate;
        this.location = location;
        this.attendanceList = new UniquePersonList(attendanceList);
        this.tags.addAll(tags);
    }

    public Occasion(OccasionDescriptor toCopy) {
        requireNonNull(toCopy);
        this.occasionName = toCopy.getOccasionName().orElse(new OccasionName());
        this.occasionDate = toCopy.getOccasionDate().orElse(new OccasionDate());
        this.location = toCopy.getOccasionLocation().orElse(new OccasionLocation());
        this.attendanceList = new UniquePersonList(new ArrayList<>());
        this.tags.addAll(toCopy.getTags().orElse(new HashSet<Tag>()));
    }

    public Occasion(OccasionName occasionName, OccasionDate occasionDate,
                    Set<Tag> tags) {
        this(occasionName, occasionDate, null, tags, TypeUtil.OCCASION, new UniquePersonList());
    }

    /**
     * Creates and returns a {@code Occasion} with the details of {@code occasionToEdit}
     * edited with {@code editOccasionDescriptor}.
     */
    public static Occasion createEditedOccasion(Occasion occasionToEdit, OccasionDescriptor editOccasionDescriptor) {
        assert occasionToEdit != null;

        OccasionName updatedOccasionName =
                editOccasionDescriptor.getOccasionName().orElse(occasionToEdit.getOccasionName());
        OccasionDate updatedOccasionDate =
                editOccasionDescriptor.getOccasionDate().orElse(occasionToEdit.getOccasionDate());
        OccasionLocation updatedOccasionLocation =
                editOccasionDescriptor.getOccasionLocation().orElse(occasionToEdit.getOccasionLocation());
        UniquePersonList updatedPersonList =
                editOccasionDescriptor.getAttendanceList().orElse(occasionToEdit.getAttendanceList());
        Set<Tag> updatedTags = editOccasionDescriptor.getTags().orElse(occasionToEdit.getTags());

        return new Occasion(updatedOccasionName, updatedOccasionDate, updatedOccasionLocation,
                updatedTags, TypeUtil.OCCASION, updatedPersonList);
    }

    public OccasionName getOccasionName() {
        return occasionName;
    }

    public OccasionDate getOccasionDate() {
        return occasionDate;
    }

    public UniquePersonList getAttendanceList() {
        return attendanceList == null ? new UniquePersonList() : attendanceList;
    }

    public OccasionLocation getOccasionLocation() {
        return location;
    }

    public Property occasionNameProperty() {
        return new SimpleStringProperty(occasionName.fullOccasionName);
    }

    /**
     * Make an identical deep copy of this occasion.
     */
    public Occasion makeDeepDuplicate() {
        OccasionName newName = this.occasionName.makeDeepDuplicate();
        OccasionDate newDate = this.occasionDate.makeDeepDuplicate();
        OccasionLocation newLocation = this.location.makeDeepDuplicate();
        UniquePersonList newList = this.attendanceList.makeDeepDuplicate();
        Set<Tag> newTags = this.tags.stream().map(value -> value.makeDeepDuplicate()).collect(Collectors.toSet());
        return new Occasion(newName, newDate, newLocation, newTags, TypeUtil.OCCASION, newList.asNormalList());
    }

    /**
     * Make an identical copy of this occasion with an empty person list.
     */
    public Occasion makeShallowDuplicate() {
        OccasionName newName = this.occasionName.makeDeepDuplicate();
        OccasionDate newDate = this.occasionDate.makeDeepDuplicate();
        OccasionLocation newLocation = this.location.makeDeepDuplicate();
        UniquePersonList newList = new UniquePersonList();
        Set<Tag> newTags = this.tags.stream().map(value -> value.makeDeepDuplicate()).collect(Collectors.toSet());
        return new Occasion(newName, newDate, newLocation, newTags, TypeUtil.OCCASION, newList.asNormalList());
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
     * Returns true if both occasion have the same name and date.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameOccasion (Occasion otherOccasion) {
        if (otherOccasion == this) {
            return true;
        }

        return otherOccasion != null
                && otherOccasion.getOccasionName().equals(this.getOccasionName())
                && otherOccasion.getOccasionDate().equals(this.getOccasionDate());
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
                && otherOccasion.getOccasionLocation().equals(this.getOccasionLocation())
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
                // object.
                .append(" Tags: ");
        getTags().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
