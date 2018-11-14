package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.attendance.Attendance;

/**
 * JAXB-friendly version of the Attendance.
 */
public class XmlAdaptedAttendance {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Attendance's %s field is missing!";

    @XmlElement(required = true)
    private String session;
    @XmlElement(required = true)
    private String uniqueId;

    /**
     * Constructs an XmlAdaptedAttendance.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAttendance() {}

    /**
     * Constructs an {@code XmlAdaptedAttendance} with the given attendance details.
     */
    public XmlAdaptedAttendance(String session, String uniqueId) {
        this.session = session;
        this.uniqueId = uniqueId;
    }

    /**
     * Converts a given Attendance into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAttendance
     */
    public XmlAdaptedAttendance(Attendance source) {
        session = source.getSession().internalString;
        uniqueId = source.getUniqueId();
    }

    /**
     * Converts this jaxb-friendly adapted attendance object into the model's Attendance object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance
     */
    public Attendance toModelType() throws IllegalValueException {
        if (session == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName())
            );
        }
        if (!Deadline.isValid(session)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        final Deadline modelSession = new Deadline(session);

        return new Attendance(modelSession, uniqueId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAttendance)) {
            return false;
        }

        XmlAdaptedAttendance otherAttendance = (XmlAdaptedAttendance) other;
        return Objects.equals(session, otherAttendance.session)
                && uniqueId.equals(otherAttendance.uniqueId);
    }
}
