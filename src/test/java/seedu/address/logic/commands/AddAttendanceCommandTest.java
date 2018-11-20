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
import seedu.address.model.attendance.Attendance;
import seedu.address.testutil.AttendanceBuilder;

public class AddAttendanceCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullAttendance_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddAttendanceCommand(null);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Attendance validAttendance = new AttendanceBuilder().build();
        try {
            new AddAttendanceCommand(validAttendance).execute(null, EMPTY_COMMAND_HISTORY);
        } catch (CommandException e) {
            throw new AssertionError("This assertion error should not be thrown.");
        }
    }

    @Test
    public void execute_duplicateAttendance_throwsCommandException() throws Exception {
        Attendance validAttendance = new AttendanceBuilder().build();
        AddAttendanceCommand addAttendanceCommand = new AddAttendanceCommand(validAttendance);
        ModelStub modelStub = new AddAttendanceCommandTest.ModelStubWithAttendance(validAttendance);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE);
        addAttendanceCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_attendanceAcceptedByModel_addSuccessful() throws Exception {
        AddAttendanceCommandTest.ModelStubAcceptingAttendanceAdded modelStub =
                new AddAttendanceCommandTest.ModelStubAcceptingAttendanceAdded();
        Attendance validAttendance = new AttendanceBuilder().build();

        CommandResult commandResult = new AddAttendanceCommand(validAttendance).execute(modelStub, commandHistory);

        assertEquals(String.format(AddAttendanceCommand.MESSAGE_SUCCESS, validAttendance),
                commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAttendance), modelStub.attendancesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Attendance lessonA = new AttendanceBuilder().withName("Lesson A").build();
        Attendance lessonB = new AttendanceBuilder().withName("Lesson B").build();
        AddAttendanceCommand addACommand = new AddAttendanceCommand(lessonA);
        AddAttendanceCommand addBCommand = new AddAttendanceCommand(lessonB);

        // same object -> returns true
        assertTrue(addACommand.equals(addACommand));

        // same values -> returns true
        AddAttendanceCommand addACommandCopy = new AddAttendanceCommand(lessonA);
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
    private class ModelStubWithAttendance extends ModelStub {
        private final Attendance attendance;

        ModelStubWithAttendance(Attendance attendance) {
            requireNonNull(attendance);
            this.attendance = attendance;
        }

        @Override
        public boolean hasAttendance(Attendance attendance) {
            requireNonNull(attendance);
            return this.attendance.isSameAttendance(attendance);
        }
    }

    /**
     * A Model stub that always accept the assignment being added.
     */
    private class ModelStubAcceptingAttendanceAdded extends ModelStub {
        final ArrayList<Attendance> attendancesAdded = new ArrayList<>();

        @Override
        public boolean hasAttendance(Attendance attendance) {
            requireNonNull(attendance);
            return attendancesAdded.stream().anyMatch(attendance::isSameAttendance);
        }

        @Override
        public void addAttendance(Attendance attendance) {
            requireNonNull(attendance);
            attendancesAdded.add(attendance);
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
        public ObservableList<Attendance> getFilteredAttendanceList() {
            return FXCollections.observableList(attendancesAdded);
        }
    }
}

