package seedu.clinicio.model.patient;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.model.tag.Tag;

//@@author iamjackslayer
/**
 * Represents a patient in ClinicIO
 * Guarantees: Details inherited from Person, as well as NRIC must be present and not null.
 * A patient may or may not have a preferredDoctor, consultation, medical history and appointment.
 */
public class Patient extends Person {
    private Set<MedicalProblem> medicalProblemss = new HashSet<>();
    private Set<Allergy> allergies = new HashSet<>();
    private Optional<Doctor> preferredDoctor = Optional.empty();
    private Optional<Appointment> appointment = Optional.empty();

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
