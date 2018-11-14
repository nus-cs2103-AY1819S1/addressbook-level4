package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
    @XmlElement
    private List<XmlAdaptedModule> moduleList = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedOccasion> occasionList = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
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
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        if (source.getModuleList() != null) {
            moduleList = source.getModuleList().asUnmodifiableObservableList()
                    .stream().map(XmlAdaptedModule::new)
                    .collect(Collectors.toList());
        } else {
            moduleList = new ArrayList<>();
        }

        if (source.getOccasionList() != null) {
            occasionList = source.getOccasionList().asUnmodifiableObservableList()
                    .stream().map(XmlAdaptedOccasion::new)
                    .collect(Collectors.toList());
        } else {
            occasionList = new ArrayList<>();
        }

    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        Phone modelPhone;
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (phone.equals("")) {
            modelPhone = new Phone();
        } else {
            if (!Phone.isValidPhone(phone)) {
                throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
            }
            modelPhone = new Phone(phone);
        }

        Email modelEmail;
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }
        if (email.equals("")) {
            modelEmail = new Email();
        } else {
            if (!Email.isValidEmail(email)) {
                throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
            }
            modelEmail = new Email(email);
        }

        Address modelAddress;
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (address.equals("")) {
            modelAddress = new Address();
        } else {
            if (!Address.isValidAddress(address)) {
                throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
            }
            modelAddress = new Address(address);
        }


        if (moduleList == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                Address.class.getSimpleName()));
        }

        List<Module> listOfModules = new ArrayList<>();
        for (XmlAdaptedModule module : moduleList) {
            listOfModules.add(module.toModelType());
        }

        if (occasionList == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                Address.class.getSimpleName()));
        }

        List<Occasion> listOfOccasions = new ArrayList<>();
        for (XmlAdaptedOccasion occasion: occasionList) {
            listOfOccasions.add(occasion.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags,
                                    listOfOccasions, listOfModules);
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
