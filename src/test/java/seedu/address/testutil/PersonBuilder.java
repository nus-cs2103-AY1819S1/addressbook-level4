package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Picture;
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
    public static final String DEFAULT_MEETING = "0000000000";

    private Name name;
    private Optional<Phone> phone;
    private Optional<Email> email;
    private Optional<Address> address;
    private Set<Tag> tags;
    private Picture picture;
    private Meeting meeting;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = Optional.of(new Phone(DEFAULT_PHONE));
        email = Optional.of(new Email(DEFAULT_EMAIL));
        address = Optional.of(new Address(DEFAULT_ADDRESS));
        tags = new HashSet<>();
        picture = new Picture(Picture.DEFAULT_PICTURE_URL.getPath());
        meeting = new Meeting(DEFAULT_MEETING);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        picture = personToCopy.getPicture();
        meeting = personToCopy.getMeeting();
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
        this.address = Optional.of(new Address(address));
        return this;
    }

    //@@author zioul123
    /**
     * Sets the {@code Address} of the {@code Person} we are building to null, for
     * testing people without an address field.
     */
    public PersonBuilder withoutAddress() {
        this.address = Optional.empty();
        return this;
    }
    //@@author

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = Optional.of(new Phone(phone));
        return this;
    }

    //@@author zioul123
    /**
     * Sets the {@code Phone} of the {@code Person} we are building to null, for testing people without a phone field.
     */
    public PersonBuilder withoutPhone() {
        this.phone = Optional.empty();
        return this;
    }
    //@@author

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = Optional.of(new Email(email));
        return this;
    }

    //@@author zioul123
    /**
     * Sets the {@code Email} of the {@code Person} we are building to null, for testing people without an email field.
     */
    public PersonBuilder withoutEmail() {
        this.email = Optional.empty();
        return this;
    }

    //@@author denzelchung
    /**
     * Sets the {@code Picture} of the {@code Person} that we are building.
     */
    public PersonBuilder withPicture(String picture) {
        this.picture = new Picture(picture);
        return this;
    }

    //@@author AyushChatto
    /**
     * Sets the {@code Meeting} of the {@code Person} that we are building.
     */
    public PersonBuilder withMeeting(String meeting) {
        this.meeting = new Meeting(meeting);
        return this;
    }

    //@@author
    /**
     * Build the person with the given fields.
     */
    public Person build() {
        Person person = new Person(name, phone, email, address, tags, meeting);
        person.setPicture(picture);
        return person;
    }
}
