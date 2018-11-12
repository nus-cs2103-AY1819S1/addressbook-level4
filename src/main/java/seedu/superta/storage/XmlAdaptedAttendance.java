package seedu.superta.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.attendance.Attendance;
import seedu.superta.model.attendance.Presence;
import seedu.superta.model.student.StudentId;

// @@author triger15
/**
 * An XML Representation of an Attendance in an attendance session.
 */
public class XmlAdaptedAttendance implements XmlAdapted<Attendance> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Attendance's %s field is missing!";

    @XmlElement(required = true)
    private String studentId;
    @XmlElement(required = true)
    private Presence status;

    /**
     * Constructs an XmlAdaptedAttendance.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAttendance() { }

    /**
     * Constructs a {@code XmlAdapatedAttendance} with the given {@code studentId} and {@code status}.
     */
    public XmlAdaptedAttendance(String studentId, Presence status) {
        this.studentId = studentId;
        this.status = status;
    }

    /**
     * Converts a given Attendance into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created attendance
     */
    public XmlAdaptedAttendance(Attendance source) {
        studentId = source.getStudentId().studentId;
        status = source.getPresence();
    }

    /**
     * Converts this jaxb-friendly adapted attendance object into the model's Attendance object.
     */
    @Override
    public Attendance toModelType() throws IllegalValueException {
        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "student ID"));
        }
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "presence"));
        }
        return new Attendance(new StudentId(studentId), status);
    }
}
