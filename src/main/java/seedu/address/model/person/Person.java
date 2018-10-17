package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private Optional<Doctor> preferredDoctor = Optional.empty();
    private Optional<Appointment> appointment = Optional.empty();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns the patient's preferred doctor wrapped in {@link Optional}. The patient may not have one.
     * @return an Optional {@link Doctor}.
     */
    public Optional<Doctor> getPrefferedDoctor() {
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
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
    public boolean hasPrefferedDoctor() {
        return preferredDoctor.isPresent();
    }

    /**
     * Checks if patient has an appointment.
     */
    public boolean hasAppointment() {
        return appointment.isPresent();
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()))
                && otherPerson.getPrefferedDoctor().equals(getPrefferedDoctor())
                && otherPerson.getAppointment().equals(getAppointment());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getPrefferedDoctor().equals(getPrefferedDoctor())
                && otherPerson.getAppointment().equals(getAppointment());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, getPrefferedDoctor(), getAppointment());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
