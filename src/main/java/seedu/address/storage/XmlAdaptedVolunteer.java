package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.volunteer.Address;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerId;

/**
 * JAXB-friendly version of the Volunteer.
 */
public class XmlAdaptedVolunteer {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Volunteer's %s field is missing!";

    @XmlElement(required = true)
    private int volunteerId;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String gender;
    @XmlElement(required = true)
    private String birthday;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedVolunteer.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedVolunteer() {
    }

    /**
     * Constructs an {@code XmlAdaptedVolunteer} with the given volunteer details.
     */
    public XmlAdaptedVolunteer(String name, String gender, String birthday, String phone, String email,
                            String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }

    }

    /**
     * Constructs an {@code XmlAdaptedVolunteer} with the given volunteer details.
     */
    public XmlAdaptedVolunteer(int volunteerId, String name, String gender, String birthday, String phone,
                            String email, String address, List<XmlAdaptedTag> tagged) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Volunteer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedVolunteer
     */
    public XmlAdaptedVolunteer(Volunteer source) {
        volunteerId = source.getVolunteerId().id;
        name = source.getName().fullName;
        gender = source.getGender().value;
        birthday = source.getBirthday().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted volunteer object into the model's Volunteer object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted volunteer
     */
    public Volunteer toModelType() throws IllegalValueException {
        final List<Tag> volunteerTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            volunteerTags.add(tag.toModelType());
        }
        if (volunteerId == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    VolunteerId.class.getSimpleName()));
        }
        if (!VolunteerId.isValidId(volunteerId)) {
            throw new IllegalValueException(VolunteerId.MESSAGE_NAME_CONSTRAINTS);
        }
        VolunteerId modelVolunteerId = new VolunteerId(volunteerId);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_GENDER_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (birthday == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Birthday.class.getSimpleName()));
        }
        if (!Birthday.isValidBirthday(birthday)) {
            throw new IllegalValueException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        if (!Birthday.isLessThanOrEqualToValidBirthday(birthday)) {
            throw new IllegalValueException(Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        final Birthday modelBirthday = new Birthday(birthday);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(volunteerTags);
        return new Volunteer(modelVolunteerId, modelName, modelGender, modelBirthday, modelPhone,
                modelEmail, modelAddress, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedVolunteer)) {
            return false;
        }

        XmlAdaptedVolunteer otherVolunteer = (XmlAdaptedVolunteer) other;
        return Objects.equals(name, otherVolunteer.name)
                && Objects.equals(gender, otherVolunteer.gender)
                && Objects.equals(birthday, otherVolunteer.birthday)
                && Objects.equals(phone, otherVolunteer.phone)
                && Objects.equals(email, otherVolunteer.email)
                && Objects.equals(address, otherVolunteer.address)
                && tagged.equals(otherVolunteer.tagged);
    }
}
