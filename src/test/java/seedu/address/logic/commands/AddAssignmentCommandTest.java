package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyArchiveList;
import seedu.address.model.ReadOnlyAssignmentList;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.model.project.Assignment;
import seedu.address.testutil.AssignmentBuilder;

public class AddAssignmentCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAssignmentCommand(null);
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        AddAssignmentCommandTest.ModelStubAcceptingAssignmentAdded modelStub =
                new AddAssignmentCommandTest.ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = new AssignmentBuilder().build();

        CommandResult commandResult = new AddAssignmentCommand(validAssignment).runBody(modelStub, commandHistory);

        assertEquals(String.format(AddAssignmentCommand.MESSAGE_SUCCESS, validAssignment),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAssignment), modelStub.assignmentsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    /*@Test
    public void execute_duplicateAssignment_throwsCommandException() throws Exception {
        Assignment validAssignment = new AssignmentBuilder().build();
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(validAssignment);
        AddAssignmentCommandTest.ModelStub modelStub =
                new AddAssignmentCommandTest.ModelStubWithAssignment(validAssignment);

        thrown.expect(DuplicateAssignmentException.class);
        thrown.expectMessage(AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
        addAssignmentCommand.execute(modelStub, commandHistory);
    }*/

    @Test
    public void equals() {
        Assignment oasis = new AssignmentBuilder().withAssignmentName("OASIS").build();
        Assignment falcon = new AssignmentBuilder().withAssignmentName("FALCON").build();
        AddAssignmentCommand addOasisCommand = new AddAssignmentCommand(oasis);
        AddAssignmentCommand addFalconCommand = new AddAssignmentCommand(falcon);

        // same object -> returns true
        assertTrue(addOasisCommand.equals(addOasisCommand));

        // same values -> returns true
        AddAssignmentCommand addOasisCommandCopy = new AddAssignmentCommand(oasis);
        assertTrue(addOasisCommand.equals(addOasisCommandCopy));

        // different types -> returns false
        assertFalse(addOasisCommand.equals(1));

        // null -> returns false
        assertFalse(addOasisCommand.equals(null));

        // different person -> returns false
        assertFalse(addOasisCommand.equals(addFalconCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getState() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setState(int state) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean alreadyContainsUsername(String username, Person ignore) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetArchive(ReadOnlyArchiveList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAssignmentList getAssignmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyArchiveList getArchiveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFromArchive(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restorePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public User getLoggedInUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoggedInUser(User u) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getArchivedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateArchivedPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<LeaveApplicationWithEmployee> getFilteredLeaveApplicationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLeaveApplicationList(Predicate<LeaveApplicationWithEmployee> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLeaveApplicationListForPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assignment> getFilteredAssignmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAssignmentListForPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoArchiveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoArchiveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoArchiveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoArchiveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(Assignment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAssignment(Assignment target, Assignment editedAssignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean containsAssignment(String newAssignment, Assignment ignore) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restartAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithAssignment extends AddAssignmentCommandTest.ModelStub {
        private final Assignment assignment;

        ModelStubWithAssignment(Assignment assignment) {
            requireNonNull(assignment);
            this.assignment = assignment;
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return this.assignment.isSameAssignment(assignment);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends AddAssignmentCommandTest.ModelStub {
        final ArrayList<Assignment> assignmentsAdded = new ArrayList<>();

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return assignmentsAdded.stream().anyMatch(assignment::isSameAssignment);
        }

        @Override
        public void addAssignment(Assignment assignment) {
            requireNonNull(assignment);
            assignmentsAdded.add(assignment);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
