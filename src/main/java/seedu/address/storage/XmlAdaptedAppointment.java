package seedu.address.storage;

import seedu.address.model.appointment.Appointment;

import javax.xml.bind.annotation.XmlElement;

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
    private String id;
    @XmlElement(required = true)
    private int status;

    /**
     * Constructs an XmlAdaptedAppointment. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {
    }

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given doctor details.
     */
    public XmlAdaptedAppointment(String date, String time, String id, int status) {
        this.date = date;
        this.time = time;
        this.id = id;
        this.status = status;
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedPerson(Appointment source) {
        /*date = String.valueOf(source.getAppointmentDate());
        time = String.valueOf(source.getAppointmentTime());
        id = source.getAppointmentDate()
        status = source.getAppointmentStatus();*/
    }
}
