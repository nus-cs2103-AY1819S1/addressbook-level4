package seedu.address.model.volunteer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Volunteer in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Volunteer {
    // Used to keep track of current max Id in the system
    private static int maxId = 0;

    // Identity fields
    private final VolunteerId volunteerId;
    private final Name name;
    private final Gender gender;
    private final Birthday birthday;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Used when creating new Volunteer. Every field must be present and not null.
     */
    public Volunteer(Name name, Gender gender, Birthday birthday, Phone phone, Email email,
                     Address address, Set<Tag> tags) {
        requireAllNonNull(name, gender, birthday, phone, email, address, tags);

        incrementMaxId();
        this.volunteerId = new VolunteerId(maxId);

        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Used when loading data from XML and editing Volunteer.
     * Every field must be present and not null.
     */
    public Volunteer(VolunteerId volunteerId, Name name, Gender gender, Birthday birthday,
                     Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(volunteerId, name, phone, email, address, tags);

        if (isVolunteerIdGreaterThanMaxId(volunteerId.id)) {
            replaceMaxIdWithVolunteerId(volunteerId.id);
        }
        this.volunteerId = volunteerId;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Increments the current maxId by 1.
     */
    private void incrementMaxId() {
        maxId += 1;
    }

    /**
     * Checks if volunteer id is greater than current max id.
     *
     * @param volunteerId event id from an existing volunteer.
     */
    private boolean isVolunteerIdGreaterThanMaxId(int volunteerId) {
        return volunteerId > maxId;
    }

    /**
     * Replaces max id with volunteer id.
     *
     * @param volunteerId event id from an existing volunteer.
     */
    private void replaceMaxIdWithVolunteerId(int volunteerId) {
        maxId = volunteerId;
    }

    public VolunteerId getVolunteerId() {
        return volunteerId;
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both volunteers of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two volunteers.
     */
    public boolean isSameVolunteer(seedu.address.model.volunteer.Volunteer otherVolunteer) {
        if (otherVolunteer == this) {
            return true;
        }

        return otherVolunteer != null
                && otherVolunteer.getName().equals(getName())
                && (otherVolunteer.getPhone().equals(getPhone()) || otherVolunteer.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both volunteers have the same identity and data fields.
     * This defines a stronger notion of equality between two volunteers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.volunteer.Volunteer)) {
            return false;
        }

        seedu.address.model.volunteer.Volunteer otherVolunteer = (seedu.address.model.volunteer.Volunteer) other;
        return otherVolunteer.getName().equals(getName())
                && otherVolunteer.getGender().equals(getGender())
                && otherVolunteer.getBirthday().equals(getBirthday())
                && otherVolunteer.getPhone().equals(getPhone())
                && otherVolunteer.getEmail().equals(getEmail())
                && otherVolunteer.getAddress().equals(getAddress())
                && otherVolunteer.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, gender, birthday, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Gender: ")
                .append(getGender())
                .append(" Birthday: ")
                .append(getBirthday())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}

