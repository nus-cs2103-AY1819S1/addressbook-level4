package seedu.superta.storage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.attendance.Attendance;
import seedu.superta.model.attendance.Session;

// @@author triger15
/**
 * Class for XML-Adapted Session
 */
public class XmlAdaptedSession implements XmlAdapted<Session> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Session's %s field is missing!";

    @XmlElement(required = true)
    private String sessionName;

    @XmlElement
    private Set<XmlAdaptedAttendance> attendance = new HashSet<>();

    /**
     * Constructs an XmlAdaptedSession.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSession() { }

    /**
     * Constructs a {@code XmlAdaptedSession} with the given {@code sessionName}.
     */
    public XmlAdaptedSession(String sessionName) {
        this.sessionName = sessionName;
    }

    /**
     * Converts a given Session into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created session
     */
    public XmlAdaptedSession(Session source) {
        sessionName = source.getSessionName();
        attendance = source.asUnmodifiableObservableSet().stream()
                .map(XmlAdaptedAttendance::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Session toModelType() throws IllegalValueException {
        final Set<Attendance> attendances = new HashSet<>();
        if (sessionName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }

        for (XmlAdaptedAttendance xmlAtt : attendance) {
            attendances.add(xmlAtt.toModelType());
        }
        final Set<Attendance> modelAttendances = new HashSet<>(attendances);

        return new Session(sessionName, modelAttendances);
    }
}
