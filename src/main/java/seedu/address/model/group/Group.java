package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private final Optional<Description> description;

    // Data fields
    private Optional<Meeting> meeting;
    private final UniquePersonList members;

    /**
     * Constructor for a simple group which requires no meeting details.
     * Members field is initialised to be empty.
     *
     * Title is guaranteed to be present and non-null.
     *
     * However, the meeting can be null and update later.
     *
     * @param title The name of the group
     */
    public Group(Title title) {
        requireNonNull(title);
        this.title = title;
        this.description = Optional.empty();
        this.meeting = Optional.empty();
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
        this.description = Optional.of(description);
        this.meeting = Optional.of(meeting);
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
        this.description = Optional.of(description);
        this.meeting = Optional.empty();
        this.members = members;
    }

    /**
     * Constructor for a full description of group, Members field is initialised to an existing list of members.
     *
     * @param title The name of the group
     * @param description The description of the group or the agenda of meeting
     * @param meeting The upcoming meeting for people in this group
     * @param members The list of members of the group
     */
    public Group(Title title, Optional<Description> description, Optional<Meeting> meeting,
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
        return description.orElse(null);
    }

    public Meeting getMeeting() {
        return meeting.orElse(null);
    }

    // @@author NyxF4ll
    /**
     * Set this group's meeting to {@code meeting}.
     */
    public void setMeeting(Meeting meeting) {
        requireNonNull(meeting);
        this.meeting = Optional.of(meeting);
    }

    /**
     * Set this group's meeting to be an empty optional.
     */
    public void cancelMeeting() {
        this.meeting = Optional.empty();
    }
    // @@author

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
        this.members.remove(toRemove);

        toRemove.removeGroupHelper(this);
    }

    /**
     * This method is reserved to be called only from
     * {@link seedu.address.model.person.Person#removeGroup(Group)} method.
     */
    public void removeMemberHelper(Person person) {
        this.members.remove(person);
    }

    /**
     * Remove all the members in this group.
     */
    public void clearMembers() {
        // enhanced for loop to remove the group from person
        for (Person member : this.members) {
            if (member.hasGroup(this)) {
                member.removeGroup(this);
            }
        }
        this.members.clear();
    }

    /**
     * Create a copy of this group.
     */
    public Group copy() {
        return new Group(title, description, meeting, members);
    }

    /**
     * Returns true if both groups have the same title.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
                && otherGroup.getTitle().equals(getTitle());
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
                && otherGroup.description.equals(this.description)
                && otherGroup.meeting.equals(this.meeting)
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
