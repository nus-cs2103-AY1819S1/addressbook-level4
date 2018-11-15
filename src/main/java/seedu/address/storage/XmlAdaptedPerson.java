package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UniqueList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String storedLocation;
    @XmlElement(required = true)
    private String timetableString;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedGroup> groups = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details. # TODO v1.3: Combine 2
     * different constructors
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address,
        List<XmlAdaptedTag> tagged, String storedLocation, String timetableString) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.storedLocation = storedLocation;
        this.timetableString = timetableString;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }

        this.groups = new ArrayList<>();
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details including {@code
     * groups}.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address,
        List<XmlAdaptedGroup> groups,
        List<XmlAdaptedTag> tagged, String storedLocation, String timetableString) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.storedLocation = storedLocation;
        this.timetableString = timetableString;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (groups != null) {
            this.groups = new ArrayList<>(groups);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        storedLocation = source.getStoredLocation();
        timetableString = source.getTimetable().getTimetableDataString();
        tagged = source.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
        groups = source.getGroups().stream()
            .map(XmlAdaptedGroup::new)
            .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);
        final Set<Tag> modelTags = new HashSet<>(personTags);

        final List<Group> groupList = new ArrayList<>();
        for (XmlAdaptedGroup group : groups) {
            groupList.add(group.toModelType());
        }

        final UniqueList<Group> modelGroupList = new UniqueList<>();
        modelGroupList.setElements(groupList);
        if (timetableString == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Timetable.class.getSimpleName()));
        }
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags,
            modelGroupList,
            storedLocation, timetableString);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
            && Objects.equals(phone, otherPerson.phone)
            && Objects.equals(email, otherPerson.email)
            && Objects.equals(address, otherPerson.address)
            && tagged.equals(otherPerson.tagged);
    }
}
