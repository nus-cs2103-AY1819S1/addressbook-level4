package seedu.superta.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlySuperTaClient newData);

    /** Returns the SuperTaClient */
    ReadOnlySuperTaClient getSuperTaClient();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the SuperTA client.
     */
    boolean hasStudent(Student student);

    /**
     * Returns true if there is a student with a similar identity.
     */
    boolean hasStudentWithIdentity(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the SuperTA client.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the SuperTA client.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the SuperTA client.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the
     * SuperTA client.
     */
    void updateStudent(Student target, Student editedStudent);

    /**
     * Adds the given tutorial group.
     * {@code tutorialGroup} must not already exist.
     */
    void addTutorialGroup(TutorialGroup tutorialGroup);

    /**
     * Adds the student to the tutorial group.
     */
    void addStudentToTutorialGroup(String tgId, StudentId studentId);

    /**
     * Removes the student from the tutorial group.
     */
    void removeStudentFromTutorialGroup(String tgId, StudentId studentId);

    /**
     * Deletes the given tutorial group.
     * The tutorial group must exist in the client.
     */
    void deleteTutorialGroup(TutorialGroup tutorialGroup);

    /**
     * Adds the given assignment to the tutorial group.
     */
    void addAssignment(String tgId, Assignment assignment);

    /**
     * Deletes the assignment in the tutorial group.
     */
    void deleteAssignment(String tgId, String assignment);

    /**
     * Replaces the given assignment {@code assignmentToChange} with {@code assignmentChanged}.
     * {@code assignmentToChange} must exist in the SuperTA client.
     * The assignment identity of {@code assignmentChanged} must not be the same as another existing assignment in the
     * tutorial group.
     */
    void updateAssignment(String tgId, Title assignmentToChange, Assignment assignmentChanged);

    /**
     * Grades a student on an assignment.
     */
    void grade(Grade grade);

    /**
     * Creates the given attendance session to the tutorial group.
     */
    void createAttendance(String tgId, Session session);

    /**
     * Marks the given students for an attendance session.
     */
    void markAttendance(String tgId, Session session, Set<StudentId> stIdSet);

    /**
     * Adds feedback to a student.
     */
    void addFeedback(Feedback feedback, StudentId studentId);

    /**
     * View feedback for a student.
     */
    List<Feedback> viewFeedback(StudentId studentId);

    /**
     * Returns true if a tutorial group with the given id exists.
     * @param id ID of the tutorial group.
     */
    boolean hasTutorialGroup(String id);

    /**
     * Returns the tutorial group with the given id, if present.
     */
    Optional<TutorialGroup> getTutorialGroup(String id);

    /**
     * Replaces the given tutorial group in the master with this ID with {@code edited}.
     * {@code target} must exist in the client.
     * The ID of {@code edited} must exist in the client.
     * @param edited The tutorial group with edited details.
     */
    void updateTutorialGroup(TutorialGroup edited);


    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the tutorial groups */
    ObservableList<TutorialGroup> getTutorialGroupList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Returns true if the model has previous SuperTA client states to restore.
     */
    boolean canUndoSuperTaClient();

    /**
     * Returns true if the model has undone SuperTA client states to restore.
     */
    boolean canRedoSuperTaClient();

    /**
     * Restores the model's SuperTA client to its previous state.
     */
    void undoSuperTaClient();

    /**
     * Restores the model's SuperTA client to its previously undone state.
     */
    void redoSuperTaClient();

    /**
     * Saves the current SuperTA client state for undo/redo.
     */
    void commitSuperTaClient();
}
