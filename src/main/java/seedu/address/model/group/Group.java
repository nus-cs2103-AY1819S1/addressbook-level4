package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;


/**
 * Represents a Group for participants in the address book.
 * The person will be able to have a list of groups that he/she has enrolled.
 * The group will also keep track of a list of its members.
 * Every group will contain one and only one meeting. The new meeting will always
 * overwrite the old one.
 *
 * Meeting can be {@code null} if no meeting is required for members of this group.
 *
 * Guarantees: title, description and UniquePersonList are present and not null,
 * field values are validated, immutable.
 *
 * {@author Derek-Hardy}
 */
public class Group {

    // Identity fields
    private final Title title;
    private final Description description;

    // Data fields
    private final Meeting meeting;
    private final UniquePersonList members;

    /**
     * Constructor for a simple group which requires no meeting details.
     * Members field is initialised to be empty.
     *
     * Title and description are guaranteed to be present and non-null.
     *
     * However, the meeting can be null and update later.
     *
     * @param title The name of the group
     * @param description The description of the group or the agenda of meeting
     */
    public Group(Title title, Description description) {
        requireAllNonNull(title, description);
        this.title = title;
        this.description = description;
        this.meeting = null;
        this.members = new UniquePersonList();
    }

    /**
     * Constructor for a simple group, where title, description and meeting
     * must be present. Members field is initialised to be empty.
     *
     * @param title The name of the group
     * @param description The description of the group or the agenda of meeting
     * @param meeting The upcoming meeting for people in this group
     */
    public Group(Title title, Description description, Meeting meeting) {
        requireAllNonNull(title, meeting, description);
        this.title = title;
        this.description = description;
        this.meeting = meeting;
        this.members = new UniquePersonList();
    }

    /**
     * Constructor for a simple group, where all details
     * must be present. Members field is initialised to an existing list of members.
     *
     * @param title The name of the group
     * @param description The description of the group or the agenda of meeting
     * @param members The list of members of the group
     */
    public Group(Title title, Description description, UniquePersonList members) {
        requireAllNonNull(title, description, members);
        this.title = title;
        this.description = description;
        this.meeting = null;
        this.members = members;
    }

    /**
     * Constructor for a full description of group, where all details
     * must be present. Members field is initialised to an existing list of members.
     *
     * @param title The name of the group
     * @param description The description of the group or the agenda of meeting
     * @param meeting The upcoming meeting for people in this group
     * @param members The list of members of the group
     */
    public Group(Title title, Description description, Meeting meeting,
                 UniquePersonList members) {
        requireAllNonNull(title, meeting, description, members);
        this.title = title;
        this.description = description;
        this.meeting = meeting;
        this.members = members;
    }


    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public UniquePersonList getMembers() {
        UniquePersonList toList = new UniquePersonList();
        toList.setPersons(members);
        return toList;
    }

    /**
     * Returns an immutable list of members in this group, which
     * throws {@code UnsupportedOperationException} if modification
     * is attempted.
     */
    public List<Person> getMembersView() {
        return Collections.unmodifiableList(members.asUnmodifiableObservableList());
    }

    /**
     * Returns true if the {@code person} is in this group.
     * @param person The person to check membership
     */
    public boolean hasMember(Person person) {
        return this.members.contains(person);
    }

    /**
     * Add a person to be the member of this group.
     */
    public void addMember(Person toAdd) {
        requireNonNull(toAdd);
        this.members.add(toAdd);

        if (!toAdd.hasGroup(this)) {
            toAdd.addGroup(this);
        }
    }

    /**
     * Remove a person from this group.
     */
    public void removeMember(Person toRemove) {
        requireNonNull(toRemove);
        if (this.members.contains(toRemove)) {
            this.members.remove(toRemove);
        }

        if (toRemove.hasGroup(this)) {
            toRemove.removeGroup(this);
        }
    }

    /**
     * Remove all the members in this group.
     */
    public void clearMembers() {

        this.members.clear();
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
                && otherGroup.getDescription().equals(getDescription())
                && otherGroup.getMeeting().equals(getMeeting())
                && otherGroup.getMembersView().equals(getMembersView());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, meeting, description, members);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle() + "\n")
                .append("Description: ")
                .append(getDescription() + "\n")
                .append("Next meeting details: ")
                .append(getMeeting() + "\n");

        return builder.toString();
    }

}
