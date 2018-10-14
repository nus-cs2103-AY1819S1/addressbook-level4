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
    private String procedureName;

    @XmlElement(required = true)
    private String dateTime;

    @XmlElement(required = true)
    private String docName;

    /**
     * Constructs an XmlAdaptedAppointment
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs a {@code XmlAdaptedAppointment} with the given details.
     */
    public XmlAdaptedAppointment(Type type, String procedureName, String dateTime, String docName)
            throws IllegalValueException {
        this.type = type;
        this.procedureName = procedureName;
        try {
            Calendar currTime = Calendar.getInstance();
            Date currentTime = currTime.getTime();
            Date dt = DateTime.DATE_TIME_FORMAT.parse(dateTime);
            if (dt.before(currentTime)) {
                throw new IllegalValueException(MESSAGE_DATE_TIME_BEFORE_NOW);
            }
        } catch (ParseException e) {
            throw new IllegalValueException(e.toString());
        }
        this.dateTime = dateTime;
        this.docName = docName;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     */
    public XmlAdaptedAppointment(Appointment source) {
        this.type = source.getType();
        this.procedureName = source.getProcedure_name();
        this.dateTime = DateTime.DATE_TIME_FORMAT.format(source.getDate_time().getTime());
        this.docName = source.getDoc_name();
    }

    /**
     * Converts this JAXB-friendly adapted Appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        Calendar dt = Calendar.getInstance();
        try {
            Date d = DateTime.DATE_TIME_FORMAT.parse(this.dateTime);
            dt.setTime(d);
        } catch (ParseException e) {
            throw new IllegalValueException(e.toString());
        }
        return new Appointment(type, procedureName, dt, docName);
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
                && procedureName.equals(((XmlAdaptedAppointment) other).procedureName)
                && dateTime.equals(((XmlAdaptedAppointment) other).dateTime)
                && docName.equals(((XmlAdaptedAppointment) other).docName);
    }
}
