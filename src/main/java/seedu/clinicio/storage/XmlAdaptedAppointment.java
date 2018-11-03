package seedu.clinicio.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.patient.Patient;


/**
 * JAXB-friendly version of the Appointment.
 */
public class XmlAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String patient;
    @XmlElement(required = true)
    private int status;

    @XmlElement
    private int type;
    @XmlElement
    private String staff;

    /**
     * Constructs an XmlAdaptedAppointment. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {
    }

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String date, String time, String ic, int status, int type, String staff) {
        this.date = date;
        this.time = time;
        this.patient = ic;
        this.status = status;
        this.type = type;
        if (staff != null) {
            this.staff = staff;
        }
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedAppointment(Appointment source) {
        int day = source.getAppointmentDate().getDay();
        int month = source.getAppointmentDate().getMonth();
        int year = source.getAppointmentDate().getYear();

        date = String.valueOf(day) + " " + String.valueOf(month) + " " + String.valueOf(year);

        int hour = source.getAppointmentTime().getHour();
        int min = source.getAppointmentTime().getMinute();

        time = String.valueOf(hour) + " " + String.valueOf(min);
        patient = source.getPatient().toString();
        status = source.getAppointmentStatus();
        type = source.getAppointmentType();
        staff = source.getAssignedStaff().toString();
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object. TODO.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(String.format(Date.MESSAGE_DATE_CONSTRAINTS));
        }
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (patient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Patient.class.getSimpleName()));
        }
        return new Appointment(Date.newDate(date), Time.newTime(time), null, type);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }

        XmlAdaptedAppointment otherAppt = (XmlAdaptedAppointment) other;
        return Objects.equals(date, otherAppt.date)
                && Objects.equals(time, otherAppt.time)
                && Objects.equals(patient, otherAppt.patient)
                && Objects.equals(status, otherAppt.status)
                && Objects.equals(type, otherAppt.type)
                && Objects.equals(staff, otherAppt.staff);
    }
}
