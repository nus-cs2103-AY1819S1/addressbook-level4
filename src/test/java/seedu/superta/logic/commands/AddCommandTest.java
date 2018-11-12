package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Grade;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.StudentBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new AddCommand(validStudent).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validStudent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validStudent), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Student validStudent = new StudentBuilder().build();
        AddCommand addCommand = new AddCommand(validStudent);
        ModelStub modelStub = new ModelStubWithPerson(validStudent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_STUDENT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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

        @Override
        public List<Feedback> viewFeedback(StudentId studentId) {
            return Collections.emptyList();
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
     * A Model stub that contains a single student.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Student student;

        ModelStubWithPerson(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Student> personsAdded = new ArrayList<>();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return personsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            personsAdded.add(student);
        }

        @Override
        public void commitSuperTaClient() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlySuperTaClient getSuperTaClient() {
            return new SuperTaClient();
        }
    }

}
