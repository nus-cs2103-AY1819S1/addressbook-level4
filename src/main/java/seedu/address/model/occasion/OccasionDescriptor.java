package seedu.address.model.occasion;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Stores the details of an occasion to be added or edited. Each non-empty field value will replace the
 * corresponding field value of the occasion.
 */
public class OccasionDescriptor {
    private OccasionName occasionName;
    private OccasionDate occasionDate;
    private OccasionLocation occasionLocation;
    private UniquePersonList attendanceList = new UniquePersonList();
    private Set<Tag> tags;

    public OccasionDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public OccasionDescriptor(OccasionDescriptor toCopy) {
        setOccasionName(toCopy.occasionName);
        setOccasionDate(toCopy.occasionDate);
        setOccasionLocation(toCopy.occasionLocation);
        setAttendanceList(toCopy.getAttendanceList().get());
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldNotEmpty() {
        return CollectionUtil.isAnyNonNull(occasionName, occasionDate, occasionLocation, tags);
    }

    public void setOccasionName(OccasionName occasionName) {
        this.occasionName = occasionName;
    }

    public Optional<OccasionName> getOccasionName() {
        return Optional.ofNullable(occasionName);
    }

    public void setOccasionDate(OccasionDate occasionDate) {
        this.occasionDate = occasionDate;
    }

    public Optional<OccasionDate> getOccasionDate() {
        return Optional.ofNullable(occasionDate);
    }

    public void setOccasionLocation(OccasionLocation occasionLocation) {
        this.occasionLocation = occasionLocation;
    }

    public Optional<OccasionLocation> getOccasionLocation() {
        return Optional.ofNullable(occasionLocation);
    }

    public void setAttendanceList(UniquePersonList personList) {
        for (Person person : personList.asUnmodifiableObservableList()) {
            attendanceList.add(person);
        }
    }

    public void setAttendanceList(List<Person> personList) {
        for (Person person : personList) {
            attendanceList.add(person);
        }
    }

    public Optional<UniquePersonList> getAttendanceList() {
        return Optional.ofNullable(attendanceList);
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


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OccasionDescriptor)) {
            return false;
        }

        // state check
        OccasionDescriptor e = (OccasionDescriptor) other;

        return getOccasionName().equals(e.getOccasionName())
                && getOccasionDate().equals(e.getOccasionDate())
                && getOccasionLocation().equals(e.getOccasionLocation())
                && getTags().equals(e.getTags());
    }
}
