package seedu.address.testutil;

import java.util.Optional;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.clinicio.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class to build {@link Patient} objects.
 */
public class PatientBuilder extends PersonBuilder {

    private Optional<Doctor> preferredDoctor;
    private Optional<Appointment> appointment;

    public PatientBuilder() {
        super();
        preferredDoctor = Optional.empty();
        appointment = Optional.empty();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        super(patientToCopy);
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
    @Override
    public Patient build() {
        Patient patient = Patient.buildFromPerson(super.build());

        preferredDoctor.ifPresent(doctor -> {
            patient.setPreferredDoctor(doctor);
        });

        appointment.ifPresent(appointment -> {
            patient.setAppointment(appointment);
        });

        return patient;
    }
}
