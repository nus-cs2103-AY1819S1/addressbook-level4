package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;

public class AddAssignmentCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAssignmentCommand(null);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Assignment validAssignment = new AssignmentBuilder().build();
        try {
            new AddAssignmentCommand(validAssignment).execute(null, EMPTY_COMMAND_HISTORY);
        } catch (CommandException e) {
            throw new AssertionError("This assertion error should not be thrown.");
        }
    }

    @Test
    public void execute_duplicateAssignment_throwsCommandException() throws Exception {
        Assignment validAssignment = new AssignmentBuilder().build();
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(validAssignment);
        ModelStub modelStub = new AddAssignmentCommandTest.ModelStubWithAssignment(validAssignment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT);
        addAssignmentCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        AddAssignmentCommandTest.ModelStubAcceptingAssignmentAdded modelStub =
                new AddAssignmentCommandTest.ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = new AssignmentBuilder().build();

        CommandResult commandResult = new AddAssignmentCommand(validAssignment).execute(modelStub, commandHistory);

        assertEquals(String.format(AddAssignmentCommand.MESSAGE_SUCCESS, validAssignment),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAssignment), modelStub.assignmentsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Assignment assignmentA = new AssignmentBuilder().withName("Assignment A").build();
        Assignment assignmentB = new AssignmentBuilder().withName("Assignment B").build();
        AddAssignmentCommand addACommand = new AddAssignmentCommand(assignmentA);
        AddAssignmentCommand addBCommand = new AddAssignmentCommand(assignmentB);

        // same object -> returns true
        assertTrue(addACommand.equals(addACommand));

        // same values -> returns true
        AddAssignmentCommand addACommandCopy = new AddAssignmentCommand(assignmentA);
        assertTrue(addACommand.equals(addACommandCopy));

        // different types -> returns false
        assertFalse(addACommand.equals(1));

        // null -> returns false
        assertFalse(addACommand.equals(null));

        // different assignment -> returns false
        assertFalse(addACommand.equals(addBCommand));
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

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return this.assignment.isSameAssignment(assignment);
        }
    }

    /**
     * A Model stub that always accept the assignment being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends ModelStub {
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
            // called by {@code AddAssignmentCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ObservableList<Assignment> getFilteredAssignmentList() {
            return FXCollections.observableList(assignmentsAdded);
        }
    }
}
