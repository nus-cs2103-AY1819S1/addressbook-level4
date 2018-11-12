package seedu.superta.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.logic.commands.MarkAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE;
import static seedu.superta.logic.commands.MarkAttendanceCommand.MESSAGE_INVALID_STUDENTS;
import static seedu.superta.logic.commands.MarkAttendanceCommand.MESSAGE_INVALID_TUTORIAL_GROUP;
import static seedu.superta.logic.commands.MarkAttendanceCommand.MESSAGE_SUCCESS;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.TutorialGroupBuilder;
import seedu.superta.ui.testutil.EventsCollectorRule;

// @@author triger15
public class MarkAttendanceCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Session typicalSession = new Session("W4 Tutorial");
    private final StudentId alice = new StudentId("A0166733Y");
    private final StudentId benson = new StudentId("A1234567Y");
    private final TutorialGroup typicalTg = new TutorialGroupBuilder().build();
    private Set<StudentId> idSet;

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        model.addTutorialGroup(typicalTg);
        model.addStudentToTutorialGroup(typicalTg.getId(), alice);
        model.createAttendance(typicalTg.getId(), typicalSession);
        idSet = new HashSet<>();
        idSet.add(alice);
    }

    @Test
    public void constructor_nullTutorialGroupId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new MarkAttendanceCommand(null, typicalSession, idSet);
    }

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new MarkAttendanceCommand(typicalTg.getId(), null, idSet);
    }

    @Test
    public void constructor_nullStudentIdSet_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new MarkAttendanceCommand(typicalTg.getId(), typicalSession, null);
    }

    @Test
    public void execute_attendanceAcceptedByModel_markedSuccessful() {
        MarkAttendanceCommand attendanceCommand = new MarkAttendanceCommand(typicalTg.getId(),
                typicalSession, idSet);

        String expectedMessage = String.format(MESSAGE_SUCCESS, idSet);

        Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        expectedModel.addTutorialGroup(new TutorialGroupBuilder().build());
        expectedModel.addStudentToTutorialGroup(typicalTg.getId(), alice);
        expectedModel.createAttendance(typicalTg.getId(), new Session(typicalSession));
        expectedModel.markAttendance(typicalTg.getId(), typicalSession, idSet);
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(attendanceCommand, model, commandHistory, expectedMessage, expectedModel);
    }


    @Test
    public void execute_markAttendanceInInvalidTutorialGroup_unsuccessful() {
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand("invalid_tg",
                typicalSession, idSet);

        assertCommandFailure(markAttendanceCommand, model, commandHistory, MESSAGE_INVALID_TUTORIAL_GROUP);
    }

    @Test
    public void execute_markAttendanceInvalidSession_unsuccessful() {
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(typicalTg.getId(),
                new Session("invalid_session"), idSet);
        assertCommandFailure(markAttendanceCommand, model, commandHistory, "Attendance session not found.");
    }

    @Test
    public void execute_markAttendanceInvalidStudents_unsuccessful() {
        Set<StudentId> invalidSet = new HashSet<>();
        invalidSet.add(new StudentId("A0123456N"));
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(typicalTg.getId(), typicalSession,
                invalidSet);
        assertCommandFailure(markAttendanceCommand, model, commandHistory, MESSAGE_INVALID_STUDENTS);
    }

    @Test
    public void execute_markAttendanceDuplicateMarking_unsuccessful() {
        MarkAttendanceCommand attendanceCommand = new MarkAttendanceCommand(typicalTg.getId(),
            typicalSession, idSet);

        Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        expectedModel.addTutorialGroup(new TutorialGroupBuilder().build());
        expectedModel.addStudentToTutorialGroup(typicalTg.getId(), alice);
        expectedModel.createAttendance(typicalTg.getId(), typicalSession);
        expectedModel.markAttendance(typicalTg.getId(), typicalSession, idSet);
        expectedModel.commitSuperTaClient();

        assertCommandFailure(attendanceCommand, model, commandHistory, MESSAGE_DUPLICATE_ATTENDANCE);
    }

    @Test
    public void equals() {
        final MarkAttendanceCommand standardCommand = new MarkAttendanceCommand(typicalTg.getId(),
                typicalSession, idSet);
        // same values -> returns true
        MarkAttendanceCommand commandWithSameValues = new MarkAttendanceCommand(typicalTg.getId(),
                typicalSession, idSet);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different tutorial group -> returns false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand("02a", typicalSession, idSet)));
        // different session name -> returns false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(typicalTg.getId(),
                new Session("W4 Lab"), idSet)));
        // different student id set -> returns false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(typicalTg.getId(),
                typicalSession, new HashSet<StudentId>())));
    }
}
