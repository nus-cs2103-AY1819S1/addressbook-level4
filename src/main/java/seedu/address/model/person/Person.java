package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.AttendanceMark;
import seedu.address.model.common.Mark;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private ProfilePhoto photo;
    private final Set<Tag> tags = new HashSet<>();
    private final Map<String, Mark> marks = new HashMap<>();
    private final Map<String, AttendanceMark> attendance = new HashMap<>();
    private Note note = new Note();

    /**
     * Default constructor, sets default pic and note, every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Map<String, Mark> marks) {
        this(name, phone, email, address, tags);
        requireNonNull(marks);
        this.marks.putAll(marks);

        // default initialization
        this.photo = new ProfilePhoto();
    }

    /**
     * Has note, no pic, calls default constructor for setting defaults.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Map<String, Mark> marks,
                  Note note) {
        this(name, phone, email, address, tags, marks);
        requireNonNull(note);
        this.note = note;
    }

    /**
     * No note, has pic, calls default constructor for setting defaults.
     */
    public Person(Name name, Phone phone, Email email, Address address, ProfilePhoto photo, Set<Tag> tags,
                  Map<String, Mark> marks) {
        this(name, phone, email, address, tags, marks);
        requireNonNull(photo);
        this.photo = photo;
    }

    /**
     * Constructor for EditPersonDescriptor
     * Has both note and pic, calls default constructor for setting defaults before overwriting.
     */
    public Person(Name name, Phone phone, Email email, Address address, ProfilePhoto photo, Set<Tag> tags,
                  Map<String, Mark> marks, Map<String, AttendanceMark> attendance, Note note) {
        this(name, phone, email, address, tags, marks);
        requireAllNonNull(photo, attendance, note);
        this.attendance.putAll(attendance);
        this.photo = photo;
        this.note = note;
    }

    /**
     * Constructor for when there are no marks given.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);

        // default initialization
        this.photo = new ProfilePhoto();
    }

    /**
     * Copy constructor.
     */
    public Person(Person source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getProfilePhoto(),
            source.getTags(), source.getMarks(), source.getAttendance(), source.getNote());
    }

    public Name getName() {
        return name;
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

    public ProfilePhoto getProfilePhoto() {
        return photo;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Delete the current photo and set up a default photo.
     */
    public void deleteProfilePhoto() {
        this.photo = new ProfilePhoto();
    }

    /**
     * Set profile photo to that in path
     */
    public void setProfilePhoto(String path) throws IllegalValueException {

        ProfilePhoto oldPhoto = this.photo;
        try {
            int fileName = this.hashCode();
            this.photo = new ProfilePhoto(path, String.valueOf(fileName));
        } catch (Exception e) {
            this.photo = oldPhoto; //changes photo back to default
            throw new IllegalValueException(ProfilePhoto.MESSAGE_PHOTO_CONSTRAINTS);
        }
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable mark map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<String, Mark> getMarks() {
        return Collections.unmodifiableMap(marks);
    }

    /**
     * Returns an immutable attendance map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<String, AttendanceMark> getAttendance() {
        return Collections.unmodifiableMap(attendance);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getProfilePhoto().equals(getProfilePhoto())
                && otherPerson.getMarks().equals(getMarks())
                && otherPerson.getAttendance().equals(getAttendance())
                && otherPerson.getNote().equals(getNote());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, marks, attendance);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Note: ")
               .append(getNote());
        return builder.toString();
    }

    /**
     * Returns true if current note has been edited, false if is default.
     */
    public boolean hasNote() {
        return note.hasChanged();
    }
}
