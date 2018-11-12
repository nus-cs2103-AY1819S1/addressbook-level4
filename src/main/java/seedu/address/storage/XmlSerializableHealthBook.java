package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.HealthBook;
import seedu.address.model.ReadOnlyHealthBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * An Immutable HealthBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableHealthBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedAppointment> appointments;
    @XmlElement
    private int appointmentCounter;

    /**
     * Creates an empty XmlSerializableHealthBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableHealthBook() {
        persons = new ArrayList<>();
        appointments = new ArrayList<>();
        appointmentCounter = 10000;
    }

    /**
     * Conversion
     */
    public XmlSerializableHealthBook(ReadOnlyHealthBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        appointments.addAll(src.getAppointmentList().stream().map(XmlAdaptedAppointment::new)
                .collect(Collectors.toList()));
        appointmentCounter = src.getAppointmentCounter();
    }

    /**
     * Converts this addressbook into the model's {@code HealthBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public HealthBook toModelType() throws IllegalValueException {
        HealthBook healthBook = new HealthBook();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (healthBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            if (!person.getTags().isEmpty() && person.getTags().toArray()[0].equals(new Tag("Doctor"))) {
                healthBook.addDoctor((Doctor) person);
            } else if (!person.getTags().isEmpty() && person.getTags().toArray()[0].equals(new Tag("Patient"))) {
                healthBook.addPatient((Patient) person);
            } else {
                healthBook.addPerson(person);
            }
        }
        for (XmlAdaptedAppointment a : appointments) {
            Appointment appointment = a.toModelType();
            healthBook.addAppointment(appointment);
        }
        healthBook.setAppointmentCounter(appointmentCounter);
        return healthBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableHealthBook)) {
            return false;
        }
        return persons.equals(((XmlSerializableHealthBook) other).persons);
    }
}
