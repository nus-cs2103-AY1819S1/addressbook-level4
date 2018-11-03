package seedu.clinicio.model.patient;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.tag.Tag;

//@@author iamjackslayer
/**
 * Represents a patient in ClinicIO
 * Guarantees: Details inherited from Person, as well as NRIC must be present and not null.
 * A patient may or may not have a preferredDoctor, consultation, medical history and appointment.
 */
public class Patient extends Person {
    private Set<MedicalProblem> medicalProblems = new HashSet<>();
    private Set<Allergy> allergies = new HashSet<>();
    private Optional<Doctor> preferredDoctor = Optional.empty();
    private boolean isQueuing = false;
    private Optional<Staff> preferredDoctor = Optional.empty();
    private Optional<Appointment> appointment = Optional.empty();
    private Time arrivalTime;
    private List<Appointment> appointmentHistory;
    private List<Consultation> consultationHistory;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        appointmentHistory = new ArrayList<>();
        consultationHistory = new ArrayList<>();
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
     * Returns the patient's preferred staff wrapped in {@link Optional}. The patient may not have one.
     * @return an Optional {@link Staff}.
     */
    public Optional<Staff> getPreferredDoctor() {
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
     * Assigns a specific staff for the patient.
     * @param staff
     */
    public void setPreferredDoctor(Staff staff) {
        requireNonNull(staff);

        preferredDoctor = Optional.of(staff);
    }

    /**
     * Sets an appointment for the patient.
     * @param appointment
     */
    public void setAppointment(Appointment appointment) {
        requireNonNull(appointment);
        appointmentHistory.add(appointment);
        this.appointment = Optional.of(appointment);
    }

    /**
     * Adds a {@code Consultation} to the patient's record.
     * A consultation must be set whenever a Patient obj is created.
     * @param consultation consultation of the patient.
     */
    public void addConsultation(Consultation consultation) {
        requireNonNull(consultation);
        this.consultationHistory.add(consultation);
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @return A list of all of the patient's appointments.
     */
    public List<Appointment> getAllAppointments() {
        return appointmentHistory;
    }

    /**
     * @return A list of all of the patient's consultations.
     */
    public List<Consultation> getAllConsultations() {
        return consultationHistory;
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
        Optional<Staff> preferredDoctor = getPreferredDoctor();
        Optional<Appointment> appointment = getAppointment();
        Name name = getName();
        Phone phone = getPhone();
        Email email = getEmail();
        Address address = getAddress();
        Set<Tag> tags = getTags();

        return Objects.hash(name, phone, email, address, tags, preferredDoctor, appointment);
    }
}
