package seedu.address.model.group;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


/**
 * Represents a Group for people in the address book.
 * Guarantees: title and description are present and not null,
 * field values are validated, immutable.
 */
public class Group {

    // Identity fields
    private final Title title;

    // Data fields
    private final Place place;
    private final Date date;
    private final Description description;
    private final UniquePersonList members = new UniquePersonList();

    /**
     * Constructor for a simple group which requires no meeting
     * details(i.e. date & place).
     *
     * Title and description are guaranteed to be present and non-null.
     *
     * However, the date and place can still be updated later.
     *
     * @param title The name of the group
     * @param description The description of the group or the agenda of meeting
     */
    public Group(Title title, Description description) {
        requireAllNonNull(title, description);
        this.title = title;
        this.description = description;
        this.date = null;
        this.place = null;
    }

    /**
     * Constructor for a full description of group, where all details
     * must be present
     *
     * @param title The name of the group
     * @param place The location of group meeting
     * @param date The specific date of the next meeting
     * @param description The description of the group or the agenda of meeting
     */
    public Group(Title title, Place place, Date date, Description description) {
        requireAllNonNull(title, place, date, description);
        this.title = title;
        this.place = place;
        this.date = date;
        this.description = description;
    }

    public Title getTitle() {
        return title;
    }

    public Place getPlace() {
        return place;
    }

    public Date getDate() {
        return date;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns an immutable list of members in this group, which
     * throws {@code UnsupportedOperationException} if modification
     * is attempted.
     */
    public List<Person> getMembers() {
        return Collections.unmodifiableList(members.asUnmodifiableObservableList());
    }

    /**
     * Returns true if both groups have the same title and description.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
                && otherGroup.getTitle().equals(getTitle())
                && otherGroup.getDescription().equals(getDescription());
    }

    /**
     * Returns true if both groups have the same identity and data fields.
     * This defines a stronger notion of equality between two groups.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getTitle().equals(getTitle())
                && otherGroup.getPlace().equals(getPlace())
                && otherGroup.getDate().equals(getDate())
                && otherGroup.getDescription().equals(getDescription())
                && otherGroup.getMembers().equals(getMembers());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, place, date, description, members);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle() + "\n")
                .append("Description: ")
                .append(getDescription() + "\n")
                .append("Next meeting: ")
                .append(getDate() + "\n")
                .append(getPlace() + "\n")
                .append("Members: ");
        getMembers().forEach(member -> builder.append(member + "\n"));

        return builder.toString();
    }

}
