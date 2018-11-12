package seedu.superta.model;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.assignment.exceptions.GradeException;
import seedu.superta.model.attendance.Attendance;
import seedu.superta.model.attendance.Presence;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.attendance.exceptions.DuplicateAttendanceException;
import seedu.superta.model.attendance.exceptions.DuplicateSessionException;
import seedu.superta.model.attendance.exceptions.SessionNotFoundException;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.student.UniqueStudentList;
import seedu.superta.model.student.exceptions.StudentNotFoundException;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.model.tutorialgroup.TutorialGroupMaster;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Wraps all data at the SuperTA client level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class SuperTaClient implements ReadOnlySuperTaClient {

    private final UniqueStudentList students;
    private final TutorialGroupMaster tutorialGroupMaster;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        tutorialGroupMaster = new TutorialGroupMaster();
    }

    public SuperTaClient() {}

    /**
     * Creates an SuperTaClient using the Persons in the {@code toBeCopied}
     */
    public SuperTaClient(ReadOnlySuperTaClient toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    public void setTutorialGroups(Map<String, TutorialGroup> tutorialGroups) {
        this.tutorialGroupMaster.setTutorialGroups(tutorialGroups);
    }

    /**
     * Resets the existing data of this {@code SuperTaClient} with {@code newData}.
     */
    public void resetData(ReadOnlySuperTaClient newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
        setTutorialGroups(newData.getTutorialGroupMap());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same ID as {@code student} exists in the address book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.getStudentWithId(student.getStudentId()).isPresent();
    }

    /**
     * Returns true if a student has the same identity.
     */
    public boolean hasStudentWithIdentity(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Returns true if a tutorial group with the same id as {@code tutorialGroup} exists in the directory.
     */
    public boolean hasTutorialGroup(String id) {
        return tutorialGroupMaster.contains(id);
    }

    public Optional<TutorialGroup> getTutorialGroup(String id) {
        return tutorialGroupMaster.getTutorialGroup(id);
    }

    public Optional<Student> getStudentWithId(StudentId studentId) {
        return students.getStudentWithId(studentId);
    }

    public void addStudentToTutorialGroup(TutorialGroup tg, Student st) {
        tg.addStudent(st);
    }

    public void removeStudentFromTutorialGroup(TutorialGroup tg, Student st) {
        tg.removeStudent(st);
    }

    /**
     * Adds a tutorial group to the directory.
     */
    public void addTutorialGroup(TutorialGroup tg) {
        tutorialGroupMaster.addTutorialGroup(tg);
    }

    /**
     * Replaces the given tutorial group {@code target} in the list with {@code edited}.
     * {@code target} must exist in the client.
     */
    public void updateTutorialGroup(TutorialGroup edited) {
        requireNonNull(edited);

        tutorialGroupMaster.setTutorialGroup(edited);
    }

    /**
     * Removes a tutorial group from the directory.
     * The tutorial group must exist in the directory.
     */
    public void removeTutorialGroup(TutorialGroup key) {
        tutorialGroupMaster.removeTutorialGroup(key);
    }

    /**
     * Adds an assignment to a tutorial group.
     */
    public void addAssignment(TutorialGroup tg, Assignment assignment) {
        requireAllNonNull(tg, assignment);

        tg.addAssignment(assignment);
    }

    /**
     * Updates an assignment in a tutorial group.
     */
    public void updateAssignment(TutorialGroup tg, Assignment assignmentToChange, Assignment assignmentChanged) {
        requireAllNonNull(tg, assignmentToChange, assignmentChanged);

        tg.updateAssignment(assignmentToChange, assignmentChanged);
    }

    /**
     * Removes an assignment from a tutorial group.
     */
    public void deleteAssignment(TutorialGroup tg, Assignment assignment) {
        requireAllNonNull(tg, assignment);

        tg.removeAssignment(assignment);
    }

    /**
     * Performs an addition of a grade to an assignment gradebook, if possible.
     */
    public void grade(Grade grade) {
        Optional<TutorialGroup> otg = tutorialGroupMaster.getTutorialGroup(grade.getTgId());
        if (!otg.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        TutorialGroup tg = otg.get();

        Optional<Assignment> oas = tg.getAssignment(grade.getAsId());
        if (!oas.isPresent()) {
            throw new AssignmentNotFoundException();
        }
        Assignment as = oas.get();

        Optional<Student> ost = tg.getStudents().getStudentWithId(grade.getStId());
        if (!ost.isPresent()) {
            throw new StudentNotFoundException();
        }
        Student st = ost.get();

        if (grade.getMarks() > as.getMaxMarks()) {
            throw new GradeException();
        }
        as.grade(st.getStudentId(), grade.getMarks());
    }

    /**
     * Creates an attendance session to a tutorial group.
     */
    public void createAttendance(TutorialGroup tg, Session session) throws DuplicateSessionException {
        requireAllNonNull(tg, session);

        tg.createAttendanceSession(session);
    }

    /**
     * Removes an attendance session to a tutorial group.
     * Not implemented.
     */
    public void removeAttendance(TutorialGroup tg, Session session) {
        requireAllNonNull(tg, session);

        tg.removeAttendanceSession(session);
    }

    /**
     * Marks attendance of students in a session.
     */
    public void markAttendance(String tutorialGroupId, Session session, Set<StudentId> stIdSet) {
        requireAllNonNull(tutorialGroupId, session, stIdSet);
        Optional<TutorialGroup> otg = getTutorialGroup(tutorialGroupId);
        if (!otg.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        TutorialGroup tg = otg.get();

        Optional<Session> opSession = tg.getSession(session);
        if (!opSession.isPresent()) {
            throw new SessionNotFoundException();
        }
        Session sess = opSession.get();

        UniqueStudentList students = tg.getStudents();
        boolean studentsMatch = stIdSet.stream().allMatch(studentId -> students.containsId(studentId));
        if (!studentsMatch) {
            throw new StudentNotFoundException();
        }

        Presence present = Presence.PRESENT;
        List<Attendance> attendanceList = stIdSet.stream().map(stdId -> new Attendance(stdId, present))
                .collect(Collectors.toList());
        boolean hasDuplicateAttendance = attendanceList.stream().anyMatch(attendance -> sess.contains(attendance));
        if (hasDuplicateAttendance) {
            throw new DuplicateAttendanceException();
        }
        attendanceList.stream().forEach(sess::addToSession);
    }

    /**
     * Adds feedback to a student.
     */
    public void addFeedback(Feedback feedback, StudentId studentId) {
        Optional<Student> ost = students.getStudentWithId(studentId);
        if (!ost.isPresent()) {
            throw new StudentNotFoundException();
        }
        Student student = ost.get();

        Student updatedStudent = new Student(student);
        updatedStudent.addFeedback(feedback);
        students.setStudent(student, updatedStudent);
    }

    /**
     * View feedback for a student.
     */
    public List<Feedback> viewFeedback(StudentId studentId) {
        Optional<Student> ost = students.getStudentWithId(studentId);
        if (!ost.isPresent()) {
            throw new StudentNotFoundException();
        }
        Student st = ost.get();
        List<Feedback> studentFeedback = st.getFeedback()
                .stream()
                .collect(Collectors.toList());
        return studentFeedback;
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the address
     * book.
     */
    public void updateStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
        tutorialGroupMaster.updateStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code SuperTaClient}.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        students.remove(key);
        tutorialGroupMaster.removeStudentReferences(key);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " students";
        // TODO: refine later
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public ObservableMap<String, TutorialGroup> getTutorialGroupMap() {
        return tutorialGroupMaster.asUnmodifiableObservableMap();
    }

    @Override
    public ObservableList<TutorialGroup> getTutorialGroupList() {
        return tutorialGroupMaster.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuperTaClient // instanceof handles nulls
                && students.equals(((SuperTaClient) other).students)
                && tutorialGroupMaster.equals(((SuperTaClient) other).tutorialGroupMaster));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
