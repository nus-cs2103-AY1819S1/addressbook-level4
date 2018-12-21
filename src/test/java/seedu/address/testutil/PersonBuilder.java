package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.attendance.AttendanceMark;
import seedu.address.model.common.Mark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePhoto;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private ProfilePhoto photo;
    private Set<Tag> tags;
    private Map<String, Mark> marks;
    private Map<String, AttendanceMark> attendanceMarks;
    private Note note;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        photo = new ProfilePhoto();
        tags = new HashSet<>();
        marks = new HashMap<>();
        attendanceMarks = new HashMap<>();
        note = new Note();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        photo = personToCopy.getProfilePhoto();
        tags = new HashSet<>(personToCopy.getTags());
        marks = new HashMap<>(personToCopy.getMarks());
        attendanceMarks = new HashMap<>(personToCopy.getAttendance());
        note = personToCopy.getNote();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code Person} that we are building.
     */
    public PersonBuilder withNote(String text) {
        this.note = new Note(text);
        return this;
    }

    /**
     * Sets the {@code Marks} of the {@code Person} that we are building.
     */
    public PersonBuilder withMarks(Map<String, Mark> marks) {
        this.marks = marks;
        return this;
    }

    /**
     * Sets the {@code AttendanceMarks} of the {@code Person} that we are building.
     */
    public PersonBuilder withAttendanceMarks(Map<String, AttendanceMark> attendanceMarks) {
        this.attendanceMarks = attendanceMarks;
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, photo, tags, marks, attendanceMarks, note);
    }

}
