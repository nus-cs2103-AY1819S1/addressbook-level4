package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.volunteer.Address;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerId;

/**
 * A utility class to help with building Person objects.
 */
public class VolunteerBuilder {
    public static final int DEFAULT_VOLUNTEERID = 1;
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_GENDER = "f";
    public static final String DEFAULT_BIRTHDAY = "01-02-1993";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private VolunteerId volunteerId;
    private Name name;
    private Gender gender;
    private Birthday birthday;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public VolunteerBuilder() {
        volunteerId = new VolunteerId(DEFAULT_VOLUNTEERID);
        name = new Name(DEFAULT_NAME);
        gender = new Gender(DEFAULT_GENDER);
        birthday = new Birthday(DEFAULT_BIRTHDAY);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }


    /**
     * Initializes the VolunteerBuilder with the data of {@code volunteerToCopy}.
     */
    public VolunteerBuilder(Volunteer volunteerToCopy) {
        volunteerId = volunteerToCopy.getVolunteerId();
        name = volunteerToCopy.getName();
        gender = volunteerToCopy.getGender();
        birthday = volunteerToCopy.getBirthday();
        phone = volunteerToCopy.getPhone();
        email = volunteerToCopy.getEmail();
        address = volunteerToCopy.getAddress();
        tags = new HashSet<>(volunteerToCopy.getTags());
    }

    /**
     * Sets the {@code VolunteerId} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withVolunteerId(int volunteerId) {
        this.volunteerId = new VolunteerId(volunteerId);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withBirthday(String birthday) {
        this.birthday = new Birthday(birthday);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Volunteer build() {
        return new Volunteer(name, gender, birthday, phone, email, address, tags);
    }

}

