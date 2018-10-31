package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.MedicalHistory;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
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
    private String remark;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedAppointment> upcomingAppointments = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedAppointment> pastAppointments = new ArrayList<>();
    @XmlElement
    private MedicalHistory medicalHistory = new MedicalHistory();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address,
                            String remark, List<XmlAdaptedTag> tagged, List<XmlAdaptedAppointment> upcomingAppointments,
                            List<XmlAdaptedAppointment> pastAppointments) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (upcomingAppointments != null) {
            this.upcomingAppointments = new ArrayList<>(upcomingAppointments);
        }
        if (pastAppointments != null) {
            this.pastAppointments = new ArrayList<>(pastAppointments);
        }

    }
    public XmlAdaptedPerson(String name, String phone, String email, String address,
                            String remark, List<XmlAdaptedTag> tagged, List<XmlAdaptedAppointment> upcomingAppointments,
                            List<XmlAdaptedAppointment> pastAppointments, MedicalHistory medicalHistory) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (upcomingAppointments != null) {
            this.upcomingAppointments = new ArrayList<>(upcomingAppointments);
        }
        if (pastAppointments != null) {
            this.pastAppointments = new ArrayList<>(pastAppointments);
        }
        if (medicalHistory != null) {
            this.medicalHistory = medicalHistory;
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
        remark = source.getRemark().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());

        if (!tagged.isEmpty() && tagged.get(0).equals(new XmlAdaptedTag("Patient"))) {
            upcomingAppointments = ((Patient) source).getUpcomingAppointments().stream()
                    .map(XmlAdaptedAppointment::new)
                    .collect(Collectors.toList());
            pastAppointments = ((Patient) source).getPastAppointments().stream()
                    .map(XmlAdaptedAppointment::new)
                    .collect(Collectors.toList());
            medicalHistory = ((Patient) source).getMedicalHistory();
        } else if (!tagged.isEmpty() && tagged.get(0).equals(new XmlAdaptedTag("Doctor"))) {
            upcomingAppointments = ((Doctor) source).getUpcomingAppointments().stream()
                    .map(XmlAdaptedAppointment::new)
                    .collect(Collectors.toList());
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

        final PriorityQueue<Appointment> allUpcomingAppointments = new PriorityQueue<>();
        for (XmlAdaptedAppointment upcomingAppointment : upcomingAppointments) {
            allUpcomingAppointments.add(upcomingAppointment.toModelType());
        }

        final List<Appointment> allPastAppointments = new ArrayList<>();
        for (XmlAdaptedAppointment pastAppointments : pastAppointments) {
            allPastAppointments.add(pastAppointments.toModelType());
        }

        final MedicalHistory modelMedicalHistory = new MedicalHistory(medicalHistory.getAllergies(),
                medicalHistory.getConditions());

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

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

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        if (!modelTags.isEmpty() && modelTags.toArray()[0].equals(new Tag("Doctor"))) {
            return new Doctor(modelName, modelPhone, modelEmail, modelAddress, modelRemark, modelTags,
                    allUpcomingAppointments);
        } else if (!modelTags.isEmpty() && modelTags.toArray()[0].equals(new Tag("Patient"))) {
            return new Patient(modelName, modelPhone, modelEmail, modelAddress, modelRemark, modelTags, "123",
                    allUpcomingAppointments, allPastAppointments, modelMedicalHistory);
        } else {
            return new Person(modelName, modelPhone, modelEmail, modelAddress, modelRemark, modelTags);
        }

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
                && Objects.equals(remark, otherPerson.remark)
                && tagged.equals(otherPerson.tagged);
    }
}
