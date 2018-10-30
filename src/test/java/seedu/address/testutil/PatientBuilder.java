package seedu.address.testutil;

import java.util.Optional;

<<<<<<< HEAD:src/test/java/seedu/clinicio/testutil/PatientBuilder.java
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;
||||||| merged common ancestors
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
=======
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
>>>>>>> fd0466fa9c4e16f0bbc839aa76ae8488c7686bff:src/test/java/seedu/address/testutil/PatientBuilder.java

/**
 * A utility class to build {@link Patient} objects.
 */
public class PatientBuilder extends PersonBuilder {

    private Optional<Staff> preferredDoctor;
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
     * Sets the staff as preferred by the patient.
     * @return a PersonBuilder
     */
    public PatientBuilder withPreferredDoctor(Staff staff) {
        preferredDoctor = Optional.of(staff);
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
