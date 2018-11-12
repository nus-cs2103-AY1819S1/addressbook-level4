package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.Assert.assertEquals;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_ID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.AssignmentBuilder;

public class CreateAssignmentCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateAssignmentCommand(null, null);
    }

    @Test
    public void execute_assignmentAcceptedByModel_createdSuccessful() throws Exception {
        ModelStubAcceptingAssignmentAdded modelStub = new ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = new AssignmentBuilder().build();

        CommandResult commandResult = new CreateAssignmentCommand(VALID_TUTORIAL_GROUP_ID, validAssignment)
            .execute(modelStub, commandHistory);

        assertEquals(String.format(CreateAssignmentCommand.MESSAGE_SUCCESS, validAssignment),
            commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAssignment), modelStub.assignmentsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlySuperTaClient newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySuperTaClient getSuperTaClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudentWithIdentity(Student student) {
            return false;
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutorialGroup(TutorialGroup tutorialGroup) {

        }

        @Override
        public void addStudentToTutorialGroup(String tgId, StudentId studentId) {

        }

        @Override
        public void removeStudentFromTutorialGroup(String tgId, StudentId studentId) {

        }

        @Override
        public void deleteTutorialGroup(TutorialGroup tutorialGroup) {

        }

        @Override
        public void addAssignment(String tgId, Assignment assignment) {

        }

        @Override
        public void updateAssignment(String tgId, Title assignmentToChange, Assignment assignmentChanged) {

        }

        @Override
        public void deleteAssignment(String tgId, String assignment) {

        }

        @Override
        public void grade(Grade grade) {

        }

        @Override
        public void createAttendance(String tgId, Session session) {

        }

        @Override
        public void markAttendance(String tgId, Session session, Set<StudentId> stIdSet) {

        }

        @Override
        public boolean hasTutorialGroup(String id) {
            return false;
        }

        @Override
        public Optional<TutorialGroup> getTutorialGroup(String id) {
            return Optional.empty();
        }

        @Override
        public void updateTutorialGroup(TutorialGroup edited) {

        }

        @Override
        public void addFeedback(Feedback feedback, StudentId studentId) {

        }

        public List<Feedback> viewFeedback(StudentId studentId) {
            return null;
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<TutorialGroup> getTutorialGroupList() {
            return null;
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoSuperTaClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoSuperTaClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoSuperTaClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoSuperTaClient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitSuperTaClient() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithAssignment extends ModelStub {
        private final Assignment assignment;

        ModelStubWithAssignment(Assignment assignment) {
            requireNonNull(assignment);
            this.assignment = assignment;
        }

    }

    /**
     * A Model stub that always accept the assignment being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends ModelStub {
        final ArrayList<Assignment> assignmentsAdded = new ArrayList<>();

        @Override
        public void addAssignment(String tdId, Assignment assignment) {
            requireNonNull(assignment);
            assignmentsAdded.add(assignment);
        }

        @Override
        public void commitSuperTaClient() {
            // called by {@code AddCommand#execute()}
        }

    }
}
