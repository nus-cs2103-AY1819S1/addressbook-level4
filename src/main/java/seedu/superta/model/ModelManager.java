package seedu.superta.model;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.superta.commons.core.ComponentManager;
import seedu.superta.commons.core.LogsCenter;
import seedu.superta.commons.events.model.SuperTaClientChangedEvent;
import seedu.superta.commons.events.ui.StateEvent;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.superta.model.assignment.exceptions.DuplicateAssignmentNameException;

import seedu.superta.model.attendance.Session;

import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.student.exceptions.StudentNotFoundException;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Represents the in-memory model of the SuperTA client data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedSuperTaClient versionedSuperTaClient;
    private final FilteredList<Student> filteredStudents;
    private final FilteredList<TutorialGroup> tutorialGroups;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlySuperTaClient addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedSuperTaClient = new VersionedSuperTaClient(addressBook);
        filteredStudents = new FilteredList<>(versionedSuperTaClient.getStudentList());

        tutorialGroups = new FilteredList<>(versionedSuperTaClient.getTutorialGroupList());
    }

    public ModelManager() {
        this(new SuperTaClient(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlySuperTaClient newData) {
        versionedSuperTaClient.resetData(newData);
        indicateSuperTaClientChanged();
    }

    @Override
    public ReadOnlySuperTaClient getSuperTaClient() {
        return versionedSuperTaClient;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateSuperTaClientChanged() {
        raise(new SuperTaClientChangedEvent(versionedSuperTaClient));
        raise(new StateEvent(versionedSuperTaClient));
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return versionedSuperTaClient.hasStudent(student);
    }

    @Override
    public boolean hasStudentWithIdentity(Student student) {
        requireNonNull(student);
        return versionedSuperTaClient.hasStudentWithIdentity(student);
    }

    @Override
    public void deleteStudent(Student target) {
        versionedSuperTaClient.removeStudent(target);
        indicateSuperTaClientChanged();
    }

    @Override
    public void addStudent(Student student) {
        versionedSuperTaClient.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);
        indicateSuperTaClientChanged();
    }

    @Override
    public void updateStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        versionedSuperTaClient.updateStudent(target, editedStudent);
        indicateSuperTaClientChanged();
    }

    @Override
    public void addTutorialGroup(TutorialGroup tutorialGroup) {
        versionedSuperTaClient.addTutorialGroup(tutorialGroup);
        indicateSuperTaClientChanged();
    }

    @Override
    public void addStudentToTutorialGroup(String tgId, StudentId studentId) {
        Optional<TutorialGroup> tg = versionedSuperTaClient.getTutorialGroup(tgId);
        Optional<Student> st = versionedSuperTaClient.getStudentWithId(studentId);
        if (!tg.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        if (!st.isPresent()) {
            throw new StudentNotFoundException();
        }
        TutorialGroup tutorialGroup = tg.get();
        Student student = st.get();
        versionedSuperTaClient.addStudentToTutorialGroup(tutorialGroup, student);
        indicateSuperTaClientChanged();
    }

    @Override
    public void removeStudentFromTutorialGroup(String tutorialGroupId, StudentId studentId) {
        Optional<TutorialGroup> tutorialGroupOptional = versionedSuperTaClient.getTutorialGroup(tutorialGroupId);
        Optional<Student> studentOptional = versionedSuperTaClient.getStudentWithId(studentId);
        if (!tutorialGroupOptional.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        if (!studentOptional.isPresent()) {
            throw new StudentNotFoundException();
        }
        TutorialGroup tutorialGroup = tutorialGroupOptional.get();
        Student student = studentOptional.get();
        if (!tutorialGroup.getStudents().contains(student)) {
            throw new StudentNotFoundException();
        }
        versionedSuperTaClient.removeStudentFromTutorialGroup(tutorialGroup, student);
        indicateSuperTaClientChanged();
    }

    @Override
    public void deleteTutorialGroup(TutorialGroup tutorialGroup) {
        if (!versionedSuperTaClient.hasTutorialGroup(tutorialGroup.getId())) {
            throw new TutorialGroupNotFoundException();
        }
        versionedSuperTaClient.removeTutorialGroup(tutorialGroup);
        indicateSuperTaClientChanged();
    }

    @Override
    public void addAssignment(String tgId, Assignment assignment) {
        Optional<TutorialGroup> tg = versionedSuperTaClient.getTutorialGroup(tgId);
        if (!tg.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        TutorialGroup t = tg.get();

        Optional<Assignment> assignmentOptional = t.getAssignment(assignment.getTitle());
        if (assignmentOptional.isPresent()) {
            throw new DuplicateAssignmentException();
        }
        versionedSuperTaClient.addAssignment(t, assignment);
        indicateSuperTaClientChanged();
    }

    @Override
    public void updateAssignment(String tgId, Title assignmentToChange, Assignment assignmentChanged) {
        Optional<TutorialGroup> tg = versionedSuperTaClient.getTutorialGroup(tgId);
        if (!tg.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        TutorialGroup t = tg.get();

        List<Assignment> assignmentList = t.getAssignmentList(assignmentToChange);
        if (assignmentList.isEmpty()) {
            throw new AssignmentNotFoundException();
        }

        if (assignmentList.size() > 1) {
            throw new DuplicateAssignmentNameException();
        }

        Assignment a = assignmentList.get(0);

        Double maxMarks;
        if (assignmentChanged.getMaxMarks().equals(-1.0)) {
            maxMarks = a.getMaxMarks();
        } else {
            maxMarks = assignmentChanged.getMaxMarks();
        }

        Assignment assignmentFinalised = new Assignment(assignmentChanged.getTitle(), maxMarks, a.getGradebook());

        versionedSuperTaClient.updateAssignment(t, a, assignmentFinalised);
        indicateSuperTaClientChanged();
    }

    @Override
    public void deleteAssignment(String tgId, String assignment) {
        Optional<TutorialGroup> tg = versionedSuperTaClient.getTutorialGroup(tgId);
        if (!tg.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        TutorialGroup t = tg.get();

        Optional<Assignment> ass = t.getAssignment(new Title(assignment));
        if (!ass.isPresent()) {
            throw new AssignmentNotFoundException();
        }
        Assignment a = ass.get();

        versionedSuperTaClient.deleteAssignment(t, a);
        indicateSuperTaClientChanged();
    }

    @Override
    public void grade(Grade grade) {
        versionedSuperTaClient.grade(grade);
        indicateSuperTaClientChanged();
    }

    @Override
    public void createAttendance(String tgId, Session session) {
        Optional<TutorialGroup> tg = versionedSuperTaClient.getTutorialGroup(tgId);
        if (!tg.isPresent()) {
            throw new TutorialGroupNotFoundException();
        }
        TutorialGroup t = tg.get();
        versionedSuperTaClient.createAttendance(t, session);
        indicateSuperTaClientChanged();
    }

    @Override
    public void markAttendance(String tgId, Session session, Set<StudentId> stIdSet) {
        versionedSuperTaClient.markAttendance(tgId, session, stIdSet);
        indicateSuperTaClientChanged();
    }

    @Override
    public void addFeedback(Feedback feedback, StudentId studentId) {
        versionedSuperTaClient.addFeedback(feedback, studentId);
        indicateSuperTaClientChanged();
    }

    @Override
    public List<Feedback> viewFeedback(StudentId studentId) {
        return versionedSuperTaClient.viewFeedback(studentId);
    }

    @Override
    public boolean hasTutorialGroup(String id) {
        return versionedSuperTaClient.hasTutorialGroup(id);
    }

    @Override
    public Optional<TutorialGroup> getTutorialGroup(String id) {
        return versionedSuperTaClient.getTutorialGroup(id);
    }

    @Override
    public void updateTutorialGroup(TutorialGroup edited) {
        versionedSuperTaClient.updateTutorialGroup(edited);
        indicateSuperTaClientChanged();
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedSuperTaClient}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return FXCollections.unmodifiableObservableList(filteredStudents);
    }

    @Override
    public ObservableList<TutorialGroup> getTutorialGroupList() {
        return FXCollections.unmodifiableObservableList(tutorialGroups);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoSuperTaClient() {
        return versionedSuperTaClient.canUndo();
    }

    @Override
    public boolean canRedoSuperTaClient() {
        return versionedSuperTaClient.canRedo();
    }

    @Override
    public void undoSuperTaClient() {
        versionedSuperTaClient.undo();
        indicateSuperTaClientChanged();
    }

    @Override
    public void redoSuperTaClient() {
        versionedSuperTaClient.redo();
        indicateSuperTaClientChanged();
    }

    @Override
    public void commitSuperTaClient() {
        versionedSuperTaClient.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedSuperTaClient.equals(other.versionedSuperTaClient)
                && filteredStudents.equals(other.filteredStudents);
    }

}
