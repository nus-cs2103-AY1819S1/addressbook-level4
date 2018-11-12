package seedu.superta.model.tutorialgroup;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.assignment.UniqueAssignmentList;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.attendance.UniqueSessionList;
import seedu.superta.model.attendance.exceptions.DuplicateSessionException;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.UniqueStudentList;

/**
 * Model for Tutorial Group.
 */
public class TutorialGroup {
    private final String id;
    private final String name;
    private final UniqueStudentList students;
    private final UniqueAssignmentList assignments;
    private final UniqueSessionList attendanceSessions;

    public TutorialGroup(String id, String name) {
        requireNonNull(id);
        this.id = id;
        this.name = name;
        this.students = new UniqueStudentList();
        this.assignments = new UniqueAssignmentList();
        this.attendanceSessions = new UniqueSessionList();
    }

    public TutorialGroup(TutorialGroup toClone) {
        this.id = toClone.id;
        this.name = toClone.name;
        this.students = toClone.students.clone();
        this.assignments = toClone.assignments.clone();
        this.attendanceSessions = toClone.attendanceSessions.clone();
    }

    public TutorialGroup(String id, String name,
                         UniqueStudentList students,
                         UniqueAssignmentList assignments,
                         UniqueSessionList sessions) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.assignments = assignments;
        this.attendanceSessions = sessions;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Removes a student from the tutorial group.
     */
    public void removeStudent(Student student) {
        students.removeById(student);
        assignments.asUnmodifiableObservableList().stream()
                .forEach(assignment -> assignment.removeStudentReferences(student));
        attendanceSessions.asUnmodifiableObservableList().stream()
                .forEach(session -> session.removeStudent(student));
    }

    /**
     * Updates a student with the updated value.
     */
    public void updateStudent(Student target, Student edited) {
        students.removeById(target);
        students.add(edited);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    /**
     * @param assignmentToChange represents assignment to be changed.
     * @param assignmentChanged represents assignment to be changed to.
     */
    public void updateAssignment(Assignment assignmentToChange, Assignment assignmentChanged) {
        assignments.setAssignment(assignmentToChange, assignmentChanged);
    }

    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
    }

    public void createAttendanceSession(Session session) throws DuplicateSessionException {
        attendanceSessions.add(session);
    }

    public void removeAttendanceSession(Session session) {
        attendanceSessions.remove(session);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public UniqueStudentList getStudents() {
        return students;
    }

    public UniqueAssignmentList getAssignments() {
        return assignments;
    }

    public Optional<Assignment> getAssignment(Title title) {
        return assignments.asUnmodifiableObservableList()
                .stream()
                .filter(as -> as.getTitle().equals(title))
                .findFirst();
    }

    public UniqueSessionList getSessions() {
        return attendanceSessions;
    }

    public Optional<Session> getSession(Session session) {
        return attendanceSessions.asUnmodifiableObservableList()
                .stream()
                .filter(ses -> ses.isSameSession(session))
                .findFirst();
    }

    /**
     * Gets the attendance session by name reference, if it exists.
     */
    public Optional<Session> getSessionByName(String name) {
        return attendanceSessions.asUnmodifiableObservableList()
                .stream()
                .filter(session -> session.getSessionName().equals(name))
                .findFirst();
    }

    public List<Assignment> getAssignmentList(Title title) {
        return assignments.asUnmodifiableObservableList()
                .stream()
                .filter(as -> as.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TutorialGroup)) {
            return false;
        }
        TutorialGroup otherTutorialGroup = (TutorialGroup) other;
        return id.equals(otherTutorialGroup.id)
            && name.equals(otherTutorialGroup.name)
            && students.equals(otherTutorialGroup.students)
            && assignments.equals(otherTutorialGroup.assignments)
            && attendanceSessions.equals(otherTutorialGroup.attendanceSessions);
    }

    @Override
    public String toString() {
        return "[Tutorial Group] ID: " + id + ", Name: " + name + "\n"
            + "Students: "
            + students.asUnmodifiableObservableList()
                .stream()
                .map(st -> st.getStudentId().toString())
                .collect(Collectors.joining(", "))
            + "\n"
            + "Assignments: \n"
            + assignments
            + "\n"
            + "Attendance Sessions: \n"
            + attendanceSessions;
    }
}
