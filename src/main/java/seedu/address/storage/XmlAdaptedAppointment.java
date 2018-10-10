package seedu.address.storage;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.DateTime;
import seedu.address.model.appointment.Type;

/**
 * JAXB-friendly adapted version of the Appointment
 */

@XmlRootElement
public class XmlAdaptedAppointment {
    public static final String MESSAGE_DATE_TIME_BEFORE_NOW = "Duration must be a positive value!";

    @XmlElement(required = true)
    private Type type;

    @XmlElement(required = true)
    private String procedure_name;

    @XmlElement(required = true)
    private String date_time;

    @XmlElement(required = true)
    private String doc_name;

    /**
     * Constructs an XmlAdaptedAppointment
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs a {@code XmlAdaptedAppointment} with the given details.
     */
    public XmlAdaptedAppointment(Type type, String procedure_name, String date_time, String doc_name)
            throws IllegalValueException {
        this.type = type;
        this.procedure_name = procedure_name;
        try {
            Calendar currTime = Calendar.getInstance();
            Date currentTime = currTime.getTime();
            Date dt = DateTime.DATE_TIME_FORMAT.parse(date_time);
            if (dt.before(currentTime)) {
                throw new IllegalValueException(MESSAGE_DATE_TIME_BEFORE_NOW);
            }
        } catch (ParseException e) {
            throw new IllegalValueException(e.toString());
        }
        this.date_time = date_time;
        this.doc_name = doc_name;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     */
    public XmlAdaptedAppointment(Appointment source) {
        this.type = source.getType();
        this.procedure_name = source.getProcedure_name();
        this.date_time = source.getDate_time().toString();
        this.doc_name = source.getDoc_name();
    }

    /**
     * Converts this JAXB-friendly adapted Appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        Calendar dt = Calendar.getInstance();
        try {
            dt.setTime(DateTime.DATE_TIME_FORMAT.parse(this.date_time));
        } catch (ParseException e) {
            throw new IllegalValueException(e.toString());
        }
        return new Appointment(type, procedure_name, dt, doc_name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }
        return type.equals(((XmlAdaptedAppointment) other).type)
                && procedure_name.equals(((XmlAdaptedAppointment) other).procedure_name)
                && date_time.equals(((XmlAdaptedAppointment) other).date_time)
                && doc_name.equals(((XmlAdaptedAppointment) other).doc_name);
    }
}
