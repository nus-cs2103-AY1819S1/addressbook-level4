package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to build {@link Patient} objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Optional<Doctor> preferredDoctor;
    private Optional<Appointment> appointment;

    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        preferredDoctor = Optional.empty();
        appointment = Optional.empty();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        address = patientToCopy.getAddress();
        tags = new HashSet<>(patientToCopy.getTags());
        preferredDoctor = patientToCopy.getPreferredDoctor();
        appointment = patientToCopy.getAppointment();
    }

    /**
     * Constructs a {@link PatientBuilder} object based on a person.
     * @param person
     * @return a new {@link PatientBuilder} object
     */
    public static PatientBuilder buildFromPerson(Person person) {
        Patient patient = Patient.buildFromPerson(person);
        return new PatientBuilder(patient);
    }

    /**
     * Sets the doctor as preferred by the patient.
     * @return a PersonBuilder
     */
    public PatientBuilder withPreferredDoctor(Doctor doctor) {
        preferredDoctor = Optional.of(doctor);
        return this;
    }

    /**
     * Sets the appointment for the patient.
     * @return a PersonBuilder
     */
    public PatientBuilder withAppointment(Appointment appointment) {
        this.appointment = Optional.of(appointment);
        return this;
    }

    /**
     * Contructs a Patient based on the PatientBuilder.
     * @return a patient.
     */
    public Patient build() {
        Patient patient = new Patient(name, phone, email, address, tags);

        preferredDoctor.ifPresent(doctor -> {
            patient.setPreferredDoctor(doctor);
        });

        appointment.ifPresent(appointment -> {
            patient.setAppointment(appointment);
        });

        return patient;
    }
}
