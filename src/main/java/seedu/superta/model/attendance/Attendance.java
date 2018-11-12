package seedu.superta.model.attendance;

import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;

import seedu.superta.model.student.StudentId;

// @@author triger15
/**
 * Represents an attendance for a particular student.
 *
 */
public class Attendance {
    private final StudentId stId;
    private Presence presence;

    /**
     * Constructs an {@code Attendance}.
     *
     * @param stId A valid student id.
     * @param presence A valid presence.
     */
    public Attendance(StudentId stId, Presence presence) {
        requireAllNonNull(stId, presence);
        this.stId = stId;
        this.presence = presence;
    }

    /**
     * A constructor for cloning.
     */
    public Attendance(Attendance toClone) {
        this.stId = toClone.stId;
        this.presence = toClone.presence;
    }

    public StudentId getStudentId() {
        return stId;
    }

    public Presence getPresence() {
        return presence;
    }

    /**
     * Returns true if both attendance belong to the same student id.
     * This defines a weaker notion of equality between two attendance.
     */
    public boolean isSameAttendance(Attendance otherAttendance) {
        if (otherAttendance == this) {
            return true;
        }

        return otherAttendance != null
                && otherAttendance.getStudentId().equals(getStudentId());
    }

    /**
     * Returns true if both attendance have the same identity and presence.
     * This defines a stronger notion of equality between two attendance.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return otherAttendance.getStudentId().equals(getStudentId())
                && otherAttendance.getPresence().equals(getPresence());
    }

    @Override
    public String toString() {
        return stId + " " + presence;
    }
}
