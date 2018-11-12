package seedu.superta.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.superta.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.superta.logic.commands.CreateAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE;
import static seedu.superta.logic.commands.CreateAttendanceCommand.MESSAGE_INVALID_TUTORIAL_GROUP;
import static seedu.superta.logic.commands.CreateAttendanceCommand.MESSAGE_SUCCESS;
import static seedu.superta.testutil.TypicalSuperTaClient.getTypicalSuperTaClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.ModelManager;
import seedu.superta.model.UserPrefs;
import seedu.superta.model.attendance.Session;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.testutil.TutorialGroupBuilder;
import seedu.superta.ui.testutil.EventsCollectorRule;

// @@author triger15
public class CreateAttendanceCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final String typicalSessionName = "W4 Tutorial";
    private final TutorialGroup typicalTg = new TutorialGroupBuilder().build();

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        model.addTutorialGroup(typicalTg);
    }

    @Test
    public void constructor_nullTutorialGroupId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateAttendanceCommand(null, new Session(typicalSessionName));
    }

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateAttendanceCommand(typicalTg.getId(), null);
    }

    @Test
    public void execute_attendanceAcceptedByModel_createdSuccessful() {
        CreateAttendanceCommand attendanceCommand = new CreateAttendanceCommand(typicalTg.getId(),
                new Session(typicalSessionName));

        String expectedMessage = String.format(MESSAGE_SUCCESS, typicalSessionName);

        Model expectedModel = new ModelManager(getTypicalSuperTaClient(), new UserPrefs());
        expectedModel.addTutorialGroup(new TutorialGroupBuilder().build());
        expectedModel.createAttendance(typicalTg.getId(), new Session(typicalSessionName));
        expectedModel.commitSuperTaClient();

        assertCommandSuccess(attendanceCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_createAttendanceInInvalidTutorialGroup_unsuccessful() {
        CreateAttendanceCommand attendanceCommand = new CreateAttendanceCommand("invalid_tg",
                new Session(typicalSessionName));

        assertCommandFailure(attendanceCommand, model, commandHistory, MESSAGE_INVALID_TUTORIAL_GROUP);
    }

    @Test
    public void execute_duplicateAttendanceAddToTutorialGroup_unsuccessful() throws Exception {
        model.createAttendance(typicalTg.getId(), new Session(typicalSessionName));
        CreateAttendanceCommand attendanceCommand = new CreateAttendanceCommand(typicalTg.getId(),
                new Session(typicalSessionName));

        assertCommandFailure(attendanceCommand, model, commandHistory, MESSAGE_DUPLICATE_ATTENDANCE);
    }

    @Test
    public void equals() {
        final Session typicalSession = new Session(typicalSessionName);
        final CreateAttendanceCommand standardCommand = new CreateAttendanceCommand(typicalTg.getId(),
                typicalSession);
        // same values -> returns true
        CreateAttendanceCommand commandWithSameValues = new CreateAttendanceCommand(typicalTg.getId(),
                new Session(typicalSessionName));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different tutorial group -> returns false
        assertFalse(standardCommand.equals(new CreateAttendanceCommand("02a", typicalSession)));
        // different session name -> returns false
        assertFalse(standardCommand.equals(new CreateAttendanceCommand(typicalTg.getId(),
                new Session("W4 Lab"))));
    }
}
