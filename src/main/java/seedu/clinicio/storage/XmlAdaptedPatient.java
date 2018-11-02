package seedu.clinicio.storage;

import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;


/**
 * This class constructs a JAXB-friendly version of the Patient.
 */
public class XmlAdaptedPatient extends XmlAdaptedPerson {
    @XmlElement
    private boolean isQueuing = false;
    @XmlElement
    private Optional<Staff> preferredDoctor = Optional.empty();
    @XmlElement
    private Optional<Appointment> appointment = Optional.empty();

    public XmlAdaptedPatient(String name, String phone, String email, String address, List<XmlAdaptedTag> tagged,
                             boolean isQueuing, Optional<Staff> preferredDoctor, Optional<Appointment> appointment) {
        super(name, phone, email, address, tagged);
        this.isQueuing = isQueuing;
        this.preferredDoctor = preferredDoctor;
        this.appointment = appointment;
    }

    public XmlAdaptedPatient(Patient patient) {
        super(patient);
        isQueuing = patient.isQueuing();
        preferredDoctor = patient.getPreferredDoctor();
        appointment = patient.getAppointment();
    }

    /**
     * Converts this jaxb-friendly adapted patient object into the model's Patient object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    @Override
    public Patient toModelType() throws IllegalValueException {
        Person person = super.toModelType();
        Patient patient = Patient.buildFromPerson(person);

        if (isQueuing) {
            patient.isQueuing();
        } else {
            patient.setIsNotQueuing();
        }

        appointment.ifPresent(appointment1 -> {
            patient.setAppointment(appointment1);
        });

        preferredDoctor.ifPresent(preferredDoctor -> {
            patient.setPreferredDoctor(preferredDoctor);
        });

        return patient;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Patient)) {
            return false;
        }
        Patient otherPatient = (Patient) other;
        return super.equals(other)
                && appointment.equals(otherPatient.getAppointment())
                && preferredDoctor.equals(otherPatient.getPreferredDoctor());
    }
}
