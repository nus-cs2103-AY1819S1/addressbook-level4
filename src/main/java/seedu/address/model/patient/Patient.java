package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.Consultation;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;



//@@author iamjackslayer
/**
 * Represents a patient in ClinicIO
 * Guarantees: Details inherited from Person, as well as NRIC must be present and not null.
 * A patient may or may not have a preferredDoctor, consultation, medical history and appointment.
 */
public class Patient extends Person {
    private boolean isQueuing = false;
    private Optional<Doctor> preferredDoctor = Optional.empty();
    private Optional<Appointment> appointment = Optional.empty();
    private Consultation consultation;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }

    /**
     * Contructs a patient from a given person.
     * @param person
     */
    public static Patient buildFromPerson(Person person) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Set<Tag> tags = person.getTags();
        return new Patient(name, phone, email, address, tags);
    }

    /**
     * Marks the patient with "isQueing" status.
     */
    public void setIsQueuing() {
        isQueuing = true;
    }

    /**
     * Marks the patient with "isNotQueing" status.
     */
    public void setIsNotQueuing() {
        isQueuing = false;
    }

    /**
     * Returns the patient's preferred doctor wrapped in {@link Optional}. The patient may not have one.
     * @return an Optional {@link Doctor}.
     */
    public Optional<Doctor> getPreferredDoctor() {
        return preferredDoctor;
    }

    /**
     * Returns patient's appointment. The patient may not have one.
     * @return an Optional {@link Appointment}
     */
    public Optional<Appointment> getAppointment() {
        return appointment;
    }

    /**
     * Assigns a specific doctor for the patient.
     * @param doctor
     */
    public void setPreferredDoctor(Doctor doctor) {
        requireNonNull(doctor);

        preferredDoctor = Optional.of(doctor);
    }

    /**
     * Sets an appointment for the patient.
     * @param appointment
     */
    public void setAppointment(Appointment appointment) {
        requireNonNull(appointment);

        this.appointment = Optional.of(appointment);
    }

    /**
     * Sets a consultation for the patient. A consultation must be set whenever a Patient obj is created.
     * @param consultation
     */
    public void setConsultation(Consultation consultation) {
        requireNonNull(consultation);
        this.consultation = consultation;
    }

    /**
     * Checks if the patient has preferred doctor.
     */
    public boolean hasPreferredDoctor() {
        return preferredDoctor.isPresent();
    }

    /**
     * Checks if patient has an appointment.
     */
    public boolean hasAppointment() {
        return appointment.isPresent();
    }

    /**
     * Returns true if both patients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two patients.
     */
    @Override
    public boolean isSamePerson(Person otherPerson) {
        if (!(otherPerson instanceof Patient)) {
            return false;
        }

        if (otherPerson == this) {
            return true;
        }

        Patient otherPatient = (Patient) otherPerson;
        return otherPatient != null
                && otherPatient.getName().equals(getName())
                && (otherPatient.getPhone().equals(getPhone()) || otherPatient.getEmail().equals(getEmail()))
                && otherPatient.getPreferredDoctor().equals(getPreferredDoctor())
                && otherPatient.getAppointment().equals(getAppointment());
    }

    /**
     * Checks if the patient is in the queue. Note that there is only one queue on a higher conceptual level.
     * @return true if the patient is in the queue. Otherwise returns false.
     */
    public boolean isQueuing() {
        return isQueuing;
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(getName())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getEmail().equals(getEmail())
                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getTags().equals(getTags())
                && otherPatient.getPreferredDoctor().equals(getPreferredDoctor())
                && otherPatient.getAppointment().equals(getAppointment());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        Optional<Doctor> preferredDoctor = getPreferredDoctor();
        Optional<Appointment> appointment = getAppointment();
        Name name = getName();
        Phone phone = getPhone();
        Email email = getEmail();
        Address address = getAddress();
        Set<Tag> tags = getTags();

        return Objects.hash(name, phone, email, address, tags, preferredDoctor, appointment);
    }
}
