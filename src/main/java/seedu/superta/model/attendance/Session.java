package seedu.superta.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import seedu.superta.model.attendance.exceptions.DuplicateAttendanceException;
import seedu.superta.model.student.Student;

/**
 * Represents a Session class in the client.
 *
 */
public class Session {
    private final String name;
    private final ObservableSet<Attendance> internalSet = FXCollections.observableSet();

    /**
     * Constructs an {@code Session}.
     *
     * @param name A valid session class name.
     */
    public Session(String name) {
        requireNonNull(name);
        this.name = name;
    }

    public Session(Session toClone) {
        requireNonNull(toClone);
        this.name = toClone.name;
        toClone.internalSet.stream().map(Attendance::new).forEach(attendance -> {
            internalSet.add(attendance);
        });
    }

    /**
     * Constructs an {@code Session}.
     *
     * @param name A valid session class name.
     * @param attendanceList A valid attendance list.
     */
    public Session(String name, Set<Attendance> attendanceList) {
        requireAllNonNull(name, attendanceList);
        this.name = name;
        for (Attendance attendance: attendanceList) {
            internalSet.add(new Attendance(attendance));
        }
    }

    /**
     * Returns true if the list contains an equivalent attendance as the given argument.
     */
    public boolean contains(Attendance toCheck) {
        requireNonNull(toCheck);
        return internalSet.stream().anyMatch(toCheck::isSameAttendance);
    }

    public String getSessionName() {
        return name;
    }

    /**
     * Returns the backing set as an unmodifiable {@code ObservableSet}.
     */
    public ObservableSet<Attendance> asUnmodifiableObservableSet() {
        return FXCollections.unmodifiableObservableSet(internalSet);
    }

    /**
     * Returns an unmodifiable observable list representation of the attendance set.
     */
    public ObservableList<Attendance> asUnmodifiableObservableList() {
        ObservableList<Attendance> list = FXCollections.observableList(internalSet.stream().collect(
                Collectors.toList()
        ));
        internalSet.addListener((SetChangeListener<? super Attendance>) change -> {
            if (change.wasAdded()) {
                list.add(change.getElementAdded());
            }
            if (change.wasRemoved()) {
                list.remove(change.getElementRemoved());
            }
        });
        return list;
    }

    /**
     * Adds the given attendance to the current attendance session.
     * @param attendance A valid attendance.
     */
    public boolean addToSession(Attendance attendance) {
        requireNonNull(attendance);
        if (contains(attendance)) {
            throw new DuplicateAttendanceException();
        }
        return internalSet.add(attendance);
    }

    /**
     * Returns true if both sessions of the same title have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two sessions.
     */
    public boolean isSameSession(Session otherSession) {
        if (otherSession == this) {
            return true;
        }

        return otherSession != null
                && otherSession.getSessionName().equals(getSessionName());
    }

    /**
     * Remove student references.
     */
    public boolean removeStudent(Student target) {
        Optional<Attendance> optional = internalSet.stream()
                .filter(attendance -> attendance.getStudentId().equals(target.getStudentId())).findAny();
        if (optional.isPresent()) {
            return internalSet.remove(optional.get());
        } else {
            return false;
        }
    }

    /**
     * Returns true if both sessions have the same identity and attendance list.
     * This defines a stronger notion of equality between two sessions.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Session)) {
            return false;
        }

        Session otherSession = (Session) other;
        boolean isSetEqual = otherSession.internalSet.size() == internalSet.size();
        for (Attendance attendance: internalSet) {
            isSetEqual = isSetEqual && otherSession.contains(attendance);
        }
        return otherSession.getSessionName().equals(getSessionName())
                && isSetEqual;
    }

    @Override
    public String toString() {
        return "[Session] " + name + ": "
            + internalSet.stream().map(att -> att.toString()).collect(Collectors.joining(", "));
    }
}
