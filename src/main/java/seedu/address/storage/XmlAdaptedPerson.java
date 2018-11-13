package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.Visitor;
import seedu.address.model.visitor.VisitorList;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String nric;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement
    private XmlAdaptedPrescriptionList prescriptions = new XmlAdaptedPrescriptionList();
    @XmlElement
    private XmlAdaptedAppointmentsList appointments = new XmlAdaptedAppointmentsList();
    @XmlElement
    private XmlAdaptedDietCollection diets = new XmlAdaptedDietCollection();
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private XmlAdaptedMedicalHistory medicalHistory = new XmlAdaptedMedicalHistory();
    @XmlElement
    private XmlAdaptedVisitorList visitorList = new XmlAdaptedVisitorList();

    /**
     * Constructs an XmlAdaptedPerson. This is the no-arg constructor that is
     * required by JAXB.
     */
    public XmlAdaptedPerson() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String nric, String name, String phone, String email, String address,
        List<XmlAdaptedTag> tagged) {
        this.nric = nric;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String nric, String name, String phone, String email, String address,
                            List<XmlAdaptedTag> tagged, List<XmlAdaptedPrescription> prescriptions,
                            List<XmlAdaptedAppointment> appointments, List<XmlAdaptedDiagnosis> diagnoses,
                            Set<XmlAdaptedDiet> diets, List<XmlAdaptedVisitor> visitors) {
        this(nric, name, phone, email, address, tagged);
        if (prescriptions != null) {
            this.prescriptions.setPrescription(prescriptions);
        }
        if (appointments != null) {
            this.appointments.setAppt(appointments);
        }
        if (diagnoses != null) {
            this.medicalHistory.setMedicalHistory(diagnoses);
        }
        if (diets != null) {
            this.diets.setDiet(diets);
        }
        if (visitors != null) {
            this.visitorList.setVisitorList(visitors);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source
     *            future changes to this will not affect the created
     *            XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        nric = source.getNric().toString();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged = source.getTags().stream().map(XmlAdaptedTag::new).collect(Collectors.toList());
        this.prescriptions.setPrescription(source.getPrescriptionList()
            .stream()
            .map(XmlAdaptedPrescription::new)
            .collect(Collectors.toList()));
        this.appointments.setAppt(source.getAppointmentsList()
            .stream()
            .map(XmlAdaptedAppointment::new)
            .collect(Collectors.toList()));
        this.medicalHistory.setMedicalHistory(source.getMedicalHistory()
            .stream()
            .map(XmlAdaptedDiagnosis::new)
            .collect(Collectors.toList()));
        this.diets.setDiet(source.getDietCollection()
            .stream()
            .map(XmlAdaptedDiet::new)
            .collect(Collectors.toSet()));
        this.visitorList.setVisitorList(source.getVisitorList()
            .stream()
            .map(XmlAdaptedVisitor::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Prescription> prescriptions = new ArrayList<>();
        final List<Appointment> appointments = new ArrayList<>();
        final List<Diagnosis> diagnoses = new ArrayList<>();
        final List<Visitor> visitors = new ArrayList<>();
        final Set<Diet> diets = new HashSet<>();


        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        for (XmlAdaptedPrescription prescription : this.prescriptions) {
            prescriptions.add(prescription.toModelType());
        }

        for (XmlAdaptedAppointment appointment : this.appointments) {
            appointments.add(appointment.toModelType());
        }

        for (XmlAdaptedDiagnosis diagnosis : this.medicalHistory) {
            diagnoses.add(diagnosis.toModelType());
        }

        for (XmlAdaptedVisitor visitor : this.visitorList) {
            visitors.add(visitor.toModelType());
        }

        for (XmlAdaptedDiet diet : this.diets) {
            diets.add(diet.toModelType());
        }

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }

        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_NAME_CONSTRAINTS);
        }

        final Nric modelNric = new Nric(nric);

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

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final PrescriptionList prescriptionList = new PrescriptionList(new ArrayList<Prescription>(prescriptions));

        final AppointmentsList appointmentsList = new AppointmentsList(new ArrayList<Appointment>(appointments));

        final MedicalHistory medicalHistory = new MedicalHistory(new ArrayList<>(diagnoses));

        final VisitorList visitorList = new VisitorList(new ArrayList<>(visitors));

        final DietCollection dietCollection = new DietCollection(new HashSet<>(diets));

        return new Person(modelNric, modelName, modelPhone, modelEmail, modelAddress, modelTags)
            .withPrescriptionList(prescriptionList)
            .withAppointmentsList(appointmentsList)
            .withMedicalHistory(medicalHistory)
            .withDietCollection(dietCollection)
            .withVisitorList(visitorList);
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
            && tagged.equals(otherPerson.tagged)
            && prescriptions.equals(otherPerson.prescriptions)
            && appointments.equals(otherPerson.appointments)
            && medicalHistory.equals(otherPerson.medicalHistory)
            && visitorList.equals(otherPerson.visitorList)
            && diets.equals(otherPerson.diets);
    }
}
