package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Phone;
import seedu.address.model.person.medicalrecord.MedicalRecord;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Patient.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String icNumber;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private XmlAdaptedMedicalRecord medicalRecord;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given patient details.
     */
    public XmlAdaptedPerson(String name, String icNumber, String phone, String email, String address,
                            XmlAdaptedMedicalRecord medicalRecord, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.icNumber = icNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (medicalRecord != null) {
            this.medicalRecord = medicalRecord;
        }
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Patient into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Patient source) {
        name = source.getName().fullName;
        icNumber = source.getIcNumber().value;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        medicalRecord = new XmlAdaptedMedicalRecord(source.getMedicalRecord());
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted patient object into the model's Patient object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted patient
     */
    public Patient toModelType() throws IllegalValueException {
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

        if (icNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, IcNumber.class.getSimpleName()));
        }
        if (!IcNumber.isValidIcNumber(icNumber)) {
            throw new IllegalValueException(IcNumber.MESSAGE_ICNUMBER_CONSTRAINTS);
        }
        final IcNumber modelIcNumber = new IcNumber(icNumber);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (medicalRecord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicalRecord.class.getSimpleName()));
        }
        final MedicalRecord modelMedicalRecord = new MedicalRecord(medicalRecord.toModelType());

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Patient(modelName, modelIcNumber, modelPhone, modelEmail, modelAddress, modelTags, modelMedicalRecord);
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
                && Objects.equals(icNumber, otherPerson.icNumber)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
